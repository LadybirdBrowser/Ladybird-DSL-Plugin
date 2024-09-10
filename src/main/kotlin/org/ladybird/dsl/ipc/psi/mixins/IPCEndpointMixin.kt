package org.ladybird.dsl.ipc.psi.mixins

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.cidr.lang.OCFileType
import com.jetbrains.cidr.lang.psi.OCElement
import com.jetbrains.cidr.lang.psi.OCFile
import com.jetbrains.cidr.lang.psi.OCStructLike
import com.jetbrains.cidr.lang.search.scopes.OCSearchScope
import org.ladybird.dsl.ipc.project.ipcProject
import org.ladybird.dsl.ipc.psi.IPCNamedElement
import org.ladybird.dsl.ipc.psi.api.IPCEndpoint
import org.ladybird.dsl.ipc.psi.multiRef

abstract class IPCEndpointMixin(node: ASTNode) : IPCNamedElement(node), IPCEndpoint {
    override fun getReference() = multiRef { resolveCppDecl() }

    private fun resolveCppDecl(): List<OCElement> {
        val scope = OCSearchScope.getProjectSourcesScope(project)
        val psiManager = PsiManager.getInstance(project)
        val ipcProject = ipcProject

        // We will only ever resolve to header files. Additionally, IPC files have some naming
        // conventions that we can use to avoid searching thousands of files
        val files = FileTypeIndex.getFiles(OCFileType.INSTANCE, scope)
            .filter {
                val name = it.nameWithoutExtension
                it.extension == "h" && ("Client" in name || "Server" in name || "Connection" in name)
            }

        val matchingEndpoints = mutableListOf<OCStructLike>()

        for (file in files) {
            val cppFile = psiManager.findFile(file) as OCFile
            val structs = PsiTreeUtil.findChildrenOfType(cppFile, OCStructLike::class.java)
            for (struct in structs) {
                if (ipcProject.findEndpointNameFromStruct(struct) != null)
                    matchingEndpoints.add(struct)
            }
        }

        return matchingEndpoints
    }
}
