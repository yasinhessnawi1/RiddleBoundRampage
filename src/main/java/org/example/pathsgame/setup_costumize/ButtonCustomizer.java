package org.example.pathsgame.setup_costumize;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * ButtonCustomizer is a Singleton factory for creating and customizing Buttons with certain
 * styles and effects like a drop shadow effects. It provides a method for setting up a button with
 * an image and for applying effects to the button.
 *
 * @author Yasin Hessnawi.
 * @author Rami.
 * @version 0.0.1
 */
public class ButtonCustomizer {
  private static ButtonCustomizer instance;

  /**
   * Private constructor to prevent creating instances of this class directly.
   * Singleton pattern is used here.
   */
  private ButtonCustomizer() {

  }

  /**
   * Provides the single instance pf ButtonCustomizer.
   * If an instance does not exist, it is created.
   *
   * @return The single instance pf ButtonCustomizer.
   */
  public static ButtonCustomizer getInstance() {

    if (instance == null) {
      instance = new ButtonCustomizer();
    }
    return instance;
  }

  /**
   * Customizes a given Button object with an image, size and effects.
   * The button will have a drop shadow, and it will bounce and scale up when hovered over.
   *
   * @param imageName The name of the image to be used as the button's icon.
   * @param width     The width of the button.
   * @param height    The height of the button.
   * @return The customized Button object.
   */
  public void getEffect(Button button, String imageName, int width, int height) {

    buttonSetUp(button, imageName, width, height);
    customizeButton(button);
  }

  /**
   * Costumatizes the given button with a drop shadow effect and a bounce and scale up effect
   * when hovered over.
   *
   * @param button the button to be customized.
   */
  private static void customizeButton(Button button) {

    DropShadow dropShadow = new DropShadow();
    dropShadow.setRadius(15.0);
    dropShadow.setOffsetX(6.0);
    dropShadow.setOffsetY(6.0);
    dropShadow.setColor(Color.color(255 / 255.0, 215 / 255.0, 0 / 255.0, 0.8));
    button.setEffect(dropShadow);

    // Add hover effect
    button.setOnMouseEntered(event -> {
      // Scale up
      ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
      st.setToX(1.05);
      st.setToY(1.05);
      st.play();

      // Bounce effect
      TranslateTransition tt = new TranslateTransition(Duration.millis(100), button);
      tt.setByY(-5);
      tt.setAutoReverse(true);
      tt.setCycleCount(2);
      tt.play();
    });

    button.setOnMouseExited(event -> {
      ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
      st.setToX(1.0);
      st.setToY(1.0);
      st.play();
    });
  }

  /**
   * Customizes a given Button object with effects.
   * The button will have a drop shadow, and it will bounce and scale up when hovered over.
   *
   * @return The customized Button object.
   */
  public Button getEffect(String text) {

    Button button = new Button(text);
    customizeButton(button);
    return button;
  }

  /**
   * Sets up the given Button object with an image and size.
   * The button's style will be set to have a transparent background and border.
   *
   * @param imageName The name of the image to be used as the button's icon.
   * @param width     The width of the button.
   * @param height    The height of the button.
   * @return The set-up Button object.
   */
  private Button buttonSetUp(Button button, String imageName, int width, int height) {
    ImageView icon =
        new ImageView(new Image(getClass()
            .getResourceAsStream("/images/icons/" + imageName)));
    button.setGraphic(icon);
    icon.setPreserveRatio(true);
    icon.setFitWidth(width);
    icon.setFitHeight(height);
    button.setStyle("-fx-background-color: transparent;"
        + " -fx-border-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
    return button;
  }

}


