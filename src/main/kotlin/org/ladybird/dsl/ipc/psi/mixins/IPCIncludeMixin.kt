package org.ladybird.dsl.ipc.psi.mixins

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.jetbrains.cidr.lang.psi.OCFile
import org.ladybird.dsl.ipc.project.ipcProject
import org.ladybird.dsl.ipc.psi.IPCNamedElement
import org.ladybird.dsl.ipc.psi.api.IPCInclude
import org.ladybird.dsl.ipc.psi.singleRef
import kotlin.io.path.Path

abstract class IPCIncludeMixin(node: ASTNode) : IPCNamedElement(node), IPCInclude {
    override fun getReference(): PsiReference? = singleRef(IPCInclude::resolveFile)

    override fun getNameIdentifier() = includePath
}

fun IPCInclude.resolveFile(): OCFile? =
    ipcProject.resolveImportedFile(Path(includePath.text.drop(1).dropLast(1)))
