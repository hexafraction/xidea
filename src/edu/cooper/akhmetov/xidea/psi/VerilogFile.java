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

package edu.cooper.akhmetov.xidea.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import edu.cooper.akhmetov.xidea.VerilogFileType;
import edu.cooper.akhmetov.xidea.VerilogLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class VerilogFile extends PsiFileBase {
    public VerilogFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, VerilogLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return VerilogFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Verilog File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}