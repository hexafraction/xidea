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

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import edu.cooper.akhmetov.xidea.VerilogUtils;
import edu.cooper.akhmetov.xidea.parser.VerilogTypes;
import edu.cooper.akhmetov.xidea.psi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //<editor-fold desc="Module name reference">
    public static PsiElement getElement(VerilogModuleName ref){
        return ref;
    }

    public static TextRange getRangeInElement(VerilogModuleName ref){
        return new TextRange(0, ref.getName()==null?0:ref.getName().length());
    }
    public static PsiElement resolve(VerilogModuleName ref){
        //System.out.println("called resolve!");
        //new Exception().printStackTrace();
       /* List<VerilogModule> mdls = VerilogUtils.findModules(ref.getProject(), ref.getIdentifier().getText());
        if(mdls.isEmpty()) return null;
        else return mdls.get(0);*/
        ResolveResult[] resolveResults = multiResolve(ref, false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    public static String getCanonicalText(VerilogModuleName ref){
        //System.out.println("canon: "+ref.getIdentifier().getText());
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
    public static boolean isReferenceTo(VerilogModuleName ref, PsiElement elem){
        if(elem instanceof VerilogModule){
            String r = ref.getIdentifier().getText();
            if(r!=null && r.equals(((VerilogModule) elem ).getName())) return true;
        }
        return false;
    }
    public static PsiElement setName(VerilogModuleName ref, String newName){
        return handleElementRename(ref, newName);
    }

    //</editor-fold>

    //<editor-fold desc="UDP name reference">
    public static PsiElement getElement(VerilogUdpName ref){
        return ref;
    }

    public static TextRange getRangeInElement(VerilogUdpName ref){
        return new TextRange(0, ref.getName()==null?0:ref.getName().length());
    }
    public static PsiElement resolve(VerilogUdpName ref){
        //System.out.println("called resolve!");
       // new Exception().printStackTrace();
       /* List<VerilogModule> mdls = VerilogUtils.findModules(ref.getProject(), ref.getIdentifier().getText());
        if(mdls.isEmpty()) return null;
        else return mdls.get(0);*/
        ResolveResult[] resolveResults = multiResolve(ref, false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    public static String getCanonicalText(VerilogUdpName ref){
        //System.out.println("canon: "+ref.getIdentifier().getText());
        return ref.getIdentifier().getText();
    }

    public static boolean isReferenceTo(VerilogUdpName ref, PsiElement elem){
        if(elem instanceof VerilogModule){
            String r = ref.getIdentifier().getText();
            if(r!=null && r.equals(((VerilogModule) elem ).getName())) return true;
        }
        else if(elem instanceof VerilogUdp){
            String r = ref.getIdentifier().getText();
            if(r!=null && r.equals(((VerilogModule) elem ).getName())) return true;
        }
        return false;
    }

    public static Object[] getVariants(VerilogUdpName ref){
        ArrayList<Object> union = new ArrayList<Object>();
        union.addAll(VerilogUtils.findUDPs(ref.getProject()));
        union.addAll(VerilogUtils.findModules(ref.getProject()));
        return union.toArray();
    }
    public static ResolveResult[] multiResolve(VerilogUdpName ref, boolean incomplete){
        List<VerilogModule> l = VerilogUtils.findModules(ref.getProject(), ref.getIdentifier().getText());
        List<VerilogUdp> l2 = VerilogUtils.findUDPs(ref.getProject(), ref.getIdentifier().getText());
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (VerilogModule m : l) {
            results.add(new PsiElementResolveResult(m));
        }
        for (VerilogUdp u : l2) {
            results.add(new PsiElementResolveResult(u));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    public static boolean isSoft(VerilogUdpName ref){
        return false;
    }

    public static String getName(VerilogUdpName ref){

        return ref.getIdentifier().getText();
    }

    public static PsiElement setName(VerilogUdpName ref, String newName){
        return handleElementRename(ref, newName);
    }

    public static PsiElement handleElementRename(VerilogUdpName ref, String name){
        ASTNode identNode = ref.getNode().findChildByType(VerilogTypes.IDENTIFIER);
        if (identNode != null) {
            ASTNode newIdent = VerilogElementFactory.createUdpName(ref.getProject(), name).getIdentifier().getNode();
            ref.getNode().replaceChild(identNode, newIdent);
        }
        return ref;
    }
    public static PsiElement bindToElement(VerilogUdpName ref, PsiElement elem){
        if((elem instanceof VerilogUdp)){
            String name = ((VerilogUdp) elem).getName();
            return handleElementRename(ref, name);
        } else if ((elem instanceof VerilogModule)){
            String name = ((VerilogModule) elem).getName();
            return handleElementRename(ref, name);
        } else throw new IncorrectOperationException();

    }




    //</editor-fold>

    //<editor-fold desc="Variable name reference">
    public static PsiElement getElement(VerilogVariableName ref){
        return ref;
    }

    public static TextRange getRangeInElement(VerilogVariableName ref){
        return new TextRange(0, ref.getName()==null?0:ref.getName().length());
    }
    public static PsiElement resolve(VerilogVariableName ref){
        //System.out.println("called resolve!");
       // new Exception().printStackTrace();
       /* List<VerilogModule> mdls = VerilogUtils.findModules(ref.getProject(), ref.getIdentifier().getText());
        if(mdls.isEmpty()) return null;
        else return mdls.get(0);*/
        ResolveResult[] resolveResults = multiResolve(ref, false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    public static String getCanonicalText(VerilogVariableName ref){
        //System.out.println("canon: "+ref.getIdentifier().getText());
        return ref.getIdentifier().getText();
    }

    public static boolean isReferenceTo(VerilogVariableName ref, PsiElement elem){
        //TODO TODO
        return false;
    }

    public static Object[] getVariants(VerilogVariableName ref){
        VerilogDescription desc = ref.getEnclosingDescription();
        if(desc==null) return new Object[0];
        return VerilogUtils.findDeclaredVariables(desc).toArray();
    }
    public static VerilogDescription getEnclosingDescription(VerilogVariableName ref){
        PsiElement elem = ref;
        while(elem!=null){
            if(elem instanceof VerilogDescription) return (VerilogDescription) elem;
            else elem = elem.getParent();
        }
        return null;
    }
    public static ResolveResult[] multiResolve(VerilogVariableName ref, boolean incomplete){
        String nn = ref.getIdentifier().getText();
        VerilogDescription desc = ref.getEnclosingDescription();
        if(desc==null) return new ResolveResult[0];
        List<VerilogVariableName> elems = VerilogUtils.findDeclaredVariables(desc).stream().filter(vvn -> nn.equals(vvn.getIdentifier().getText())).collect(Collectors.toList());
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (PsiElement m : elems) {
            results.add(new PsiElementResolveResult(m));
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    public static boolean isSoft(VerilogVariableName ref){
        return false;
    }

    public static String getName(VerilogVariableName ref){

        return ref.getIdentifier().getText();
    }

    public static PsiElement setName(VerilogVariableName ref, String newName){
        return handleElementRename(ref, newName);
    }

    public static PsiElement handleElementRename(VerilogVariableName ref, String name){
        ASTNode identNode = ref.getNode().findChildByType(VerilogTypes.IDENTIFIER);
        if (identNode != null) {
            ASTNode newIdent = VerilogElementFactory.createModuleName(ref.getProject(), name).getIdentifier().getNode();
            ref.getNode().replaceChild(identNode, newIdent);
        }
        return ref;
    }
    public static PsiElement bindToElement(VerilogVariableName ref, PsiElement elem){
        if((elem instanceof VerilogVariableName)){
            String name = ((VerilogVariableName) elem).getIdentifier().getText();
            return handleElementRename(ref, name);
        } else throw new IncorrectOperationException();

    }




    //</editor-fold>

}
