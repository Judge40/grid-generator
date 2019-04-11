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
import java.util.stream.Collectors;

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
  public static List<List<List<String>>> drawGridsForClass(String className,
    Set<Integer> excludedGrids)
    throws BackingStoreException, IOException, ClassNotFoundException {
    int numberOfHeats = PreferenceHelper.getNumberOfHeats();
    List<List<List<String>>> heats = new ArrayList<>();

    for (int heat = 1; heat <= numberOfHeats; heat++) {
      heats.add(drawGridsForClassAndHeat(className, heat, excludedGrids));
    }

    return heats;
  }

  /**
   * Draw the grids for a given class, organizing the class's participants in to races of equal
   * size.
   *
   * @param className The name of the class to perform a draw for.
   * @param heatNumber The number of the heat to perform the draw for.
   * @param excludedGrids The grid numbers which have been excluded.
   * @return A list of lists of participants representing the races that have been drawn.
   * @throws BackingStoreException If the class's participants could not be retrieved.
   * @throws IOException If the class's participants could not be retrieved.
   * @throws ClassNotFoundException If the class's participants could not be retrieved.
   */
  private static List<List<String>> drawGridsForClassAndHeat(String className, int heatNumber,
    Set<Integer> excludedGrids) throws BackingStoreException, IOException, ClassNotFoundException {
    List<String> participants = PreferenceHelper.getClassParticipants(className);

    if (participants.isEmpty()) {
      return Collections.emptyList();
    }

    // Randomize the participants.
    randomizeParticipants(participants, heatNumber, excludedGrids);

    List<List<String>> races = splitCombinedParticipants(participants, excludedGrids.size());

    for (List<String> race : races) {
      // Randomize the race's participants.
      randomizeParticipants(race, heatNumber, excludedGrids);

      // Insert the excluded grids.
      for (int excludedGrid : excludedGrids) {
        race.add(excludedGrid - 1, "");
      }
    }

    return races;
  }

  /**
   * Splits the participants in to groups based on the grouping filter and then in to roughly equal
   * races based on the number of available grids, races will be padded with empty strings to fill
   * the available grids.
   *
   * @param participants The participants to split.
   * @param numberOfExcludedGrids The number of excluded grids.
   * @return A list of participant lists.
   */
  private static List<List<String>> splitCombinedParticipants(List<String> participants,
    int numberOfExcludedGrids) {

    // Check if a split needs to be done based on the grouping filter.
    String groupingFilter = PreferenceHelper.getParticipantGroupingFilter();
    int groupingThreshold = PreferenceHelper.getParticipantGroupingThreshold();

    List<String> ungroupedParticipants = participants.stream()
      .filter(participant -> !participant.matches(groupingFilter))
      .collect(Collectors.toList());

    List<List<String>> races = new ArrayList<>();

    // If there are enough ungrouped participants to meet the threshold then split them.
    if (!ungroupedParticipants.isEmpty() && ungroupedParticipants.size() >= groupingThreshold) {
      participants = new ArrayList<>(participants);
      participants.removeAll(ungroupedParticipants);
      races.addAll(splitGroupedParticipants(ungroupedParticipants, numberOfExcludedGrids));
    }

    races.addAll(0, splitGroupedParticipants(participants, numberOfExcludedGrids));
    return races;
  }

  /**
   * Splits the pre-grouped participants in to roughly equal races based on the number of available
   * grids, races will be padded with empty strings to fill the available grids.
   *
   * @param participants The participants to split.
   * @param numberOfExcludedGrids The number of excluded grids.
   * @return A list of participant lists.
   */
  private static List<List<String>> splitGroupedParticipants(List<String> participants,
    int numberOfExcludedGrids) {
    int numberOfGrids = PreferenceHelper.getNumberOfGrids();
    int numberOfAvailableGrids = numberOfGrids - numberOfExcludedGrids;

    // If there are no available grids or no participants return empty list.
    if (numberOfAvailableGrids <= 0 || participants.isEmpty()) {
      return Collections.emptyList();
    }

    // If there are enough grids without performing a split then return the whole list.
    if (numberOfAvailableGrids >= participants.size()) {
      participants = new ArrayList<>(participants);

      // Pad the race's grids with empty values to fill the available grids.
      while (participants.size() < numberOfAvailableGrids) {
        participants.add("");
      }

      return Collections.singletonList(participants);
    }

    int numberOfParticipants = participants.size();
    int numberOfRaces = (int) Math.ceil((double) numberOfParticipants / numberOfAvailableGrids);
    int baseSizeOfRace = numberOfParticipants / numberOfRaces;
    int remainder = numberOfParticipants % numberOfRaces;

    List<List<String>> splitParticipants = new ArrayList<>(numberOfRaces);

    for (int i = 0; i < numberOfParticipants; ) {
      int sizeOfRace = baseSizeOfRace + (remainder-- > 0 ? 1 : 0);
      List<String> subParticipants = new ArrayList<>(participants.subList(i, i += sizeOfRace));

      // Pad the race's grids with empty values to fill the available grids.
      while (subParticipants.size() < numberOfAvailableGrids) {
        subParticipants.add("");
      }

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
  private static void randomizeParticipants(List<String> participants, int heatNumber,
    Set<Integer> excludedGrids) {
    // Generate the seed based on variable factors and the current day.
    long daysSinceEpoch = LocalDate.now(ZoneId.of("Z")).toEpochDay();
    int seed = Objects.hash(participants, heatNumber, excludedGrids, daysSinceEpoch);

    // Sort and then shuffle the participants.
    Collections.sort(participants);
    Collections.shuffle(participants, new Random(seed));
  }
}
