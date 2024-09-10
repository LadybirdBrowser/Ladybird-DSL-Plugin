package org.ladybird.dsl.idl.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.ladybird.dsl.idl.IDLTypes

abstract class IDLNamedElement(node: ASTNode) : ASTWrapperPsiElement(node), IDLNameIdentifierOwner {
    override fun getNameIdentifier(): PsiElement? = findChildByType(IDLTypes.IDENTIFIER)

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement = apply {
        nameIdentifier?.replace(IDLPsiFactory(project).createIdentifier(name))
    }

    override fun getTextOffset() = nameIdentifier?.textOffset ?: super.getTextOffset()
}
