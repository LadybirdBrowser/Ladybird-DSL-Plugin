package org.ladybird.dsl.ipc

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScopes
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.cidr.lang.OCFileType
import com.jetbrains.cidr.lang.psi.OCFile
import com.jetbrains.cidr.lang.psi.OCStructLike
import org.ladybird.dsl.ipc.project.ipcProject
import org.ladybird.dsl.ipc.psi.api.IPCEndpoint

class IPCLineMarkerProvider : LineMarkerProvider {
    override fun collectSlowLineMarkers(
        elements: MutableList<out PsiElement>,
        result: MutableCollection<in LineMarkerInfo<*>>
    ) {
        // C++ is considered "slow" as opposed to IPC lookup, which is considered "fast",
        // because there are thousands of C++ files and only a handful (~35) of IPC files

        val ipcElements = elements.filterIsInstance<IPCEndpoint>()
        val project = ipcElements.firstOrNull()?.project ?: return
        val ipcProject = project.ipcProject
        val psiManager = PsiManager.getInstance(project)
        val roots = ipcProject.getProjectRootDirectories()
        val scope = GlobalSearchScopes.directoriesScope(project, true, *roots.toTypedArray())

        val remainingEndpoints = ipcElements.associateByTo(mutableMapOf()) { it.identifier.text }
        FileTypeIndex.processFiles(OCFileType.INSTANCE, {
            // Only look in header files, this speeds it up a bit
            if (it.extension != "h") return@processFiles true

            val ocFile = psiManager.findFile(it) as? OCFile ?: return@processFiles true
            val structs = PsiTreeUtil.findChildrenOfType(ocFile, OCStructLike::class.java)

            for (struct in structs) {
                val endpointNames = ipcProject.findEndpointNameFromStruct(struct) ?: continue
                for (name in endpointNames) {
                    val removed = remainingEndpoints.remove(name)
                    if (removed != null) {
                        NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
                            .setTarget(struct)
                            .setTooltipText("Jump to C++ endpoint implementation")
                            .createLineMarkerInfo(removed.identifier)
                            .let(result::add)
                    }
                }
            }

            remainingEndpoints.isNotEmpty()
        }, scope)
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        val ipcProject = element.ipcProject

        if (element is OCStructLike) {
            val endpoints = ipcProject.findEndpointNameFromStruct(element)?.map(ipcProject::findIPCEndpoint)
                ?: return null

            if (endpoints.isNotEmpty()) {
                return NavigationGutterIconBuilder.create(IPCLanguage.FILE_ICON_FOR_MARKER)
                    .setTargets(endpoints)
                    .setTooltipText("Jump to IPC endpoint declaration")
                    .createLineMarkerInfo(element.nameIdentifier ?: return null)
            }
        }

        return null
    }
}
