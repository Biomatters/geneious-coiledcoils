package com.biomatters.plugins.coiledCoils;

import com.biomatters.geneious.publicapi.plugin.GeneiousGraphics2D;
import com.biomatters.geneious.publicapi.plugin.Options;
import com.biomatters.geneious.publicapi.plugin.SequenceGraph;
import jebl.util.ProgressListener;
import org.virion.jam.util.SimpleListener;

import java.awt.*;

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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Detects coiled coils based on Lupas (1991)'s algorithm.
 *
 * @author Tobias Thierer
 * @version $Id: CoiledCoilsGraph.java 43609 2011-07-28 03:19:13Z matthew $
 *          <p/>
 *          created on 24.01.2007 23:50:26
 */
class CoiledCoilsGraph extends SequenceGraph {
    private final int MAX_GRAPH_COUNT = 3;
    private double[][] scores = null;
    private final Color[] colors = new Color[] { Color.GREEN, Color.BLUE, Color.RED };
    private final int numGraphs;
    private final CharSequence sequence;
    private final int sequenceLength;
    private final CoiledCoilsGraphOptions options;

    CoiledCoilsGraph(CharSequence sequence) {
        options = new CoiledCoilsGraphOptions();
        options.addChangeListener(new SimpleListener() {
            public void objectChanged() {
                synchronized(CoiledCoilsGraph.this) {
                    scores = null;
                    setScores = false;
                }
            }
        });
        this.numGraphs = MAX_GRAPH_COUNT;
        this.sequence = sequence;
        this.sequenceLength = sequence.length();
        if (numGraphs > MAX_GRAPH_COUNT) {
            throw new IllegalArgumentException("Can't paint " + scores.length + " graphs");
        }
    }

    private double getScore(final int graphNumber, final int residue) {
        return scores == null? 0: scores[graphNumber][residue];
    }

    /**
     * Averaged CpG island classification for a range of residues.
     *
     * @param startResidue first residue to consider, inclusive (counting from 1)
     * @param endResidue last residue to consider, inclusive (counting from 1)
     * @return Averaged CpG island classification for a range of residues.
     */
    private Double getScore(final int graphNumber, int startResidue, int endResidue) {
        if (startResidue > endResidue) {
            throw new IllegalArgumentException(startResidue + " > " + endResidue);
        }
        startResidue = Math.max(0, startResidue);
        endResidue = Math.min(sequenceLength-1, endResidue);
        if (endResidue < startResidue) {
            return null;
        }
        if (startResidue == endResidue) {
            return getScore(graphNumber, startResidue); // for performance
        } else {
            double sum = 0.0;
            for (int i = startResidue; i <= endResidue; i++) {
                sum += getScore(graphNumber, i);
            }
            return sum / (1 + endResidue - startResidue);
        }
    }

    public boolean isProOnly() {
        return true;
    }


    public int getApproximateCalculationWorkRequiredPerResidue() {
        return 1;
    }

    private boolean setScores = false;
    @Override
    public synchronized void performBackgroundCalculations(ProgressListener progressListener) {
        if (!setScores) {
            scores = CoiledCoilsDetector.calculateProbs(sequence,  options);
            setScores = true;
        }
    }

    public void draw(GeneiousGraphics2D graphics, int startResidue, int endResidue, int startX, int startY, int endX, int endY, double averageResidueWidth, int previousSectionWidth, int nextSectionWidth, int previousSectionResidueCount, int nextSectionResidueCount) {
        performBackgroundCalculations(ProgressListener.EMPTY);

        for (int graphNumber=0; graphNumber < numGraphs; graphNumber++) {
            if (sequenceLength < CoiledCoilsDetector.getWindow(graphNumber)) {
                return;
            }
            double score = getScore(graphNumber, startResidue, endResidue);
            Color color = colors[graphNumber];
            graphics.setColor(color);
            final double height = (endY - startY) * score;            

            Double previousScore = getScore(graphNumber, startResidue- previousSectionResidueCount, startResidue-1);
            Double nextScore = getScore(graphNumber, endResidue+1, endResidue+ nextSectionResidueCount);
            final double heightPrevious = (endY - startY) * (previousScore!=null? previousScore:score);
            final double heightNext = (endY - startY) * (nextScore != null ? nextScore : score);
            int y = (int) (endY - height);
            int yPrevious = (int) (endY - heightPrevious);
            int yNext = (int) (endY - heightNext);
            int xCentre = (startX + endX) / 2;
            int previousX2=startX-1;
            int previousX1=startX-previousSectionWidth;
            int previousXCentre=(previousX1+ previousX2)/2;
            int nextX1 = endX + 1;
            int nextX2 = endX + nextSectionWidth;
            int nextXCentre = (nextX1 + nextX2) / 2;
            graphics.drawLine(previousXCentre, yPrevious, xCentre, y);
            graphics.drawLine(xCentre, y,nextXCentre,yNext);

            /*
            int y = (int) (endY - height);
            graphics.drawLine(startX, y, endX, y);*/
        }
    }

    @Override
    public Options getOptions() {
        return options;
    }

    public String getName() {
        return "Coiled Coils";
    }
}
