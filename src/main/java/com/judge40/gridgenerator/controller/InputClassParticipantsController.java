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

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
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

  private static final String VALID_FORMAT = "[A-Z]+\\d+[A-Z]*|\\d+F";

  private final ResourceBundle messageBundle = ResourceBundle.getBundle("i18n.Messages");

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

  @FXML
  private void addParticipant() {
    String newParticipant = newParticipantInput.getText();

    if (newParticipant.isEmpty()) {
      return;
    }

    if (!newParticipant.matches(VALID_FORMAT)) {
      error("participant.add.invalid", newParticipant);
      return;
    }

    if (participantsDisplay.getItems().contains(newParticipant)) {
      error("participant.add.alreadyExists", newParticipant);
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
   * Displays an error message.
   *
   * @param errorMessageKey The resource bundle key for the error message to display.
   * @param parameters The parameters to be added to the message.
   */
  private void error(String errorMessageKey, Object... parameters) {
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
}
