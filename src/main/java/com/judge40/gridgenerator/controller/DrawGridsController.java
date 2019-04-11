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

import com.judge40.gridgenerator.GridDrawHelper;
import com.judge40.gridgenerator.PreferenceHelper;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.BackingStoreException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * An FXML controller controller for grid drawing.
 */
public class DrawGridsController {

  private final ResourceBundle messageBundle = ResourceBundle.getBundle("i18n.Messages");
  @FXML
  private ResourceBundle resources;

  @FXML
  private ComboBox<Integer> excludedGridsSelector;
  private Set<Integer> excludedGrids = new TreeSet<>();

  @FXML
  private TabPane drawnGridsDisplay;

  @FXML
  private void initialize() throws BackingStoreException, ClassNotFoundException, IOException {
    initializeExcludedGridsSelector();
    initializeDrawnGridsDisplay();
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
      booleanObservable.set(excludedGrids.contains(grid));
      booleanObservable.addListener((observable, wasSelected, isNowSelected) -> {
        if (isNowSelected) {
          excludedGrids.add(grid);
        } else {
          excludedGrids.remove(grid);
        }

        String selectedGridsText = String.join(", ", excludedGrids.stream()
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

    excludedGridsSelector.addEventHandler(ComboBox.ON_HIDDEN, event -> {
      try {
        initializeDrawnGridsDisplay();
      } catch (BackingStoreException | ClassNotFoundException | IOException e) {
        Alert errorAlert = new Alert(AlertType.ERROR, e.getLocalizedMessage());
        errorAlert.show();
      }
    });
  }

  /**
   * Perform a grid draw and populate the drawn grids display with the results.
   *
   * @throws BackingStoreException If the participants could not be retrieved.
   * @throws ClassNotFoundException If the participants could not be retrieved.
   * @throws IOException If the participants could not be retrieved.
   */
  private void initializeDrawnGridsDisplay()
    throws BackingStoreException, ClassNotFoundException, IOException {
    // Reset the tabs.
    ObservableList<Tab> drawnGridTabs = drawnGridsDisplay.getTabs();
    drawnGridTabs.clear();

    List<String> participantClassNames = PreferenceHelper.getParticipantClassNames();

    for (String participantClassName : participantClassNames) {
      List<List<List<String>>> heats = GridDrawHelper
        .drawGridsForClass(participantClassName, excludedGrids);

      Tab classTab = new Tab();
      classTab.setText(participantClassName);
      drawnGridTabs.add(classTab);

      if (heats.isEmpty()) {
        classTab.setDisable(true);
      } else {
        VBox tabContent = new VBox();
        classTab.setContent(tabContent);
        ObservableList<Node> tabChildren = tabContent.getChildren();

        // Add meeting and class headings.
        tabChildren.add(new Text("NWAA Qualifier - " + LocalDate.now().toString())); // TODO: use preference or other input
        tabChildren.add(new Text(participantClassName));

        // Add heat headings and tables.
        for (ListIterator<List<List<String>>> raceIterator = heats.listIterator();
          raceIterator.hasNext(); ) {
          List<List<String>> races = raceIterator.next();
          int heatNumber = raceIterator.nextIndex();

          String heatNumberText = resources.getString("draw.heatNumber");
          heatNumberText = MessageFormat.format(heatNumberText, heatNumber);
          Text heatTableText = new Text(heatNumberText);
          heatTableText.setId("heatTableText" + heatNumber);
          tabChildren.add(heatTableText);

          TableView<List<String>> heatTable = createHeatTable(races);
          heatTable.setId("heatTable" + heatNumber);
          tabChildren.add(heatTable);
        }
      }
    }
  }

  /**
   * Create a display table for a particular heat from the given races.
   *
   * @param races A list of races containing lists of participants.
   * @return A {@link TableView} which will display the heat's races.
   */
  private TableView<List<String>> createHeatTable(List<List<String>> races) {
    ObservableList<List<String>> observableRaces = FXCollections.observableArrayList(races);
    TableView<List<String>> heatTable = new TableView<>(observableRaces);

    int numberOfGrids = PreferenceHelper.getNumberOfGrids();

    for (int columnNumber = 1; columnNumber <= numberOfGrids; columnNumber++) {
      TableColumn<List<String>, String> column = new TableColumn<>(String.valueOf(columnNumber));
      int gridIndex = columnNumber - 1;
      column
        .setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().get(gridIndex)));
      column.setEditable(false);
      column.setSortable(false);
      heatTable.getColumns().add(column);
    }

    // Customize how the table is displayed.
    heatTable.setSelectionModel(null);
    heatTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    heatTable.setPrefSize(400, 200);
    heatTable.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
    heatTable.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

    return heatTable;
  }

  /**
   * Prints the currently displayed class's heats and races.
   */
  @FXML
  private void printCurrentClass() {
    // Get the current class tab.
    Tab currentTab = drawnGridsDisplay.getSelectionModel().getSelectedItem();
    VBox tabContent = (VBox) currentTab.getContent();

    // Print the tab content using default printer and settings.
    PrinterJob printerJob = PrinterJob.createPrinterJob();
    printerJob.printPage(tabContent);
    printerJob.endJob();
  }
}
