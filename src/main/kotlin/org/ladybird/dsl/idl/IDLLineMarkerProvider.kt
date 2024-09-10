package org.ladybird.dsl.idl

import ai.grazie.utils.dropPrefix
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiElement
import com.intellij.psi.util.descendantsOfType
import com.jetbrains.cidr.lang.psi.OCFunctionDeclaration
import com.jetbrains.cidr.lang.psi.OCStructLike
import org.ladybird.dsl.idl.project.idlProject
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLPsiElement
import org.ladybird.dsl.idl.psi.api.IDLAttributeRest
import org.ladybird.dsl.idl.psi.api.IDLConstructor
import org.ladybird.dsl.idl.psi.api.IDLOperation

class IDLLineMarkerProvider : LineMarkerProvider {
    override fun collectSlowLineMarkers(
        elements: MutableList<out PsiElement>,
        result: MutableCollection<in LineMarkerInfo<*>>
    ) {
        ProgressManager.checkCanceled()

        for (element in elements) {
            if (element !is IDLDeclaration)
                continue

            val cppDecls = element.idlProject.findCppDeclarations(
                element.containingFile.parent?.virtualFile ?: continue,
                element.name ?: continue,
            )

            // TODO: This should probably only ever be a single element
            val decl = cppDecls.firstOrNull() ?: continue
            result.add(
                NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
                    .setTarget(decl)
                    .setTooltipText("Jump to C++ implementation")
                    .createLineMarkerInfo(element.nameIdentifier ?: continue)
            )
        }
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element is OCStructLike) {
            val idlDecls = element.idlProject.findIDLDeclarations(
                element.containingFile.parent?.virtualFile ?: return null,
                element.name ?: return null,
            )

            // TODO: This should probably only ever be a single element
            val member = idlDecls.firstOrNull() ?: return null
            return NavigationGutterIconBuilder.create(IDLLanguage.FILE_ICON)
                .setTarget(member)
                .setTooltipText("Jump to IDL declaration")
                .createLineMarkerInfo(element.nameIdentifier ?: return null)
        }

        // We don't show icons for elements in header files as it's a bit too noisy. We
        // only show an icon for the struct
        if (element is OCFunctionDeclaration && !element.containingOCFile.isHeader) {
            val ns = element.namespaceQualifier?.text ?: (element.parent as? OCStructLike)?.name ?: return null
            val idlDecls =
                element.idlProject.findIDLDeclarations(element.containingFile.parent?.virtualFile ?: return null, ns)
            idlDecls.forEach {
                val member = findIDLMember(it, element.name ?: return@forEach)
                if (member != null) {
                    return NavigationGutterIconBuilder.create(IDLLanguage.FILE_ICON)
                        .setTarget(member)
                        .setTooltipText("Jump to IDL declaration")
                        .createLineMarkerInfo(element.nameIdentifier ?: return null)
                }
            }
        }

        return null
    }

    private fun findIDLMember(declaration: IDLDeclaration, cppName: String): IDLPsiElement? {
        if (cppName == "construct_impl") {
            val constructors = declaration.descendantsOfType<IDLConstructor>()
            // TODO: Resolve different constructors
            return constructors.firstOrNull()
        }

        val operations = declaration.descendantsOfType<IDLOperation>()
        val opName = toTitleCase(cppName)
        for (operation in operations) {
            if (operation.operationName?.text == opName)
                return operation
        }

        val attributes = declaration.descendantsOfType<IDLAttributeRest>()
        val attrName = toTitleCase(cppName.dropPrefix("get_").dropPrefix("set_"))
        for (attribute in attributes) {
            if (attribute.attributeName.text == attrName)
                return attribute
        }

        return null
    }

    companion object {
        fun toTitleCase(name: String) = buildString {
            var capitalize = false
            for (ch in name) {
                when {
                    ch == '_' -> capitalize = true
                    capitalize -> {
                        append(ch.uppercase())
                        capitalize = false
                    }
                    else -> append(ch)
                }
            }
        }
    }
}
