package edu.cooper.akhmetov.xidea.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import edu.cooper.akhmetov.xidea.psi.VerilogNamedElement;
import edu.cooper.akhmetov.xidea.psi.VerilogReference;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Andrey Akhmetov on 8/2/2016.
 */
public abstract class VerilogReferenceImpl extends ASTWrapperPsiElement implements VerilogReference {
    public VerilogReferenceImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiReference getReference() {
        return this;
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return new PsiReference[]{this};
    }

    @Override
    public PsiReference findReferenceAt(int i) {
        return this;
    }
}