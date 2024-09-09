package org.example.pathsgame.entities.goals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import org.example.pathsgame.factories.AlertFactory;

/**
 * The GoalsSavingHandler class is a Singleton utility class responsible for the serialization
 * and deserialization of Goals objects.
 * It provides the functionality to save and load a List of Goal objects from a file.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GoalsSavingHandler {
  private static GoalsSavingHandler goalsSavingHandlerInstance;

  /**
   * Private constructor to prevent creating instances of this class directly.
   * Singleton pattern is used here.
   */
  private GoalsSavingHandler() {
  }

  /**
   * Retrieves the singleton instance of the GoalsSavingHandler class.
   *
   * @return An instance of the GoalsSavingHandler.
   */
  public static synchronized GoalsSavingHandler getInstance() {
    if (goalsSavingHandlerInstance == null) {
      goalsSavingHandlerInstance = new GoalsSavingHandler();
    }
    return goalsSavingHandlerInstance;
  }

  /**
   * Serialized a List of Goal objects and saves it to a file.
   *
   * @param goalList The list of Goal objects to be serialized and saved.
   * @param filename The name of the file where the Goal objects will be saved.
   * @throws IOException If there is an error during the file write process.
   */
  public void serializeAndSaveGoalList(List<Goal> goalList, String filename) throws IOException {
    try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
      objectOutputStream.writeObject(goalList);
    } catch (IOException e) {
      AlertFactory.getInstance().getErrorAlert("Error", "Problem with saving",
          "There is a problem with saving the goal list").showAndWait();
    }
  }

  /**
   * Deserializes a List of Goal objects from the file.
   *
   * @param filename The name of the file from where the Goal objects will be loaded.
   * @return The list of Goal objects deserialized from the file.
   * @throws ClassNotFoundException If the class of a serialized object cannot be found.
   */
  public List<Goal> deserializeGoalList(String filename) throws ClassNotFoundException {
    List<Goal> goalList = null;
    try (FileInputStream fileInputStream = new FileInputStream(filename);
         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
      goalList = (List<Goal>) objectInputStream.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw e;
    }
    return goalList;
  }
}
