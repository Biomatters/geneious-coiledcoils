package com.biomatters.plugins.coiledCoils;

import jebl.math.MachineAccuracy;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
 * @version $Id$
 *          <p/>
 *          Created on 27/11/2009 3:09:05 PM
 */
public class CoiledCoilsDetectorTest extends TestCase {

    private static final String sequence = "ADDEYMALMSQKTLRYIRAPDEDVYVSPLNLIEVFMTPIFRILPPKRAKDLSYTVMTIVYSPFLLLISVKETREAR" +
                "RIKYNRMKRLNDDANEYDTPWDLTDGYLDDDDGLFSDNRNSGMRATQLKNSRSLKLQRTAEQEDVHFKVPKKWYKN" +
                "VKKCSPSFEQYDNDDTEDDAGEDKDEVKELTKKVENLTAVITDLLEKLDIKDKKE";
    private static final int WINDOW_COUNT = 3;
    public void testMtk() throws IOException {
        test(false, false);
    }

    public void testMtkAd25() throws IOException {
        test(false, true);
    }

    public void testMtidk() throws IOException {
        test(true, false);
    }

    public void testMtidkAd25() throws IOException {
        test(true, true);
    }

    private static void test(boolean mtidk, boolean ad25) throws IOException {
        double[][] results = CoiledCoilsDetector.calculateProbs(sequence, getOptions(mtidk, ad25));
        double[][] expectedResults = getExpectedResults(mtidk, ad25);
        for (int i = 0; i < WINDOW_COUNT; ++i) {
            for (int j = 0; j < sequence.length(); ++j) {
                assertTrue(MachineAccuracy.same(results[i][j], expectedResults[i][j]));
            }
        }
    }

    private static CoiledCoilsGraphOptions getOptions(final boolean mtidk, final boolean ad25) {
         return new CoiledCoilsGraphOptions() {
            @Override
            boolean useMtidkMatrix() {
                return mtidk;
            }

            @Override
            boolean useAdWeight25() {
                return ad25;
            }
        };
    }

    private static double[][] getExpectedResults(boolean mtidk, boolean ad25) throws IOException {
        String filename = (mtidk ? "MTIDK" : "MTK") + (ad25 ? "_25" : "") + ".txt";
        File file = new File(CoiledCoilsDetector.class.getResource(filename).getFile().replace("%20", " "));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        double[][] expectedResults = new double[WINDOW_COUNT][sequence.length()];
        for (int i = 0; i < WINDOW_COUNT; ++i) {
            String[] tokens = reader.readLine().split("\\s+");
            for (int j =0 ; j < sequence.length(); ++j) {
                try {
                expectedResults[i][j] = Double.parseDouble(tokens[j]);
                } catch (NumberFormatException e) {
                    throw e;
                }
            }
        }
        return expectedResults;
    }

}
