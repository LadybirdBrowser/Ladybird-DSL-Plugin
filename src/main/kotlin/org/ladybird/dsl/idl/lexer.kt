package org.ladybird.dsl.idl

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.FlexLexer
import com.intellij.psi.tree.IElementType

class IDLLexerAdapter : FlexAdapter(IDLLexer(null))

class IDLToken(debugName: String) : IElementType(debugName, IDLLanguage) {
    override fun toString() = "IDLToken.${super.toString()}"
}

class IDLElementType(debugName: String) : IElementType(debugName, IDLLanguage)

abstract class IDLLexerBase : FlexLexer {
    companion object {
        @JvmField
        val SPACE = IDLElementType("SPACE")
    }
}
