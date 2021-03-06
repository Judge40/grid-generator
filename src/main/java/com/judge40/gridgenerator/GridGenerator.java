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

import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * An application for organizing race competitors in to randomized starting grids.
 */
public class GridGenerator extends Application {

  @Override
  public void start(Stage primaryStage)
      throws BackingStoreException, ClassNotFoundException, IOException {
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");
    BorderPane main = FXMLLoader
        .load(getClass().getResource("/fxml/GridGenerator.fxml"), labelsBundle);

    primaryStage.setTitle("Grid Generator");
    primaryStage.setScene(new Scene(main));
    primaryStage.show();

    PreferenceHelper.initializePreferences();
  }
}
