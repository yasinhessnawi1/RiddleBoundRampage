package org.example.pathsgame.entities.actions;

import org.example.pathsgame.entities.characters.Player;

/**
 * The Action interface represents a future change in the state of a player,
 * including changes in the player's score, health, gold, or inventory.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.05.23).
 */
public interface Action {
  /**
   * Executes the action and updates the state of the player.
   *
   * @param player The player whose state will be updated.
   */
  void execute(Player player);

  /**
   * Returns a string representation of the action.
   *
   * @return a string representation of the action.
   */
  String toString();
}
