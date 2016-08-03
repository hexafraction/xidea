package edu.cooper.akhmetov.xidea.psi.impl;

import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Andrey Akhmetov on 8/2/2016.
 */
public class VerilogModuleScopeProcessor implements PsiScopeProcessor {
    @Override
    public boolean execute(@NotNull PsiElement psiElement, @NotNull ResolveState resolveState) {
        return false;
    }

    @Nullable
    @Override
    public <T> T getHint(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public void handleEvent(@NotNull Event event, @Nullable Object o) {
        //pass
    }
}
