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

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * The unit tests for {@link GridGeneratorController}.
 */
@ExtendWith(ApplicationExtension.class)
class GridGeneratorControllerTest {

  private Stage stage;

  @Start
  void setUp(Stage stage) throws IOException {
    this.stage = stage;

    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");
    BorderPane gridGenerator = FXMLLoader
      .load(getClass().getResource("/fxml/GridGenerator.fxml"), labelsBundle);
    stage.setScene(new Scene(gridGenerator));
    stage.show();
  }

  /**
   * Test that the draw grids layout is added to the BorderPane's center when the display input
   * participant action is triggered.
   */
  @Test
  void testDisplayDrawGrids_drawGridsDisplayed(FxRobot robot) {
    // Call the code under test.
    robot.clickOn("#navigateMenu");
    robot.clickOn("#drawGridsMenuItem");

    // Perform assertions.
    BorderPane mainLayout = robot.lookup("#mainLayout").query();
    Node drawGrids = mainLayout.getCenter();
    MatcherAssert.assertThat("The main layout's center's ID did not match the expected value.",
      drawGrids.getId(), CoreMatchers.is("drawGridsLayout"));
  }

  /**
   * Test that the input participants layout is added to the BorderPane's center when the display
   * input participant action is triggered.
   */
  @Test
  void testDisplayInputParticipants_inputParticipantsDisplayed(FxRobot robot) {
    // Call the code under test.
    robot.clickOn("#navigateMenu");
    robot.clickOn("#inputParticipantsMenuItem");

    // Perform assertions.
    BorderPane mainLayout = robot.lookup("#mainLayout").query();
    Node inputParticipants = mainLayout.getCenter();
    MatcherAssert.assertThat("The main layout's center's ID did not match the expected value.",
      inputParticipants.getId(), CoreMatchers.is("inputParticipantLayout"));
  }

  /**
   * Test that the application is terminated when the exit action is triggered.
   */
  @Test
  void testExit_applicationTerminated(FxRobot robot) {
    // Call the code under test.
    robot.clickOn("#fileMenu");
    robot.clickOn("#exitMenuItem");

    // Perform assertions.
    MatcherAssert.assertThat("The application did not exit as expected.", stage.isShowing(),
      CoreMatchers.is(false));
  }
}
