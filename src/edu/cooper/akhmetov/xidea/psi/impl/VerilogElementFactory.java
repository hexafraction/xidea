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
