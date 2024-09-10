package org.ladybird.dsl.ipc;

import com.intellij.psi.tree.IElementType;

import com.intellij.lexer.FlexLexer;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static org.ladybird.dsl.ipc.IPCTypes.*;

%%

%{
  public IPCLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class IPCLexer
%extends IPCLexerBase
%implements FlexLexer
%function advance
%type IElementType
%unicode

WHITE_SPACE=[\t\n\r ]+
IDENTIFIER=[A-Za-z_]\w*
INCLUDE_IDENTIFIER=[A-Za-z_\./]*
COMMENT=\/\/.*|\/\*[^]*?\*\/

%%
<YYINITIAL> {
    {WHITE_SPACE}        { return SPACE; }

    "endpoint"           { return KEYWORD_ENDPOINT; }
    "#include"           { return KEYWORD_INCLUDE; }

    "{"                  { return OPEN_CURLY; }
    "}"                  { return CLOSE_CURLY; }
    "("                  { return OPEN_PAREN; }
    ")"                  { return CLOSE_PAREN; }
    "["                  { return OPEN_BRACKET; }
    "]"                  { return CLOSE_BRACKET; }
    "<"                  { return OPEN_ANGLE; }
    ">"                  { return CLOSE_ANGLE; }
    "\""                 { return QUOTE; }
    ","                  { return COMMA; }
    "::"                 { return NAMESPACE; }
    "=>"                 { return SYNC_ARROW; }
    "=|"                 { return ASYNC_ARROW; }

    {IDENTIFIER}         { return IDENTIFIER; }
    {INCLUDE_IDENTIFIER} { return INCLUDE_IDENTIFIER; }
    {COMMENT}            { return COMMENT; }
}

[^]                      { return BAD_CHARACTER; }
