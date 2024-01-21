package me.mattco.serenityos.ipc.psi.mixins

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfType
import com.jetbrains.cidr.lang.psi.OCCppNamespace
import com.jetbrains.cidr.lang.psi.OCElement
import com.jetbrains.cidr.lang.psi.OCStructLike
import com.jetbrains.cidr.lang.symbols.OCResolveContext
import me.mattco.serenityos.common.findChildrenOfType
import me.mattco.serenityos.ipc.IPCFile
import me.mattco.serenityos.ipc.psi.IPCNamedElement
import me.mattco.serenityos.ipc.psi.api.IPCInclude
import me.mattco.serenityos.ipc.psi.api.IPCType
import me.mattco.serenityos.ipc.psi.api.IPCTypePart
import me.mattco.serenityos.ipc.psi.multiRef

abstract class IPCTypePartMixin(node: ASTNode) : IPCNamedElement(node), IPCTypePart {
    override fun getReference(): PsiReference? = multiRef { resolveCppDecl() }

    override fun getNameIdentifier(): PsiElement? = identifier

    private fun resolveCppDecl(): List<OCElement> {
        val qualifiedName = parentOfType<IPCType>()?.text?.substring(0, textRangeInParent.endOffset)

        val ipcFile = containingFile as? IPCFile ?: return emptyList()
        val allMatches = mutableListOf<OCElement>()

        for (include in ipcFile.findChildrenOfType<IPCInclude>()) {
            val cppFile = include.resolveFile() ?: continue
            val structs = PsiTreeUtil.findChildrenOfType(cppFile, OCStructLike::class.java)

            for (struct in structs) {
                val symbol = struct.symbol ?: continue
                if (!symbol.isStruct && !symbol.isClass) continue

                val name = symbol.getResolvedQualifiedName(OCResolveContext.forSymbol(symbol, project))
                if (name?.toString() == qualifiedName) {
                    // If we find a struct, this must be the only match
                    return listOf(struct)
                }
            }

            val namespaces = PsiTreeUtil.findChildrenOfType(cppFile, OCCppNamespace::class.java)
            for (namespace in namespaces) {
                val symbol = namespace.symbol ?: continue
                val name = symbol.getResolvedQualifiedName(OCResolveContext.forSymbol(symbol, project))
                if (name?.toString() == qualifiedName)
                    allMatches.add(namespace)
            }
        }

        return allMatches
    }
}
