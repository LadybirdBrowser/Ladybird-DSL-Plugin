package org.ladybird.dsl.ipc

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet

class IPCParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?) = IPCLexerAdapter()
    override fun createParser(project: Project?) = IPCParser()

    override fun getFileNodeType() = IPCFileStub.Type

    override fun createElement(node: ASTNode?): PsiElement = IPCTypes.Factory.createElement(node)
    override fun createFile(viewProvider: FileViewProvider) = IPCFile(viewProvider)

    override fun getWhitespaceTokens() = WHITE_SPACES
    override fun getCommentTokens() = COMMENTS
    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    companion object {
        private val WHITE_SPACES = TokenSet.create(IPCLexerBase.SPACE)
        private val COMMENTS = TokenSet.create(IPCTypes.COMMENT)
    }
}
