package org.example.pathsgame.setup_costumize;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * The BackgroundSetUp class provides a Singleton utility to create and retrieve JavaFX backGround
 * objects. This is primarily used for setting backGround images to various Scene or Pane objects.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class BackgroundSetUp {
  private static BackgroundSetUp instance;

  /**
   * Private constructor for the Singleton pattern.
   */
  private BackgroundSetUp() {
    //An empty constructor.
  }

  /**
   * Retrieves the singleton instance of the BackgroundSetUp class.
   *
   * @return An instance of BackgroundSetUp class.
   */
  public static synchronized BackgroundSetUp getInstance() {

    if (instance == null) {
      instance = new BackgroundSetUp();
    }
    return instance;
  }

  /**
   * Returns a new Background object using the given path to an image file.
   *
   * @param path The relative path to the image file from the project directory.
   * @return A new Background object with the specified image.
   */
  public Background getBackground(String path) {
    return new Background(new BackgroundImage(
        new Image(getClass().getResourceAsStream(path)),
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1, 1, true,
            true, false, false)));
  }
}
