package edu.cooper.akhmetov.xidea.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import edu.cooper.akhmetov.xidea.VerilogFileType;
import edu.cooper.akhmetov.xidea.VerilogLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class VerilogFile extends PsiFileBase {
    public VerilogFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, VerilogLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return VerilogFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Verilog File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}