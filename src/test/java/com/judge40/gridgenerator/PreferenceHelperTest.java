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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The unit tests for {@link PreferenceHelper}.
 */
class PreferenceHelperTest {

  private static PreferenceTestHelper preferenceTestHelper;

  @BeforeAll
  static void setUpBeforeAll() throws BackingStoreException, IOException {
    preferenceTestHelper = new PreferenceTestHelper();
  }

  @AfterAll
  static void tearDownAfterAll()
    throws BackingStoreException, IOException, InvalidPreferencesFormatException {
    preferenceTestHelper.restorePreferences();
  }

  @AfterEach
  void tearDown() throws BackingStoreException {
    preferenceTestHelper.clearPreferences();
  }

  /**
   * Test that the application's preferences are initialized if no values exist.
   */
  @Test
  void testInitializePreferences_noPreferenceValues_preferencesInitialized()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Call the method under test.
    PreferenceHelper.initializePreferences();

    // Perform assertions.
    String meetingName = PreferenceHelper.getMeetingName();
    MatcherAssert.assertThat("The meeting name did not match the expected value.", meetingName,
      CoreMatchers.is("NWAA Qualifier"));

    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();
    MatcherAssert.assertThat("The participant class names did not match the expected value.",
      participantClassNames, CoreMatchers.is(Arrays.asList("Class 1", "Class 2", "Class 3",
        "Class 4", "Class 5", "Class 6", "Class 7", "Class 8", "Class 9", "Class 10")));

    String participantValidator = PreferenceHelper.getParticipantValidator();
    MatcherAssert.assertThat("The participant validator did not match the expected value.",
      participantValidator, CoreMatchers.is("[A-Z]+\\d+[A-Z]*|\\d+F"));

    String participantGroupingFilter = PreferenceHelper.getParticipantGroupingFilter();
    MatcherAssert.assertThat("The participant grouping filter did not match the expected value.",
      participantGroupingFilter, CoreMatchers.is("ARC\\d+|LM\\d+|NW\\d+"));

    int participantGroupingThreshold = PreferenceHelper.getParticipantGroupingThreshold();
    MatcherAssert.assertThat("The participant grouping threshold did not match the expected value.",
      participantGroupingThreshold, CoreMatchers.is(4));

    int numberOfGrids = PreferenceHelper.getNumberOfGrids();
    MatcherAssert.assertThat("The number of grids did not match the expected value.", numberOfGrids,
      CoreMatchers.is(8));

    int numberOfHeats = PreferenceHelper.getNumberOfHeats();
    MatcherAssert.assertThat("The number of heats did not match the expected value.", numberOfHeats,
      CoreMatchers.is(3));
  }

  /**
   * Test that the application's preferences are not initialized if values exist.
   */
  @Test
  void testInitializePreferences_hasPreferenceValues_preferencesNotInitialized()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Set up test scenario.
    PreferenceHelper.setMeetingName("meetingName");
    PreferenceHelper.setParticipantClassNames(Arrays.asList("class1", "class2"));
    PreferenceHelper.setParticipantValidator("participantValidator");
    PreferenceHelper.setParticipantGroupingFilter("participantGroupingFilter");
    PreferenceHelper.setParticipantGroupingThreshold(10);
    PreferenceHelper.setNumberOfGrids(40);
    PreferenceHelper.setNumberOfHeats(5);

    // Call the method under test.
    PreferenceHelper.initializePreferences();

    // Perform assertions.
    String meetingName = PreferenceHelper.getMeetingName();
    MatcherAssert.assertThat("The meeting name did not match the expected value.", meetingName,
      CoreMatchers.is("meetingName"));

    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();
    MatcherAssert.assertThat("The participant class names did not match the expected value.",
      participantClassNames, CoreMatchers.is(Arrays.asList("class1", "class2")));

    String participantValidator = PreferenceHelper.getParticipantValidator();
    MatcherAssert.assertThat("The participant validator did not match the expected value.",
      participantValidator, CoreMatchers.is("participantValidator"));

    String participantGroupingFilter = PreferenceHelper.getParticipantGroupingFilter();
    MatcherAssert.assertThat("The participant grouping filter did not match the expected value.",
      participantGroupingFilter, CoreMatchers.is("participantGroupingFilter"));

    int participantGroupingThreshold = PreferenceHelper.getParticipantGroupingThreshold();
    MatcherAssert.assertThat("The participant grouping threshold did not match the expected value.",
      participantGroupingThreshold, CoreMatchers.is(10));

    int numberOfGrids = PreferenceHelper.getNumberOfGrids();
    MatcherAssert.assertThat("The number of grids did not match the expected value.", numberOfGrids,
      CoreMatchers.is(40));

    int numberOfHeats = PreferenceHelper.getNumberOfHeats();
    MatcherAssert.assertThat("The number of heats did not match the expected value.", numberOfHeats,
      CoreMatchers.is(5));
  }

  /**
   * Test that an empty string is returned when there is no preference value set.
   */
  @Test
  void testGetMeetingName_noPreferenceValue_emptyString() {
    // Call the code under test.
    String meetingName = PreferenceHelper.getMeetingName();

    // Perform assertions.
    MatcherAssert.assertThat("The meeting name did not match the expected value.", meetingName,
      CoreMatchers.is(""));
  }

  /**
   * Test that the preference value is returned when there is a preference value set.
   */
  @Test
  void testGetMeetingName_hasPreferenceValue_preferenceValue() {
    // Set up test scenario.
    PreferenceHelper.setMeetingName("meetingName");

    // Call the code under test.
    String meetingName = PreferenceHelper.getMeetingName();

    // Perform assertions.
    MatcherAssert.assertThat("The meeting name did not match the expected value.", meetingName,
      CoreMatchers.is("meetingName"));
  }

  /**
   * Test that an empty list is returned when there is no preference value set.
   */
  @Test
  void testGetClassParticipants_noPreferenceValue_emptyList()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Call the code under test.
    List<String> classParticipants = PreferenceHelper.getClassParticipants("testClass");

    // Perform assertions.
    MatcherAssert.assertThat("The class participants did not match the expected value.",
      classParticipants, CoreMatchers.is(Collections.emptyList()));
  }

  /**
   * Test that the preference value is returned when there is a small preference value set.
   */
  @Test
  void testGetClassParticipants_hasSmallPreferenceValue_preferenceValue()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Set up test scenario.
    PreferenceHelper.setClassParticipants("testClass", Arrays.asList("value1", "value2"));

    // Call the code under test.
    List<String> classParticipants = PreferenceHelper.getClassParticipants("testClass");

    // Perform assertions.
    List<String> expectedClassNames = Arrays.asList("value1", "value2");
    MatcherAssert.assertThat("The class participants did not match the expected value.",
      classParticipants, CoreMatchers.is(expectedClassNames));
  }

  /**
   * Test that the preference value is returned when there is a large preference value set.
   */
  @Test
  void testGetClassParticipants_hasLargePreferenceValue_preferenceValue()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Set up test scenario.
    List<String> largeValueList = new ArrayList<>();
    char[] largeValueChars = new char[1000];
    Arrays.fill(largeValueChars, ' ');

    for (int i = 0; i < 1000; i++) {
      largeValueList.add(new String(largeValueChars));
    }

    PreferenceHelper.setClassParticipants("testClass", largeValueList);

    // Call the code under test.
    List<String> classParticipants = PreferenceHelper.getClassParticipants("testClass");

    // Perform assertions.
    MatcherAssert.assertThat("The number of class participants did not match the expected value.",
      classParticipants.size(), CoreMatchers.is(1000));

    for (int i = 0; i < 1000; i++) {
      MatcherAssert.assertThat("The class participants did not match the expected value.",
        classParticipants.get(i), CoreMatchers.is(new String(largeValueChars)));
    }
  }

  /**
   * Test that an empty list is returned when there is no preference value set.
   */
  @Test
  void testGetParticipantClassNames_noPreferenceValue_emptyList()
    throws BackingStoreException, IOException, ClassNotFoundException {
    // Call the code under test.
    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();

    // Perform assertions.
    MatcherAssert.assertThat("The participant class names did not match the expected value.",
      participantClassNames, CoreMatchers.is(Collections.emptyList()));
  }

  /**
   * Test that the preference value is returned when there is a small preference value set.
   */
  @Test
  void testGetParticipantClassNames_hasSmallPreferenceValue_preferenceValue()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Set up test scenario.
    PreferenceHelper.setParticipantClassNames(Arrays.asList("value1", "value2"));

    // Call the code under test.
    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();

    // Perform assertions.
    List<String> expectedClassNames = Arrays.asList("value1", "value2");
    MatcherAssert.assertThat("The participant class names did not match the expected value.",
      participantClassNames, CoreMatchers.is(expectedClassNames));
  }

  /**
   * Test that the preference value is returned when there is a large preference value set.
   */
  @Test
  void testGetParticipantClassNames_hasLargePreferenceValue_preferenceValue()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Set up test scenario.
    List<String> largeValueList = new ArrayList<>();
    char[] largeValueChars = new char[1000];
    Arrays.fill(largeValueChars, ' ');

    for (int i = 0; i < 1000; i++) {
      largeValueList.add(new String(largeValueChars));
    }

    PreferenceHelper.setParticipantClassNames(largeValueList);

    // Call the code under test.
    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();

    // Perform assertions.
    MatcherAssert
      .assertThat("The number of participant class names did not match the expected value.",
        participantClassNames.size(), CoreMatchers.is(1000));

    for (int i = 0; i < 1000; i++) {
      MatcherAssert.assertThat("The participant class name did not match the expected value.",
        participantClassNames.get(i), CoreMatchers.is(new String(largeValueChars)));
    }
  }

  /**
   * Test that an empty string is returned when there is no preference value set.
   */
  @Test
  void testGetParticipantValidator_noPreferenceValue_emptyString() {
    // Call the code under test.
    String participantValidator = PreferenceHelper.getParticipantValidator();

    // Perform assertions.
    MatcherAssert.assertThat("The participant validator did not match the expected value.",
      participantValidator, CoreMatchers.is(""));
  }

  /**
   * Test that the preference value is returned when there is a preference value set.
   */
  @Test
  void testGetParticipantValidator_hasPreferenceValue_preferenceValue() {
    // Set up test scenario.
    PreferenceHelper.setParticipantValidator("preferenceValue");

    // Call the code under test.
    String participantValidator = PreferenceHelper.getParticipantValidator();

    // Perform assertions.
    MatcherAssert.assertThat("The participant validator did not match the expected value.",
      participantValidator, CoreMatchers.is("preferenceValue"));
  }

  /**
   * Test that an empty string is returned when there is no preference value set.
   */
  @Test
  void testGetParticipantGroupingFilter_noPreferenceValue_emptyString() {
    // Call the code under test.
    String participantGroupingFilter = PreferenceHelper.getParticipantGroupingFilter();

    // Perform assertions.
    MatcherAssert.assertThat("The participant grouping filter did not match the expected value.",
      participantGroupingFilter, CoreMatchers.is(""));
  }

  /**
   * Test that the preference value is returned when there is a preference value set.
   */
  @Test
  void testGetParticipantGroupingFilter_hasPreferenceValue_preferenceValue() {
    // Set up test scenario.
    PreferenceHelper.setParticipantGroupingFilter("groupingFilter1");

    // Call the code under test.
    String participantGroupingFilter = PreferenceHelper.getParticipantGroupingFilter();

    // Perform assertions.
    MatcherAssert.assertThat("The participant grouping filter did not match the expected value.",
      participantGroupingFilter, CoreMatchers.is("groupingFilter1"));
  }

  /**
   * Test that zero is returned when there is no preference value set.
   */
  @Test
  void testGetParticipantGroupingThreshold_noPreferenceValue_zero() {
    // Call the code under test.
    int participantGroupingThreshold = PreferenceHelper.getNumberOfGrids();

    // Perform assertions.
    MatcherAssert.assertThat("The participant grouping threshold did not match the expected value.",
      participantGroupingThreshold, CoreMatchers.is(0));
  }

  /**
   * Test that the preference value is returned when there is a preference value set.
   */
  @Test
  void testGetParticipantGroupingThreshold_hasPreferenceValue_preferenceValue() {
    // Set up test scenario.
    PreferenceHelper.setParticipantGroupingThreshold(10);

    // Call the code under test.
    int participantGroupingThreshold = PreferenceHelper.getParticipantGroupingThreshold();

    // Perform assertions.
    MatcherAssert.assertThat("The participant grouping threshold did not match the expected value.",
      participantGroupingThreshold, CoreMatchers.is(10));
  }

  /**
   * Test that zero is returned when there is no preference value set.
   */
  @Test
  void testGetNumberOfGrids_noPreferenceValue_zero() {
    // Call the code under test.
    int numberOfGrids = PreferenceHelper.getNumberOfGrids();

    // Perform assertions.
    MatcherAssert.assertThat("The number of grids did not match the expected value.", numberOfGrids,
      CoreMatchers.is(0));
  }

  /**
   * Test that the preference value is returned when there is a preference value set.
   */
  @Test
  void testGetNumberOfGrids_hasPreferenceValue_preferenceValue() {
    // Set up test scenario.
    PreferenceHelper.setNumberOfGrids(40);

    // Call the code under test.
    int numberOfGrids = PreferenceHelper.getNumberOfGrids();

    // Perform assertions.
    MatcherAssert.assertThat("The number of grids did not match the expected value.", numberOfGrids,
      CoreMatchers.is(40));
  }

  /**
   * Test that zero is returned when there is no preference value set.
   */
  @Test
  void testGetNumberOfHeats_noPreferenceValue_zero() {
    // Call the code under test.
    int numberOfHeats = PreferenceHelper.getNumberOfHeats();

    // Perform assertions.
    MatcherAssert.assertThat("The number of heats did not match the expected value.", numberOfHeats,
      CoreMatchers.is(0));
  }

  /**
   * Test that the preference value is returned when there is a preference value set.
   */
  @Test
  void testGetNumberOfHeats_hasPreferenceValue_preferenceValue() {
    // Set up test scenario.
    PreferenceHelper.setNumberOfHeats(5);

    // Call the code under test.
    int numberOfHeats = PreferenceHelper.getNumberOfHeats();

    // Perform assertions.
    MatcherAssert.assertThat("The number of heats did not match the expected value.", numberOfHeats,
      CoreMatchers.is(5));
  }
}
