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

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import edu.cooper.akhmetov.xidea.psi.VerilogModule;
import edu.cooper.akhmetov.xidea.psi.VerilogUdp;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class VerilogChooseByNameContributor implements ChooseByNameContributor {

    public static final String[] EMPTY_STRING_ARR = new String[0];
    public static final NavigationItem[] EMPTY_NAVITEM_ARRAY = new NavigationItem[0];

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        ArrayList<String> merged = new ArrayList<>();
        merged.addAll(VerilogUtils.findModules(project).stream().map(VerilogModule::getName).collect(Collectors.toList()));
        merged.addAll(VerilogUtils.findUDPs(project).stream().map(VerilogUdp::getName).collect(Collectors.toList()));
        return merged.toArray(EMPTY_STRING_ARR);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProj) {
        ArrayList<NavigationItem> merged = new ArrayList<>();
        merged.addAll(VerilogUtils.findModules(project, name));
        merged.addAll(VerilogUtils.findUDPs(project, name));
        return merged.toArray(EMPTY_NAVITEM_ARRAY);
    }
}
