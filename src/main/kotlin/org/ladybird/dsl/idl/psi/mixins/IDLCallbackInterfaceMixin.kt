package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLCallbackInterface

abstract class IDLCallbackInterfaceMixin(node: ASTNode) : IDLNamedElement(node), IDLCallbackInterface, IDLDeclaration {
    override fun getNameIdentifier() = identifier
}
