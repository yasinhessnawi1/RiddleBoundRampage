package org.example.pathsgame.utility;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

/**
 * The NavigationBetweenSceneData class provides a Singleton utility to manage navigation history
 * and data storage between different scenes in a JavaFX application.
 * It uses a Deque to store the navigation history and HashMap for data storage.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class NavigationBetweenSceneData {
  private final Deque<String> navigationHistory;
  private final HashMap<String, Object> dataStore;
  private static NavigationBetweenSceneData instance;

  /**
   * Private constructor for the Singleton pattern.
   * Initializes the navigationHistory Deque and dataStore HashMap.
   */
  private NavigationBetweenSceneData() {
    navigationHistory = new ArrayDeque<>();
    dataStore = new HashMap<>();
  }

  /**
   * Retrieves the singleton instance of the NavigationBetweenSceneData class.
   *
   * @return An instance of NavigationBetweenSceneData class.
   */
  public static synchronized NavigationBetweenSceneData getInstance() {
    if (instance == null) {
      instance = new NavigationBetweenSceneData();
    }
    return instance;
  }

  /**
   * Adds the provided scene to the front of the navigation history.
   *
   * @param scene The scene name to add to the navigation history.
   */
  public void push(String scene) {
    navigationHistory.addFirst(scene);
  }

  /**
   * Removes and returns the first scene from the navigation history.
   *
   * @return The scene name that is removed from the navigation history.
   */
  public String pop() {
    return navigationHistory.removeFirst();
  }

  /**
   * Stores an Object data with the specified key in the dataStore.
   *
   * @param key The key to store the data under.
   * @param data The data to store.
   */
  public void storeData(String key, Object data) {
    dataStore.put(key, data);
  }

  /**
   * Retrieved an Object data from the dataStore using the specified key.
   *
   * @param key The key to retrieve the data from.
   * @return The Object data stored under the key, or null if the key does not exist.
   */
  public Object getData(String key) {
    return dataStore.get(key);
  }

  /**
   * Retrieves the Deque containing the navigation history.
   *
   * @return The Deque of navigation history.
   */
  public Deque<String> getNavigationHistory() {
    return navigationHistory;
  }
}

