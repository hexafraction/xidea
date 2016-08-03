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

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import edu.cooper.akhmetov.xidea.psi.VerilogNamedElement;
import edu.cooper.akhmetov.xidea.psi.VerilogReference;
import org.jetbrains.annotations.NotNull;


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