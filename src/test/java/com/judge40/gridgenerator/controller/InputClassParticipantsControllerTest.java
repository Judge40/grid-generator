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
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * The unit tests for {@link InputClassParticipantsController}.
 */
@ExtendWith(ApplicationExtension.class)
class InputClassParticipantsControllerTest {

  private static final String ADD_BUTTON = "#addButton";
  private static final String CLEAR_BUTTON = "#clearButton";
  private static final String DELETE_BUTTON = "#deleteButton";
  private static final String ERROR_MESSAGE_DISPLAY = "#errorMessageDisplay";
  private static final String NEW_PARTICIPANT_INPUT = "#newParticipantInput";
  private static final String PARTICIPANTS_DISPLAY = "#participantsDisplay";

  private static Locale defaultLocale;

  private Stage stage;

  @BeforeAll
  static void setUpBeforeAll() {
    defaultLocale = Locale.getDefault();
  }

  @Start
  void setUp(Stage stage) {
    this.stage = stage;
  }

  @AfterAll
  static void tearDownAfterAll() {
    Locale.setDefault(defaultLocale);
  }

  /**
   * Test that the buttons are disabled and have English text when they are initialized with the
   * locale set to English.
   */
  @ParameterizedTest(name = "Button \"{0}\" should be disabled and its text should be \"{1}\"")
  @CsvSource({
    "'" + ADD_BUTTON + "', Add",
    "'" + CLEAR_BUTTON + "', Clear",
    "'" + DELETE_BUTTON + "', Delete"
  })
  void testInitialize_en_buttonDisabledTextEnglish(String buttonId, String label, FxRobot robot)
      throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    // Call the code under test.
    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    Button button = (Button) scene.lookup(buttonId);
    MatcherAssert
      .assertThat("The button's text did not match the expected value.", button.getText(),
        CoreMatchers.is(label));
    MatcherAssert.assertThat("The button's enabled state did not match the expected value.",
      button.isDisabled(), CoreMatchers.is(true));
  }

  /**
   * Test that the buttons are disabled and have Pseudo English text when they are initialized with
   * the locale set to Pseudo English.
   */
  @ParameterizedTest(name = "Button \"{0}\" should be disabled and its text should be \"{1}\"")
  @CsvSource({
    "'" + ADD_BUTTON + "', [!!! Âδδ  !!!]",
    "'" + CLEAR_BUTTON + "', [!!! Çℓèář ℓ !!!]",
    "'" + DELETE_BUTTON + "', [!!! Ðèℓèƭè ℓ !!!]"
  })
  void testInitialize_enPseudo_buttonDisabledTextPseudoEnglish(String buttonId, String label,
      FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    // Call the code under test.
    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    Button button = (Button) scene.lookup(buttonId);
    MatcherAssert
      .assertThat("The button's text did not match the expected value.", button.getText(),
        CoreMatchers.is(label));
    MatcherAssert.assertThat("The button's enabled state did not match the expected value.",
      button.isDisabled(), CoreMatchers.is(true));
  }

  /**
   * Test that the input prompt has English text when it is initialized with the locale set to
   * English.
   */
  @Test
  void testInitialize_en_inputPromptTextEnglish(FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    // Call the code under test.
    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    MatcherAssert.assertThat("The input's prompt text did not match the expected value.",
      newParticipantInput.getPromptText(), CoreMatchers.is("Enter driver..."));
  }

  /**
   * Test that the input prompt has Pseudo English text when it is initialized with the locale set
   * to Pseudo English.
   */
  @Test
  void testInitialize_en_inputPromptTextPseudoEnglish(FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    // Call the code under test.
    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Perform assertions.
    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    MatcherAssert.assertThat("The input's prompt text did not match the expected value.",
      newParticipantInput.getPromptText(), CoreMatchers.is("[!!! Éñƭèř δřïƲèř... ℓôřè !!!]"));
  }

  /**
   * Test that the add button is enabled when the new participant input field is populated.
   */
  @Test
  void testInitialize_inputPopulated_addButtonEnabled(FxRobot robot) throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);

    // Call the code under test.
    newParticipantInput.setText("populated");

    // Perform assertions.
    Button addButton = (Button) scene.lookup(ADD_BUTTON);
    MatcherAssert.assertThat("The add button's enabled state did not match the expected value.",
      addButton.isDisabled(), CoreMatchers.is(false));
  }

  /**
   * Test that the add button is disabled when the new participant input field is populated and then
   * deleted.
   */
  @Test
  void testInitialize_inputPopulatedThenDeleted_addButtonDisabled(FxRobot robot)
      throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    newParticipantInput.setText("populated");

    // Call the code under test.
    newParticipantInput.setText("");

    // Perform assertions.
    Button addButton = (Button) scene.lookup(ADD_BUTTON);
    MatcherAssert.assertThat("The add button's enabled state did not match the expected value.",
      addButton.isDisabled(), CoreMatchers.is(true));
  }

  /**
   * Test that the clear button is enabled when a participant is added.
   */
  @Test
  void testInitialize_participantAdded_clearButtonEnabled(FxRobot robot) throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();

    // Call the code under test.
    participants.add("participant");

    // Perform assertions.
    Button clearButton = (Button) scene.lookup(CLEAR_BUTTON);
    MatcherAssert.assertThat("The clear button's enabled state did not match the expected value.",
      clearButton.isDisabled(), CoreMatchers.is(false));
  }

  /**
   * Test that the clear button is disabled when a participant is added and then removed.
   */
  @Test
  void testInitialize_participantAddedThenRemoved_clearButtonDisabled(FxRobot robot)
      throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.add("participant");

    // Call the code under test.
    participants.remove("participant");

    // Perform assertions.
    Button clearButton = (Button) scene.lookup(CLEAR_BUTTON);
    MatcherAssert.assertThat("The clear button's enabled state did not match the expected value.",
      clearButton.isDisabled(), CoreMatchers.is(true));
  }

  /**
   * Test that the delete button is enabled when a displayed participant is selected.
   */
  @Test
  void testInitialize_displayedParticipantSelected_deleteButtonEnabled(FxRobot robot)
      throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ListView<String> participantsDisplay = (ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY);
    participantsDisplay.getItems().add("participant");

    // Call the code under test.
    participantsDisplay.getSelectionModel().selectFirst();

    // Perform assertions.
    Button deleteButton = (Button) scene.lookup(DELETE_BUTTON);
    MatcherAssert.assertThat("The delete button's enabled state did not match the expected value.",
      deleteButton.isDisabled(), CoreMatchers.is(false));
  }

  /**
   * Test that the delete button is disabled when a displayed participant is selected and then
   * unselected.
   */
  @Test
  void testInitialize_displayedParticipantSelectedThenUnselected_deleteButtonDisabled(FxRobot robot)
      throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ListView<String> participantsDisplay = (ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY);
    participantsDisplay.getItems().add("participant");
    MultipleSelectionModel<String> participantsSelectionModel = participantsDisplay
      .getSelectionModel();
    participantsSelectionModel.selectFirst();

    // Call the code under test.
    participantsSelectionModel.clearSelection();

    // Perform assertions.
    Button deleteButton = (Button) scene.lookup(DELETE_BUTTON);
    MatcherAssert.assertThat("The delete button's enabled state did not match the expected value.",
      deleteButton.isDisabled(), CoreMatchers.is(true));
  }

  /**
   * Test that no action is performed when the input field is empty and the add action is triggered
   * from the input field.
   */
  @ParameterizedTest(name = "No action should be taken when the add action is triggered from \"{0}\"")
  @ValueSource(strings = {ADD_BUTTON, NEW_PARTICIPANT_INPUT})
  void testAddParticipant_emptyInputFieldAction_noAction(String trigger, FxRobot robot)
      throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    // Call the code under test.
    robot.clickOn(trigger, MouseButton.MIDDLE).push(KeyCode.ENTER);

    // Perform assertions.
    Text errorMessageDisplay = (Text) scene.lookup(ERROR_MESSAGE_DISPLAY);
    MatcherAssert
      .assertThat("The error text did not match the expected value.", errorMessageDisplay.getText(),
        CoreMatchers.is(""));

    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    MatcherAssert
      .assertThat("The input text did not match the expected value.", newParticipantInput.getText(),
        CoreMatchers.is(""));

    ObservableList participants = ((ListView) scene.lookup(PARTICIPANTS_DISPLAY)).getItems();
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(0));
  }

  /**
   * Test that the new participant is not added and an English error message is displayed when the
   * input field is invalid, the error message display is available, the locale is English and the
   * add action is triggered.
   */
  @ParameterizedTest(name = "Error message should be displayed when the add action is triggered from \"{0}\"")
  @ValueSource(strings = {ADD_BUTTON, NEW_PARTICIPANT_INPUT})
  void testAddParticipant_invalidInputHasErrorDisplayEn_englishErrorMessage(String trigger,
      FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    newParticipantInput.setText("invalidInput");

    // Call the code under test.
    robot.clickOn(trigger, MouseButton.MIDDLE).push(KeyCode.ENTER);

    // Perform assertions.
    Text errorMessageDisplay = (Text) scene.lookup(ERROR_MESSAGE_DISPLAY);
    MatcherAssert
      .assertThat("The error text did not match the expected value.", errorMessageDisplay.getText(),
        CoreMatchers.is("\"invalidInput\" is not a valid input."));
    MatcherAssert
      .assertThat("The input text did not match the expected value.", newParticipantInput.getText(),
        CoreMatchers.is("invalidInput"));

    ObservableList participants = ((ListView) scene.lookup(PARTICIPANTS_DISPLAY)).getItems();
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(0));
  }

  /**
   * Test that the new participant is not added and a Pseudo English error message is displayed when
   * the input field is invalid, the error message display is available, the locale is Pseudo
   * English and the add action is triggered.
   */
  @ParameterizedTest(name = "Error message should be displayed when the add action is triggered from \"{0}\"")
  @ValueSource(strings = {ADD_BUTTON, NEW_PARTICIPANT_INPUT})
  void testAddParticipant_invalidInputHasErrorDisplayEnPseudo_pseudoEnglishErrorMessage(
      String trigger, FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    newParticipantInput.setText("invalidInput");

    // Call the code under test.
    robot.clickOn(trigger, MouseButton.MIDDLE).push(KeyCode.ENTER);

    // Perform assertions.
    Text errorMessageDisplay = (Text) scene.lookup(ERROR_MESSAGE_DISPLAY);
    MatcherAssert
      .assertThat("The error text did not match the expected value.", errorMessageDisplay.getText(),
        CoreMatchers.is("[!!! \"invalidInput\" ïƨ ñôƭ á Ʋáℓïδ ïñƥúƭ. ℓôřè₥ ï !!!]"));
    MatcherAssert
      .assertThat("The input text did not match the expected value.", newParticipantInput.getText(),
        CoreMatchers.is("invalidInput"));

    ObservableList participants = ((ListView) scene.lookup(PARTICIPANTS_DISPLAY)).getItems();
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(0));
  }

  /**
   * Test that the new participant is added when the input field is valid and the add action is
   * triggered.
   */
  @ParameterizedTest(name = "Participant should be added when the add action is triggered from \"{0}\"")
  @ValueSource(strings = {ADD_BUTTON, NEW_PARTICIPANT_INPUT})
  void testAddParticipant_validInput_participantAdded(String trigger, FxRobot robot)
      throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    newParticipantInput.setText("A1");

    Text errorMessageDisplay = (Text) scene.lookup(ERROR_MESSAGE_DISPLAY);
    errorMessageDisplay.setText("errorMessage");

    // Call the code under test.
    robot.clickOn(trigger, MouseButton.MIDDLE).push(KeyCode.ENTER);

    // Perform assertions.
    MatcherAssert
      .assertThat("The error text did not match the expected value.", errorMessageDisplay.getText(),
        CoreMatchers.is(""));
    MatcherAssert
      .assertThat("The input text did not match the expected value.", newParticipantInput.getText(),
        CoreMatchers.is(""));

    ObservableList participants = ((ListView) scene.lookup(PARTICIPANTS_DISPLAY)).getItems();
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(1));
    MatcherAssert
      .assertThat("The item in the participants display did not match the expected value.",
        participants.get(0), CoreMatchers.is("A1"));
  }

  /**
   * Test that the new participant is not added and an English error message is displayed when the
   * input field is valid, the input already exists in the table, the locale is English and the add
   * action is triggered.
   */
  @ParameterizedTest(name = "Participant should not be added when the add action is triggered from \"{0}\"")
  @ValueSource(strings = {ADD_BUTTON, NEW_PARTICIPANT_INPUT})
  void testAddParticipant_validInputDuplicateDisplayedEn_englishErrorMessage(String trigger,
      FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.add("A1");
    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    newParticipantInput.setText("A1");

    // Call the code under test.
    robot.clickOn(trigger, MouseButton.MIDDLE).push(KeyCode.ENTER);

    // Perform assertions.
    Text errorMessageDisplay = (Text) scene.lookup(ERROR_MESSAGE_DISPLAY);
    MatcherAssert
      .assertThat("The error text did not match the expected value.", errorMessageDisplay.getText(),
        CoreMatchers.is("\"A1\" already exists."));
    MatcherAssert
      .assertThat("The input text did not match the expected value.", newParticipantInput.getText(),
        CoreMatchers.is("A1"));

    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(1));
    MatcherAssert
      .assertThat("The item in the participants display did not match the expected value.",
        participants.get(0), CoreMatchers.is("A1"));
  }

  /**
   * Test that the new participant is not added and a Pseudo English error message is displayed when
   * the input field is valid, the input already exists in the table, the locale is Pseudo English
   * and the add action is triggered.
   */
  @ParameterizedTest(name = "Participant should not be added when the add action is triggered from \"{0}\"")
  @ValueSource(strings = {ADD_BUTTON, NEW_PARTICIPANT_INPUT})
  void testAddParticipant_validInputDuplicateDisplayedEnPseudo_pseudoEnglishErrorMessage(
      String trigger, FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.add("A1");
    TextField newParticipantInput = (TextField) scene.lookup(NEW_PARTICIPANT_INPUT);
    newParticipantInput.setText("A1");

    // Call the code under test.
    robot.clickOn(trigger, MouseButton.MIDDLE).push(KeyCode.ENTER);

    // Perform assertions.
    Text errorMessageDisplay = (Text) scene.lookup(ERROR_MESSAGE_DISPLAY);
    MatcherAssert
      .assertThat("The error text did not match the expected value.", errorMessageDisplay.getText(),
        CoreMatchers.is("[!!! \"A1\" áℓřèáδ¥ èжïƨƭƨ. ℓôřè₥ !!!]"));
    MatcherAssert
      .assertThat("The input text did not match the expected value.", newParticipantInput.getText(),
        CoreMatchers.is("A1"));

    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(1));
    MatcherAssert
      .assertThat("The item in the participants display did not match the expected value.",
        participants.get(0), CoreMatchers.is("A1"));
  }

  /**
   * Test that a confirmation dialog is displayed in English when the clear action is triggered and
   * the locale is English.
   */
  @Test
  void testClearParticipants_en_englishConfirmationDialog(FxRobot robot) throws IOException {
    // Set up test scenario.
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.addAll("participant1", "participant2", "participant3");

    // Call the code under test.
    robot.clickOn(CLEAR_BUTTON);

    // Perform assertions.
    String expectedDialogMessage = "All participants will be cleared, this cannot be undone.";
    Label dialogMessage = robot.lookup(expectedDialogMessage).query();
    DialogPane dialog = (DialogPane) dialogMessage.getParent();

    MatcherAssert.assertThat("The dialog's header text did not match the expected value.",
      dialog.getHeaderText(), CoreMatchers.is("Confirmation"));
    MatcherAssert.assertThat("The dialog's content text did not match the expected value.",
      dialog.getContentText(), CoreMatchers.is(expectedDialogMessage));

    ObservableList<ButtonType> buttonTypes = dialog.getButtonTypes();
    MatcherAssert.assertThat("The number of buttons did not match the expected value.",
      buttonTypes.size(), CoreMatchers.is(2));
    MatcherAssert.assertThat("The button type did not match the expected value.",
      buttonTypes.get(0), CoreMatchers.is(ButtonType.OK));
    MatcherAssert.assertThat("The button type did not match the expected value.",
      buttonTypes.get(1), CoreMatchers.is(ButtonType.CANCEL));

    // Perform clean up.
    robot.clickOn("Cancel");
  }

  /**
   * Test that a confirmation dialog is displayed in Pseudo English when the clear action is
   * triggered and the locale is Pseudo English.
   */
  @Test
  void testClearParticipants_enPseudo_pseudoEnglishConfirmationDialog(FxRobot robot)
      throws IOException {
    // Set up test scenario.
    Locale.setDefault(new Locale("en", "PSEUDO"));
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.addAll("participant1", "participant2", "participant3");

    // Call the code under test.
    robot.clickOn(CLEAR_BUTTON);

    // Perform assertions.
    String expectedDialogMessage = "[!!! Âℓℓ ƥářƭïçïƥáñƭƨ ωïℓℓ βè çℓèářèδ, ƭλïƨ çáññôƭ βè úñδôñè. ℓôřè₥ ïƥƨú₥ δôℓô !!!]";
    Label dialogMessage = robot.lookup(expectedDialogMessage).query();
    DialogPane dialog = (DialogPane) dialogMessage.getParent();

    MatcherAssert.assertThat("The dialog's header text did not match the expected value.",
      dialog.getHeaderText(), CoreMatchers.is("Confirmation"));
    MatcherAssert.assertThat("The dialog's content text did not match the expected value.",
      dialog.getContentText(), CoreMatchers.is(expectedDialogMessage));

    ObservableList<ButtonType> buttonTypes = dialog.getButtonTypes();
    MatcherAssert.assertThat("The number of buttons did not match the expected value.",
      buttonTypes.size(), CoreMatchers.is(2));
    MatcherAssert.assertThat("The button type did not match the expected value.",
      buttonTypes.get(0), CoreMatchers.is(ButtonType.OK));
    MatcherAssert.assertThat("The button type did not match the expected value.",
      buttonTypes.get(1), CoreMatchers.is(ButtonType.CANCEL));

    // Perform clean up.
    robot.clickOn("Cancel");
  }

  /**
   * Test that the displayed participants are not cleared when the clear action is triggered and the
   * confirmation is cancelled.
   */
  @Test
  void testClearParticipants_cancel_participantsNotCleared(FxRobot robot) throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.addAll("participant1", "participant2", "participant3");

    // Call the code under test.
    robot.clickOn(CLEAR_BUTTON);
    robot.clickOn("Cancel");

    // Perform assertions.
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(3));
  }

  /**
   * Test that the displayed participants are cleared when the clear action is triggered and the
   * confirmation is confirmed.
   */
  @Test
  void testClearParticipants_ok_participantsCleared(FxRobot robot) throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ObservableList<String> participants = ((ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY))
      .getItems();
    participants.addAll("participant1", "participant2", "participant3");

    // Call the code under test.
    robot.clickOn(CLEAR_BUTTON);
    robot.clickOn("OK");

    // Perform assertions.
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(0));
  }

  /**
   * Test that the selected displayed participants is deleted when the delete action is triggered.
   */
  @Test
  void testDeleteParticipant_participantsDisplayedWithSelection_selectedParticipantDeleted(
      FxRobot robot) throws IOException {
    // Set up test scenario.
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");

    VBox inputClassParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-class-participants.fxml"), labelsBundle);
    Scene scene = new Scene(inputClassParticipants);

    robot.interact(() -> {
      stage.setScene(scene);
      stage.show();
    });

    ListView<String> participantsDisplay = (ListView<String>) scene.lookup(PARTICIPANTS_DISPLAY);
    ObservableList<String> participants = participantsDisplay.getItems();
    participants.addAll("participant1", "participant2", "participant3");
    participantsDisplay.getSelectionModel().select(1);

    // Call the code under test.
    robot.clickOn(DELETE_BUTTON);

    // Perform assertions.
    MatcherAssert.assertThat(
      "The number of items in the participants display did not match the expected value.",
      participants.size(), CoreMatchers.is(2));
    MatcherAssert
      .assertThat("The item in the participants display did not match the expected value.",
        participants.get(0), CoreMatchers.is("participant1"));
    MatcherAssert
      .assertThat("The item in the participants display did not match the expected value.",
        participants.get(1), CoreMatchers.is("participant3"));
  }
}
