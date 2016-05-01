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
 *          Created on 27/11/2009 5:27:33 PM
 */
public class Matrices {


    static double[][] getMatrix(boolean useMtidkMatrix) {
        return useMtidkMatrix? mtidk: mtk;
    }
    // from Lupas et al. (1991), Predicting coiled coils from protein sequences.
    // Science. 252(5010):1162-4.
    private static double[][] mtidk = new double[][] {
            {2.998, 0.269, 0.367, 3.852, 0.51, 0.514, 0.562},
            {2.408, 0.261, 0.345, 0.931, 0.402, 0.44, 0.289},
            {1.525, 0.479, 0.35, 0.887, 0.286, 0.35, 0.362},
            {2.161, 0.605, 0.442, 1.441, 0.607, 0.457, 0.57},
            {0.49, 0.075, 0.391, 0.639, 0.125, 0.081, 0.038},
            {1.319, 0.064, 0.081, 1.526, 0.204, 0.118, 0.096},
            {0.084, 0.215, 0.432, 0.111, 0.153, 0.367, 0.125},
            {1.283, 1.364, 1.077, 2.219, 0.49, 1.265, 0.903},
            {1.233, 2.194, 1.817, 0.611, 2.095, 1.686, 2.027},
            {1.014, 1.476, 1.771, 0.114, 1.667, 2.006, 1.844},
            {0.59, 0.646, 0.584, 0.842, 0.307, 0.611, 0.396},
            {0.281, 3.351, 2.998, 0.789, 4.868, 2.735, 3.812},
            {0.068, 2.103, 1.646, 0.182, 0.664, 1.581, 1.401},
            {0.311, 2.29, 2.33, 0.811, 2.596, 2.155, 2.585},
            {1.231, 1.683, 2.157, 0.197, 1.653, 2.43, 2.065},
            {0.332, 0.753, 0.93, 0.424, 0.734, 0.801, 0.518},
            {0.197, 0.543, 0.647, 0.68, 0.905, 0.643, 0.808},
            {0.918, 0.0020, 0.385, 0.44, 0.138, 0.432, 0.079},
            {0.066, 0.064, 0.065, 0.747, 0.0060, 0.115, 0.014},
            {0.0040, 0.108, 0.018, 0.0060, 0.01, 0.0040, 0.0070},
    };

    // Table from Lupas et al. (1991); see also p. 160 of Huson's
    // 07_protstruct2d.ps.gz (Algorithms in Bioinformatics I script,
    // University of Tuebingen, WS 02/03, 5 February 2003)
    // Holds the frequencz of each of the 20 standard amino acids
    // occurring at each of the 7 heptad positions.
    private static double[][] mtk = new double[][] {
            {3.167, 0.297, 0.398, 3.902, 0.585, 0.501, 0.483},
            {2.597, 0.098, 0.345, 0.894, 0.514, 0.471, 0.431},
            {1.665, 0.403, 0.386, 0.949, 0.211, 0.342, 0.36},
            {2.24, 0.37, 0.48, 1.409, 0.541, 0.772, 0.663},
            {0.531, 0.076, 0.403, 0.662, 0.189, 0.106, 0.013},
            {1.417, 0.09, 0.122, 1.659, 0.19, 0.13, 0.155},
            {0.045, 0.275, 0.578, 0.216, 0.211, 0.426, 0.156},
            {1.297, 1.551, 1.084, 2.612, 0.377, 1.248, 0.877},
            {1.375, 2.639, 1.763, 0.191, 1.815, 1.961, 2.795},
            {0.659, 1.163, 1.21, 0.031, 1.358, 1.937, 1.798},
            {0.347, 0.275, 0.679, 0.395, 0.294, 0.579, 0.213},
            {0.262, 3.496, 3.108, 0.998, 5.685, 2.494, 3.048},
            {0.03, 2.352, 2.268, 0.237, 0.663, 1.62, 1.448},
            {0.179, 2.114, 1.778, 0.631, 2.55, 1.578, 2.526},
            {0.835, 1.475, 1.534, 0.039, 1.722, 2.456, 2.28},
            {0.382, 0.583, 1.052, 0.419, 0.525, 0.916, 0.628},
            {0.169, 0.702, 0.955, 0.654, 0.791, 0.843, 0.647},
            {0.824, 0.022, 0.308, 0.152, 0.18, 0.156, 0.044},
            {0.24, 0.0, 0.0, 0.456, 0.019, 0.0, 0.0},
            {0.0, 0.0080, 0.0, 0.013, 0.0, 0.0, 0.0},
    };

    private Matrices() {
    }
}
