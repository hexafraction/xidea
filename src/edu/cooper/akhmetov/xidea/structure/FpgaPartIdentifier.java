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

import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;

public interface FpgaPartIdentifier extends JDOMExternalizable {
    @Override
    public default void readExternal(Element element) throws InvalidDataException {
        XmlSerializer.deserializeInto(this, element);
    }

    @Override
    public default void writeExternal(Element element) throws WriteExternalException {
        XmlSerializer.serializeInto(this, element);
    }

    // e.g. xc3s500e
    public String getID();

    // e.g. 4 or 1L
    public String getSpeedgrade();

    // e.g. Xilinx
    public String getManufacturer();

    // e.g. spartan3e
    public String getArchitecture();

    // e.g. ff1136
    public String getPackage();
}
