package goals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.goals.ScoreGoal;
import org.junit.jupiter.api.Test;

class ScoreGoalTest {
  @Test
  void indirectTestToMinimumScoreSetterPositive(){
    ScoreGoal setNewMinimumScore= new ScoreGoal ( 10 );
    Player rami = new Player.PlayerBuilder().setName("rami").setHealth( 10 ).setGold(10).setScore(10).build();
    assertTrue (setNewMinimumScore.isFulfilled ( rami ));
  }
  @Test
  void indirectTestToMinimumScoreSetterNegative(){
    try {
      ScoreGoal setNewMinimumScore = new ScoreGoal ( -10 );
      assertEquals("ScoreGoal = 0", setNewMinimumScore.toString());
    }catch ( IllegalArgumentException e ){
      assertTrue ( true );
    }

  }
}