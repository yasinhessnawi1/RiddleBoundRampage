package org.example.pathsgame.entities.actions;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;

/**
 * The GoldAction class implements the Action interface and represents a future change
 * in the player's gold amount.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.02.23).
 */
public class GoldAction implements Action {
  // The amount of gold to be added to the player's current gold.
  private int gold;


  /**
   * Constructor for the GoldAction class.
   *
   * @param gold The amount of gold to be added to the player's current gold.
   */
  public GoldAction(int gold) {
    setGold(gold);
    Logger.getLogger(CHARACTERS_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");
  }

  /**
   * set's the new gold value.
   *
   * @param gold the new gold value
   * @throws IllegalArgumentException if the amount of gold is invalid.
   */
  private void setGold(int gold) throws IllegalArgumentException {
    try {
      this.gold = gold;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
    }

  }

  /**
   * Overridden method from the Action interface that executes the GoldAction.
   * It adds the 'gold' amount to the player's current gold.
   *
   * @param player The player whose gold value will be modified.
   * @throws IllegalArgumentException if the amount of gold to add is invalid.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    try {
      player.addGold(gold);
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
    }
  }

  /**
   * Returns a string representation of the GoldAction object.
   *
   * @return a string representation of the GoldAction object.
   */
  @Override
  public String toString() {
    return "Gold = " + gold;
  }

  /**
   * Returns the amount of gold.
   *
   * @return the amount of gold
   */
  public int getGold() {
    return gold;
  }
}

