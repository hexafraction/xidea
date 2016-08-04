package edu.cooper.akhmetov.xidea.parser;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static edu.cooper.akhmetov.xidea.parser.VerilogTypes.*;

%%

%{
  public VerilogLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class VerilogLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s

TIMEBASE=[0-9]+[ \t\n\x0B\f\r]*[munpf]?s
NEWLINE=[\r\n]
WHITE_SPACE=[ \t\n\x0B\f\r]
LINE_COMMENT="//".*
BLOCK_COMMENT="/"\*([^*]|\*+[^*/])*(\*+"/")?
QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
DEC_NUM=[0-9_]+
NUM_SCI=[0-9_]+(\.[0-9_]*)?[eE][+\-]?[0-9_]+
DEC_FRAC=[0-9_]+(\.[0-9_]*)?
UNSIGNED=[0-9_]+
NUM_WITH_BASE=[0-9]*'[bBoOdDhH][0-9a-fA-FxXzZ?_]+
SPEC_BLOCK=specify.*?endspecify
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_$]*
SYSIDENTIFIER=\$[a-zA-Z_][a-zA-Z0-9_$]*

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "module"             { return KW_MODULE; }
  "endmodule"          { return KW_ENDMODULE; }
  ";"                  { return SEMICOLON; }
  "("                  { return LPAREN; }
  ")"                  { return RPAREN; }
  "{"                  { return LBRACE; }
  "}"                  { return RBRACE; }
  ","                  { return COMMA; }
  "<="                 { return NBEQUALS; }
  "="                  { return EQUALS; }
  "input"              { return KW_INPUT; }
  "output"             { return KW_OUTPUT; }
  "inout"              { return KW_INOUT; }
  "reg"                { return KW_REG; }
  "real"               { return KW_REAL; }
  "primitive"          { return KW_PRIMITIVE; }
  "endprimitive"       { return KW_ENDPRIMITIVE; }
  "initial"            { return KW_INITIAL; }
  "table"              { return KW_TABLE; }
  "endtable"           { return KW_ENDTABLE; }
  "time"               { return KW_TIME; }
  "parameter"          { return KW_PARAMETER; }
  "assign"             { return KW_ASSIGN; }
  "integer"            { return KW_INTEGER; }
  "always"             { return KW_ALWAYS; }
  "case"               { return KW_CASE; }
  "casex"              { return KW_CASEX; }
  "casez"              { return KW_CASEZ; }
  "if"                 { return KW_IF; }
  "."                  { return DOT; }
  "["                  { return LBRACK; }
  "#"                  { return POUND; }
  "]"                  { return RBRACK; }
  "?"                  { return QUESTION; }
  ":"                  { return COLON; }
  "@"                  { return AT; }
  "event"              { return KW_EVENT; }
  "defparam"           { return KW_DEFPARAM; }
  "forever"            { return KW_FOREVER; }
  "while"              { return KW_WHILE; }
  "repeat"             { return KW_REPEAT; }
  "for"                { return KW_FOR; }
  "endcase"            { return KW_ENDCASE; }
  "or"                 { return KW_OR; }
  "posedge"            { return KW_POSEDGE; }
  "negedge"            { return KW_NEGEDGE; }
  "wait"               { return KW_WAIT; }
  "->"                 { return RARROW; }
  "else"               { return KW_ELSE; }
  "=>"                 { return RARROWEQ; }
  "disable"            { return KW_DISABLE; }
  "deassign"           { return KW_DEASSIGN; }
  "force"              { return KW_FORCE; }
  "release"            { return KW_RELEASE; }
  "generate"           { return KW_GENERATE; }
  "endgenerate"        { return KW_ENDGENERATE; }
  "task"               { return KW_TASK; }
  "endtask"            { return KW_ENDTASK; }
  "function"           { return KW_FUNCTION; }
  "endfunction"        { return KW_ENDFUNCTION; }
  "scalared"           { return KW_SCALARED; }
  "vectored"           { return KW_VECTORED; }
  "default"            { return KW_DEFAULT; }
  "begin"              { return KW_BEGIN; }
  "end"                { return KW_END; }
  "fork"               { return KW_FORK; }
  "join"               { return KW_JOIN; }
  "+"                  { return PLUS; }
  "-"                  { return MINUS; }
  "&"                  { return AMPERSAND; }
  "|"                  { return PIPE; }
  "^~"                 { return CARTILDE; }
  "~^"                 { return TILCARET; }
  "^"                  { return CARET; }
  "!"                  { return EXCLAM; }
  "~"                  { return TILDE; }
  "~&"                 { return NAND; }
  "^|"                 { return CARPIPE; }
  "*"                  { return ASTERISK; }
  "/"                  { return SOLIDUS; }
  "%"                  { return PERCENT; }
  "=="                 { return DBLEQ; }
  "!="                 { return NEQ; }
  "==="                { return TRIPLEEQ; }
  "!=="                { return NDEQ; }
  "&&"                 { return DBLAMP; }
  "||"                 { return DBLPIPE; }
  ">="                 { return GTE; }
  "<"                  { return LT; }
  ">"                  { return GT; }
  ">>"                 { return SHR; }
  "<<"                 { return SHL; }
  "`timescale"         { return BT_TIMESCALE; }
  "genvar"             { return GENVAR; }

  {TIMEBASE}           { return TIMEBASE; }
  {NEWLINE}            { return NEWLINE; }
  {WHITE_SPACE}        { return WHITE_SPACE; }
  {LINE_COMMENT}       { return LINE_COMMENT; }
  {BLOCK_COMMENT}      { return BLOCK_COMMENT; }
  {QUOTED_STRING}      { return QUOTED_STRING; }
  {DEC_NUM}            { return DEC_NUM; }
  {NUM_SCI}            { return NUM_SCI; }
  {DEC_FRAC}           { return DEC_FRAC; }
  {UNSIGNED}           { return UNSIGNED; }
  {NUM_WITH_BASE}      { return NUM_WITH_BASE; }
  {SPEC_BLOCK}         { return SPEC_BLOCK; }
  {IDENTIFIER}         { return IDENTIFIER; }
  {SYSIDENTIFIER}      { return SYSIDENTIFIER; }

}

[^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
