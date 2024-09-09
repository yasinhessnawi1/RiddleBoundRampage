package org.example.pathsgame.entities.characters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

/**
 * The PlayerFileHandler class is a singleton class that handles saving and loading Player objects
 * to and from files. It uses serialization to achieve this.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class PlayerFileHandler {
  private static PlayerFileHandler playerFileHandlerInstance;

  /**
   * Private constructor to prevent instantiation of this class.
   */
  private PlayerFileHandler() {
  }

  /**
   * Method to retrieve ths singleton instance of PlayerFileHandler.
   * If the instance is null, a new instance is created.
   *
   * @return The singleton instance of PlayerFileHandler.
   */
  public static synchronized PlayerFileHandler getInstance() {
    if (playerFileHandlerInstance == null) {
      playerFileHandlerInstance = new PlayerFileHandler();
    }
    return playerFileHandlerInstance;
  }

  /**
   * Saves a player object to a file using serialization.
   * The filename is provided as a parameter.
   *
   * @param player The player object to be saved.
   * @param filename The name of the file where the player object will be saved.
   */
  public void savePlayer(Player player, String filename) {
    try (FileOutputStream fileOut = new FileOutputStream(filename);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(player);
    } catch (IOException i) {
      throw new IllegalArgumentException("File not found!");
    }
  }

  /**
   * Loads a Player object from a file using serialization.
   * The filename is provided as a parameter.
   *
   * @param filename The name of the file from where the Player object is loaded.
   * @return The loaded Player object, or null if the file is not found.
   */
  public Player loadPlayer(String filename) throws IOException, ClassNotFoundException {

    Player player;
    try (FileInputStream fileIn = new FileInputStream(filename);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
      player = (Player) in.readObject();
    } catch (IOException i) {
      throw new IOException("File not found!");
    } catch (ClassNotFoundException c) {
      throw new ClassNotFoundException("Player class not found!");
    }
    return player;
  }

}

