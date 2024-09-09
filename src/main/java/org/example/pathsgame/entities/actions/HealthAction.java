package org.example.pathsgame.entities.actions;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;

/**
 * The HealthAction class implements the Action interface and represents a
 * chang in a player's health.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.02.23).
 */
public class HealthAction implements Action {

  // The amount of health to be added to the player's current health value.
  private final int health;

  /**
   * Constructor for HealthAction that sets the health value to the specified value.
   *
   * @param health The amount of health to be added to the player's current health value.
   */
  public HealthAction(int health) {
    this.health = health;
    Logger.getLogger(CHARACTERS_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");
  }

  /**
   * Overridden method from the Action interface that executes the HealthAction
   * by adding the health value to the player's current health value.
   *
   * @param player The player whose health value will be modified.
   */
  @Override
  public void execute(Player player) {
    player.addHealth(health);
  }

  /**
   * Returns a string representation of the HealthAction object.
   *
   * @return a string representation of the HealthAction object.
   */
  @Override
  public String toString() {
    return "Health = " + health;
  }

  /**
   * Returns the amount of health.
   *
   * @return the amount of health.
   */
  public int getHealth() {
    return health;
  }
}
