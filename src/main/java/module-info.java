module grid.generator {
  exports com.judge40.gridgenerator;
  exports com.judge40.gridgenerator.controller;

  opens com.judge40.gridgenerator.controller;

  requires java.logging;
  requires java.prefs;
  requires javafx.controls;
  requires javafx.fxml;
}
