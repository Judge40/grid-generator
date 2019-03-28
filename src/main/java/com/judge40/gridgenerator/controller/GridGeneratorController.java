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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * An FXML controller controller for handling high level events, such as menu interaction.
 */
public class GridGeneratorController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private BorderPane mainLayout;

  /**
   * Display the Draw Grids interface.
   * @throws IOException If the required FXML could not be loaded.
   */
  @FXML
  private void displayDrawGrids() throws IOException {
    VBox drawGrids = FXMLLoader
      .load(getClass().getResource("/fxml/draw-grids.fxml"), resources);
    mainLayout.setCenter(drawGrids);
  }

  /**
   * Display the Input Participants interface.
   * @throws IOException If the required FXML could not be loaded.
   */
  @FXML
  private void displayInputParticipants() throws IOException {
    TabPane inputParticipants = FXMLLoader
      .load(getClass().getResource("/fxml/input-participants.fxml"), resources);
    mainLayout.setCenter(inputParticipants);
  }

  /**
   * Exit the application.
   */
  @FXML
  private void exit() {
    for (Window window : Window.getWindows()) {
      if (window instanceof Stage) {
        Platform.runLater(((Stage) window)::close);
      }
    }
  }
}
