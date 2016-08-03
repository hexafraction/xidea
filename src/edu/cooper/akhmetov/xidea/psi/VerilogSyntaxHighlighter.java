package edu.cooper.akhmetov.xidea.psi;

import static edu.cooper.akhmetov.xidea.parser.VerilogTypes.*;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import edu.cooper.akhmetov.xidea.VerilogLexerAdapter;
import edu.cooper.akhmetov.xidea.parser.VerilogTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
import static edu.cooper.akhmetov.xidea.parser.VerilogTypes.IDENTIFIER;

public class VerilogSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey OP =
            createTextAttributesKey("VERILOG_OP", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KW =
            createTextAttributesKey("VERILOG_KW", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("VERILOG_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("VERILOG_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("VERILOG_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("VERILOG_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey BRACES =
            createTextAttributesKey("VERILOG_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS =
            createTextAttributesKey("VERILOG_BRACES", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey PARENS =
            createTextAttributesKey("VERILOG_PARENS", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey IDENTIFIER =
            createTextAttributesKey("VERILOG_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);

    private static final TextAttributesKey[] OP_KEYS = {OP};
    private static final TextAttributesKey[] KW_KEYS = {KW};
    private static final TextAttributesKey[] NUM_KEYS = {NUMBER};
    private static final TextAttributesKey[] COMMENT_KEYS = {COMMENT};
    private static final TextAttributesKey[] STRING_KEYS = {STRING};
    private static final TextAttributesKey[] BADCHAR_KEYS = {BAD_CHARACTER};
    private static final TextAttributesKey[] BRACE_KEYS = {BRACES};
    private static final TextAttributesKey[] BRACKET_KEYS = {BRACKETS};
    private static final TextAttributesKey[] PAREN_KEYS = {PARENS};
    private static final TextAttributesKey[] IDENT_KEYS = {IDENTIFIER};

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new VerilogLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(BAD_CHARACTER)) {
            return BADCHAR_KEYS;
        } else if (tokenType.equals(QUOTED_STRING)) {
            return STRING_KEYS;
        } else if (tokenType.equals(LBRACE)
                || tokenType.equals(RBRACE)) {
            return BRACE_KEYS;
        } else if (tokenType.equals(LBRACK)
                || tokenType.equals(RBRACK)) {
            return BRACKET_KEYS;
        } else if (tokenType.equals(LPAREN)
                || tokenType.equals(RPAREN)) {
            return PAREN_KEYS;

        } else if (tokenType.equals(IDENTIFIER)){
            return IDENT_KEYS;
        } else if (tokenType.equals(LINE_COMMENT)
                || tokenType.equals(BLOCK_COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(DEC_NUM)
                || tokenType.equals(NUM_SCI)
                || tokenType.equals(DEC_FRAC)
                || tokenType.equals(NUM_WITH_BASE)) {
            return NUM_KEYS;
        } else if (tokenType.equals(KW_ALWAYS)
                || tokenType.equals(KW_ASSIGN)
                || tokenType.equals(KW_ASSIGN)
                || tokenType.equals(KW_BEGIN)
                || tokenType.equals(KW_CASE)
                || tokenType.equals(KW_CASEX)
                || tokenType.equals(KW_CASEZ)
                || tokenType.equals(KW_DEASSIGN)
                || tokenType.equals(KW_ASSIGN)
                || tokenType.equals(KW_DEFAULT)
                || tokenType.equals(KW_DEFPARAM)
                || tokenType.equals(KW_DISABLE)
                || tokenType.equals(KW_ELSE)
                || tokenType.equals(KW_END)
                || tokenType.equals(KW_ENDCASE)
                || tokenType.equals(KW_ENDMODULE)
                || tokenType.equals(KW_ENDPRIMITIVE)
                || tokenType.equals(KW_ENDTABLE)
                || tokenType.equals(KW_EVENT)
                || tokenType.equals(KW_FOR)
                || tokenType.equals(KW_FORCE)
                || tokenType.equals(KW_FOREVER)
                || tokenType.equals(KW_FORK)
                || tokenType.equals(KW_IF)
                || tokenType.equals(KW_INITIAL)
                || tokenType.equals(KW_INOUT)
                || tokenType.equals(KW_INPUT)
                || tokenType.equals(KW_INTEGER)
                || tokenType.equals(KW_JOIN)
                || tokenType.equals(KW_MODULE)
                || tokenType.equals(KW_NEGEDGE)
                || tokenType.equals(KW_OR)
                || tokenType.equals(KW_OUTPUT)
                || tokenType.equals(KW_PARAMETER)
                || tokenType.equals(KW_POSEDGE)
                || tokenType.equals(KW_PRIMITIVE)
                || tokenType.equals(KW_REAL)
                || tokenType.equals(KW_REG)
                || tokenType.equals(KW_RELEASE)
                || tokenType.equals(KW_REPEAT)
                || tokenType.equals(KW_SCALARED)
                || tokenType.equals(KW_TABLE)
                || tokenType.equals(KW_TIME)
                || tokenType.equals(KW_VECTORED)
                || tokenType.equals(KW_WAIT)
                || tokenType.equals(KW_WHILE)) {
            return KW_KEYS;
        } else if (tokenType.equals(PLUS)
                || tokenType.equals(MINUS)
                || tokenType.equals(AMPERSAND)
                || tokenType.equals(PIPE)
                || tokenType.equals(CARTILDE)
                || tokenType.equals(TILCARET)
                || tokenType.equals(CARET)
                || tokenType.equals(EXCLAM)
                || tokenType.equals(TILDE)
                || tokenType.equals(NAND)
                || tokenType.equals(CARPIPE)
                || tokenType.equals(ASTERISK)
                || tokenType.equals(SOLIDUS)
                || tokenType.equals(PERCENT)
                || tokenType.equals(DBLEQ)
                || tokenType.equals(NEQ)
                || tokenType.equals(TRIPLEEQ)
                || tokenType.equals(NDEQ)
                || tokenType.equals(DBLAMP)
                || tokenType.equals(DBLPIPE)
                || tokenType.equals(GTE)
                || tokenType.equals(LT)
                || tokenType.equals(GT)
                || tokenType.equals(SHR)
                || tokenType.equals(SHL)) {
            return OP_KEYS;
        } else return new TextAttributesKey[]{};

    }
}


