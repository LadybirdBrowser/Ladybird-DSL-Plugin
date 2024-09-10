package org.ladybird.dsl.ipc.project

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.jetbrains.cidr.lang.psi.OCFile
import com.jetbrains.cidr.lang.psi.OCStructLike
import org.ladybird.dsl.ipc.psi.api.IPCEndpoint
import java.nio.file.Path

interface IPCProjectService {
    fun resolveImportedFile(path: Path): OCFile?

    fun findIPCEndpoint(name: String): IPCEndpoint?

    fun findEndpointNameFromStruct(struct: OCStructLike): List<String>?

    fun getProjectRootDirectories(): List<VirtualFile>
}

val Project.ipcProject: IPCProjectService
    get() = service()

val PsiElement.ipcProject: IPCProjectService
    get() = project.ipcProject
