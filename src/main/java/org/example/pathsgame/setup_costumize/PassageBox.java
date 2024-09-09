package org.example.pathsgame.setup_costumize;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

/**
 * The PassageBox class is a singleton that provides s stylized text boxes for displaying passages
 * in the game. It includes methods to create and animate the display box, including color
 * transition animations and a typing effect for the text.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class PassageBox extends Region {
  private static PassageBox instance;

  /**
   * Constructor for PassageBox class.
   * This constructor initializes the colors array for the box's transition animation and sets the
   * initial color index.
   */
  private PassageBox() {
  }

  /**
   * Static method to get the single instance of the PassageBox class.
   *
   * @return The singleton instance of the PassageBox class.
   */
  public static synchronized PassageBox getInstance() {
    if (instance == null) {
      instance = new PassageBox();
    }
    return instance;
  }

  /**
   * Method to generate a stylized text box with the provided text.
   *
   * @param text the text to be displayed in the passage box.
   * @return The passage box as a JavaFX Node.
   */
  public Node getPassageBox(String text) {
    // Create and configure the outer box
    Rectangle outerBox = new Rectangle(380, 180);
    outerBox.setArcWidth(20);
    outerBox.setArcHeight(20);
    outerBox.getStyleClass().add("outer-box");

    // Create and configure the passage text
    Text passageText = new Text(text);
    passageText.setFont(new Font("Arial", 18));
    passageText.setWrappingWidth(320);
    // Justify text alignment for better readability
    passageText.setTextAlignment(
        TextAlignment.LEFT);
    passageText.setBoundsType(TextBoundsType.LOGICAL);
    passageText.getStyleClass().add("passage-text");
    createTypingAnimation(passageText, text);

    // Create and configure the container for the text
    ScrollPane textContainer = new ScrollPane(passageText);
    textContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    textContainer.setPrefSize(350, 150);
    textContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    textContainer.getStyleClass().add("text-container");

    StackPane passageRegion = new StackPane(outerBox, textContainer);
    passageRegion.getStyleClass().add("passage-region");

    return passageRegion;
  }


  /**
   * Method to create a typing animation for the text displayed in the passage box.
   *
   * @param textNode The text node to apply the typing animation to.
   * @param fullText The full text to be displayed in the passage box.
   */
  private void createTypingAnimation(Text textNode, String fullText) {
    final StringBuilder visibleText = new StringBuilder();
    int textLength = fullText.length();

    Timeline typingAnimation = new Timeline();
    typingAnimation.setCycleCount(1);

    for (int i = 0; i < textLength; i++) {
      final int currentCharIndex = i;
      KeyFrame keyFrame = new KeyFrame(Duration.millis(50.0 * (i + 1)), event -> {
        visibleText.append(fullText.charAt(currentCharIndex));
        textNode.setText(visibleText.toString());
      });
      typingAnimation.getKeyFrames().add(keyFrame);
    }
    typingAnimation.play();
  }

}



