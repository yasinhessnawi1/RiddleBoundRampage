package org.example.pathsgame.entities.goals;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.utility.CheckValid;

/**
 * The InventoryGoal class implements the Goal interface and checks if a player's
 * inventory contains all the items specified in the mandatory items list.
 *
 * @author Rami.
 * @version 0.1 (14.02.23).
 */
public class InventoryGoal implements Goal, Serializable {
  // The list of items that player's inventory must contain to fulfill this goal.
  private List<String> mandatoryItems;

  /**
   * Constructor that initialized the mandatory items list.
   *
   * @param mandatoryItems The list of items that the player's inventory
   *                       must contain to fulfill this goal.
   */
  public InventoryGoal(List<String> mandatoryItems) {
    setMandatoryItems(mandatoryItems);
  }

  /**
   * set's the list of mandatoryItems.
   *
   * @param mandatoryItems the list to be set at mandatoryItems
   * @throws NullPointerException if the list is empty
   */

  private void setMandatoryItems(List<String> mandatoryItems) throws NullPointerException {
    //throwing needs to be deleted when the error is handled
    try {
      CheckValid.checkListAndThrowException(mandatoryItems, "Mandatory items list");
      this.mandatoryItems = mandatoryItems;
    } catch (NullPointerException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Not valid items");
    }
  }

  public Boolean hasMandatoryItems() {
    return !mandatoryItems.isEmpty();
  }

  /**
   * Overridden method from the Goal interface that checks if the player's inventory
   * contains all the items in the mandatory items list.
   *
   * @param player The player whose inventory is being checked.
   * @return True if the player's inventory contains all the items in
   *         the mandatory items list, false otherwise.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getInventory().containsAll(mandatoryItems);
  }

  /**
   * Returns a string representation of the InventoryGoal object.
   *
   * @return a string representation of the InventoryGoal object.
   */
  public String toString() {
    return "InventoryGoal = " + mandatoryItems;
  }
}
