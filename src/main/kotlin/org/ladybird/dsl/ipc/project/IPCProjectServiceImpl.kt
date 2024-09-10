package org.ladybird.dsl.ipc.project

import com.intellij.openapi.project.BaseProjectDirectories.Companion.getBaseDirectories
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScopes
import com.jetbrains.cidr.lang.psi.OCFile
import com.jetbrains.cidr.lang.psi.OCStructLike
import org.ladybird.dsl.common.findChildOfType
import org.ladybird.dsl.ipc.IPCFile
import org.ladybird.dsl.ipc.IPCFileType
import org.ladybird.dsl.ipc.psi.api.IPCEndpoint
import java.nio.file.Path

class IPCProjectServiceImpl(private val project: Project) : IPCProjectService {
    private var includeRoots: List<VirtualFile>? = null

    override fun resolveImportedFile(path: Path): OCFile? {
        if (DumbService.isDumb(project))
            return null

        for (root in getProjectRootDirectories()) {
            val file = path.fold(root as VirtualFile?) { f, name -> f?.findChild(name.toString()) } ?: continue
            return PsiManager.getInstance(project).findFile(file) as? OCFile ?: continue
        }

        return null
    }

    override fun findIPCEndpoint(name: String): IPCEndpoint? {
        if (DumbService.isDumb(project))
            return null

        val psiManager = PsiManager.getInstance(project)
        val scope = GlobalSearchScopes.directoriesScope(project, true, *getProjectRootDirectories().toTypedArray())
        var matchingEndpoint: IPCEndpoint? = null

        FileTypeIndex.processFiles(IPCFileType, {
            val ipcFile = psiManager.findFile(it) as? IPCFile ?: return@processFiles true
            val endpoint = ipcFile.findChildOfType<IPCEndpoint>() ?: return@processFiles true

            if (endpoint.identifier.text == name) {
                matchingEndpoint = endpoint
                return@processFiles false
            }

            true
        }, scope)

        return matchingEndpoint
    }

    override fun findEndpointNameFromStruct(struct: OCStructLike): List<String>? {
        for (base in struct.baseClausesList?.baseClauses.orEmpty()) {
            val baseText = base.text
            if (!baseText.startsWith("public IPC::ConnectionFromClient")
                && !baseText.startsWith("public IPC::ConnectionToServer")
            ) {
                continue
            }

            val templateArgs = baseText.substringAfter('<').substringBefore('>').split(',').map(String::trim)
            return templateArgs.map {
                it.removeSuffix("Endpoint")
            }
        }

        return null
    }

    override fun getProjectRootDirectories(): List<VirtualFile> {
        if (includeRoots == null) {
            val base = project.getBaseDirectories().singleOrNull() ?: return emptyList()
            val userland = base.findChild("Userland")
            includeRoots = listOfNotNull(
                base,
                base.findChild("AK"),
                userland,
                userland?.findChild("Libraries"),
                userland?.findChild("Services"),
            )
        }

        return includeRoots!!
    }
}
