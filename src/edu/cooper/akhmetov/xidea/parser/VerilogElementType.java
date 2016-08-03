package edu.cooper.akhmetov.xidea.parser;

import com.intellij.psi.tree.IElementType;
import edu.cooper.akhmetov.xidea.VerilogLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


public class VerilogElementType extends IElementType {
    public VerilogElementType(@NotNull @NonNls String debugName) {
        super(debugName, VerilogLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "VerilogTokenType." + super.toString();
    }
}
