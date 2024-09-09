package actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.entities.actions.ScoreAction;
import org.example.pathsgame.entities.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreActionTest implements Action {
    ScoreAction scoreAction;
    Player rami;
    @BeforeEach
    void setUp()
    {
        rami = new Player.PlayerBuilder().setName("rami").setHealth(10).setGold(10).setScore(10).build();
    }
    @Test
    void testExecutePositive() {
        scoreAction = new ScoreAction ( 10 );
        scoreAction.execute(rami);
        assertEquals(20, rami.getScore());
    }
    @Test
    void testExecuteNegative() {
       try {
           scoreAction = new ScoreAction ( -20 );
           scoreAction.execute ( rami );
           assertEquals(-1, rami.getScore());
       } catch (IllegalArgumentException e ){
           assertTrue ( true );
       }


    }


    @Override
    public void execute(Player player) {

    }
}