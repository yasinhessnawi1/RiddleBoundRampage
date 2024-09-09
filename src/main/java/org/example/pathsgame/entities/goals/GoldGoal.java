package org.example.pathsgame.entities.goals;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.io.Serializable;
import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.utility.CheckValid;

/**
 * The GoldGoal class represents a goal that the player must achieve a certain amount
 * of gold.
 * This class implements the Goal interface and checks if the player has achieved the
 * expected gold amount.
 *
 * @author Rami.
 * @version 0.1 (14.02.23).
 */
public class GoldGoal implements Goal, Serializable {
  // The minimum amount of gold the player must have to fulfill this goal.
  private int minimumGold;

  /**
   * Constructor for the GoldGoal class.
   *
   * @param minimumGold The minimum amount of gold the player must have to fulfill this goal.
   */
  public GoldGoal(int minimumGold) {

    setGold(minimumGold);
  }

  /**
   * set's the new minimumGold value.
   *
   * @param minimumGold the new minimumGold value
   */
  private void setGold(int minimumGold) throws IllegalArgumentException {
    //throwing needs to be deleted when the error is handled
    try {
      CheckValid.checkIntAndThrowException(minimumGold, "Minimum Gold");
      this.minimumGold = minimumGold;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Not valid minimum Gold");
    }
  }

  /**
   * Implements the isFulfilled method of the Goal interface.
   * Checks if the player's gold is greater than or equal to the minimum gold.
   *
   * @param player The player to check.
   * @return True if the player's gold is greater than or equal to the minimum gold,
   *         false otherwise.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getGold() >= minimumGold;
  }

  /**
   * Returns a string representation of the GoldGoal object.
   *
   * @return a string representation of the GoldGoal object.
   */
  @Override
  public String toString() {
    return "GoldGoal = " + minimumGold;
  }
}
