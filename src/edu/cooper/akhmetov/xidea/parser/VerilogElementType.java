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

package edu.cooper.akhmetov.xidea.parser;

import com.intellij.psi.tree.IElementType;
import edu.cooper.akhmetov.xidea.VerilogLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


public class VerilogElementType extends IElementType {
    public VerilogElementType(@NotNull @NonNls String debugName) {
        super(debugName, VerilogLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "VerilogTokenType." + super.toString();
    }
}
