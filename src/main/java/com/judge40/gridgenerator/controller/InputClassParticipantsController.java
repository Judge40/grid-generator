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

import static com.judge40.gridgenerator.PreferenceHelper.getClassParticipants;

import com.judge40.gridgenerator.PreferenceHelper;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * An FXML controller controller for participant input events.
 */
public class InputClassParticipantsController {

  private static final Logger LOGGER = Logger
    .getLogger(InputClassParticipantsController.class.getName());

  private final ResourceBundle messageBundle = ResourceBundle.getBundle("i18n.Messages");

  private String participantClassName;

  @FXML
  private ListView<String> participantsDisplay;
  @FXML
  private TextField newParticipantInput;
  @FXML
  private Text errorMessageDisplay;

  @FXML
  private Button addButton;
  @FXML
  private Button clearButton;
  @FXML
  private Button deleteButton;

  /**
   * Initialize the elements used by this controller.
   */
  @FXML
  private void initialize() {
    addButton.setDisable(newParticipantInput.getText().length() == 0);
    newParticipantInput.textProperty().addListener(
      (observable, oldValue, newValue) -> addButton.setDisable(newValue.length() == 0));

    updateListButtonsDisableState();
    participantsDisplay.getSelectionModel().selectedItemProperty()
      .addListener((observable, oldValue, newValue) -> updateListButtonsDisableState());
    participantsDisplay.getItems()
      .addListener(
        (ListChangeListener<? super String>) observable -> updateListButtonsDisableState());
  }

  /**
   * Initialize the participant display with stored data from the last session for the given
   * participant class, also initializes a listener to update the stored data when changes are made
   * to the participants.
   *
   * @param participantClassName The name of the participant class to initialize the controller
   * for.
   */
  void initializeData(String participantClassName) {
    this.participantClassName = participantClassName;
    ObservableList<String> participants = participantsDisplay.getItems();

    // Set the participants from the stored preference value.
    try {
      participants.addAll(getClassParticipants(participantClassName));
    } catch (BackingStoreException | ClassNotFoundException | IOException e) {
      String errorMessage = messageBundle.getString("participant.read.error");
      errorMessage = MessageFormat.format(errorMessage, participantClassName);
      LOGGER.log(Level.WARNING, errorMessage, e);
    }

    participants
      .addListener((ListChangeListener<? super String>) observable -> updateStoredParticipants());
  }

  @FXML
  private void addParticipant() {
    String newParticipant = newParticipantInput.getText();

    if (newParticipant.isEmpty()) {
      return;
    }

    String participantValidator = PreferenceHelper.getParticipantValidator();

    // TODO: The preference should be initialized with a default so it is not assumed here.
    if (participantValidator.isEmpty()) {
      participantValidator = "[A-Z]+\\d+[A-Z]*|\\d+F";
    }

    if (!newParticipant.matches(participantValidator)) {
      displayInputValidationError("participant.add.invalid", newParticipant);
      return;
    }

    if (participantsDisplay.getItems().contains(newParticipant)) {
      displayInputValidationError("participant.add.alreadyExists", newParticipant);
      return;
    }

    participantsDisplay.getItems().add(newParticipant);
    newParticipantInput.clear();
    errorMessageDisplay.setText("");
  }

  @FXML
  private void clearParticipants() {
    String confirmationMessage = messageBundle.getString("participant.clear.confirm");
    Alert clearConfirmation = new Alert(AlertType.CONFIRMATION, confirmationMessage);
    ButtonType result = clearConfirmation.showAndWait().orElse(ButtonType.CANCEL);

    if (result.equals(ButtonType.OK)) {
      participantsDisplay.getItems().clear();
    }
  }

  @FXML
  private void deleteParticipant() {
    int selectedParticipant = participantsDisplay.getSelectionModel().getSelectedIndex();
    participantsDisplay.getItems().remove(selectedParticipant);
  }

  /**
   * Displays an input validation error message.
   *
   * @param errorMessageKey The resource bundle key for the error message to display.
   * @param parameters The parameters to be added to the message.
   */
  private void displayInputValidationError(String errorMessageKey, Object... parameters) {
    String errorMessage = messageBundle.getString(errorMessageKey);
    errorMessage = MessageFormat.format(errorMessage, parameters);
    errorMessageDisplay.setText(errorMessage);
  }

  /**
   * Update the disable state of the participant list's buttons based on the current state of the
   * list.
   */
  private void updateListButtonsDisableState() {
    clearButton.setDisable(participantsDisplay.getItems().size() == 0);
    deleteButton.setDisable(participantsDisplay.getSelectionModel().isEmpty());
  }

  /**
   * Updated the stored participant list for this controller's participant class.
   */
  private void updateStoredParticipants() {
    List<String> participants = new ArrayList<>(participantsDisplay.getItems());

    try {
      PreferenceHelper.setClassParticipants(participantClassName, participants);
    } catch (BackingStoreException | IOException e) {
      LOGGER.logrb(Level.WARNING, messageBundle, "participant.update.error", e);
    }
  }
}
