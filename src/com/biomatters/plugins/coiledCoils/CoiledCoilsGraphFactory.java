package com.biomatters.plugins.coiledCoils;

import com.biomatters.geneious.publicapi.documents.AnnotatedPluginDocument;
import com.biomatters.geneious.publicapi.documents.PluginDocument;
import com.biomatters.geneious.publicapi.documents.sequence.AminoAcidSequenceDocument;
import com.biomatters.geneious.publicapi.documents.sequence.SequenceDocument;
import com.biomatters.geneious.publicapi.plugin.SequenceGraph;
import com.biomatters.geneious.publicapi.plugin.SequenceGraphFactory;
import com.biomatters.geneious.publicapi.utilities.CharSequenceUtilities;

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
 *          created on 24.01.2007 21:58:33
 */
class CoiledCoilsGraphFactory extends SequenceGraphFactory {
    private static final int MAX_SEQUENCE_LENGTH = 1000000;


    public SequenceGraph createResidueBasedGraph(SequenceDocument.Alphabet alphabet, boolean isAlignment, boolean isChromatogram, boolean isContig) {
        return null;
    }

    @Override public SequenceGraph createDocumentBasedGraphForSequence(AnnotatedPluginDocument annotatedDocument) {
        PluginDocument document = annotatedDocument.getDocumentOrNull();
        if (!(document instanceof SequenceDocument)) {
            return null;
        } else if (document instanceof AminoAcidSequenceDocument) {
            CharSequence sequence = ((SequenceDocument) document).getCharSequence();
            // Is it ok to scan the sequence for gaps like this, or does this need to be moved
            // into a background thread? Note that we're aiming to expand SequenceCharSequnece
            // to one day know about all (not only the terminal) gaps in a sequence, so that
            // the following call will then return very quickly.
            if (sequence.length() > MAX_SEQUENCE_LENGTH) {
                return null;
            } else if (CharSequenceUtilities.contains(sequence, '-')) {
                return null;
            } else {
                return new CoiledCoilsGraph(sequence);
            }
        } else {
            return null;
        }
    }
}
