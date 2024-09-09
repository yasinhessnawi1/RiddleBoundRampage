package org.example.pathsgame.setup_costumize;

import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The TextStyler class provides functionality for styling text and checkboxes in the application.
 * It follows the Singleton design pattern to ensure that only one instance of the class is created.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class TextStyler {
  private static TextStyler instance;

  /**
   * Constructor for the TextStyler class. It's private to support the Singleton pattern.
   */
  private TextStyler() {
  }

  /**
   * Returns the singleton instance of the TextStyler class.
   *
   * @return The singleton instance of the TextStyler class.
   */
  public static synchronized TextStyler getInstance() {
    if (instance == null) {
      instance = new TextStyler();
    }
    return instance;
  }

  /**
   * Styles a Text node with predefined styles including font size, font weight, font family, test
   * fill color, and drop shadow.
   *
   * @param text The text to be displayed.
   * @param size The size of the text to be displayed.
   * @return A styled Text node.
   */
  public Text styleText(String text, int size) {
    Text styledText = new Text(text);
    styledText.setStyle(
        " -fx-font-size: " + size
            + "px; -fx-font-weight: bold; -fx-fill: #D4AF37; -fx-text-fill: #D4AF37; "
            + "-fx-font-family: "
            + "Impact ; ");
    styledText.setEffect(new DropShadow(10, Color.BLACK));
    return styledText;
  }

  /**
   * Styles a CheckBox node with pre-defined styles including font size, font weight, font family,
   * and text fill color.
   *
   * @param text The text to be displayed on the CheckBox.
   * @param size The size of the text to be displayed.
   * @return A styles CheckBox node.
   */
  public CheckBox styleCheckBox(String text, int size) {
    CheckBox styledCheckBox = new CheckBox(text);
    styledCheckBox.setStyle(
        " -fx-font-size: " + size
            + "px; -fx-font-weight: bold; -fx-text-fill: #D4AF37; -fx-font-family: "
            + "Impact ; ");
    return styledCheckBox;
  }
}
