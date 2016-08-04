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

package edu.cooper.akhmetov.xidea;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.cooper.akhmetov.xidea.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public class VerilogFoldingBuilder extends FoldingBuilderEx {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        FoldingGroup group = FoldingGroup.newGroup("simple");

        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        Collection<VerilogModule> modules =
                PsiTreeUtil.findChildrenOfType(root, VerilogModule.class);

        for (final VerilogModule mdl : modules) {
            buildDescriptors(descriptors, mdl, "module " + mdl.getName() + "...");
        }

        Collection<VerilogUdp> udps =
                PsiTreeUtil.findChildrenOfType(root, VerilogUdp.class);
        for (final VerilogUdp udp : udps) {
            buildDescriptors(descriptors, udp, "primitive " + udp.getName() + "...");
        }

        Collection<VerilogParallelBlock> vpbs =
                PsiTreeUtil.findChildrenOfType(root, VerilogParallelBlock.class);
        for (final VerilogParallelBlock vpb : vpbs) {
            buildDescriptors(descriptors, vpb, "fork ... join");
        }

        Collection<VerilogSequentialBlock> vsbs =
                PsiTreeUtil.findChildrenOfType(root, VerilogSequentialBlock.class);
        for (final VerilogSequentialBlock vsb : vsbs) {
            buildDescriptors(descriptors, vsb, (vsb.getIdentifier()==null)?"begin ... end":"begin: "+vsb.getIdentifier().getText()+" ... end");
        }

        Collection<VerilogConnlist> vcls =
                PsiTreeUtil.findChildrenOfType(root, VerilogConnlist.class);
        for (final VerilogConnlist vcl : vcls) {
            buildDescriptors(descriptors, vcl, "(...)");
        }

        Collection<VerilogPortlist> vpls =
                PsiTreeUtil.findChildrenOfType(root, VerilogPortlist.class);
        for (final VerilogPortlist vpl : vpls) {
            buildDescriptors(descriptors, vpl, "(...)");
        }

        Collection<VerilogParamValAssign> vpvas =
                PsiTreeUtil.findChildrenOfType(root, VerilogParamValAssign.class);
        for (final VerilogParamValAssign vpva : vpvas) {
            buildDescriptors(descriptors, vpva, "#(...)");
        }

        Collection<PsiComment> comments =
                PsiTreeUtil.findChildrenOfType(root, PsiComment.class);
        commentLoop: for (final PsiComment comment : comments) {
            String[] lines = comment.getText().split("[\\r\\n]");
            if(lines.length<=1) continue commentLoop;
            for(int i = 0; i < lines.length; i++){
                String lineNormalized = lines[i].replaceAll("^/*", "").trim();
                if(!lineNormalized.isEmpty()){

                    if(lineNormalized.length()>=40){
                        lineNormalized = lineNormalized.substring(0, 40)+"...";
                    } else lineNormalized = lineNormalized+"...";
                    buildDescriptors(descriptors, comment, "// " + lineNormalized);
                    continue commentLoop;
                }
            }

        }

        return descriptors.toArray(new FoldingDescriptor[0]);
    }


    private void buildDescriptors(List<FoldingDescriptor> target, final PsiElement elem, String text) {
        FoldingGroup group = FoldingGroup.newGroup(elem.getClass().getName()+": "+((elem.getTextLength()>=40)?elem.getText().substring(0,20)+"...":elem.getText()));

        target.add(new FoldingDescriptor(elem.getNode(),
                new TextRange(elem.getTextRange().getStartOffset(),
                        elem.getTextRange().getEndOffset()),
                group) {
            @Nullable
            @Override
            public String getPlaceholderText() {
                return text;
            }
        });
    }


    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode astNode) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return false;
    }


}


