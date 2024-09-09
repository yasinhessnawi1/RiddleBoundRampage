package actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.entities.actions.InventoryAction;
import org.example.pathsgame.entities.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryActionTest implements Action {
  InventoryAction inventoryAction;
  Player yasin;

  @BeforeEach
  void setUp() {

    yasin = new Player.PlayerBuilder().setName("yasin").setGold(10).setHealth(10).setScore(10).build();
  }

  @Test
  void testExecutePositive() {
    inventoryAction = new InventoryAction("Bandage to increase health");
    inventoryAction.execute(yasin);
    assertEquals("[Bandage to increase health]", yasin.getInventory().toString());
  }

  @Test
  void testExecuteNegative() {
      inventoryAction = new InventoryAction(" ");
      inventoryAction.execute(yasin);
      assertEquals(0, yasin.getInventory().size());
  }

  @Override
  public void execute(Player player) {

  }
}