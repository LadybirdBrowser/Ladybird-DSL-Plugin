package org.ladybird.dsl.ipc.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.ladybird.dsl.ipc.IPCTypes

abstract class IPCNamedElement(node: ASTNode) : ASTWrapperPsiElement(node), IPCNameIdentifierOwner {
    override fun getNameIdentifier(): PsiElement? = findChildByType(IPCTypes.IDENTIFIER)

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement = apply {
        nameIdentifier?.replace(IPCPsiFactory(project).createIdentifier(name))
    }

    override fun getTextOffset() = nameIdentifier?.textOffset ?: super.getTextOffset()
}
