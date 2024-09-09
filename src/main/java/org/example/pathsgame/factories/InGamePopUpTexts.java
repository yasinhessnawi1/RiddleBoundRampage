package org.example.pathsgame.factories;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.pathsgame.ui.views.game.ingame.InGameLayout;

/**
 * The InGamePopUpTexts is a singleton class that provides functionality for creating pop-up text
 * during gameplay. The PopUpText is used for temporary messages that disappear after a set
 * duration.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class InGamePopUpTexts {
  private static InGamePopUpTexts instance;

  /**
   * Private constructor for the Singleton pattern.
   */
  private InGamePopUpTexts() {
  }

  /**
   * Returns the single instance of the InGamePopUpTexts class.
   * If there isn't an instance already, it creates one.
   *
   * @return The Singleton instance of the InGamePopUpTexts class.
   */
  public static synchronized InGamePopUpTexts getInstance() {
    if (instance == null) {
      instance = new InGamePopUpTexts();
    }
    return instance;
  }

  /**
   * Creates a pop-up text with the given string content.
   * The text is displayed in the center of the screen for a short period of time.
   *
   * @param text The string to be displayed in the pop-up text.
   * @return The Text object representing the pop-up text.
   */
  public Text getPopUpText(String text) {
    Text popUpText = new Text(text);
    popUpText.getStyleClass().add("popUpText");
    popUpText.setTranslateY(InGameLayout.getInstance().getCenterView().getHeight() / 2);

    InGameLayout.getInstance().getCenterView().getChildren().add(popUpText);

    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(popUpText.translateYProperty(), -50);
    KeyFrame kf = new KeyFrame(Duration.seconds(2), kv);
    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(
        event -> InGameLayout.getInstance().getCenterView().getChildren().remove(popUpText));
    timeline.play();

    return popUpText;
  }
}
