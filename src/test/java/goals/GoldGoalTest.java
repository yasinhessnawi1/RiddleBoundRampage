package goals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.goals.GoldGoal;
import org.junit.jupiter.api.Test;

class GoldGoalTest {
  @Test
  void indirectTestToMinimumGoldSetterPositive(){
    GoldGoal setNewMinimumGold= new GoldGoal ( 10 );
    Player rami = new Player.PlayerBuilder().setName("rami").setHealth( 100 ).setGold( 10 ).setScore(10).build ();
    assertTrue (setNewMinimumGold.isFulfilled ( rami ));
  }
  @Test
  void indirectTestToMinimumGoldSetterNegative(){
    try {
      GoldGoal setNewMinimumGold = new GoldGoal ( -10 );
      assertEquals("GoldGoal = 0", setNewMinimumGold.toString());
    }catch ( IllegalArgumentException e ){
      assertTrue ( true );
    }

  }

}