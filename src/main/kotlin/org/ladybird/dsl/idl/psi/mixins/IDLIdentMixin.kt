package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import org.ladybird.dsl.idl.IDLFile
import org.ladybird.dsl.idl.IDLResolver
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLIdent
import org.ladybird.dsl.idl.psi.singleRef

abstract class IDLIdentMixin(node: ASTNode) : IDLNamedElement(node), IDLIdent {
    override fun getReference() = singleRef {
        IDLResolver(containingFile as IDLFile).resolveIdent(this)
    }
}
