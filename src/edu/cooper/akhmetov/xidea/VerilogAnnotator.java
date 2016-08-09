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

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import edu.cooper.akhmetov.xidea.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


public class VerilogAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement elem, @NotNull AnnotationHolder holder) {
        if(elem instanceof VerilogModuleName){
            VerilogModuleName vmn = (VerilogModuleName) elem;
            if(vmn.getParent() instanceof VerilogModuleInstantiation){
                annotateVerilogModuleInstantiation(elem, holder, vmn);

            }
        } else if(elem instanceof VerilogUdpName){
            VerilogUdpName vudpn = (VerilogUdpName) elem;
            if(vudpn.getParent() instanceof VerilogUdpInstantiation){
                annotateVerilogUdpInstantiation(elem, holder, vudpn);

            }
        } else if (elem instanceof VerilogVariableName){
            VerilogVariableName vvnn = (VerilogVariableName) elem;
            String nn = vvnn.getIdentifier().getText();
            VerilogDescription desc = vvnn.getEnclosingDescription();
            if(desc==null) return;
            if(VerilogUtils.findDeclaredVariables(desc).stream().filter(vvn -> nn.equals(vvn.getIdentifier().getText())).collect(Collectors.counting())==0){
                holder.createErrorAnnotation(elem.getTextRange(), "Cannot find variable "+nn); // TODO add intention to add module here
            }
        }
    }

    private void annotateVerilogModuleInstantiation(@NotNull PsiElement elem, @NotNull AnnotationHolder holder, VerilogModuleName vmn) {
        //System.out.println("foo:");
        // we should check if this is instantiable...
        Project project = elem.getProject();
        String name = vmn.getIdentifier().getText();
        List<VerilogModule> modules = VerilogUtils.findModules(project, name);
        if(modules.isEmpty()){

            holder.createErrorAnnotation(elem.getTextRange(), "Cannot find module "+name); // TODO add intention to add module here
        } else if (modules.size()>1){
            holder.createWarningAnnotation(elem.getTextRange(), "Multiple definitions for module "+name);
        }
    }
    private void annotateVerilogUdpInstantiation(@NotNull PsiElement elem, @NotNull AnnotationHolder holder, VerilogUdpName vmn) {
        //System.out.println("bar:");
        // we should check if this is instantiable...
        Project project = elem.getProject();
        String name = vmn.getIdentifier().getText();
        List<VerilogModule> modules = VerilogUtils.findModules(project, name);
        List<VerilogUdp> udps = VerilogUtils.findUDPs(project, name);
        if(modules.isEmpty() && udps.isEmpty()){

            holder.createErrorAnnotation(elem.getTextRange(), "Cannot find module or UDP "+name); // TODO add intention to add module here
        } else if ((modules.size()+udps.size())>1){
            holder.createWarningAnnotation(elem.getTextRange(), "Multiple definitions for module or UDP "+name);
        }
    }
}
