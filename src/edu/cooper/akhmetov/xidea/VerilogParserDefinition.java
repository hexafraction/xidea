package edu.cooper.akhmetov.xidea;

import com.intellij.lang.*;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.*;
import edu.cooper.akhmetov.xidea.parser.VerilogParser;
import edu.cooper.akhmetov.xidea.parser.VerilogTypes;
import edu.cooper.akhmetov.xidea.psi.VerilogFile;
import org.jetbrains.annotations.NotNull;

public class VerilogParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(VerilogTypes.BLOCK_COMMENT, VerilogTypes.LINE_COMMENT);
    public static final TokenSet STRING_LITERALS = TokenSet.create(VerilogTypes.QUOTED_STRING);
    public static final IFileElementType FILE =
            new IFileElementType(Language.<VerilogLanguage>findInstance(VerilogLanguage.class));

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new VerilogLexerAdapter();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return STRING_LITERALS;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new VerilogParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new VerilogFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return VerilogTypes.Factory.createElement(node);
    }
}