package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLDictionary

abstract class IDLDictionaryMixin(node: ASTNode) : IDLNamedElement(node), IDLDictionary, IDLDeclaration {
    override fun getNameIdentifier() = identifier
}
