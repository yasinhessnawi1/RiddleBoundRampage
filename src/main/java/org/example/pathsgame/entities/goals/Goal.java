package org.example.pathsgame.entities.goals;

import org.example.pathsgame.entities.characters.Player;

/**
 * This Goal interface represents a goal value related to the player's state.
 * While actions change the player's state along the way, goals allow us to check
 * if the player has achieved the expected result.
 *
 * @author Rami.
 * @version 0.1 (14.02.23).
 */
public interface Goal {
  /**
   * This method checks if the player has achieved the expected result.
   *
   * @param player The current player.
   * @return A boolean value indicating if the goal is fulfilled or not.
   */
  boolean isFulfilled(Player player);

  /**
   * Returns a string representation of the Goal.
   *
   * @return a string representation of the Goal.
   */
  String toString();
}
