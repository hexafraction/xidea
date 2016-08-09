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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import edu.cooper.akhmetov.xidea.psi.*;

import java.util.*;
import java.util.stream.Collectors;


public class VerilogUtils {
    public static List<VerilogModule> findModules(Project project, String key) {
        List<VerilogModule> result = null;
        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, VerilogFileType.INSTANCE,
                        GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            VerilogFile verFile = (VerilogFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (verFile != null) {
                VerilogDescription[] descriptions = PsiTreeUtil.getChildrenOfType(verFile, VerilogDescription.class);
                // System.out.println(virtualFile+":"+(descriptions==null?"null":descriptions.length));
                if (descriptions != null) {
                    for (VerilogDescription desc : descriptions) {
                        VerilogModule mdl = desc.getModule();
                        // System.out.println("Found "+mdl.getName()+" in "+virtualFile);
                        if (mdl != null && key.equals(mdl.getName())) {
                            if (result == null) {
                                result = new ArrayList<VerilogModule>();
                            }
                            result.add(mdl);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<VerilogModule>emptyList();
    }

    public static List<VerilogModule> findModules(Project project) {
        List<VerilogModule> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, VerilogFileType.INSTANCE,
                        GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            VerilogFile verFile = (VerilogFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (verFile != null) {
                VerilogDescription[] descriptions = PsiTreeUtil.getChildrenOfType(verFile, VerilogDescription.class);
                if (descriptions != null) {
                    for (VerilogDescription desc : descriptions) {
                        VerilogModule module = desc.getModule();
                        if (module != null) result.add(module);
                    }
                }
            }
        }
        return result;
    }

    public static List<VerilogUdp> findUDPs(Project project, String key) {
        List<VerilogUdp> result = null;
        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, VerilogFileType.INSTANCE,
                        GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            VerilogFile verFile = (VerilogFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (verFile != null) {
                VerilogDescription[] descriptions = PsiTreeUtil.getChildrenOfType(verFile, VerilogDescription.class);
                if (descriptions != null) {
                    for (VerilogDescription desc : descriptions) {
                        VerilogUdp udp = desc.getUdp();
                        if (udp != null && key.equals(udp.getName())) {
                            if (result == null) {
                                result = new ArrayList<VerilogUdp>();
                            }
                            result.add(udp);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<VerilogUdp>emptyList();
    }

    public static List<VerilogUdp> findUDPs(Project project) {
        List<VerilogUdp> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, VerilogFileType.INSTANCE,
                        GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            VerilogFile verFile = (VerilogFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (verFile != null) {
                VerilogDescription[] descriptions = PsiTreeUtil.getChildrenOfType(verFile, VerilogDescription.class);
                if (descriptions != null) {
                    for (VerilogDescription desc : descriptions) {
                        VerilogUdp udp = desc.getUdp();
                        if (udp != null) result.add(udp);
                    }
                }
            }
        }
        return result;
    }

    public static List<VerilogVariableName> findDeclaredVariables(VerilogDescription desc) {
        VerilogModule mdl = desc.getModule();
        VerilogUdp udp = desc.getUdp();
        if (mdl != null) return findDeclaredVariables(mdl);
        else if (udp != null) return findDeclaredVariables(udp);
        else return Collections.EMPTY_LIST;
    }

    public static List<VerilogVariableName> findDeclaredVariables(VerilogModule mdl) {
        ArrayList<VerilogVariableName> vars = new ArrayList<>();
        VerilogModuleItem[] mdItems = PsiTreeUtil.getChildrenOfType(mdl, VerilogModuleItem.class);
        if (mdl.getPortlist() != null) {

            VerilogPort[] ports = PsiTreeUtil.getChildrenOfType(mdl.getPortlist(), VerilogPort.class);
            if (ports != null) {
                for (VerilogPort port : ports) {
                    if (port.getNewPort() != null) {
                        vars.add(port.getNewPort().getVariableName());
                    }
                }
            }
        }
        if (mdItems == null) return vars;
        for (VerilogModuleItem itm : mdItems) {
            PsiElement item = itm.getFirstChild();
            // module_item ::= param_decl|input_decl|output_decl
            //                  |inout_decl|net_decl|reg_decl|time_decl
            //                  |int_decl|real_decl|event_decl|gate_decl
            //                  |udp_instantiation|module_instantiation|param_ovrd
            //                  |cont_assign|spec_block|init_stmt|always_stmt
            //                  |task|function
            fillFromItem(vars, item);
        }
        return vars;
    }

    public static List<VerilogModuleInstantiation> findModuleDependencies(VerilogModule mdl) {
        ArrayList<VerilogModuleInstantiation> vars = new ArrayList<>();
        VerilogModuleItem[] mdItems = PsiTreeUtil.getChildrenOfType(mdl, VerilogModuleItem.class);
        if (mdItems == null) {

        } else {
            for(int i = 0; i < mdItems.length; i++){
                if(mdItems[i].getModuleInstantiation()!=null){
                    vars.add(mdItems[i].getModuleInstantiation());
                }
            }
        }
        return vars;
    }

    public static List<VerilogUdpInstantiation> findUdpDependencies(VerilogModule mdl) {
        ArrayList<VerilogUdpInstantiation> vars = new ArrayList<>();
        VerilogModuleItem[] mdItems = PsiTreeUtil.getChildrenOfType(mdl, VerilogModuleItem.class);
        if (mdItems == null) {

        } else {
            for(int i = 0; i < mdItems.length; i++){
                if(mdItems[i].getUdpInstantiation()!=null){
                    vars.add(mdItems[i].getUdpInstantiation());
                }
            }
        }
        return vars;
    }

    public static Set<PsiFile> getFileDependencies(VerilogModule root){
        HashSet<PsiFile> files = new HashSet<>();
        HashSet<VerilogModule> visited = new HashSet<>();
        HashSet<VerilogUdp> udps = new HashSet<>();
        PsiFile rootFile = root.getContainingFile();
        recurseFileDependencies(root, visited, udps);
        visited.stream().map(VerilogModule::getContainingFile).forEach(files::add);
        udps.stream().map(VerilogUdp::getContainingFile).forEach(files::add);
        return files;
    }

    private static void recurseFileDependencies(VerilogModule root, Set<VerilogModule> visited, Set<VerilogUdp> udps) {
        findModuleDependencies(root).stream().map(vim -> findModules(root.getProject(), vim.getModuleName().getText()))
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream().filter(visited::add).forEach(vm -> recurseFileDependencies(vm, visited, udps));
        findUdpDependencies(root).stream().map(vim -> findModules(root.getProject(), vim.getUdpName().getText()))
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream().filter(visited::add).forEach(vm -> recurseFileDependencies(vm, visited, udps));
        findUdpDependencies(root).stream().map(vim -> findUDPs(root.getProject(), vim.getUdpName().getText()))
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream().forEach(udps::add);
    }



    public static List<VerilogVariableName> findDeclaredVariables(VerilogUdp udp) {
        ArrayList<VerilogVariableName> vars = new ArrayList<>();
        VerilogUdpDecl[] declarations = PsiTreeUtil.getChildrenOfType(udp, VerilogUdpDecl.class);

        if (declarations == null) return vars;
        for (VerilogUdpDecl itm : declarations) {
            PsiElement item = itm.getFirstChild();
            // udp_decl ::= output_decl|reg_decl|input_decl
            fillFromItem(vars, item);
        }
        return vars;
    }

    private static void fillFromItem(ArrayList<VerilogVariableName> vars, PsiElement item) {
        // module_item ::= param_decl|input_decl|output_decl
        //                  |inout_decl|net_decl|reg_decl|time_decl
        //                  |int_decl|real_decl|event_decl|gate_decl
        //                  |udp_instantiation|module_instantiation|param_ovrd
        //                  |cont_assign|spec_block|init_stmt|always_stmt
        //                  |task|function
        if (item instanceof VerilogParamDecl) {
            VerilogParamAssign[] vpds = PsiTreeUtil.getChildrenOfType(item, VerilogParamAssign.class);
            if (vpds != null) {
                for (VerilogParamAssign vpa : vpds) {
                    vars.add(vpa.getVariableName());
                }
            }
        } else if (item instanceof VerilogInputDecl) {
            fillFromVarlistAssign(vars, ((VerilogInputDecl) item).getVarlistAssign());
        } else if (item instanceof VerilogInoutDecl) {
            fillFromVarlistAssign(vars, ((VerilogInoutDecl) item).getVarlistAssign());
        } else if (item instanceof VerilogOutputDecl) {
            fillFromVarlistAssign(vars, ((VerilogOutputDecl) item).getVarlistAssign());
        } else if (item instanceof VerilogNetDecl) {
            VerilogVarlist varlist = ((VerilogNetDecl) item).getVarlist();
            VerilogVariableName[] vvns = PsiTreeUtil.getChildrenOfType(varlist, VerilogVariableName.class);
            if (vvns != null) {
                Collections.addAll(vars, vvns);
            }
        } else if (item instanceof VerilogIntDecl) {
            fillFromRegvarList(vars, ((VerilogIntDecl) item).getRegvarList());
        } else if (item instanceof VerilogRegDecl) {
            fillFromRegvarList(vars, ((VerilogRegDecl) item).getRegvarList());
        } else if (item instanceof VerilogTimeDecl) {
            fillFromRegvarList(vars, ((VerilogTimeDecl) item).getRegvarList());
        } else if (item instanceof VerilogRealDecl) {
            fillFromRegvarList(vars, ((VerilogRealDecl) item).getRegvarList());
        } else if (item instanceof VerilogEventDecl) {
            VerilogVariableName[] vvns = PsiTreeUtil.getChildrenOfType(item, VerilogVariableName.class);
            if (vvns != null) {
                Collections.addAll(vars, vvns);
            }
        } else if (item instanceof VerilogContAssign) {
            VerilogContAssign vca = (VerilogContAssign) item;
            VerilogAssignList val = vca.getAssignList();
            for (VerilogAssignment va : val.getAssignmentList()) {
                VerilogLvalue lvalue = va.getLvalue();
                if (lvalue.getVariableName() != null && lvalue.getRange() == null && lvalue.getExpr() == null) {
                    vars.add(lvalue.getVariableName());
                }
            }
        }
    }

    private static void fillFromRegvarList(ArrayList<VerilogVariableName> vars, VerilogRegvarList vrvl) {
        VerilogRegvar[] vrvs = PsiTreeUtil.getChildrenOfType(vrvl, VerilogRegvar.class);
        if (vrvs != null) {
            for (VerilogRegvar vrv : vrvs) {
                vars.add(vrv.getVariableName());
            }
        }
    }

    private static void fillFromVarlistAssign(ArrayList<VerilogVariableName> vars, VerilogVarlistAssign vva) {
        if (vva.getVarlist() != null) {
            VerilogVariableName[] vvns = PsiTreeUtil.getChildrenOfType(vva.getVarlist(), VerilogVariableName.class);
            if (vvns != null) {
                Collections.addAll(vars, vvns);
            }
        } else if (vva.getVarlistFancy() != null) {
            VerilogAssignList val = vva.getVarlistFancy().getAssignList();
            VerilogAssignment[] vas = PsiTreeUtil.getChildrenOfType(val, VerilogAssignment.class);
            if (vas != null) {
                for (VerilogAssignment va : vas) {
                    VerilogVariableName var = va.getLvalue().getVariableName();
                    if (var != null) vars.add(var);
                }
            }
        }
    }


}
