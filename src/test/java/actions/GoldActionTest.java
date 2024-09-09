package actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.entities.actions.GoldAction;
import org.example.pathsgame.entities.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldActionTest implements Action {
  GoldAction goldAction;
  Player  rami;
  @BeforeEach
  void setUp ( ) {
    rami = new Player.PlayerBuilder ()
        .setName("rami")
        .setGold(10)
        .setHealth(10)
        .setScore(10)
        .build ( );
  }


  @Test
  void testExecutePositive ( ) {
    goldAction = new GoldAction ( 10 );
    try{
      goldAction.execute ( rami );
      assertEquals ( 20, rami.getGold ( ));
    } catch (IllegalArgumentException e){
      fail ();
    }

  }


  @Test
  void testExecuteNegative ( ) {
    try {
      goldAction = new GoldAction ( -30 );
      goldAction.execute ( rami );
     assertEquals ( -1, rami.getGold ( ) );
    } catch ( IllegalArgumentException e){
      assertTrue ( true );
    }

  }

  @Override
  public void execute ( Player player ) {

  }
}