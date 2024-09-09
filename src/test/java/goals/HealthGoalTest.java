package goals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.goals.HealthGoal;
import org.junit.jupiter.api.Test;

class HealthGoalTest {
  @Test
  void indirectTestToMinimumHealthSetterPositive() {
    HealthGoal setNewMinimumHealth = new HealthGoal(10);
    Player rami = new Player.PlayerBuilder().setName("rami").setGold(10).setHealth(10).setScore(10).build();
    assertTrue(setNewMinimumHealth.isFulfilled(rami));
  }

  @Test
  void indirectTestToMinimumHealthSetterNegative() {
    try {
      HealthGoal setNewMinimumHealth = new HealthGoal(-10);
      assertEquals("HealthGoal = 0", setNewMinimumHealth.toString());
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

  }
}