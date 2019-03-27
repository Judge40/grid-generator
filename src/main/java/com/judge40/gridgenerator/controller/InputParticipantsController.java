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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * An FXML controller controller for participant management.
 */
public class InputParticipantsController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private TabPane inputParticipantLayout;

  /**
   * Initialize the elements used by this controller.
   */
  @FXML
  private void initialize() throws BackingStoreException, ClassNotFoundException, IOException {
    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();

    ObservableList<Tab> tabs = inputParticipantLayout.getTabs();

    for (String className : participantClassNames) {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/fxml/input-class-participants.fxml"), resources);
      VBox inputClassParticipants = loader.load();
      InputClassParticipantsController inputClassController = loader.getController();
      inputClassController.initializeData(className);

      Tab classTab = new Tab(className, inputClassParticipants);
      tabs.add(classTab);
    }
  }
}
