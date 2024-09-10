package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLEnum

abstract class IDLEnumMixin(node: ASTNode) : IDLNamedElement(node), IDLEnum, IDLDeclaration {
    override fun getNameIdentifier() = identifier
}
