package edu.cooper.akhmetov.xidea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.cooper.akhmetov.xidea.VerilogUtils;
import edu.cooper.akhmetov.xidea.parser.VerilogTypes;
import edu.cooper.akhmetov.xidea.psi.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Akhmetov on 8/2/2016.
 */
public class VerilogPsiUtil {

    public static String getName(VerilogModule element){
        return element.getModuleName().getIdentifier().getText();
    }

    public static VerilogModule setName(VerilogModule element, String newName){
        ASTNode nameNode = element.getNode().findChildByType(VerilogTypes.MODULE_NAME);
        if (nameNode != null) {

            VerilogModuleName newVMN = VerilogElementFactory.createModuleName(element.getProject(), newName);
            ASTNode newNameNode = newVMN.getNode();
            element.getNode().replaceChild(nameNode, newNameNode);
        }
        return element;
    }
    public static String getName(VerilogUdp element){
        return element.getUdpName().getIdentifier().getText();
    }

    public static VerilogUdp setName(VerilogUdp element, String newName){
        ASTNode nameNode = element.getNode().findChildByType(VerilogTypes.MODULE_NAME);
        if (nameNode != null) {

            VerilogUdpName property = VerilogElementFactory.createUdpName(element.getProject(), newName);
            ASTNode newNameNode = property.getFirstChild().getNode();
            element.getNode().replaceChild(nameNode, newNameNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(VerilogModule element) {
        return element.getModuleName();
    }

    public static PsiElement getNameIdentifier(VerilogUdp element) {
        return element.getUdpName();
    }

    public static PsiElement getElement(VerilogModuleName ref){
        return ref;
    }

    public static TextRange getRangeInElement(VerilogModuleName ref){
        return new TextRange(0, ref.getName()==null?0:ref.getName().length());
    }
    public static PsiElement resolve(VerilogModuleName ref){
        //System.out.println("called resolve!");
        new Exception().printStackTrace();
       /* List<VerilogModule> mdls = VerilogUtils.findModules(ref.getProject(), ref.getIdentifier().getText());
        if(mdls.isEmpty()) return null;
        else return mdls.get(0);*/
        ResolveResult[] resolveResults = multiResolve(ref, false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    public static String getCanonicalText(VerilogModuleName ref){
        System.out.println("canon: "+ref.getIdentifier().getText());
        return ref.getIdentifier().getText();
    }

    public static PsiElement handleElementRename(VerilogModuleName ref, String name){
        ASTNode identNode = ref.getNode().findChildByType(VerilogTypes.IDENTIFIER);
        if (identNode != null) {
            ASTNode newIdent = VerilogElementFactory.createModuleName(ref.getProject(), name).getIdentifier().getNode();
            ref.getNode().replaceChild(identNode, newIdent);
        }
        return ref;
    }
    public static PsiElement bindToElement(VerilogModuleName ref, PsiElement elem){
        if(!(elem instanceof VerilogModule)){
            throw new IncorrectOperationException();
        }
        String name = ((VerilogModule) elem).getName();
        return handleElementRename(ref, name);
    }

    public static boolean isReferenceTo(VerilogModuleName ref, PsiElement elem){
        if(elem instanceof VerilogModule){
            String r = ref.getIdentifier().getText();
            if(r!=null && r.equals(((VerilogModule) elem ).getName())) return true;
        }
        return false;
    }

    public static Object[] getVariants(VerilogModuleName ref){
        return VerilogUtils.findModules(ref.getProject()).toArray();
    }
    public static ResolveResult[] multiResolve(VerilogModuleName ref, boolean incomplete){
        List<VerilogModule> l = VerilogUtils.findModules(ref.getProject(), ref.getIdentifier().getText());
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (VerilogModule m : l) {
            results.add(new PsiElementResolveResult(m));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    public static boolean isSoft(VerilogModuleName ref){
        return false;
    }

    public static String getName(VerilogModuleName ref){

        return ref.getIdentifier().getText();
    }

    public static PsiElement setName(VerilogModuleName ref, String newName){
        return handleElementRename(ref, newName);
    }

}
