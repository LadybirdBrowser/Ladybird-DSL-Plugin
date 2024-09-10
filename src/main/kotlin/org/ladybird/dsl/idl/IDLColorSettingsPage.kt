package org.ladybird.dsl.idl

import org.ladybird.dsl.common.DSLColorSettingsPage

class IDLColorSettingsPage : DSLColorSettingsPage(IDLLanguage) {
    override fun getIcon() = IDLLanguage.FILE_ICON

    override fun getHighlighter() = IDLSyntaxHighlighter()

    // TODO: Put something more demonstrative here
    override fun getTemplate() = """
        #import IMPORT_PATH{<Animations/DocumentTimeline.idl>}
        #import IMPORT_PATH{<CSS/StyleSheetList.idl>}
        #import IMPORT_PATH{<DOM/Comment.idl>}
        #import IMPORT_PATH{<DOM/DOMImplementation.idl>}
        #import IMPORT_PATH{<DOM/DocumentFragment.idl>}
        #import IMPORT_PATH{<DOM/DocumentType.idl>}
        #import IMPORT_PATH{<DOM/Element.idl>}
        #import IMPORT_PATH{<DOM/Event.idl>}
        #import IMPORT_PATH{<DOM/EventHandler.idl>}
        #import IMPORT_PATH{<DOM/HTMLCollection.idl>}
        #import IMPORT_PATH{<DOM/Node.idl>}
        #import IMPORT_PATH{<DOM/NodeFilter.idl>}
        #import IMPORT_PATH{<DOM/NodeIterator.idl>}
        #import IMPORT_PATH{<DOM/NodeList.idl>}
        #import IMPORT_PATH{<DOM/ParentNode.idl>}
        #import IMPORT_PATH{<DOM/ProcessingInstruction.idl>}
        #import IMPORT_PATH{<DOM/Range.idl>}
        #import IMPORT_PATH{<DOM/Text.idl>}
        #import IMPORT_PATH{<DOM/TreeWalker.idl>}
        #import IMPORT_PATH{<HTML/HTMLElement.idl>}
        #import IMPORT_PATH{<HTML/HTMLHeadElement.idl>}
        #import IMPORT_PATH{<HTML/HTMLScriptElement.idl>}
        #import IMPORT_PATH{<HTML/Location.idl>}
        #import IMPORT_PATH{<Selection/Selection.idl>}
        
        // https://dom.spec.whatwg.org/#document
        [EXTENDED_ATTR_NAME{Exposed}=Window]
        interface INTF_NAME{Document} : PLATFORM_TYPE{Node} {
            FN_NAME{constructor}();
        
            PRIM_TYPE{boolean} FN_NAME{hasFocus}();
        
            [EXTENDED_ATTR_NAME{PutForwards}=href, EXTENDED_ATTR_NAME{LegacyUnforgeable}] readonly attribute PLATFORM_TYPE{Location}? ATTR_NAME{location};
            attribute PLATFORM_TYPE{USVString} ATTR_NAME{domain};
        
            readonly attribute PLATFORM_TYPE{DOMImplementation} ATTR_NAME{implementation};
        
            [EXTENDED_ATTR_NAME{ImplementedAs}=url_string] readonly attribute PLATFORM_TYPE{USVString} ATTR_NAME{URL};
            readonly attribute PLATFORM_TYPE{USVString} ATTR_NAME{documentURI};
        
            readonly attribute PLATFORM_TYPE{DOMString} ATTR_NAME{characterSet};
            readonly attribute PLATFORM_TYPE{DOMString} ATTR_NAME{charset};
            readonly attribute PLATFORM_TYPE{DOMString} ATTR_NAME{inputEncoding};
            readonly attribute PLATFORM_TYPE{DOMString} ATTR_NAME{contentType};
        
            readonly attribute PLATFORM_TYPE{Window}? ATTR_NAME{defaultView};
        
            [EXTENDED_ATTR_NAME{CEReactions}] PLATFORM_TYPE{Document} FN_NAME{open}(optional PLATFORM_TYPE{DOMString} unused1, optional PLATFORM_TYPE{DOMString} unused2);
            PLATFORM_TYPE{WindowProxy}? FN_NAME{open}(PLATFORM_TYPE{USVString} url, PLATFORM_TYPE{DOMString} name, PLATFORM_TYPE{DOMString} features);
            [EXTENDED_ATTR_NAME{CEReactions}] UNDEF{undefined} FN_NAME{close}();
            [EXTENDED_ATTR_NAME{CEReactions}] UNDEF{undefined} FN_NAME{write}(PLATFORM_TYPE{DOMString}... text);
            [EXTENDED_ATTR_NAME{CEReactions}] UNDEF{undefined} FN_NAME{writeln}(PLATFORM_TYPE{DOMString}... text);
        
            attribute PLATFORM_TYPE{DOMString} ATTR_NAME{cookie};
        
            readonly attribute PLATFORM_TYPE{USVString} ATTR_NAME{referrer};
        
            readonly attribute PLATFORM_TYPE{Element}? ATTR_NAME{activeElement};
        
            PLATFORM_TYPE{Element}? FN_NAME{getElementById}(PLATFORM_TYPE{DOMString} id);
            PLATFORM_TYPE{HTMLCollection} FN_NAME{getElementsByName}(PLATFORM_TYPE{DOMString} name);
            PLATFORM_TYPE{HTMLCollection} FN_NAME{getElementsByTagName}(PLATFORM_TYPE{DOMString} tagName);
            PLATFORM_TYPE{HTMLCollection} FN_NAME{getElementsByTagNameNS}(PLATFORM_TYPE{DOMString}? namespace, PLATFORM_TYPE{DOMString} localName);
            PLATFORM_TYPE{HTMLCollection} FN_NAME{getElementsByClassName}(PLATFORM_TYPE{DOMString} className);
        
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{applets};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{anchors};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{images};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{embeds};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{plugins};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{links};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{forms};
            [EXTENDED_ATTR_NAME{SameObject}] readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{scripts};
        
            // FIXME: Should return an HTMLAllCollection
            readonly attribute PLATFORM_TYPE{HTMLCollection} ATTR_NAME{all};
        
            [EXTENDED_ATTR_NAME{CEReactions}, EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{Element} FN_NAME{createElement}(PLATFORM_TYPE{DOMString} tagName, optional (PLATFORM_TYPE{DOMString} or PLATFORM_TYPE{ElementCreationOptions}) options = {});
            [EXTENDED_ATTR_NAME{CEReactions}, EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{Element} FN_NAME{createElementNS}(PLATFORM_TYPE{DOMString}? namespace, PLATFORM_TYPE{DOMString} qualifiedName, optional (PLATFORM_TYPE{DOMString} or PLATFORM_TYPE{ElementCreationOptions}) options = {});
            PLATFORM_TYPE{DocumentFragment} FN_NAME{createDocumentFragment}();
            PLATFORM_TYPE{Text} FN_NAME{createTextNode}(PLATFORM_TYPE{DOMString} data);
            PLATFORM_TYPE{Comment} FN_NAME{createComment}(PLATFORM_TYPE{DOMString} data);
            [EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{ProcessingInstruction} FN_NAME{createProcessingInstruction}(PLATFORM_TYPE{DOMString} target, PLATFORM_TYPE{DOMString} data);
        
            [EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{Attr} FN_NAME{createAttribute}(PLATFORM_TYPE{DOMString} localName);
            [EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{Attr} FN_NAME{createAttributeNS}(PLATFORM_TYPE{DOMString}? namespace, PLATFORM_TYPE{DOMString} qualifiedName);
        
            PLATFORM_TYPE{Range} FN_NAME{createRange}();
            PLATFORM_TYPE{Event} FN_NAME{createEvent}(PLATFORM_TYPE{DOMString} interface);
        
            [EXTENDED_ATTR_NAME{CEReactions}, EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{Node} FN_NAME{importNode}(PLATFORM_TYPE{Node} node, optional PLATFORM_TYPE{boolean} deep = false);
            [EXTENDED_ATTR_NAME{CEReactions}, EXTENDED_ATTR_NAME{ImplementedAs}=adopt_node_binding] PLATFORM_TYPE{Node} FN_NAME{adoptNode}(PLATFORM_TYPE{Node} node);
        
            [ImplementedAs=style_sheets_for_bindings] readonly attribute PLATFORM_TYPE{StyleSheetList} ATTR_NAME{styleSheets};
        
            readonly attribute PLATFORM_TYPE{DOMString} ATTR_NAME{compatMode};
            readonly attribute PLATFORM_TYPE{DocumentType}? ATTR_NAME{doctype};
        
            readonly attribute PLATFORM_TYPE{Element}? ATTR_NAME{documentElement};
            [EXTENDED_ATTR_NAME{CEReactions}] attribute PLATFORM_TYPE{HTMLElement}? ATTR_NAME{body};
            readonly attribute PLATFORM_TYPE{HTMLHeadElement}? ATTR_NAME{head};
            readonly attribute PLATFORM_TYPE{HTMLScriptElement}? ATTR_NAME{currentScript};
        
            readonly attribute PLATFORM_TYPE{DOMString} ATTR_NAME{readyState};
        
            [EXTENDED_ATTR_NAME{CEReactions}] attribute PLATFORM_TYPE{DOMString} ATTR_NAME{title};
        
            PRIM_TYPE{boolean} FN_NAME{queryCommandSupported}(PLATFORM_TYPE{DOMString} commandId);
            readonly PRIM_TYPE{boolean} ATTR_NAME{hidden};
            readonly PLATFORM_TYPE{DOMString} ATTR_NAME{visibilityState};
        
            [EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{NodeIterator} FN_NAME{createNodeIterator}(PLATFORM_TYPE{Node} root, optional PRIM_TYPE{unsigned long} whatToShow = 0xFFFFFFFF, optional PLATFORM_TYPE{NodeFilter}? filter = null);
            [EXTENDED_ATTR_NAME{NewObject}] PLATFORM_TYPE{TreeWalker} FN_NAME{createTreeWalker}(PLATFORM_TYPE{Node} root, optional PRIM_TYPE{unsigned long} whatToShow = 0xFFFFFFFF, optional PLATFORM_TYPE{NodeFilter}? filter = null);
        
            PLATFORM_TYPE{Selection}? FN_NAME{getSelection}();
        
            // https://www.w3.org/TR/web-animations-1/#extensions-to-the-document-interface
            readonly attribute PLATFORM_TYPE{DocumentTimeline} ATTR_NAME{timeline};
        };
        
        dictionary DICT_NAME{ElementCreationOptions} {
            PLATFORM_TYPE{DOMString} ATTR_NAME{is};
        };
        PLATFORM_TYPE{Document} includes PLATFORM_TYPE{ParentNode};
        PLATFORM_TYPE{Document} includes PLATFORM_TYPE{GlobalEventHandlers};

        typedef EventHandlerNonNull? EventHandler;
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap() = mapOf(
        "PRIM_TYPE" to Highlights.PRIMITIVE_TYPE,
        "PLATFORM_TYPE" to Highlights.PLATFORM_TYPE,
        "IMPORT_PATH" to Highlights.IMPORT_PATH,
        "ATTR_NAME" to Highlights.ATTRIBUTE_NAME,
        "EXTENDED_ATTR_NAME" to Highlights.EXTENDED_ATTRIBUTE_NAME,
        "FN_NAME" to Highlights.FUNCTION_NAME,
        "INTF_NAME" to Highlights.INTERFACE_NAME,
        "DICT_NAME" to Highlights.DICTIONARY_NAME,
        "ENUM_NAME" to Highlights.ENUM_NAME,
        "UNDEF" to Highlights.UNDEFINED,
    )

    override fun getAttributes() = mapOf(
        "Comments" to Highlights.COMMENT,
        "Imports" to Highlights.IMPORT_PATH,

        "Identifiers//Enums" to Highlights.ENUM_NAME,
        "Identifiers//Interfaces" to Highlights.INTERFACE_NAME,
        "Identifiers//Dictionaries" to Highlights.DICTIONARY_NAME,
        "Identifiers//Functions" to Highlights.FUNCTION_NAME,
        "Identifiers//Other" to Highlights.IDENTIFIER,
        "Identifiers//Primitive Types" to Highlights.PRIMITIVE_TYPE,
        "Identifiers//Platform Types" to Highlights.PLATFORM_TYPE,
        "Identifiers//Attributes" to Highlights.ATTRIBUTE_NAME,
        "Identifiers//Extended Attributes" to Highlights.EXTENDED_ATTRIBUTE_NAME,

        "Literals//Numbers" to Highlights.NUMBER,
        "Literals//Booleans" to Highlights.BOOLEAN,
        "Literals//Strings" to Highlights.STRING,
        "Literals//Null" to Highlights.NULL,
        "Literals//Undefined" to Highlights.UNDEFINED,

        "Keywords" to Highlights.KEYWORD,

        "Operators and Delimiters//Parenthesis" to Highlights.PARENS,
        "Operators and Delimiters//Braces" to Highlights.BRACES,
        "Operators and Delimiters//Brackets" to Highlights.BRACKETS,
        "Operators and Delimiters//Angle Brackets" to Highlights.ANGLE_BRACKETS,
        "Operators and Delimiters//Optional Qualifier" to Highlights.QUESTION_MARK,
        "Operators and Delimiters//Colon" to Highlights.COLON,
        "Operators and Delimiters//Semicolon" to Highlights.SEMICOLON,
        "Operators and Delimiters//Equals" to Highlights.EQUALS,
        "Operators and Delimiters//Comma" to Highlights.COMMA,
        "Operators and Delimiters//Dash" to Highlights.DASH,
        "Operators and Delimiters//Rest" to Highlights.TRIPLE_DOT,
        "Operators and Delimiters//Dot" to Highlights.DOT,
        "Operators and Delimiters//Asterisk" to Highlights.ASTERISK,
    )
}
