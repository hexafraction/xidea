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

package edu.cooper.akhmetov.xidea.structure;


import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.projectRoots.Sdk;
import edu.cooper.akhmetov.xidea.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class XIdeaModuleType extends ModuleType {
    protected XIdeaModuleType(@NotNull @NonNls String id) {
        super(id);
    }

    public XIdeaModuleType(){
        this("XIDEA_MODULE");
    }

    @NotNull
    @Override
    public ModuleBuilder createModuleBuilder() {
        return new XIdeaModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "XIdea Module";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "XIdea modules may be used for FPGA synthesis followed by implementation, as well as for simulation.";
    }

    @Override
    public boolean isValidSdk(@NotNull Module module, @Nullable Sdk projectSdk) {
        return super.isValidSdk(module, projectSdk);
    }

    @Override
    public Icon getBigIcon() {
        return Icons.CHIP;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean isOpened) {
        return Icons.CHIP;
    }
}
