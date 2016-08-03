package edu.cooper.akhmetov.xidea.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference;


public interface VerilogReference extends PsiReference, PsiElement, PsiPolyVariantReference,PsiNamedElement {
}
