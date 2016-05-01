package com.biomatters.plugins.coiledCoils;

import com.biomatters.geneious.publicapi.plugin.GeneiousPlugin;
import com.biomatters.geneious.publicapi.plugin.SequenceGraphFactory;

/**
 * Copyright (C) 2009-2014, Biomatters Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @author Tobias Thierer
 *          <p/>
 *          created on 24.01.2007 21:42:41
 */
public class CoiledCoilsPlugin extends GeneiousPlugin {

    /**
     * 1.1 Released 2009.12.02, now actually returns the same results as coils2
     * 1.1.1 Released 2009.12.04, fix bug where it crashes on sequences < 28 residues
     */
    public static final String PLUGIN_VERSION = "1.1.1";

    public String getAuthors() {
        return "Tobias Thierer and Biomatters Ltd";
    }

    public String getDescription() {
        return "Predicts coiled coils in amino acid sequences";
    }

    public String getHelp() {
        return null;
    }

    public int getMaximumApiVersion() {
        return 4;
    }

    public String getMinimumApiVersion() {
        return "4.6";
    }

    public String getName() {
        return "Coiled coils prediction";
    }

    public String getVersion() {
        return PLUGIN_VERSION;
    }

    @Override
    public SequenceGraphFactory[] getSequenceGraphFactories() {
        return new SequenceGraphFactory[] {
                new CoiledCoilsGraphFactory()
        };
    }
}
