package org.ladybird.dsl.ipc

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.FlexLexer
import com.intellij.psi.tree.IElementType

class IPCLexerAdapter : FlexAdapter(IPCLexer(null))

class IPCToken(debugName: String) : IElementType(debugName, IPCLanguage) {
    override fun toString() = "IPCToken.${super.toString()}"
}

class IPCElementType(debugName: String) : IElementType(debugName, IPCLanguage)

abstract class IPCLexerBase : FlexLexer {
    companion object {
        @JvmField
        val SPACE = IPCElementType("SPACE")
    }
}
