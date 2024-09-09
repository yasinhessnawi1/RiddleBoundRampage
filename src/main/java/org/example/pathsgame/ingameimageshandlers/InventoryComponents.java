package org.example.pathsgame.ingameimageshandlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The InventoryComponents class is a singleton that provides utility methods to handle the
 * inventory components in the game view, like loading the images of the components.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class InventoryComponents {
  private static InventoryComponents instance;

  /**
   * The private constructor of the InventoryComponents class.
   * It initializes the InventoryComponents instance.
   */
  private InventoryComponents() {
  }

  /**
   * This method provides a way to access a single instance of  the InventoryComponents class.
   *
   * @return The single instance of the InventoryComponents class
   */
  public static synchronized InventoryComponents getInstance() {
    if (instance == null) {
      instance = new InventoryComponents();
    }
    return instance;
  }

  /**
   * The method returns an ImageView representing a specific room component.
   * It loads an image from the given resource path and if the image cannot be loaded, it displays
   * an error alert.
   *
   * @param name The name of the room component whose image is to be loaded.
   * @return An ImageView representing the room component, or null if the image cannot be loaded.
   */
  public ImageView getRoomComponentsView(String name) {

    String imagePath = "/images/game/inventoryitems/" + name.toLowerCase() + ".png";
    InputStream resourceStream = getClass().getResourceAsStream(imagePath);
    if (resourceStream == null) {
      return null;
    }

    try {
      resourceStream.close(); // Close the stream if it's not null
    } catch (IOException e) {
      e.printStackTrace();
    }

    // If the resource stream was not null, let's try to get the file directly.
    URL imageUrl = getClass().getResource(imagePath);
    if (imageUrl == null) {
      return null;
    }

    ImageView componentView = new ImageView(new Image(imageUrl.toExternalForm()));
    componentView.setFitHeight(100);
    componentView.setPreserveRatio(true);
    return componentView;
  }

}
