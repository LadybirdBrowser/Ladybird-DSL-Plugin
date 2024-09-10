package org.ladybird.dsl.ipc

import org.ladybird.dsl.common.DSLColorSettingsPage

class IPCColorSettingsPage : DSLColorSettingsPage(IPCLanguage) {
    override fun getIcon() = IPCLanguage.FILE_ICON

    override fun getHighlighter() = IPCSyntaxHighlighter()

    override fun getTemplate() = """
        #include INCLUDE_PATH{<AK/URL.h>}
        #include INCLUDE_PATH{<LibCore/AnonymousBuffer.h>}
        #include INCLUDE_PATH{<LibGfx/Color.h>}
        
        endpoint EP_NAME{WebContentClient}
        {
            FN_NAME{did_change_favicon}(NS_NAME{Gfx}::CLASS_TYPE{ShareableBitmap} PARAM_NAME{favicon}) =|
            FN_NAME{did_request_all_cookies}(CLASS_TYPE{URL} PARAM_NAME{url}) => (CLASS_TYPE{Vector}<NS_NAME{Web}::NS_NAME{Cookie}::CLASS_TYPE{Cookie}> PARAM_NAME{cookies})
            FN_NAME{did_set_cookie}(CLASS_TYPE{URL} PARAM_NAME{url}, NS_NAME{Web}::NS_NAME{Cookie}::CLASS_TYPE{ParsedCookie} PARAM_NAME{cookie}, PRIM_TYPE{u8} PARAM_NAME{source}) => ()
            FN_NAME{create_menu}(PRIM_TYPE{i32} PARAM_NAME{menu_id}, [ATTR_NAME{UTF8}] CLASS_TYPE{String} PARAM_NAME{name}) =|
        }
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap() = mapOf(
        "INCLUDE_PATH" to Highlights.INCLUDE_PATH,
        "EP_NAME" to Highlights.ENDPOINT_NAME,
        "NS_NAME" to Highlights.NAMESPACE_NAME,
        "CLASS_TYPE" to Highlights.CLASS_TYPE,
        "PRIM_TYPE" to Highlights.PRIMITIVE_TYPE,
        "FN_NAME" to Highlights.FUNCTION_NAME,
        "ATTR_NAME" to Highlights.ATTRIBUTE_NAME,
        "PARAM_NAME" to Highlights.PARAMETER_NAME,
    )

    override fun getAttributes() = mapOf(
        "Comments" to Highlights.COMMENT,
        "Includes" to Highlights.INCLUDE_PATH,

        "Identifiers//Endpoints" to Highlights.ENDPOINT_NAME,
        "Identifiers//Functions" to Highlights.FUNCTION_NAME,
        "Identifiers//Attributes" to Highlights.ATTRIBUTE_NAME,
        "Identifiers//Namespaces" to Highlights.NAMESPACE_NAME,
        "Identifiers//Parameters" to Highlights.PARAMETER_NAME,

        "Keywords" to Highlights.KEYWORD,

        "Operators and Delimiters//Parenthesis" to Highlights.PARENS,
        "Operators and Delimiters//Braces" to Highlights.BRACES,
        "Operators and Delimiters//Brackets" to Highlights.BRACKETS,
        "Operators and Delimiters//Angle Brackets" to Highlights.ANGLE_BRACKETS,
        "Operators and Delimiters//Sync Arrow" to Highlights.SYNC_ARROW,
        "Operators and Delimiters//Async Arrow" to Highlights.ASYNC_ARROW,
        "Operators and Delimiters//Comma" to Highlights.COMMA,
        "Operators and Delimiters//Namespace Separator" to Highlights.NAMESPACE,

        "Types//Primitives" to Highlights.PRIMITIVE_TYPE,
        "Types//Classes" to Highlights.CLASS_TYPE
    )
}
