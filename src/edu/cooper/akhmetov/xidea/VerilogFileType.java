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


import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class VerilogFileType extends LanguageFileType {
    public static final VerilogFileType INSTANCE = new VerilogFileType();

    private VerilogFileType() {
        super(VerilogLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Verilog File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Verilog source file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "v";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.FILE_V;
    }
}