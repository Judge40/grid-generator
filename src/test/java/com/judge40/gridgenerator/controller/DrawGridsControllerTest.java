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

import com.judge40.gridgenerator.PreferenceHelper;
import com.judge40.gridgenerator.PreferenceTestHelper;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * The unit tests for {@link DrawGridsController}.
 */
@ExtendWith(ApplicationExtension.class)
class DrawGridsControllerTest {

  private static PreferenceTestHelper preferenceTestHelper;
  private static Locale defaultLocale;

  private Stage stage;

  @BeforeAll
  static void setUpBeforeAll() throws BackingStoreException, IOException {
    preferenceTestHelper = new PreferenceTestHelper();
    defaultLocale = Locale.getDefault();
  }

  @AfterAll
  static void tearDownAfterAll()
    throws BackingStoreException, IOException, InvalidPreferencesFormatException {
    Locale.setDefault(defaultLocale);
    preferenceTestHelper.restorePreferences();
  }

  @Start
  void setUp(Stage stage) {
    this.stage = stage;
  }

  @AfterEach
  void tearDown() throws BackingStoreException {
    preferenceTestHelper.clearPreferences();
  }

  /**
   * Test that the grid draw layout has English labels when the locale is set to English.
   */
  @Test
  void testInitialize_en_labelsEnglish(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    Labeled selectorLabel = robot.lookup("#excludedGridsSelectorLabel").queryLabeled();
    MatcherAssert.assertThat(
      "The excluded grid selector's label did not match the expected value.",
      selectorLabel.getText(), CoreMatchers.is("Excluded Grids"));
  }

  /**
   * Test that the grid draw layout has Pseudo English labels when the locale is set to Pseudo
   * English.
   */
  @Test
  void testInitialize_enPseudo_labelsPseudoEnglish(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    Labeled selectorLabel = robot.lookup("#excludedGridsSelectorLabel").queryLabeled();
    MatcherAssert.assertThat(
      "The excluded grid selector's label did not match the expected value.",
      selectorLabel.getText(), CoreMatchers.is("[!!! Éжçℓúδèδ Gřïδƨ ℓôř !!!]"));
  }

  /**
   * Test that the grid selector has an English text when the number of grids is zero and the locale
   * is set to English.
   */
  @Test
  void testInitialize_zeroGridsEn_gridSelectorDisabledTextEnglish(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setNumberOfGrids(0);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    ComboBox<Integer> gridSelector = robot.lookup("#excludedGridsSelector").query();
    MatcherAssert.assertThat(
      "The excluded grid selector's disabled state did not match the expected value.",
      gridSelector.isDisabled(), CoreMatchers.is(true));
    MatcherAssert.assertThat(
      "The excluded grid selector's text did not match the expected value.",
      gridSelector.getPromptText(), CoreMatchers.is("Number of grids is zero."));
  }

  /**
   * Test that the grid selector has Pseudo English text when the number of grids is zero and the
   * locale is set to Pseudo English.
   */
  @Test
  void testInitialize_zeroGridsEnPseudo_gridSelectorDisabledTextPseudoEnglish(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setNumberOfGrids(0);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    ComboBox<Integer> gridSelector = robot.lookup("#excludedGridsSelector").query();
    MatcherAssert.assertThat(
      "The excluded grid selector's disabled state did not match the expected value.",
      gridSelector.isDisabled(), CoreMatchers.is(true));
    MatcherAssert.assertThat(
      "The excluded grid selector's text did not match the expected value.",
      gridSelector.getPromptText(), CoreMatchers.is("[!!! Nú₥βèř ôƒ ϱřïδƨ ïƨ ƺèřô. ℓôřè₥  !!!]"));
  }

  /**
   * Test that the grid selector has the expected grids when the the number of grids is not zero.
   */
  @Test
  void testInitialize_hasGrids_gridSelectorHasGrids(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setNumberOfGrids(4);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    ComboBox<Integer> gridSelector = robot.lookup("#excludedGridsSelector").query();
    MatcherAssert.assertThat(
      "The excluded grid selector's disabled state did not match the expected value.",
      gridSelector.isDisabled(), CoreMatchers.is(false));

    ObservableList<Integer> grids = gridSelector.getItems();
    MatcherAssert.assertThat("The the number of excluded grids did not match the expected value.",
      grids.size(), CoreMatchers.is(4));
    MatcherAssert.assertThat("The the excluded grids did not match the expected value.",
      grids, CoreMatchers.hasItems(1, 2, 3, 4));
  }

  /**
   * Test that the grid selector displays the selected grid when a grid is selected by clicking the
   * cell.
   */
  @Test
  void testInitialize_gridSelectedByCell_gridSelectorDisplaysGrid(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setNumberOfGrids(4);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ComboBox<Integer> gridSelector = robot.lookup("#excludedGridsSelector").query();
    robot.clickOn(gridSelector);
    robot.clickOn("2");

    // Perform assertions.
    MatcherAssert.assertThat(
      "The excluded grid selector's text did not match the expected value.",
      gridSelector.getButtonCell().getText(), CoreMatchers.is("2"));
  }

  /**
   * Test that the grid selector displays the selected grid when a grid is selected by clicking the
   * checkbox.
   */
  @Test
  void testInitialize_gridSelectedByCheckbox_gridSelectorDisplaysGrid(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setNumberOfGrids(4);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ComboBox<Integer> gridSelector = robot.lookup("#excludedGridsSelector").query();
    robot.clickOn(gridSelector);
    CheckBoxListCell<Integer> checkBoxCell = robot.lookup("3").query();
    CheckBox checkBox = (CheckBox) checkBoxCell.getGraphic();
    robot.clickOn(checkBox);

    // Perform assertions.
    MatcherAssert.assertThat(
      "The excluded grid selector's text did not match the expected value.",
      gridSelector.getButtonCell().getText(), CoreMatchers.is("3"));
  }

  /**
   * Test that the grid selector displays the selected grids when multiple grids are selected..
   */
  @Test
  void testInitialize_gridsSelected_gridSelectorDisplaysGrids(FxRobot robot)
    throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setNumberOfGrids(4);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ComboBox<Integer> gridSelector = robot.lookup("#excludedGridsSelector").query();
    robot.clickOn(gridSelector);
    robot.clickOn("2");
    CheckBoxListCell<Integer> checkBoxCell = robot.lookup("3").query();
    CheckBox checkBox = (CheckBox) checkBoxCell.getGraphic();
    robot.clickOn(checkBox);
    robot.clickOn("1");

    // Perform assertions.
    MatcherAssert.assertThat(
      "The excluded grid selector's text did not match the expected value.",
      gridSelector.getButtonCell().getText(), CoreMatchers.is("1, 2, 3"));
  }
}
