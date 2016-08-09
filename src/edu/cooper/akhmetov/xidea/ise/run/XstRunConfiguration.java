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

package edu.cooper.akhmetov.xidea.ise.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.JavaRunConfigurationExtensionManager;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.util.xmlb.XmlSerializer;
import edu.cooper.akhmetov.xidea.ise.XilinxIseSdk;
import edu.cooper.akhmetov.xidea.psi.VerilogModule;
import edu.cooper.akhmetov.xidea.psi.VerilogModuleName;
import edu.cooper.akhmetov.xidea.run.XstSettingsEditor;
import edu.cooper.akhmetov.xidea.structure.XideaFpgaSdk;
import org.jdom.Content;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


public class XstRunConfiguration extends ModuleBasedConfiguration implements RefactoringListenerProvider {

    public String topModule;

    public XstRunConfiguration(String name, @NotNull RunConfigurationModule configurationModule, @NotNull ConfigurationFactory factory) {
        super(name, configurationModule, factory);
    }

    public XstRunConfiguration(RunConfigurationModule configurationModule, ConfigurationFactory factory) {
        super(configurationModule, factory);
    }


    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new XstSettingsEditor();
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return null;
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);
        XmlSerializer.deserializeInto(this, element);
    }

    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        XmlSerializer.serializeInto(this, element);

    }

    @Nullable
    @Override
    public RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        if (element instanceof VerilogModuleName) {
            if (((VerilogModuleName) element).getName().equals(topModule)) {
                return new RefactoringElementListener() {
                    @Override
                    public void elementMoved(@NotNull PsiElement newElement) {
                        // pass
                    }

                    @Override
                    public void elementRenamed(@NotNull PsiElement newElement) {
                        if (newElement instanceof VerilogModuleName)
                            ((VerilogModuleName) element).handleElementRename(((VerilogModuleName) newElement).getName());
                    }
                };
            }
        }
        return null;

    }

    @Override
    public Collection<Module> getValidModules() {
        return Arrays.stream(ModuleManager.getInstance(getProject()).getModules())
                .filter(m -> m instanceof XideaFpgaSdk && ModuleRootManager.getInstance(m).getSdk() instanceof XilinxIseSdk)
                .collect(Collectors.toList());
    }
}