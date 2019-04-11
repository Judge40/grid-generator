/*
 * Grid Generator Copyright (c) 2019 Judge40
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.judge40.gridgenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.prefs.BackingStoreException;

/**
 * A helper with methods for performing grid draws.
 */
public class GridDrawHelper {

  /**
   * Draw the grids for a given class, organizing the class's participants in to races of equal
   * size.
   *
   * @param className The name of the class to perform a draw for.
   * @param excludedGrids The grid numbers which have been excluded.
   * @return A list of lists of participants representing the races that have been drawn.
   * @throws BackingStoreException If the class's participants could not be retrieved.
   * @throws IOException If the class's participants could not be retrieved.
   * @throws ClassNotFoundException If the class's participants could not be retrieved.
   */
  public static List<List<String>> drawGridsForClass(String className, Set<Integer> excludedGrids)
    throws BackingStoreException, IOException, ClassNotFoundException {
    List<String> participants = PreferenceHelper.getClassParticipants(className);

    int maxGrids = PreferenceHelper.getNumberOfGrids();
    int gridSize = maxGrids - excludedGrids.size();

    if (gridSize <= 0 || participants.isEmpty()) {
      return Collections.emptyList();
    }

    // Randomize the participants.
    randomizeParticipants(participants, excludedGrids);

    List<List<String>> races = splitParticipants(participants, gridSize);

    for (List<String> race : races) {
      // Pad the race's grids with empty values to fill the available grids.
      while (race.size() < gridSize) {
        race.add("");
      }

      // Randomize the race's participants.
      randomizeParticipants(race, excludedGrids);

      // Insert the excluded grids.
      for (int excludedGrid : excludedGrids) {
        race.add(excludedGrid - 1, "");
      }
    }

    return races;
  }

  /**
   * Splits the participants in to roughly equal races based on the number of available grids.
   *
   * @param participants The participants to split.
   * @param availableGrids The number of available grids.
   * @return A list of participant lists.
   */
  private static List<List<String>> splitParticipants(List<String> participants,
    int availableGrids) {
    if (availableGrids >= participants.size()) {
      return Collections.singletonList(participants);
    }

    int numberOfParticipants = participants.size();
    int numberOfRaces = (int) Math.ceil((double) numberOfParticipants / availableGrids);
    int baseSizeOfRace = numberOfParticipants / numberOfRaces;
    int remainder = numberOfParticipants % numberOfRaces;

    List<List<String>> splitParticipants = new ArrayList<>(numberOfRaces);

    for (int i = 0; i < numberOfParticipants; ) {
      int sizeOfRace = baseSizeOfRace + (remainder-- > 0 ? 1 : 0);
      List<String> subParticipants = participants.subList(i, i += sizeOfRace);
      splitParticipants.add(new ArrayList<>(subParticipants));
    }

    return splitParticipants;
  }

  /**
   * Randomize the participants list.
   *
   * @param participants The participants to randomize.
   * @param excludedGrids The grid numbers which have been excluded.
   */
  private static void randomizeParticipants(List<String> participants, Set<Integer> excludedGrids) {
    // Generate the seed based on variable factors and the current day.
    long daysSinceEpoch = LocalDate.now(ZoneId.of("Z")).toEpochDay();
    int seed = Objects.hash(participants, excludedGrids, daysSinceEpoch);

    // Sort and then shuffle the participants.
    Collections.sort(participants);
    Collections.shuffle(participants, new Random(seed));
  }
}
