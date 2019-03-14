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
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * The unit tests for {@link InputParticipantsController}.
 */
@ExtendWith(ApplicationExtension.class)
class InputParticipantsControllerTest {

  private TabPane inputLayout;

  @Start
  void setUp(Stage stage) throws IOException {
    ResourceBundle labelsBundle = ResourceBundle.getBundle("i18n.Labels");
    inputLayout = FXMLLoader
      .load(getClass().getResource("/fxml/input-participants.fxml"), labelsBundle);
    stage.setScene(new Scene(inputLayout));
    stage.show();
  }

  /**
   * Test that the class tabs are added when the controller is initialized.
   */
  @Test
  void testInitialize_classTabsAdded() {
    // Perform assertions.
    ObservableList<Tab> tabs = inputLayout.getTabs();
    MatcherAssert.assertThat("The number of tabs did not match the expected value.", tabs.size(),
      CoreMatchers.is(10));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(0).getText(),
        CoreMatchers.is("Class 1"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(1).getText(),
        CoreMatchers.is("Class 2"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(2).getText(),
        CoreMatchers.is("Class 3"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(3).getText(),
        CoreMatchers.is("Class 4"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(4).getText(),
        CoreMatchers.is("Class 5"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(5).getText(),
        CoreMatchers.is("Class 6"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(6).getText(),
        CoreMatchers.is("Class 7"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(7).getText(),
        CoreMatchers.is("Class 8"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(8).getText(),
        CoreMatchers.is("Class 9"));
    MatcherAssert
      .assertThat("The tab's text did not match the expected value.", tabs.get(9).getText(),
        CoreMatchers.is("Class 10"));
  }
}
