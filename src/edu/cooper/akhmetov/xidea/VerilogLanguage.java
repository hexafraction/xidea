package edu.cooper.akhmetov.xidea;


import com.intellij.lang.Language;

public class VerilogLanguage extends Language {
    public static final VerilogLanguage INSTANCE = new VerilogLanguage();

    private VerilogLanguage() {
        super("Verilog");
    }
}