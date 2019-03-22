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

package com.judge40.gridgenerator.controller;

import com.judge40.gridgenerator.GridGenerator;
import com.judge40.gridgenerator.PreferenceHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

/**
 * The unit tests for {@link InputParticipantsController}.
 */
@ExtendWith(ApplicationExtension.class)
class InputParticipantsControllerTest {

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
   * Test that the class tabs are added when the controller is initialized.
   */
  @Test
  void testInitialize_classTabsAdded() throws BackingStoreException, IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setParticipantClassNames(Arrays.asList("class1", "class2"));

    // Call the code under test.
    TabPane inputLayout = FXMLLoader
      .load(getClass().getResource("/fxml/input-participants.fxml"), labelsBundle);

    // Perform assertions.
    ObservableList<Tab> tabs = inputLayout.getTabs();
    MatcherAssert.assertThat("The number of tabs did not match the expected value.", tabs.size(),
      CoreMatchers.is(2));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(0).getText(),
        CoreMatchers.is("class1"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(1).getText(),
        CoreMatchers.is("class2"));
  }

  /**
   * Test that the class tabs have their data initialized when the controller is initialized.
   */
  @Test
  void testInitialize_classTabsDataInitialized() throws BackingStoreException, IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setParticipantClassNames(Arrays.asList("class1", "class2"));
    PreferenceHelper.setClassParticipants("class1", Collections.singletonList("participant1"));
    PreferenceHelper.setClassParticipants("class2", Collections.singletonList("participant2"));

    // Call the code under test.
    TabPane inputLayout = FXMLLoader
      .load(getClass().getResource("/fxml/input-participants.fxml"), labelsBundle);

    // Perform assertions.
    ObservableList<Tab> tabs = inputLayout.getTabs();
    VBox tab = (VBox) tabs.get(0).getContent();
    ListView<?> participantsDisplay = (ListView<?>) tab.lookup("#participantsDisplay");
    ObservableList<?> participants = participantsDisplay.getItems();
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      participants.size(), CoreMatchers.is(1));
    MatcherAssert
      .assertThat("The participant did not match the expected value.", participants.get(0),
        CoreMatchers.is("participant1"));

    tab = (VBox) tabs.get(1).getContent();
    participantsDisplay = (ListView<?>) tab.lookup("#participantsDisplay");
    participants = participantsDisplay.getItems();
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      participants.size(), CoreMatchers.is(1));
    MatcherAssert
      .assertThat("The participant did not match the expected value.", participants.get(0),
        CoreMatchers.is("participant2"));
  }
}
