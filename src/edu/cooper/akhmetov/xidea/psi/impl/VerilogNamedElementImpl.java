package edu.cooper.akhmetov.xidea.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import edu.cooper.akhmetov.xidea.psi.VerilogNamedElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Andrey Akhmetov on 8/2/2016.
 */
public abstract class VerilogNamedElementImpl extends ASTWrapperPsiElement implements VerilogNamedElement {
    public VerilogNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}