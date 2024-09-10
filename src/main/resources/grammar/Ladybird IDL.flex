package org.ladybird.dsl.idl;

import com.intellij.psi.tree.IElementType;

import com.intellij.lexer.FlexLexer;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static org.ladybird.dsl.idl.IDLTypes.*;

%%

%{
  public IDLLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class IDLLexer
%extends IDLLexerBase
%implements FlexLexer
%function advance
%type IElementType
%unicode

WHITE_SPACE=[\t\n\r ]+
INTEGER=-?([1-9][0-9]*|0[Xx][0-9A-Fa-f]+|0[0-7]*)
DECIMAL=-?(([0-9]+\.[0-9]*|[0-9]*\.[0-9]+)([Ee][+-]?[0-9]+)?|[0-9]+[Ee][+-]?[0-9]+)
IDENTIFIER=[_-]?[A-Za-z][0-9A-Z_a-z-]*
COMMENT=\/\/.*|\/\*[^]*?\*\/

%s YYSTRING

%%
<YYINITIAL> {
  {WHITE_SPACE}  { return SPACE; }

  "async"        { return KEYWORD_ASYNC; }
  "attribute"    { return KEYWORD_ATTRIBUTE; }
  "callback"     { return KEYWORD_CALLBACK; }
  "const"        { return KEYWORD_CONST; }
  "constructor"  { return KEYWORD_CONSTRUCTOR; }
  "deleter"      { return KEYWORD_DELETER; }
  "dictionary"   { return KEYWORD_DICTIONARY; }
  "enum"         { return KEYWORD_ENUM; }
  "getter"       { return KEYWORD_GETTER; }
  "#import"      { return KEYWORD_IMPORT; } // Non-standard
  "includes"     { return KEYWORD_INCLUDES; }
  "inherit"      { return KEYWORD_INHERIT; }
  "interface"    { return KEYWORD_INTERFACE; }
  "iterable"     { return KEYWORD_ITERABLE; }
  "maplike"      { return KEYWORD_MAPLIKE; }
  "mixin"        { return KEYWORD_MIXIN; }
  "namespace"    { return KEYWORD_NAMESPACE; }
  "optional"     { return KEYWORD_OPTIONAL; }
  "partial"      { return KEYWORD_PARTIAL; }
  "readonly"     { return KEYWORD_READONLY; }
  "required"     { return KEYWORD_REQUIRED; }
  "setlike"      { return KEYWORD_SETLIKE; }
  "setter"       { return KEYWORD_SETTER; }
  "static"       { return KEYWORD_STATIC; }
  "stringifier"  { return KEYWORD_STRINGIFIER; }
  "typedef"      { return KEYWORD_TYPEDEF; }
  "unrestricted" { return KEYWORD_UNRESTRICTED; }

  "{"            { return OPEN_CURLY; }
  "}"            { return CLOSE_CURLY; }
  "("            { return OPEN_PAREN; }
  ")"            { return CLOSE_PAREN; }
  "["            { return OPEN_BRACKET; }
  "]"            { return CLOSE_BRACKET; }
  "<"            { return OPEN_ANGLE; }
  ">"            { return CLOSE_ANGLE; }
  "?"            { return QUESTION_MARK; }
  ":"            { return COLON; }
  ";"            { return SEMICOLON; }
  "="            { return EQUALS; }
  ","            { return COMMA; }
  "-"            { return DASH; }
  "..."          { return TRIPLE_DOT; }
  "."            { return DOT; }
  "*"            { return ASTERISK; }
  "/"            { return SLASH; }

  "\""           { yybegin(YYSTRING); }

  {INTEGER}      { return INTEGER; }
  {DECIMAL}      { return DECIMAL; }
  {IDENTIFIER}   { return IDENTIFIER; }
  {COMMENT}      { return COMMENT; }
}

<YYSTRING> {
  "\\"           { zzMarkedPos += 1; }
  <<EOF>>        { yybegin(YYINITIAL); return STRING; }
  "\""           { yybegin(YYINITIAL); return STRING; }
  [^]            { }
}

[^]              { return BAD_CHARACTER; }
