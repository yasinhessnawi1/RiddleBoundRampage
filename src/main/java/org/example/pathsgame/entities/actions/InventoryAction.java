package org.example.pathsgame.entities.actions;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.utility.CheckValid;

/**
 * InventoryAction class implements the Action interface.
 * It represents an action that adds an item to a player's inventory.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.02.23).
 */
public class InventoryAction implements Action {
  // The item to be added to the inventory.
  private String item;

  /**
   * A constructor that takes the item to be added to the inventory.
   *
   * @param item The item to be added to the inventory.
   */
  public InventoryAction(String item) {
    setItem(item);
    Logger.getLogger(CHARACTERS_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");
  }

  /**
   * sets the new value of item.
   *
   * @param item the new item value
   * @throws IllegalArgumentException if the item to add is invalid.
   */
  private void setItem(String item) throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(item, "item");
      this.item = item;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
    }

  }

  /**
   * This method execute is overridden from the Action interface.
   * It adds the item to the player's inventory.
   *
   * @param player The player to have the item added to their inventory.
   * @throws IllegalArgumentException if the item to add is invalid.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    try {
      player.addToInventory(item);
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Error when trying to add item to the inventory");
    }
  }

  /**
   * Returns a string representation of the InventoryAction object.
   *
   * @return a string representation of the InventoryAction object.
   */
  @Override
  public String toString() {
    return "Inventory = " + item;
  }

}
