package org.ladybird.dsl.ipc

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import org.ladybird.dsl.ipc.IPCTypes.*

object Highlights {
    val COMMENT = Default.LINE_COMMENT.extend("COMMENT")
    val IDENTIFIER = Default.IDENTIFIER.extend("IDENTIFIER")

    val KEYWORD = Default.KEYWORD.extend("KEYWORD")
    val INCLUDE_PATH = Default.STRING.extend("INCLUDE_PATH")

    val PARENS = Default.PARENTHESES.extend("PARENTHESIS")
    val BRACES = Default.BRACES.extend("BRACES")
    val BRACKETS = Default.BRACKETS.extend("BRACKETS")

    val COMMA = Default.COMMA.extend("COMMA")
    val OPERATOR = Default.OPERATION_SIGN.extend("OPERATOR")
    val SYNC_ARROW = OPERATOR.extend("SYNC_ARROW")
    val ASYNC_ARROW = OPERATOR.extend("ASYNC_ARROW")
    val NAMESPACE = OPERATOR.extend("NAMESPACE")
    val ANGLE_BRACKETS = OPERATOR.extend("ANGLE_BRACKETS")

    val PRIMITIVE_TYPE = KEYWORD.extend("PRIMITIVE_TYPE")
    val CLASS_TYPE = Default.CLASS_NAME.extend("CLASS_TYPE")

    val ATTRIBUTE_NAME = Default.INSTANCE_FIELD.extend("ATTRIBUTE_NAME")
    val ENDPOINT_NAME = Default.CLASS_NAME.extend("ENDPOINT_NAME")
    val FUNCTION_NAME = Default.FUNCTION_DECLARATION.extend("FUNCTION_NAME")
    val NAMESPACE_NAME = Default.CLASS_NAME.extend("NAMESPACE_NAME")
    val PARAMETER_NAME = Default.PARAMETER.extend("PARAMETER_NAME")

    private fun TextAttributesKey.extend(name: String) = TextAttributesKey.createTextAttributesKey("IPC_$name", this)
}

class IPCSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer() = IPCLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            COMMENT -> Highlights.COMMENT
            IDENTIFIER -> Highlights.IDENTIFIER
            INCLUDE_IDENTIFIER -> Highlights.INCLUDE_PATH

            KEYWORD_ENDPOINT,
            KEYWORD_INCLUDE -> Highlights.KEYWORD

            OPEN_CURLY, CLOSE_CURLY -> Highlights.BRACES
            OPEN_BRACKET, CLOSE_BRACKET -> Highlights.BRACKETS
            OPEN_PAREN, CLOSE_PAREN -> Highlights.PARENS
            OPEN_ANGLE, CLOSE_ANGLE -> Highlights.ANGLE_BRACKETS

            COMMA -> Highlights.COMMA
            SYNC_ARROW -> Highlights.SYNC_ARROW
            ASYNC_ARROW -> Highlights.ASYNC_ARROW
            NAMESPACE -> Highlights.NAMESPACE

            QUOTE -> return emptyArray()

            else -> {
                if (tokenType is IPCToken)
                    println("No token highlighting defined for element $tokenType!")
                return emptyArray()
            }
        }.let { arrayOf(it) }
    }
}

class IPCSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?) = IPCSyntaxHighlighter()
}
