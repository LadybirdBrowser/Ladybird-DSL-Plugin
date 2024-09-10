package org.ladybird.dsl.idl.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import org.ladybird.dsl.common.descendantOfType
import org.ladybird.dsl.idl.IDLFile
import org.ladybird.dsl.idl.IDLFileType
import org.ladybird.dsl.idl.psi.api.IDLNamespace

class IDLPsiFactory(private val project: Project) {
    private fun createFile(text: String, fileName: String = "dummy.idl") = PsiFileFactory
        .getInstance(project)
        .createFileFromText(
            fileName,
            IDLFileType,
            text,
        ) as IDLFile

    fun createIdentifier(name: String) = createFromText<IDLNamespace>("namespace $name {}")?.identifier
        ?: error("Failed to create namespace")

    private inline fun <reified T : PsiElement> createFromText(text: String): T? = createFile(text).descendantOfType()
}
