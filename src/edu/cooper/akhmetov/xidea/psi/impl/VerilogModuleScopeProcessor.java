/*
 * XIdea--IntellJ IDEA plugin for FPGA toolchains.
 *
 *     Copyright (C) 2016 Andrey Akhmetov
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
