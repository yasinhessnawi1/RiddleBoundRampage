package org.example.pathsgame.utility;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;

/**
 * The SceneHandler class provides a Singleton utility to manage a collection of JavaFX Scene
 * objects. It allows you to register a Scene with a specific name and then retrieve it later.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class SceneHandler {
  private static final HashMap<String, Scene> scenes = new HashMap<>();

  private static SceneHandler instance;

  /**
   * Private constructor for the Singleton pattern.
   */
  private SceneHandler() {

  }

  /**
   * Retrieves the singleton instance of the SceneHandler class.
   *
   * @return An instance of the SceneHandler class.
   */
  public static synchronized SceneHandler getInstance() {

    if (instance == null) {
      instance = new SceneHandler();
    }
    return instance;
  }

  /**
   * Returns the map of all registered scenes.
   *
   * @return A Map where the keys are the names of the scenes and the values are the Scene objects.
   */
  public Map<String, Scene> getScenes() {
    return scenes;
  }

  /**
   * Registers a Scene with a specific name.
   *
   * @param scene The Scene object to be registered.
   * @param sceneName The name with which the scene will be registered..
   */
  public void setScene(Scene scene, String sceneName) {
    scenes.put(sceneName, scene);
  }

}
