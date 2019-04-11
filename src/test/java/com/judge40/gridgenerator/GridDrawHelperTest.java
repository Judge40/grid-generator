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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import mockit.Expectations;
import mockit.Mocked;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GridDrawHelperTest {

  private static PreferenceTestHelper preferenceTestHelper;

  @Mocked
  private LocalDate date;

  @BeforeAll
  static void setUpBeforeAll() throws BackingStoreException, IOException {
    preferenceTestHelper = new PreferenceTestHelper();
  }

  @AfterAll
  static void tearDownAfterAll()
    throws InvalidPreferencesFormatException, BackingStoreException, IOException {
    preferenceTestHelper.restorePreferences();
  }

  @BeforeEach
  void setUp() {
    new Expectations() {
      {
        date.toEpochDay();
        result = 1;
        minTimes = 0;
      }
    };
  }

  @AfterEach
  void tearDown() throws BackingStoreException {
    preferenceTestHelper.clearPreferences();
  }

  /**
   * Test that no races are returned when there are no participants for the class.
   */
  @Test
  void testDrawGridsForClass_noClassParticipants_noRaces()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Set up the test scenario.
    PreferenceHelper.setClassParticipants("testClass", Collections.emptyList());
    PreferenceHelper.setNumberOfGrids(10);

    // Call the code under test.
    List<List<String>> races = GridDrawHelper
      .drawGridsForClass("testClass", Collections.emptySet());

    // Perform assertions.
    MatcherAssert.assertThat("The number of races did not match the expected value.", races.size(),
      CoreMatchers.is(0));
  }

  /**
   * Test that no races are returned when all grids are excluded.
   */
  @Test
  void testDrawGridsForClass_allGridsExcluded_noRaces()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Set up the test scenario.
    PreferenceHelper.setClassParticipants("testClass",
      Arrays
        .asList("participant1", "participant2", "participant3", "participant4", "participant5"));
    PreferenceHelper.setNumberOfGrids(10);

    // Call the code under test.
    List<List<String>> races = GridDrawHelper.drawGridsForClass("testClass",
      new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

    // Perform assertions.
    MatcherAssert.assertThat("The number of races did not match the expected value.", races.size(),
      CoreMatchers.is(0));
  }

  /**
   * Test that one race is returned with empty or excluded grids padded when there are more
   * available grids than participants.
   */
  @Test
  void testDrawGridsForClass_moreAvailableGridsThanParticipants_oneRace()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Set up the test scenario.
    PreferenceHelper.setClassParticipants("testClass",
      Arrays
        .asList("participant1", "participant2", "participant3", "participant4", "participant5"));
    PreferenceHelper.setNumberOfGrids(10);

    // Call the code under test.
    List<List<String>> races = GridDrawHelper.drawGridsForClass("testClass",
      new HashSet<>(Arrays.asList(2, 4, 6, 8)));

    // Perform assertions.
    MatcherAssert.assertThat("The number of races did not match the expected value.", races.size(),
      CoreMatchers.is(1));

    List<String> race = races.get(0);
    MatcherAssert
      .assertThat("The number of race participants did not match the expected value.", race.size(),
        CoreMatchers.is(10));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(0),
      CoreMatchers.is("participant1"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(1),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(2),
      CoreMatchers.is("participant5"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(3),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(4),
      CoreMatchers.is("participant3"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(5),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(6),
      CoreMatchers.is("participant4"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(7),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(8),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(9),
      CoreMatchers.is("participant2"));
  }

  /**
   * Test that one race is returned with empty or excluded grids padded when there are equal
   * available grids and participants.
   */
  @Test
  void testDrawGridsForClass_equalAvailableGridsAndParticipants_oneRace()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Set up the test scenario.
    PreferenceHelper.setClassParticipants("testClass",
      Arrays
        .asList("participant1", "participant2", "participant3", "participant4", "participant5"));
    PreferenceHelper.setNumberOfGrids(10);

    // Call the code under test.
    List<List<String>> races = GridDrawHelper.drawGridsForClass("testClass",
      new HashSet<>(Arrays.asList(2, 4, 6, 8, 10)));

    // Perform assertions.
    MatcherAssert.assertThat("The number of races did not match the expected value.", races.size(),
      CoreMatchers.is(1));

    List<String> race = races.get(0);
    MatcherAssert
      .assertThat("The number of race participants did not match the expected value.", race.size(),
        CoreMatchers.is(10));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(0),
      CoreMatchers.is("participant5"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(1),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(2),
      CoreMatchers.is("participant3"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(3),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(4),
      CoreMatchers.is("participant1"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(5),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(6),
      CoreMatchers.is("participant2"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(7),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(8),
      CoreMatchers.is("participant4"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(9),
      CoreMatchers.is(""));
  }

  /**
   * Test that multiple equal races are returned with empty or excluded grids padded when there are
   * less available grids than participants.
   */
  @Test
  void testDrawGridsForClass_lessAvailableGridsThanParticipants_multipleRaces()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Set up the test scenario.
    PreferenceHelper.setClassParticipants("testClass",
      Arrays.asList("participant1", "participant2", "participant3", "participant4", "participant5",
        "participant6", "participant7", "participant8"));
    PreferenceHelper.setNumberOfGrids(10);

    // Call the code under test.
    List<List<String>> races = GridDrawHelper.drawGridsForClass("testClass",
      new HashSet<>(Arrays.asList(1, 3, 5)));

    // Perform assertions.
    MatcherAssert.assertThat("The number of races did not match the expected value.", races.size(),
      CoreMatchers.is(2));

    List<String> race = races.get(0);
    MatcherAssert
      .assertThat("The number of race participants did not match the expected value.", race.size(),
        CoreMatchers.is(10));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(0),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(1),
      CoreMatchers.is("participant7"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(2),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(3),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(4),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(5),
      CoreMatchers.is("participant4"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(6),
      CoreMatchers.is("participant2"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(7),
      CoreMatchers.is("participant5"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(8),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(9),
      CoreMatchers.is(""));

    race = races.get(1);
    MatcherAssert
      .assertThat("The number of race participants did not match the expected value.", race.size(),
        CoreMatchers.is(10));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(0),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(1),
      CoreMatchers.is("participant8"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(2),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(3),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(4),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(5),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(6),
      CoreMatchers.is(""));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(7),
      CoreMatchers.is("participant6"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(8),
      CoreMatchers.is("participant3"));
    MatcherAssert.assertThat("The race participant did not match the expected value.", race.get(9),
      CoreMatchers.is("participant1"));
  }
}
