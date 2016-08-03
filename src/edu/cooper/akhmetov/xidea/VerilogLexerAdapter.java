package edu.cooper.akhmetov.xidea;


import com.intellij.lexer.FlexAdapter;
import edu.cooper.akhmetov.xidea.parser.VerilogLexer;

import java.io.Reader;

public class VerilogLexerAdapter extends FlexAdapter {
    public VerilogLexerAdapter() {
        super(new VerilogLexer((Reader) null));
    }
}
