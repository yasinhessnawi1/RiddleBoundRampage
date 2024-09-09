package goals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.goals.InventoryGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryGoalTest {
  private InventoryGoal inventoryGoal;
  private List< String > list;
  private List< String > list1;
  private Player rami;

  @BeforeEach
  void setUp ( ) {
    list = new ArrayList<> ( );
    list1 = new ArrayList<> ();
  }

  @Test
  void testInventoryGoalSetterPositive ( ) {
    list.add ( "hello world" );
    inventoryGoal = new InventoryGoal ( list );
    assertEquals ( true, inventoryGoal.hasMandatoryItems ( ) );

  }
  @Test
  void testInventoryGoalSetterNegative ( ) {
    try {
      inventoryGoal = new InventoryGoal ( list1 );
      assertEquals ( false, inventoryGoal.hasMandatoryItems ( ) );
      fail ();
    } catch ( IllegalArgumentException e ){
      assertTrue ( true );
    }

  }

}