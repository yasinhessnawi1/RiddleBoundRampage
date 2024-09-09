package org.example.pathsgame.entities.goals;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.io.Serializable;
import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.utility.CheckValid;

/**
 * The HealthGoal class represents a goal value related to the player's health.
 * This class implements the Goal interface and provides a concrete implementation
 * of the isFulfilled method, which checks if the player's health is greater than or
 * equal to the minimumHealth value specified in the constructor.
 *
 * @author Rami.
 * @version 0.1 (14.02.23).
 */
public class HealthGoal implements Goal, Serializable {
  // The minimum health required for the goal to be fulfilled.
  private int minimumHealth;

  /**
   * Constructor for the HealthGoal class.
   *
   * @param minimumHealth The minimum health required for the goal to be fulfilled.
   */
  public HealthGoal(int minimumHealth) {

    setMinimumHealth(minimumHealth);
  }

  /**
   * set's the new minimumHealth value.
   *
   * @param minimumHealth the new minimumHealth value
   */
  private void setMinimumHealth(int minimumHealth) throws IllegalArgumentException {
    //throwing needs to be deleted when the error is handled
    try {
      CheckValid.checkIntAndThrowException(minimumHealth, "Minimum health");
      this.minimumHealth = minimumHealth;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Not valid minimum health");
    }
  }

  /**
   * Implementation of the isFulfilled method.
   *
   * @param player The player whose health is being checked.
   * @return True if the player's health is greater than or equal to the minimumHealth value,
   *        false otherwise.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getHealth() >= minimumHealth;
  }

  /**
   * Returns a string representation of the HealthGoal object.
   *
   * @return a string representation of the HealthGoal object.
   */
  @Override
  public String toString() {
    return "HealthGoal = " + minimumHealth;
  }
}
