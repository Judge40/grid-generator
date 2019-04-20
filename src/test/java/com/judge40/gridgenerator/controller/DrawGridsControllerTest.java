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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    throws BackingStoreException, IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setMeetingName("meetingName");

    PreferenceHelper.setParticipantClassNames(Collections.singletonList("class"));
    PreferenceHelper.setClassParticipants("class", Collections.singletonList("participant"));

    PreferenceHelper.setNumberOfGrids(1);
    PreferenceHelper.setNumberOfHeats(2);

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

    Labeled printCurrentButton = robot.lookup("#printCurrentClassButton").queryLabeled();
    MatcherAssert.assertThat(
      "The excluded grid selector's label did not match the expected value.",
      printCurrentButton.getText(), CoreMatchers.is("Print Current"));

    Text meetingInformation = robot.lookup("#meetingInformation").queryText();
    String expectedMeetingInformation =
      "meetingName - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    MatcherAssert.assertThat("The meeting information's text did not match the expected value.",
      meetingInformation.getText(), CoreMatchers.is(expectedMeetingInformation));

    Text classInformation = robot.lookup("#classInformation").queryText();
    MatcherAssert.assertThat("The class information text did not match the expected value.",
      classInformation.getText(), CoreMatchers.is("class"));

    Text heatText = robot.lookup("#heatTableText1").queryText();
    MatcherAssert.assertThat("The heat table's title text did not match the expected value.",
      heatText.getText(), CoreMatchers.is("Heat 1"));

    heatText = robot.lookup("#heatTableText2").queryText();
    MatcherAssert.assertThat("The heat table's title text did not match the expected value.",
      heatText.getText(), CoreMatchers.is("Heat 2"));
  }

  /**
   * Test that the grid draw layout has Pseudo English labels when the locale is set to Pseudo
   * English.
   */
  @Test
  void testInitialize_enPseudo_labelsPseudoEnglish(FxRobot robot)
    throws BackingStoreException, IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setMeetingName("meetingName");

    PreferenceHelper.setParticipantClassNames(Collections.singletonList("class"));
    PreferenceHelper.setClassParticipants("class", Collections.singletonList("participant"));

    PreferenceHelper.setNumberOfGrids(1);
    PreferenceHelper.setNumberOfHeats(2);

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

    Labeled printCurrentButton = robot.lookup("#printCurrentClassButton").queryLabeled();
    MatcherAssert.assertThat(
      "The excluded grid selector's label did not match the expected value.",
      printCurrentButton.getText(), CoreMatchers.is("[!!! Þřïñƭ Çúřřèñƭ ℓôř !!!]"));

    Text meetingInformation = robot.lookup("#meetingInformation").queryText();
    String expectedMeetingInformation =
      "meetingName - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    MatcherAssert.assertThat("The meeting information's text did not match the expected value.",
      meetingInformation.getText(), CoreMatchers.is(expectedMeetingInformation));

    Text classInformation = robot.lookup("#classInformation").queryText();
    MatcherAssert.assertThat("The class information text did not match the expected value.",
      classInformation.getText(), CoreMatchers.is("class"));

    Text heatText = robot.lookup("#heatTableText1").queryText();
    MatcherAssert.assertThat("The heat table's title text did not match the expected value.",
      heatText.getText(), CoreMatchers.is("[!!! Hèáƭ 1 ℓ !!!]"));

    heatText = robot.lookup("#heatTableText2").queryText();
    MatcherAssert.assertThat("The heat table's title text did not match the expected value.",
      heatText.getText(), CoreMatchers.is("[!!! Hèáƭ 2 ℓ !!!]"));
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
   * Test that the grid selector displays the selected grids when multiple grids are selected.
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

  /**
   * Test that the class's tab is disabled when there are no participants for the class.
   */
  @Test
  void testInitialize_classHasNoParticipants_classTabDisabled(FxRobot robot)
    throws BackingStoreException, IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setParticipantClassNames(Arrays.asList("class1", "class2"));
    PreferenceHelper.setClassParticipants("class1", Collections.singletonList("participant"));

    PreferenceHelper.setNumberOfGrids(4);
    PreferenceHelper.setNumberOfHeats(2);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    TabPane classTab = robot.lookup("#drawnGridsDisplay").query();
    ObservableList<Tab> tabs = classTab.getTabs();
    MatcherAssert
      .assertThat("The number of class tabs did not match the expected value.", tabs.size(),
        CoreMatchers.is(2));

    Tab tab = tabs.get(0);
    MatcherAssert.assertThat("The tab's text did not match the expected value.", tab.getText(),
      CoreMatchers.is("class1"));
    MatcherAssert
      .assertThat("The tab's disabled state did not match the expected value.", tab.isDisabled(),
        CoreMatchers.is(false));

    tab = tabs.get(1);
    MatcherAssert.assertThat("The tab's text did not match the expected value.", tab.getText(),
      CoreMatchers.is("class2"));
    MatcherAssert
      .assertThat("The tab's disabled state did not match the expected value.", tab.isDisabled(),
        CoreMatchers.is(true));
  }

  /**
   * Test that the drawn grids are displayed with English headers when there are participants for
   * the class and the locale is set to English.
   */
  @Test
  void testInitialize_classHasParticipantsEn_drawnGridsDisplayedHeadersEnglish(FxRobot robot)
    throws BackingStoreException, IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setParticipantClassNames(Collections.singletonList("class"));
    PreferenceHelper.setClassParticipants("class", Arrays.asList("A1", "A2", "A3", "A4", "A5"));

    PreferenceHelper.setNumberOfGrids(4);
    PreferenceHelper.setNumberOfHeats(2);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    TableView<List<String>> heatTable = robot.lookup("#heatTable1").query();
    ObservableList<TableColumn<List<String>, ?>> columns = heatTable.getColumns();
    MatcherAssert
      .assertThat("The number of columns did not match the expected value.", columns.size(),
        CoreMatchers.is(5));

    for (ListIterator<TableColumn<List<String>, ?>> columnIterator = columns.listIterator();
      columnIterator.hasNext(); ) {
      int columnIndex = columnIterator.nextIndex();
      TableColumn<List<String>, ?> column = columnIterator.next();
      MatcherAssert
        .assertThat("The column's header did not match the expected value.", column.getText(),
          CoreMatchers.is(columnIndex == 0 ? "Grid:" : String.valueOf(columnIndex)));
      MatcherAssert.assertThat("The column's editable flag did not match the expected value.",
        column.isEditable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's reorderable flag did not match the expected value.",
        column.isReorderable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's sortable flag did not match the expected value.",
        column.isSortable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's resizable flag did not match the expected value.",
        column.isResizable(), CoreMatchers.is(true));
    }

    ObservableList<List<String>> items = heatTable.getItems();
    List<String> race = items.get(0);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("Race 1"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    List<String> realParticipants = race.stream()
      .filter(p -> !p.isEmpty() && !p.equals("Race")).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(3));

    race = items.get(1);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("Race 2"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    realParticipants = race.stream().filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(2));

    heatTable = robot.lookup("#heatTable2").query();
    columns = heatTable.getColumns();
    MatcherAssert
      .assertThat("The number of columns did not match the expected value.", columns.size(),
        CoreMatchers.is(5));

    for (ListIterator<TableColumn<List<String>, ?>> columnIterator = columns.listIterator();
      columnIterator.hasNext(); ) {
      int columnIndex = columnIterator.nextIndex();
      TableColumn<List<String>, ?> column = columnIterator.next();
      MatcherAssert
        .assertThat("The column's header did not match the expected value.", column.getText(),
          CoreMatchers.is(columnIndex == 0 ? "Grid:" : String.valueOf(columnIndex)));
      MatcherAssert.assertThat("The column's editable flag did not match the expected value.",
        column.isEditable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's reorderable flag did not match the expected value.",
        column.isReorderable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's sortable flag did not match the expected value.",
        column.isSortable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's resizable flag did not match the expected value.",
        column.isResizable(), CoreMatchers.is(true));
    }

    items = heatTable.getItems();
    race = items.get(0);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("Race 1"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    realParticipants = race.stream().filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(3));

    race = items.get(1);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("Race 2"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    realParticipants = race.stream().filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(2));
  }

  /**
   * Test that the drawn grids are displayed with Pseudo English headers when there are participants
   * for the class and the locale is set to Pseudo English.
   */
  @Test
  void testInitialize_classHasParticipantsEnPseudo_drawnGridsDisplayedHeadersPseudoEnglish(
    FxRobot robot)
    throws BackingStoreException, IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    PreferenceHelper.setParticipantClassNames(Collections.singletonList("class"));
    PreferenceHelper.setClassParticipants("class", Arrays.asList("A1", "A2", "A3", "A4", "A5"));

    PreferenceHelper.setNumberOfGrids(4);
    PreferenceHelper.setNumberOfHeats(2);

    // Call the code under test.
    VBox drawGridsLayout = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), labelsBundle);
    Scene scene = new Scene(drawGridsLayout);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    TableView<List<String>> heatTable = robot.lookup("#heatTable1").query();
    ObservableList<TableColumn<List<String>, ?>> columns = heatTable.getColumns();
    MatcherAssert
      .assertThat("The number of columns did not match the expected value.", columns.size(),
        CoreMatchers.is(5));

    for (ListIterator<TableColumn<List<String>, ?>> columnIterator = columns.listIterator();
      columnIterator.hasNext(); ) {
      int columnIndex = columnIterator.nextIndex();
      TableColumn<List<String>, ?> column = columnIterator.next();
      MatcherAssert
        .assertThat("The column's header did not match the expected value.", column.getText(),
          CoreMatchers.is(columnIndex == 0 ? "[!!! Gřïδ: ℓ !!!]" : String.valueOf(columnIndex)));
      MatcherAssert.assertThat("The column's editable flag did not match the expected value.",
        column.isEditable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's reorderable flag did not match the expected value.",
        column.isReorderable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's sortable flag did not match the expected value.",
        column.isSortable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's resizable flag did not match the expected value.",
        column.isResizable(), CoreMatchers.is(true));
    }

    ObservableList<List<String>> items = heatTable.getItems();
    List<String> race = items.get(0);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("[!!! Ráçè 1 ℓ !!!]"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    List<String> realParticipants = race.stream()
      .filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(3));

    race = items.get(1);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("[!!! Ráçè 2 ℓ !!!]"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    realParticipants = race.stream().filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(2));

    heatTable = robot.lookup("#heatTable2").query();
    columns = heatTable.getColumns();
    MatcherAssert
      .assertThat("The number of columns did not match the expected value.", columns.size(),
        CoreMatchers.is(5));

    for (ListIterator<TableColumn<List<String>, ?>> columnIterator = columns.listIterator();
      columnIterator.hasNext(); ) {
      int columnIndex = columnIterator.nextIndex();
      TableColumn<List<String>, ?> column = columnIterator.next();
      MatcherAssert
        .assertThat("The column's header did not match the expected value.", column.getText(),
          CoreMatchers.is(columnIndex == 0 ? "[!!! Gřïδ: ℓ !!!]" : String.valueOf(columnIndex)));
      MatcherAssert.assertThat("The column's editable flag did not match the expected value.",
        column.isEditable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's reorderable flag did not match the expected value.",
        column.isReorderable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's sortable flag did not match the expected value.",
        column.isSortable(), CoreMatchers.is(false));
      MatcherAssert.assertThat("The column's resizable flag did not match the expected value.",
        column.isResizable(), CoreMatchers.is(true));
    }

    items = heatTable.getItems();
    race = items.get(0);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("[!!! Ráçè 1 ℓ !!!]"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    realParticipants = race.stream().filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(3));

    race = items.get(1);
    MatcherAssert.assertThat("The race number did not match the expected value.", race.remove(0),
      CoreMatchers.is("[!!! Ráçè 2 ℓ !!!]"));
    MatcherAssert
      .assertThat("The number of participants did not match the expected value.", race.size(),
        CoreMatchers.is(4));
    realParticipants = race.stream().filter(p -> !p.isEmpty()).collect(Collectors.toList());
    MatcherAssert.assertThat("The number of participants did not match the expected value.",
      realParticipants.size(), CoreMatchers.is(2));
  }
}
