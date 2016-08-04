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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.ListSpeedSearch;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.Function;
import edu.cooper.akhmetov.xidea.VerilogUtils;
import edu.cooper.akhmetov.xidea.psi.VerilogModule;
import edu.cooper.akhmetov.xidea.psi.VerilogUdp;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class HdlModuleChooserDialog extends DialogWrapper{
    private final Project proj;

    public HdlModuleChooserDialog(Project proj) {
        super(proj);
        this.proj = proj;
        this.init();
    }
    String selected;
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        List<VerilogModule> mdls = VerilogUtils.findModules(proj);
        List<String> names = mdls.stream().map(VerilogModule::getName).collect(Collectors.toList());
        JBList jbList = new JBList(names);
        jbList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selected = jbList.getSelectedValue().toString();
            }
        });

        jbList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JBList list = (JBList)evt.getSource();
                if (evt.getClickCount() == 2) {

                    int index = list.locationToIndex(evt.getPoint());
                    selected = names.get(index);
                    HdlModuleChooserDialog.this.close(0, true);
                }
            }
        });
        ListSpeedSearch lss = new ListSpeedSearch(jbList, (Function<Object, String>) Object::toString);
        JBScrollPane jbsp = new JBScrollPane(jbList);
        jbsp.setMaximumSize(new Dimension(160, 100));
        return jbsp;
    }

    public String getSelection() {
        return selected;
    }
}
