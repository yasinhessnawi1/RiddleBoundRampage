package actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.entities.actions.HealthAction;
import org.example.pathsgame.entities.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthActionTest implements Action {
    HealthAction healthAction;
    Player  yasin;
    @BeforeEach
    void setUp() {
        yasin = new Player.PlayerBuilder().setName("yasin").setHealth(10).setGold(10).setScore(10).build();

    }

    @Test
    void testExecutePositive() {
        try {
            healthAction = new HealthAction(10);
            healthAction.execute ( yasin );
            assertEquals ( 20, yasin.getHealth ( ) );
        }catch ( IllegalArgumentException e ){
            fail ();
        }
    }
    @Test
    void testExecuteNegative() {

            healthAction = new HealthAction(-20);
            healthAction.execute ( yasin );
            assertEquals ( -1, yasin.getHealth ( ));

    }

    @Override
    public void execute(Player player) {

    }
}