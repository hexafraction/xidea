package edu.cooper.akhmetov.xidea;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import edu.cooper.akhmetov.xidea.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class VerilogAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement elem, @NotNull AnnotationHolder holder) {
        if(elem instanceof VerilogModuleName){
            VerilogModuleName vmn = (VerilogModuleName) elem;
            if(vmn.getParent() instanceof VerilogModuleInstantiation){
                annotateVerilogModuleInstantiation(elem, holder, vmn);

            }
        } if(elem instanceof VerilogUdpName){
            VerilogUdpName vudpn = (VerilogUdpName) elem;
            if(vudpn.getParent() instanceof VerilogUdpInstantiation){
                annotateVerilogUdpInstantiation(elem, holder, vudpn);

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
        }
    }
}
