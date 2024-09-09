module org.example.pathsgame {
  requires javafx.controls;
  requires javafx.fxml;
  requires jdk.jfr;
  requires java.logging;
  requires org.controlsfx.controls;
  requires javafx.media;

  opens org.example.pathsgame to javafx.fxml, java.base;
  exports org.example.pathsgame;
  exports org.example.pathsgame.entities.story;
  opens org.example.pathsgame.entities.story to javafx.fxml, javafx.base;
  opens org.example.pathsgame.entities.events to javafx.base;
  exports org.example.pathsgame.entities.events;
}