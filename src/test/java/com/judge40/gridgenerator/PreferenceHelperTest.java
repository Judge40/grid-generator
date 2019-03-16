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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The unit tests for {@link PreferenceHelper}.
 */
class PreferenceHelperTest {

  private static Preferences preferences;
  private static byte[] originalPreferenceBackup;

  @BeforeAll
  static void setUpBeforeAll() throws BackingStoreException, IOException {
    preferences = Preferences.userNodeForPackage(GridGenerator.class);

    // Create a backup of the original preferences values.
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      preferences.exportSubtree(baos);
      originalPreferenceBackup = baos.toByteArray();
    }
  }

  @AfterEach
  void tearDown() throws BackingStoreException, IOException, InvalidPreferencesFormatException {
    // Clear all the existing preferences values.
    preferences.clear();

    for (String childName : preferences.childrenNames()) {
      preferences.node(childName).removeNode();
    }

    // Import the backed up preferences.
    try (ByteArrayInputStream bais = new ByteArrayInputStream(originalPreferenceBackup)) {
      Preferences.importPreferences(bais);
    }
  }

  /**
   * Test that an empty list is returned when there is no preference value set.
   */
  @Test
  void testGetClassParticipants_noPreferenceValue_emptyList()
      throws BackingStoreException, IOException, ClassNotFoundException {
    // Set up test scenario.
    preferences.node("testClass/participants").removeNode();

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
    // Set up test scenario.
    preferences.node("participantClassNames").removeNode();

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
    // Set up test scenario.
    preferences.remove("participantValidator");

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
}
