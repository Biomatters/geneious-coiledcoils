package com.biomatters.plugins.coiledCoils;

import com.biomatters.geneious.publicapi.plugin.Options;

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
 * @version $Id: CoiledCoilsGraphOptions.java 43609 2011-07-28 03:19:13Z matthew $
 *          <p/>
 *          created on 17.02.2007 20:10:39
 */
class CoiledCoilsGraphOptions extends Options {
    private static class ScoreMatrixChoice extends OptionValue {
        ScoreMatrixChoice(String name, String label) {
            super(name, label);
        }
    }
    private static final ScoreMatrixChoice mtidk = new ScoreMatrixChoice("mtidk", "MTIDK");
    private static final ScoreMatrixChoice mtk = new ScoreMatrixChoice("mtk", "MTK");

    private final ComboBoxOption<ScoreMatrixChoice> scoreMatrixOption;
    private final BooleanOption adWeight25Option;

    CoiledCoilsGraphOptions() {
        super(CoiledCoilsGraph.class);
        scoreMatrixOption = addComboBoxOption("matrix","Score matrix",
                new ScoreMatrixChoice[] { mtidk, mtk}, mtidk);
        adWeight25Option = addBooleanOption("adWeight25", "2.5x weight heptad positions A&D", true);
        adWeight25Option.setSpanningComponent(true);
    }

    boolean useMtidkMatrix() {
        return scoreMatrixOption.getValue().equals(mtidk);
    }

    boolean useAdWeight25() {
        return adWeight25Option.getValue();
    }
}
