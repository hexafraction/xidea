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
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VfsBundle;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

/**
 * Created by Andrey Akhmetov on 8/8/2016.
 */
public class XIdeaModuleBuilder extends ModuleBuilder {

    private final XIdeaModuleType xIdeaModuleType = new XIdeaModuleType();

    @Override
    public void setupRootModel(ModifiableRootModel mrm) throws ConfigurationException {

        VirtualFile moduleDir = createSubdir(mrm.getProject().getBaseDir(), mrm.getModule().getName());

        ContentEntry mainCE = mrm.addContentEntry(moduleDir);
        VirtualFile rtlDir = createSubdir(moduleDir, "rtl");
        VirtualFile testDir = createSubdir(moduleDir, "test");
        VirtualFile constraintDir = createSubdir(moduleDir, "constrs");


        mainCE.addExcludeFolder("build");
        mainCE.addSourceFolder(rtlDir, RtlModuleSourceRootType.RTL_SOURCE);
        mainCE.addSourceFolder(testDir, RtlModuleSourceRootType.RTL_TEST_SOURCE);
        mainCE.addSourceFolder(constraintDir, ConstraintModuleSourceRootType.IMPLEMENTATION_CONSTRAINTS);
    }

    VirtualFile createSubdir(VirtualFile parent, String name) throws ConfigurationException {
        if (!parent.isDirectory())
            throw new ConfigurationException("A file was encountered where a directory was expected.");
        if (!parent.isValid()) {
            throw new ConfigurationException(VfsBundle.message("invalid.directory.create.files"));
        }

        if (!parent.getFileSystem().isValidName(name)) {
            throw new ConfigurationException(VfsBundle.message("directory.invalid.name.error", name));
        }

        VirtualFile child = parent.findChild(name);
        if (child != null) {
            return child;
        }

        try {
            return ApplicationManager.getApplication().runWriteAction(
                    (ThrowableComputable<VirtualFile, IOException>) () -> parent.createChildDirectory(null, name));
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }

    }

    @Override
    public ModuleType getModuleType() {
        return xIdeaModuleType;
    }
}
