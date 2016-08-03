package edu.cooper.akhmetov.xidea.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import edu.cooper.akhmetov.xidea.VerilogFileType;
import edu.cooper.akhmetov.xidea.psi.*;

/**
 * Created by Andrey Akhmetov on 8/2/2016.
 */
public class VerilogElementFactory {
    public static VerilogModuleName createModuleName(Project p, String name){
        final VerilogFile f = createFile(p, "module "+ name + "; endmodule");
        VerilogModule vm = (VerilogModule) f.getFirstChild();
        return vm.getModuleName();
    }

    public static VerilogFile createFile(Project project, String text) {
        String name = "dummy.v";
        return (VerilogFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, VerilogFileType.INSTANCE, text);
    }


    public static VerilogUdpName createUdpName(Project p, String name) {
        final VerilogFile f = createFile(p, "primitive "+name+"(a); input a; table 0 : 1; endtable endprimitive");
        VerilogUdp vm = (VerilogUdp) f.getFirstChild();
        return vm.getUdpName();
    }
}
