package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLInterfaceMixin

abstract class IDLInterfaceMixinMixin(node: ASTNode) : IDLNamedElement(node), IDLInterfaceMixin, IDLDeclaration {
    override fun getNameIdentifier() = identifier
}
