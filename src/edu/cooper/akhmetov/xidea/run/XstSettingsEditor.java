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

package edu.cooper.akhmetov.xidea.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.sun.istack.internal.NotNull;
import edu.cooper.akhmetov.xidea.ise.run.XstRunConfiguration;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class XstSettingsEditor extends SettingsEditor<XstRunConfiguration> {
    private JPanel mainPanel;
    private LabeledComponent<TextFieldWithBrowseButton> topMdlLbl;
    private Project proj;
    private TextFieldWithBrowseButton topMdlField;

    @Override
    protected void resetEditorFrom(XstRunConfiguration conf) {
        proj = conf.getProject();
        topMdlField.getTextField().setText(conf.topModule);

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void applyEditorTo(XstRunConfiguration conf) throws ConfigurationException {
        conf.topModule = topMdlField.getTextField().getText();
    }


    @NotNull
    @Override
    protected JComponent createEditor() {

        return mainPanel;
    }

    private void createUIComponents() {
        topMdlLbl = new LabeledComponent<TextFieldWithBrowseButton>();
        topMdlLbl.setText("Top module: ");
        topMdlLbl.setLabelLocation("West");
        topMdlLbl.setComponent(buildTopModuleChooser());


    }

    private TextFieldWithBrowseButton buildTopModuleChooser() {
        topMdlField = new TextFieldWithBrowseButton.NoPathCompletion();
        topMdlField.getButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                HdlModuleChooserDialog topChooserDlg = new HdlModuleChooserDialog(proj);
                topChooserDlg.setTitle("Select Top Module...");
                topChooserDlg.setModal(true);
                topChooserDlg.show();
                if(topChooserDlg.isOK()){
                    topMdlField.setText(topChooserDlg.getSelection());
                }
            }
        });
        return topMdlField;
    }
}

