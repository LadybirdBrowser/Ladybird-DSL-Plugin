package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLCallback

abstract class IDLCallbackMixin(node: ASTNode) : IDLNamedElement(node), IDLCallback, IDLDeclaration {
    override fun getNameIdentifier() = identifier
}
