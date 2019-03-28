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
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.util.Callback;

/**
 * An FXML controller controller for grid drawing.
 */
public class DrawGridsController {

  private final ResourceBundle messageBundle = ResourceBundle.getBundle("i18n.Messages");

  @FXML
  private ComboBox<Integer> excludedGridsSelector;

  private Set<Integer> selectedGrids = new TreeSet<>();

  @FXML
  private void initialize() {
    initializeExcludedGridsSelector();
  }

  /**
   * Populate the excluded grids selector based on the total number of grids and initialize the
   * callbacks for interacting with the selector.
   */
  private void initializeExcludedGridsSelector() {
    // Populate the selector's choices.
    int numberOfGrids = PreferenceHelper.getNumberOfGrids();

    if (numberOfGrids <= 0) {
      excludedGridsSelector.setDisable(true);
      String message = messageBundle.getString("draw.zeroGrids");
      excludedGridsSelector.setPromptText(message);
      return;
    }

    ObservableList<Integer> excludedGridsItems = excludedGridsSelector.getItems();

    for (int gridNumber = 1; gridNumber <= numberOfGrids; gridNumber++) {
      excludedGridsItems.add(gridNumber);
    }

    // When a grid's checkbox is changed update the selected grids and the summary text.
    Callback<Integer, ObservableValue<Boolean>> gridCellCallback = grid -> {
      BooleanProperty booleanObservable = new SimpleBooleanProperty();
      booleanObservable.set(selectedGrids.contains(grid));
      booleanObservable.addListener((observable, wasSelected, isNowSelected) -> {
        if (isNowSelected) {
          selectedGrids.add(grid);
        } else {
          selectedGrids.remove(grid);
        }

        String selectedGridsText = String.join(", ", selectedGrids.stream()
          .map(String::valueOf).toArray(String[]::new));
        excludedGridsSelector.getButtonCell().setText(selectedGridsText);
      });
      return booleanObservable;
    };

    // Allow clicking on the cell to trigger the checkbox.
    Callback<ListView<Integer>, ListCell<Integer>> cellFactory = param -> {
      CheckBoxListCell<Integer> gridCell = new CheckBoxListCell<>(gridCellCallback);
      gridCell.setOnMouseClicked(event -> {
        CheckBox checkBox = (CheckBox) gridCell.getGraphic();
        if (checkBox != null) {
          checkBox.fire();
        }
      });
      return gridCell;
    };

    excludedGridsSelector.setCellFactory(cellFactory);
    excludedGridsSelector.setButtonCell(new ListCell<>());

    // Disable hide on click so multiple selections can be made.
    excludedGridsSelector.skinProperty().addListener(
      (observable, oldValue, newValue) -> ((ComboBoxListViewSkin) newValue).setHideOnClick(false));
  }
}
