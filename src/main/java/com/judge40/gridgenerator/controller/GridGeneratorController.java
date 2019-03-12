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

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * An FXML controller controller for handling high level events, such as menu interaction.
 */
public class GridGeneratorController {

  /**
   * Display the Draw Grids interface.
   */
  @FXML
  public void displayDrawGrids() {
    System.out.println("Draw Grids not yet implemented.");
  }

  /**
   * Display the Input Participants interface.
   */
  @FXML
  private void displayInputParticipants() {
    System.out.println("Input Participants not yet implemented.");
  }

  /**
   * Exit the application.
   */
  @FXML
  private void exit() {
    Platform.exit();
  }
}
