package org.ladybird.dsl.ipc.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import org.ladybird.dsl.common.descendantOfType
import org.ladybird.dsl.ipc.IPCFile
import org.ladybird.dsl.ipc.IPCFileType
import org.ladybird.dsl.ipc.psi.api.IPCEndpoint

class IPCPsiFactory(private val project: Project) {
    private fun createFile(text: String, fileName: String = "dummy.ipc") = PsiFileFactory
        .getInstance(project)
        .createFileFromText(
            fileName,
            IPCFileType,
            text
        ) as IPCFile

    fun createIdentifier(name: String) = createFromText<IPCEndpoint>("endpoint $name {}")?.identifier
        ?: error("Failed to create endpoint")

    private inline fun <reified T : PsiElement> createFromText(text: String): T? = createFile(text).descendantOfType()
}
