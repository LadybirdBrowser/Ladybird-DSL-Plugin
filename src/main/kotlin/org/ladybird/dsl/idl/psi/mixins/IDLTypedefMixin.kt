package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLTypedef

abstract class IDLTypedefMixin(node: ASTNode) : IDLNamedElement(node), IDLTypedef, IDLDeclaration {
    override fun getNameIdentifier() = identifier
}
