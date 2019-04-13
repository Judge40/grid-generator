/*
 * Grid Generator Copyright (c) 2018 Judge40
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

import com.sun.javafx.scene.control.MenuBarButton;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

/**
 * The unit tests for {@link GridGenerator}.
 */
@ExtendWith(ApplicationExtension.class)
class GridGeneratorTest {

  private static PreferenceTestHelper preferenceTestHelper;

  private GridGenerator gridGenerator;

  private Stage stage;

  @BeforeAll
  static void setUpBeforeAll() throws BackingStoreException, IOException {
    preferenceTestHelper = new PreferenceTestHelper();
  }

  @AfterAll
  static void tearDownAfterAll()
    throws BackingStoreException, IOException, InvalidPreferencesFormatException {
    preferenceTestHelper.restorePreferences();
  }

  @Start
  void setUp(Stage stage) {
    this.stage = stage;
    gridGenerator = new GridGenerator();
  }

  @AfterEach
  void tearDown() throws BackingStoreException {
    preferenceTestHelper.clearPreferences();
  }

  /**
   * Test that the stage title is set to "Grid Generator" when the locale is English.
   */
  @Test
  void testStart_en_titleGridGenerator(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MatcherAssert.assertThat("The stage's title did not match the expected value.",
      stage.getTitle(), CoreMatchers.is("Grid Generator"));
  }

  /**
   * Test that the "File" menu is available on the dialog when the locale is English.
   */
  @Test
  void testStart_en_hasFileMenu(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#fileMenu", NodeMatchers.isVisible());
    FxAssert.verifyThat("#fileMenu", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#fileMenu", LabeledMatchers.hasText("File"));
  }

  /**
   * Test that the "Exit" menu is available on the dialog when the locale is English.
   */
  @Test
  void testStart_en_hasExitMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MenuBarButton menuBarButton = (MenuBarButton) stage.getScene().lookup("#fileMenu");
    MenuItem menuItem = menuBarButton.menu.getItems().stream()
      .filter(child -> child.getId().equals("exitMenuItem")).findFirst().get();

    MatcherAssert.assertThat("The menu item's visibility did not match the expected value.",
      menuItem.isVisible(), CoreMatchers.is(true));
    MatcherAssert.assertThat("The menu item's enabled state did not match the expected value.",
      menuItem.isDisable(), CoreMatchers.is(false));
    MatcherAssert.assertThat("The menu item's text  did not match the expected value.",
      menuItem.getText(), CoreMatchers.is("Exit"));
  }

  /**
   * Test that the "Navigate" menu is available on the dialog when the locale is English.
   */
  @Test
  void testStart_en_hasNavigateMenu(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#navigateMenu", NodeMatchers.isVisible());
    FxAssert.verifyThat("#navigateMenu", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#navigateMenu", LabeledMatchers.hasText("Navigate"));
  }


  /**
   * Test that the "Input Participants" menu is available on the dialog when the locale is English.
   */
  @Test
  void testStart_en_hasInputParticipantsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MenuBarButton menuBarButton = (MenuBarButton) stage.getScene().lookup("#navigateMenu");
    MenuItem menuItem = menuBarButton.menu.getItems().stream()
      .filter(child -> child.getId().equals("inputParticipantsMenuItem")).findFirst().get();

    MatcherAssert.assertThat("The menu item's visibility did not match the expected value.",
      menuItem.isVisible(), CoreMatchers.is(true));
    MatcherAssert.assertThat("The menu item's enabled state did not match the expected value.",
      menuItem.isDisable(), CoreMatchers.is(false));
    MatcherAssert.assertThat("The menu item's text  did not match the expected value.",
      menuItem.getText(), CoreMatchers.is("Input Drivers"));
  }

  /**
   * Test that the "Draw Grids" menu item is available on the dialog when the locale is English.
   */
  @Test
  void testStart_en_hasDrawGridsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MenuBarButton menuBarButton = (MenuBarButton) stage.getScene().lookup("#navigateMenu");
    MenuItem menuItem = menuBarButton.menu.getItems().stream()
      .filter(child -> child.getId().equals("drawGridsMenuItem")).findFirst().get();

    MatcherAssert.assertThat("The menu item's visibility did not match the expected value.",
      menuItem.isVisible(), CoreMatchers.is(true));
    MatcherAssert.assertThat("The menu item's enabled state did not match the expected value.",
      menuItem.isDisable(), CoreMatchers.is(false));
    MatcherAssert.assertThat("The menu item's text  did not match the expected value.",
      menuItem.getText(), CoreMatchers.is("Draw Grids"));
  }

  /**
   * Test that the stage title is set to "Grid Generator" when the locale is English.
   */
  @Test
  void testStart_enPseudo_titleGridGenerator(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MatcherAssert.assertThat("The stage's title did not match the expected value.",
      stage.getTitle(), CoreMatchers.is("Grid Generator"));
  }

  /**
   * Test that the "File" menu is available on the dialog when the locale is en-PSEUDO.
   */
  @Test
  void testStart_enPseudo_hasFileMenu(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#fileMenu", NodeMatchers.isVisible());
    FxAssert.verifyThat("#fileMenu", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#fileMenu", LabeledMatchers.hasText("[!!! Fïℓè  !!!]"));
  }

  /**
   * Test that the "Exit" menu is available on the dialog when the locale is en-PSEUDO.
   */
  @Test
  void testStart_enPseudo_hasExitMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MenuBarButton menuBarButton = (MenuBarButton) stage.getScene().lookup("#fileMenu");
    MenuItem menuItem = menuBarButton.menu.getItems().stream()
      .filter(child -> child.getId().equals("exitMenuItem")).findFirst().get();

    MatcherAssert.assertThat("The menu item's visibility did not match the expected value.",
      menuItem.isVisible(), CoreMatchers.is(true));
    MatcherAssert.assertThat("The menu item's enabled state did not match the expected value.",
      menuItem.isDisable(), CoreMatchers.is(false));
    MatcherAssert.assertThat("The menu item's text  did not match the expected value.",
      menuItem.getText(), CoreMatchers.is("[!!! Éжïƭ  !!!]"));
  }

  /**
   * Test that the "Navigate" menu is available on the dialog when the locale is en-PSEUDO.
   */
  @Test
  void testStart_enPseudo_hasNavigateMenu(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#navigateMenu", NodeMatchers.isVisible());
    FxAssert.verifyThat("#navigateMenu", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#navigateMenu", LabeledMatchers.hasText("[!!! NáƲïϱáƭè ℓ !!!]"));
  }

  /**
   * Test that the "Input Participants" menu is available on the dialog when the locale is
   * en-PSEUDO.
   */
  @Test
  void testStart_enPseudo_hasInputParticipantsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MenuBarButton menuBarButton = (MenuBarButton) stage.getScene().lookup("#navigateMenu");
    MenuItem menuItem = menuBarButton.menu.getItems().stream()
      .filter(child -> child.getId().equals("inputParticipantsMenuItem")).findFirst().get();

    MatcherAssert.assertThat("The menu item's visibility did not match the expected value.",
      menuItem.isVisible(), CoreMatchers.is(true));
    MatcherAssert.assertThat("The menu item's enabled state did not match the expected value.",
      menuItem.isDisable(), CoreMatchers.is(false));
    MatcherAssert.assertThat("The menu item's text  did not match the expected value.",
      menuItem.getText(), CoreMatchers.is("[!!! Ìñƥúƭ ÐřïƲèřƨ ℓôř !!!]"));
  }

  /**
   * Test that the "Draw Grids" menu item is available on the dialog when the locale is en-PSEUDO.
   */
  @Test
  void testStart_enPseudo_hasDrawGridsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MenuBarButton menuBarButton = (MenuBarButton) stage.getScene().lookup("#navigateMenu");
    MenuItem menuItem = menuBarButton.menu.getItems().stream()
      .filter(child -> child.getId().equals("drawGridsMenuItem")).findFirst().get();

    MatcherAssert.assertThat("The menu item's visibility did not match the expected value.",
      menuItem.isVisible(), CoreMatchers.is(true));
    MatcherAssert.assertThat("The menu item's enabled state did not match the expected value.",
      menuItem.isDisable(), CoreMatchers.is(false));
    MatcherAssert.assertThat("The menu item's text  did not match the expected value.",
      menuItem.getText(), CoreMatchers.is("[!!! Ðřáω Gřïδƨ ℓô !!!]"));
  }

  /**
   * Test that the application's preferences are initialized if no values exist.
   */
  @Test
  void testStart_noPreferenceValues_preferencesInitialized(FxRobot robot)
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    String meetingName = PreferenceHelper.getMeetingName();
    MatcherAssert.assertThat("The meeting name did not match the expected value.", meetingName,
      CoreMatchers.notNullValue());

    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();
    MatcherAssert.assertThat("The participant class names did not match the expected value.",
      participantClassNames, CoreMatchers.notNullValue());

    String participantValidator = PreferenceHelper.getParticipantValidator();
    MatcherAssert.assertThat("The participant validator did not match the expected value.",
      participantValidator, CoreMatchers.notNullValue());

    String participantGroupingFilter = PreferenceHelper.getParticipantGroupingFilter();
    MatcherAssert.assertThat("The participant grouping filter did not match the expected value.",
      participantGroupingFilter, CoreMatchers.notNullValue());

    int participantGroupingThreshold = PreferenceHelper.getParticipantGroupingThreshold();
    MatcherAssert.assertThat("The participant grouping threshold did not match the expected value.",
      participantGroupingThreshold, CoreMatchers.not(0));

    int numberOfGrids = PreferenceHelper.getNumberOfGrids();
    MatcherAssert.assertThat("The number of grids did not match the expected value.", numberOfGrids,
      CoreMatchers.not(0));

    int numberOfHeats = PreferenceHelper.getNumberOfHeats();
    MatcherAssert.assertThat("The number of heats did not match the expected value.", numberOfHeats,
      CoreMatchers.not(0));
  }

  /**
   * Test that the application's preferences are not initialized if values exist.
   */
  @Test
  void testStart_hasPreferenceValues_preferencesNotInitialized(FxRobot robot)
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
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

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
      participantGroupingFilter, CoreMatchers.is(participantGroupingFilter));

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
}
