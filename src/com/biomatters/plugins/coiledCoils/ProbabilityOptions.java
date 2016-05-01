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
 * @author Amy Wilson
 *          <p/>
 *          Created on 27/11/2009 5:26:37 PM
 */
class ProbabilityOptions {
    final double meancc;
    final double stddevcc;
    final double meangl;
    final double stddevgl;
    final double ratio_gl_cc;

    private ProbabilityOptions(double meancc, double stddevcc, double meangl, double stddevgl, double ratio_gl_cc) {
        this.meancc = meancc;
        this.stddevcc = stddevcc;
        this.meangl = meangl;
        this.stddevgl = stddevgl;
        this.ratio_gl_cc = ratio_gl_cc;
    }

    double calcprob(double x) {
        // what wonderfully useful names (I don't know what this is doing
        // so it's still pretty much in the form it was in the pascal program)
        double prob1 = 0.5 * Math.pow(((x - meancc) / stddevcc), 2);
        double prob2 = 0.5 * Math.pow(((x - meangl) / stddevgl), 2);
        double prob3 = stddevgl * Math.exp(-prob1);
        double prob4 = ratio_gl_cc * stddevcc * Math.exp(-prob2);
        return prob3/(prob3 + prob4);
    }

    static ProbabilityOptions[] get(boolean useMtidkMatrix, boolean use25AdWeight) {
        if (useMtidkMatrix) {
            if(use25AdWeight) {
                return new ProbabilityOptions[] {
                        new ProbabilityOptions(1.88,0.34,1.00,0.33,20),
                        new ProbabilityOptions(1.76,0.28,0.86,0.26,25),
                        new ProbabilityOptions(1.70,0.24,0.79,0.23,30)
                };
            } else {
                return new ProbabilityOptions[] {
                        new ProbabilityOptions(1.79,0.30,0.94,0.29,20),
                        new ProbabilityOptions(1.70,0.25,0.83,0.24,25),
                        new ProbabilityOptions(1.63,0.22,0.77,0.20,30)};
            }
        } else {
            if (use25AdWeight) {
                return new ProbabilityOptions[] {
                        new ProbabilityOptions(1.89,0.30,1.04,0.27,20),
                        new ProbabilityOptions(1.79,0.24,0.92,0.22,25),
                        new ProbabilityOptions(1.74,0.20,0.86,0.18,30)
                };
            } else {
                return new ProbabilityOptions[] {
                        new ProbabilityOptions(1.82,0.28,0.95,0.26,20),
                        new ProbabilityOptions(1.74,0.23,0.86,0.21,25),
                        new ProbabilityOptions(1.69,0.18,0.80,0.18,30)
                };
            }
        }
    }
}

