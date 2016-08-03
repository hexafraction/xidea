package edu.cooper.akhmetov.xidea;


import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class VerilogFileType extends LanguageFileType {
    public static final VerilogFileType INSTANCE = new VerilogFileType();

    private VerilogFileType() {
        super(VerilogLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Verilog File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Verilog source file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "v";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.FILE_V;
    }
}