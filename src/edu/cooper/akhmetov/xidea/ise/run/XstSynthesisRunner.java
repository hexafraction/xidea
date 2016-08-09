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
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.psi.*;
import edu.cooper.akhmetov.xidea.VerilogUtils;
import edu.cooper.akhmetov.xidea.psi.VerilogModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;


public class XstSynthesisRunner implements ProgramRunner<XstRunSettings> {
    @NotNull
    @Override
    public String getRunnerId() {
        return "XST_SYNTH";
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return executorId.equals(DefaultRunExecutor.EXECUTOR_ID) && profile instanceof XstRunConfiguration;
    }

    @Nullable
    @Override
    public XstRunSettings createConfigurationData(ConfigurationInfoProvider settingsProvider) {
        XstRunSettings xrs = new XstRunSettings();
        xrs.timestamp = System.currentTimeMillis();

        XstRunConfiguration config = (XstRunConfiguration) settingsProvider.getConfiguration();
        xrs.name = config.getName().replaceAll("[^a-zA-Z0-9]", "")
                .concat("-").concat(Integer.toHexString(config.getName().hashCode()))
                .concat("-").concat(Long.toString(xrs.timestamp));

        List<VerilogModule> topCandidates = VerilogUtils.findModules(config.getProject(),
                config.topModule);
        if(topCandidates.isEmpty()) {
            xrs.errors.add("Top module "+config.topModule+" not found.");
            return xrs;
        } else if (topCandidates.size()>1){
            xrs.warnings.add("Multiple definitions for top module "+config.topModule+" found.");
        }
        VerilogModule top = topCandidates.get(0);
        xrs.sourceFiles = VerilogUtils.getFileDependencies(top).stream().map(PsiFileSystemItem::getName).collect(Collectors.toSet());
        return xrs;
    }

    @Override
    public void checkConfiguration(RunnerSettings settings, @Nullable ConfigurationPerRunnerSettings configurationPerRunnerSettings) throws RuntimeConfigurationException {
        System.out.println("checkconfig settings = [" + settings + "], configurationPerRunnerSettings = [" + configurationPerRunnerSettings + "]");
    }

    @Override
    public void onProcessStarted(RunnerSettings settings, ExecutionResult executionResult) {
        System.out.println("onprocstart settings = [" + settings + "], executionResult = [" + executionResult + "]");
    }

    @Nullable
    @Override
    public SettingsEditor<XstRunSettings> getSettingsEditor(Executor executor, RunConfiguration configuration) {
        System.out.println("getseteditor executor = [" + executor + "], configuration = [" + configuration + "]");
        return null;
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment environment) throws ExecutionException {
        System.out.println("exec environment = [" + environment + "]");
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment environment, @Nullable Callback callback) throws ExecutionException {
        System.out.println("exec environment = [" + environment + "], callback = [" + callback + "]");
    }


}
