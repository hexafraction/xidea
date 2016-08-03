package edu.cooper.akhmetov.xidea;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import edu.cooper.akhmetov.xidea.psi.VerilogDescription;
import edu.cooper.akhmetov.xidea.psi.VerilogFile;
import edu.cooper.akhmetov.xidea.psi.VerilogModule;
import edu.cooper.akhmetov.xidea.psi.VerilogUdp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


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
                if (descriptions != null) {
                    for (VerilogDescription desc : descriptions) {
                        VerilogModule mdl = desc.getModule();
                        if (mdl!=null && key.equals(mdl.getName())) {
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
                    for(VerilogDescription desc : descriptions){
                        VerilogModule module = desc.getModule();
                        if(module != null) result.add(module);
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
                        if (udp!=null && key.equals(udp.getName())) {
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
                    for(VerilogDescription desc : descriptions){
                        VerilogUdp udp = desc.getUdp();
                        if(udp != null) result.add(udp);
                    }
                }
            }
        }
        return result;
    }
}
