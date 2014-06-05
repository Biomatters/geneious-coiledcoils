package com.biomatters.plugins.coiledCoils;

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
 * This code was written based on the COILS2 pascal program. The pascal source is in the docs folder for this plugin.
 * It's written in some weird dialect of Pascal (HP-Pascal?)
 * so I modified it until it compiled and didn't segfault, making sure it still gave the right results
 * (based on the implementation (NCOILS) at http://www.ch.embnet.org/software/COILS_form.html)-
 * you can see the result as coils2_actually_compiles.pas.  Download dev-pascal and compile it through there if you need to.
 *
 * This class started out as a pretty faithful translation of the pascal program,
 * but I refactored it a bit after that to remove unnecessary bits.
 * But mostly I've left the variable names the same so it's easy to see the correlation to the pascal.
 *
 * @author Amy Wilson
 * @version $Id$
 *          <p/>
 *          Created on 26/11/2009 12:22:23 PM
 */
class CoiledCoilsDetector {
    private CoiledCoilsDetector() {}

    private static final int MIN_WINDOW_SIZE = 14;
    private static final int WINDOW_COUNT = 3;
    private static final int WINDOW_STEP = 7;

    private static class Weights {
        final int hept_weight;
        final double ad_weight;
        final boolean isAd;

        private Weights(int hept_weight, double ad_weight, boolean isAd) {
            this.hept_weight = hept_weight;
            this.ad_weight = ad_weight;
            this.isAd = isAd;
        }
    }

    private static final String aminosInOrder = "livmfygakrhedqnstcwp";

    static double[][] calculateProbs(CharSequence sequence, CoiledCoilsGraphOptions options) {
        double[][] chartValue = Matrices.getMatrix(options.useMtidkMatrix());
        Weights weights = options.useAdWeight25()? new Weights(10, 2.5, true): new Weights(7, 1.0, false);
        return calculateProbs(sequence, chartValue, ProbabilityOptions.get(options.useMtidkMatrix(), options.useAdWeight25()), weights);
    }

    private static double[][] calculateProbs(CharSequence residue, double[][] chartValue, ProbabilityOptions[] probabilityOptions, Weights weights) {
        if (residue.length() >= MIN_WINDOW_SIZE) {
            double[][] results = calculate(residue, chartValue, weights);
            return column_probs(results, probabilityOptions, weights.isAd);
        } else {
            return null;
        }
    }

    private static double[][] calculate(CharSequence sequence, double[][] chartvalue, Weights weights) {
        double[][] tempcalc = getResults(sequence, chartvalue, weights);
        return maximise(sequence, tempcalc);
    }

    private static double[][] getResults(CharSequence sequence, double[][] chartvalue, Weights weights) {
        double[][] results = new double[WINDOW_COUNT][sequence.length()];

        for (int index = 0; index < WINDOW_COUNT; ++index) {
            int window = getWindow(index);
            if (window > sequence.length()) {
                return results;
            }
            int root = (window / WINDOW_STEP) * weights.hept_weight;
            int res_pos;
            for (res_pos = 0; res_pos + window < sequence.length() + 1; ++res_pos) {
                results[index][res_pos] = (double) 0;
                for (int heptad_pos = 0; heptad_pos <= 6; ++heptad_pos) {
                    double scores = 1;
                    for (int window_pos = 0; window_pos < window; ++ window_pos) {
                        int x = aminosInOrder.indexOf(Character.toLowerCase(sequence.charAt(window_pos + res_pos)));
                        if (x == -1) {
                            scores = 0;
                            continue;
                        }
                        int y = (window_pos + res_pos + heptad_pos) % WINDOW_STEP;
                        double weight;
                        if (y == 0 || y == 3) weight = weights.ad_weight;
                        else weight = 1;
                        scores*=(Math.pow(Math.pow(chartvalue[x][y],weight),(1.0/root)));
                    }

                    if (scores > results[index][res_pos]) {
                        results[index][res_pos] = scores;
                    }
                }
            }
            for (int extras = 0; extras < window - 1; ++extras) {
                results[index][(res_pos + extras)] = results[index][res_pos];
            }
        }
        return results;
    }

    static int getWindow(int index) {
        return MIN_WINDOW_SIZE + index*WINDOW_STEP;
    }

    private static double[][] maximise(CharSequence sequence, double[][] tempcalc) {
        double[][] results = new double[WINDOW_COUNT][sequence.length()];
        for (int index = 0; index < WINDOW_COUNT; ++index) {
            int window = getWindow(index);
            for (int res_pos = 0; res_pos < sequence.length(); ++res_pos) {
                results[index][res_pos] = tempcalc[index][res_pos];
                for (int window_pos = 1; window_pos < window; ++window_pos) {
                    if (res_pos < window_pos) {
                        window_pos = window - 1;
                    } else {
                        if (tempcalc[index][(res_pos - window_pos)] > results[index][res_pos]) {
                            results[index][res_pos] = tempcalc[index][(res_pos - window_pos)];
                        }
                    }
                }
            }
        }
        return results;
    }

    private static double[][] column_probs(double[][] calcnumb, ProbabilityOptions[] probabilityOptions, boolean use25AdWeight) {
        int sequenceLength = calcnumb[0].length;
        double[][] probs = new double[WINDOW_COUNT][sequenceLength];
        for (int res_pos = 0; res_pos < sequenceLength; ++res_pos) {
            for (int i = 0; i < WINDOW_COUNT; ++i) {
                boolean increaseToZero = use25AdWeight && calcnumb[i][res_pos] < 0;
                probs[i][res_pos] = increaseToZero? 0: probabilityOptions[i].calcprob(calcnumb[i][res_pos]);
            }
        }
        return probs;
    }
}
