package org.example.pathsgame.entities.characters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.example.pathsgame.utility.CheckValid;
import org.example.pathsgame.utility.ConstantStrings;

/**
 * The Player class represents a player with different attributes that can be influenced
 * in a story. The player has a name, health ( can never be less than 0), score,
 * gold, and a list of items (inventory).
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.02.23).
 */
public class Player implements Serializable {
  // The name of the player.
  private final String name;
  // The health of the player.
  private int health;
  // The score of the player.
  private int score;
  // The amount of the gold the player has.
  private int gold;
  // The list of items the player has.
  private List<String> inventory;
  private String playerSpritePath;
  private static final Logger logger = Logger.getLogger(ConstantStrings.CHARACTERS_LOGGER);

  /**
   * Constructor for creating a new Player object.
   *
   * @param builder The PlayerBuilder instance used to build this Player.
   */
  private Player(PlayerBuilder builder) {
    this.name = builder.name;
    this.health = builder.health;
    this.score = builder.score;
    this.gold = builder.gold;
    this.inventory = builder.inventory;
    this.playerSpritePath = builder.playerSpritePath;
    logger.info("Object of the class " + this.getClass().getName() + " was created");
  }


  /**
   * Gets the path of the player sprite.
   *
   * @return the path of player sprite as a string.
   */
  public String getPlayerSpritePath() {
    return playerSpritePath;
  }

  /**
   * Gets the name of the player.
   *
   * @return The name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Add health to the player.
   *
   * @param health The amount of health to add to the player.
   */
  public void addHealth(int health) {
    //makes new int to be checked if negative
    int newHealth = this.health + health;
    if (CheckValid.checkInt(newHealth)) {
      //if the new health is negative then it will be set to -1.
      newHealth = -1;
    }
    this.health = newHealth;
  }

  /**
   * Gets the health of the player.
   *
   * @return the health of the player
   */
  public int getHealth() {
    return health;
  }

  /**
   * Add points to the player's score.
   *
   * @param points The amount of points to add to the player's score.
   */
  public void addScore(int points) {
    //makes new int to be checked if negative
    int newScore = this.score + points;
    if (CheckValid.checkInt(newScore)) {
      //if the new score is negative then it will be set to -1.
      newScore = -1;
    }
    this.score = newScore;
  }

  /**
   * Gets the score of the player.
   *
   * @return The score of the player.
   */
  public int getScore() {
    return score;
  }

  /**
   * Add gold to the player.
   *
   * @param gold The amount of gold to add to the player.
   */
  public void addGold(int gold) throws IllegalArgumentException {
    //makes new int to be checked if negative
    int newGold = this.gold + gold;
    if (CheckValid.checkInt(newGold)) {
      //if the new gold is negative then it will be set to -1.
      newGold = -1;
    }
    this.gold = newGold;
  }

  /**
   * Gets the amount of the gold the player has.
   *
   * @return The amount of gold the player has.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Add an item to the player's inventory.
   *
   * @param item The item to add to the player's inventory.
   */
  public void addToInventory(String item) throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(item, "Item");
      inventory.add(item);
    } catch (IllegalArgumentException e) {
      logger.warning(e.getMessage());
    }

  }

  /**
   * Gets the player's inventory.
   *
   * @return The player's inventory.
   */
  public List<String> getInventory() {
    return inventory;
  }

  /**
   * Removes an item from the player's inventory.
   *
   * @param item The item to remove from the inventory.
   */
  public void removeFromInventory(String item) {
    inventory.removeIf(s -> s.toLowerCase().contains(item.toLowerCase()));
  }

  /**
   * Sets the player's health.
   *
   * @param health The player's health to set.
   * @throws IllegalArgumentException if the player's health is invalid.
   */
  public void setHealth(int health) {
    try {
      CheckValid.checkIntAndThrowException(health, "Health");
      this.health = health;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Sets the player's score.
   *
   * @param score The player's score to set.
   * @throws IllegalArgumentException if the player's score is invalid.
   */
  public void setScore(int score) {
    try {
      CheckValid.checkIntAndThrowException(score, "Score");
      this.score = score;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Sets the player's gold.
   *
   * @param gold The player's gold to set.
   * @throws IllegalArgumentException if the player's score is invalid.
   */
  public void setGold(int gold) {
    try {
      CheckValid.checkIntAndThrowException(gold, "Gold");
      this.gold = gold;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Set a list of items to the player's inventory.
   *
   * @param inventory The items to add to the player's inventory.
   */
  public void setInventory(List<String> inventory) {
    try {
      CheckValid.checkListAndThrowException(inventory, "Inventory");
      this.inventory = inventory;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * sets the path of the player sprite.
   *
   * @param playerSpritePath the player sprite path
   * @throws IllegalArgumentException if the player sprite path not valid.
   */
  public void setPlayerSpritePath(String playerSpritePath) {
    try {
      CheckValid.checkStringAndThrowException(playerSpritePath, "Sprite Path");
      this.playerSpritePath = playerSpritePath;
    } catch (IllegalArgumentException e) {
      logger.warning("Not valid path.");
      throw new IllegalArgumentException("Path is not valid");
    }
  }

  /**
   * This inner class is responsible for building a new instance of Player's class.
   */
  public static class PlayerBuilder {
    private String name;
    private int health;
    private int score;
    private int gold;
    private List<String> inventory;
    private String playerSpritePath;

    /**
     * Constructor for creating a new PlayerBuilder object with default parameters.
     */
    public PlayerBuilder() {
      this.name = "";
      this.health = 0;
      this.score = 0;
      this.gold = 0;
      this.inventory = new ArrayList<>();
      this.playerSpritePath = "";
    }

    /**
     * sets the value of name.
     *
     * @param name the new name value
     * @throws IllegalArgumentException if name is not valid.
     */
    public PlayerBuilder setName(String name) {
      if (CheckValid.checkString(name)) {
        throw new IllegalArgumentException("Name cannot be null or empty");
      }
      this.name = name;
      return this;
    }

    /**
     * sets the value of health.
     *
     * @param health the new health value
     * @throws IllegalArgumentException if health is not valid.
     */
    public PlayerBuilder setHealth(int health) {
      if (CheckValid.checkInt(health)) {
        throw new IllegalArgumentException("Health cannot be less than 0");
      }
      this.health = health;
      return this;
    }

    /**
     * sets the value of score.
     *
     * @param score the new score value
     * @throws IllegalArgumentException if score value is not valid.
     */
    public PlayerBuilder setScore(int score) {
      if (CheckValid.checkInt(score)) {
        throw new IllegalArgumentException("Score cannot be less than 0");
      }
      this.score = score;
      return this;
    }

    /**
     * sets the value of gold.
     *
     * @param gold the new score value
     * @throws IllegalArgumentException if the gold value is invalid.
     */
    public PlayerBuilder setGold(int gold) {

      try {
        CheckValid.checkIntAndThrowException(gold, "Gold");
        this.gold = gold;
        return this;
      } catch (IllegalArgumentException e) {
        logger.warning(e.getMessage());
        throw new IllegalArgumentException("Gold cannot be less than 0");
      }
    }

    /**
     * sets the path of the player sprite.
     *
     * @param path the player sprite path
     */
    public PlayerBuilder setSpritePath(String path) {

      try {
        CheckValid.checkStringAndThrowException(path, "Sprite Path");
        this.playerSpritePath = path;
      } catch (IllegalArgumentException e) {
        logger.warning(e.getMessage());
        throw new IllegalArgumentException("Path is not valid");
      }
      return this;
    }

    /**
     * Add a list of items to the player's inventory.
     *
     * @param inventory The items to add to the player's inventory.
     */
    public PlayerBuilder setInventory(List<String> inventory) {
      this.inventory = inventory;
      return this;
    }

    /**
     * Build a new instance of Player using the current state of the builder.
     * The builder pattern is used here to allow the creation of a Player object with various
     * optional parameters.
     *
     * @return A new Player instance, built according to the parameters set on this builder.
     */
    public Player build() {
      return new Player(this);
    }
  }
}
