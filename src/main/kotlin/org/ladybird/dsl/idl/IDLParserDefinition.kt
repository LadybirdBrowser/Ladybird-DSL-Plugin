package org.ladybird.dsl.idl

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet

class IDLParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?) = IDLLexerAdapter()
    override fun createParser(project: Project?) = IDLParser()

    override fun getFileNodeType() = IDLFileStub.Type

    override fun createElement(node: ASTNode?): PsiElement = IDLTypes.Factory.createElement(node)
    override fun createFile(viewProvider: FileViewProvider) = IDLFile(viewProvider)

    override fun getWhitespaceTokens() = WHITE_SPACES
    override fun getCommentTokens() = COMMENTS
    override fun getStringLiteralElements() = STRING_LITERAL

    companion object {
        private val WHITE_SPACES = TokenSet.create(IDLLexerBase.SPACE)
        private val COMMENTS = TokenSet.create(IDLTypes.COMMENT)
        private val STRING_LITERAL = TokenSet.create(IDLTypes.STRING)
    }
}
