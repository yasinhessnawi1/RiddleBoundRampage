package org.example.pathsgame.ui.views.homepage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

/**
 * The HelpView class is a singleton class responsible for creating and managing the Help scene
 * in the game. It reads the help text from a file and presents it in a scrollable pane.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class HelpView {
  private static HelpView instance;
  private final Text helpTextArea;

  /**
   * Private constructor for the HelpView class.
   * It initializes the help text area.
   */
  private HelpView() {
    helpTextArea = new Text();
  }

  /**
   * Retrieves the singleton instance of the HelpView class.
   * If the instance does not exist, it creates one.
   *
   * @return The instance of the HelpView class.
   */
  public static synchronized HelpView getInstance() {
    if (instance == null) {
      instance = new HelpView();
    }
    return instance;
  }

  /**
   * Builds and returns the help scene for the game.
   * It reads help content from a file, presents it in a text area within a scrollable pane, and
   * sets the style for the text.
   *
   * @return Scene containing the help text.
   */
  public Scene getHelpScene() {
    // Create a BufferedReader from the InputStream of the file
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(getClass().getResourceAsStream("/Riddlebound rampage.txt")));
    // Use the lines() method of BufferedReader to get a Stream of lines, then
    // use the collect() method of Stream to join them together into a single String
    String fileContent = reader.lines().collect(Collectors.joining("\n"));
    ScrollPane scrollPane = new ScrollPane();
    helpTextArea.setText(fileContent);
    scrollPane.setContent(helpTextArea);
    helpTextArea.setStyle(
        "-fx-font-size: 26;"
            + "-fx-font-weight: bold;"
            + "-fx-font-family: 'Comic Sans MS';"
            + "-fx-padding: 20;"
            + "-fx-text-alignment: center;");
    return new Scene(scrollPane);
  }
}
