package org.ladybird.dsl.idl

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import org.ladybird.dsl.idl.IDLTypes.*
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

object Highlights {
    val COMMENT = Default.LINE_COMMENT.extend("COMMENT")

    val IDENTIFIER = Default.IDENTIFIER.extend("IDENTIFIER")
    val NUMBER = Default.NUMBER.extend("NUMBER")
    val STRING = Default.STRING.extend("STRING")
    val BOOLEAN = Default.NUMBER.extend("BOOLEAN")

    val KEYWORD = Default.KEYWORD.extend("KEYWORD")
    val UNDEFINED = KEYWORD.extend("UNDEFINED")
    val NULL = KEYWORD.extend("NULL")
    val IMPORT_PATH = Default.STRING.extend("IMPORT_PATH")

    val PARENS = Default.PARENTHESES.extend("PARENTHESIS")
    val BRACES = Default.BRACES.extend("BRACES")
    val BRACKETS = Default.BRACKETS.extend("BRACKETS")
    val ANGLE_BRACKETS = Default.BRACKETS.extend("ANGLE_BRACKETS")

    val OPERATOR = Default.OPERATION_SIGN.extend("OPERATOR")
    val QUESTION_MARK = OPERATOR.extend("QUESTION_MARK")
    val COLON = OPERATOR.extend("COLON")
    val SEMICOLON = Default.SEMICOLON.extend("SEMICOLON")
    val EQUALS = OPERATOR.extend("EQUALS")
    val COMMA = Default.COMMA.extend("COMMA")
    val DASH = OPERATOR.extend("DASH")
    val TRIPLE_DOT = OPERATOR.extend("TRIPLE_DOT")
    val DOT = OPERATOR.extend("DOT")
    val ASTERISK = OPERATOR.extend("ASTERISK")

    val PRIMITIVE_TYPE = KEYWORD.extend("PRIMITIVE_TYPE")
    val PLATFORM_TYPE = Default.CLASS_NAME.extend("PLATFORM_TYPE")

    val ATTRIBUTE_NAME = Default.INSTANCE_FIELD.extend("ATTRIBUTE_NAME")
    val EXTENDED_ATTRIBUTE_NAME = Default.MARKUP_ENTITY.extend("EXTENDED_ATTRIBUTE_NAME")

    val INTERFACE_NAME = Default.INTERFACE_NAME.extend("INTERFACE_NAME")
    val DICTIONARY_NAME = Default.CLASS_NAME.extend("DICTIONARY_NAME")
    val ENUM_NAME = Default.CLASS_NAME.extend("ENUM_NAME")
    val FUNCTION_NAME = Default.FUNCTION_DECLARATION.extend("FUNCTION_NAME")

    private fun TextAttributesKey.extend(name: String) = TextAttributesKey.createTextAttributesKey("IDL_$name", this)
}

class IDLSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer() = IDLLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            COMMENT -> Highlights.COMMENT
            IDENTIFIER -> Highlights.IDENTIFIER
            INTEGER, DECIMAL -> Highlights.NUMBER
            BOOLEAN_LITERAL -> Highlights.BOOLEAN
            NULL_LITERAL -> Highlights.NULL
            UNDEFINED_LITERAL -> Highlights.UNDEFINED
            STRING -> Highlights.STRING

            SLASH, DOT -> return emptyArray()

            KEYWORD_ASYNC,
            KEYWORD_ATTRIBUTE,
            KEYWORD_CALLBACK,
            KEYWORD_CONST,
            KEYWORD_CONSTRUCTOR,
            KEYWORD_DELETER,
            KEYWORD_DICTIONARY,
            KEYWORD_ENUM,
            KEYWORD_GETTER,
            KEYWORD_IMPORT,
            KEYWORD_INCLUDES,
            KEYWORD_INHERIT,
            KEYWORD_INTERFACE,
            KEYWORD_ITERABLE,
            KEYWORD_MAPLIKE,
            KEYWORD_MIXIN,
            KEYWORD_NAMESPACE,
            KEYWORD_OPTIONAL,
            KEYWORD_PARTIAL,
            KEYWORD_READONLY,
            KEYWORD_REQUIRED,
            KEYWORD_SETLIKE,
            KEYWORD_SETTER,
            KEYWORD_STATIC,
            KEYWORD_STRINGIFIER,
            KEYWORD_TYPEDEF,
            KEYWORD_UNRESTRICTED -> Highlights.KEYWORD

            OPEN_CURLY, CLOSE_CURLY -> Highlights.BRACES
            OPEN_BRACKET, CLOSE_BRACKET -> Highlights.BRACKETS
            OPEN_PAREN, CLOSE_PAREN -> Highlights.PARENS
            OPEN_ANGLE, CLOSE_ANGLE -> Highlights.ANGLE_BRACKETS

            QUESTION_MARK -> Highlights.QUESTION_MARK
            COLON -> Highlights.COLON
            SEMICOLON -> Highlights.SEMICOLON
            EQUALS -> Highlights.EQUALS
            COMMA -> Highlights.COMMA
            DASH -> Highlights.DASH
            TRIPLE_DOT -> Highlights.TRIPLE_DOT
            ASTERISK -> Highlights.ASTERISK

            else -> {
                if (tokenType is IDLToken)
                    println("No token highlighting defined for element $tokenType!")
                return emptyArray()
            }
        }.let { arrayOf(it) }
    }
}

class IDLSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?) = IDLSyntaxHighlighter()
}
