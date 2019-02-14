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
import java.util.Locale;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
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
public class GridGeneratorTest {

  private GridGenerator gridGenerator;

  private Stage stage;

  @Start
  public void setUp(Stage stage) {
    this.stage = stage;
    gridGenerator = new GridGenerator();
  }

  /**
   * Test that the stage title is set to "Grid Generator" when the locale is en-GB.
   */
  @Test
  public void testStart_enGb_titleGridGenerator(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "GB"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    MatcherAssert.assertThat("The stage's title did not match the expected value.",
        stage.getTitle(), CoreMatchers.is("Grid Generator"));
  }

  /**
   * Test that the "File" menu is available on the dialog when the locale is en-GB.
   */
  @Test
  public void testStart_enGb_hasFileMenu(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "GB"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#fileMenu", NodeMatchers.isVisible());
    FxAssert.verifyThat("#fileMenu", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#fileMenu", LabeledMatchers.hasText("File"));
  }

  /**
   * Test that the "Exit" menu is available on the dialog when the locale is en-GB.
   */
  @Test
  public void testStart_enGb_hasExitMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "GB"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
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
   * Test that the "Input Participants" menu is available on the dialog when the locale is en-GB.
   */
  @Test
  public void testStart_enGb_hasInputParticipantsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "GB"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#inputParticipantsMenuItem", NodeMatchers.isVisible());
    FxAssert.verifyThat("#inputParticipantsMenuItem", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#inputParticipantsMenuItem", LabeledMatchers.hasText("Input Drivers"));
  }

  /**
   * Test that the "Draw Grids" menu item is available on the dialog when the locale is en-GB.
   */
  @Test
  public void testStart_enGb_hasDrawGridsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "GB"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#drawGridsMenuItem", NodeMatchers.isVisible());
    FxAssert.verifyThat("#drawGridsMenuItem", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#drawGridsMenuItem", LabeledMatchers.hasText("Draw Grids"));
  }

  /**
   * Test that the stage title is set to "Grid Generator" when the locale is en-GB.
   */
  @Test
  public void testStart_enPseudo_titleGridGenerator(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
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
  public void testStart_enPseudo_hasFileMenu(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
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
  public void testStart_enPseudo_hasExitMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
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
   * Test that the "Input Participants" menu is available on the dialog when the locale is
   * en-PSEUDO.
   */
  @Test
  public void testStart_enPseudo_hasInputParticipantsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#inputParticipantsMenuItem", NodeMatchers.isVisible());
    FxAssert.verifyThat("#inputParticipantsMenuItem", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#inputParticipantsMenuItem",
        LabeledMatchers.hasText("[!!! Ìñƥúƭ ÐřïƲèřƨ ℓôř !!!]"));
  }

  /**
   * Test that the "Draw Grids" menu item is available on the dialog when the locale is en-PSEUDO.
   */
  @Test
  public void testStart_enPseudo_hasDrawGridsMenuItem(FxRobot robot) {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));

    // Call the method under test.
    robot.interact(() -> {
      try {
        gridGenerator.start(stage);
      } catch (IOException e) {
        Assertions.fail("An unexpected exception was thrown.", e);
      }
    });

    // Perform assertions.
    FxAssert.verifyThat("#drawGridsMenuItem", NodeMatchers.isVisible());
    FxAssert.verifyThat("#drawGridsMenuItem", NodeMatchers.isEnabled());
    FxAssert.verifyThat("#drawGridsMenuItem", LabeledMatchers.hasText("[!!! Ðřáω Gřïδƨ ℓô !!!]"));
  }
}
