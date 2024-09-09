package characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.example.pathsgame.entities.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    Player yasin;
    Player rami;
    @BeforeEach
    void setUp() {
        rami = new Player.PlayerBuilder().setName("rami").setHealth(10).setGold(10).setHealth(10).build();
        yasin = new Player.PlayerBuilder().setName("yasin").setHealth(10).setGold(10).setHealth(10).build();
    }


    @Test
    void addHealthPositive() {

       try {
           yasin.addHealth ( 90 );
           assertEquals ( 100, yasin.getHealth ( ) );
       } catch ( IllegalArgumentException e )
       {
           fail ();
       }


    }
    @Test
    void addHealthNegative() {
        rami.addHealth(-50);
        assertEquals(-1, rami.getHealth());
    }

    @Test
    void addScorePositive() {
        try {
            yasin.addScore(90);
            assertEquals(90, yasin.getScore());

        } catch(IllegalArgumentException e){
            fail ();
        }
    }
    @Test
    void addScoreNegative() {
        try {
            rami.addScore(-50);
            assertEquals(-1, rami.getScore());
        } catch(IllegalArgumentException e){
            assertTrue ( true );
        }
    }

    @Test
    void addGoldPositive() {
       try{
           yasin.addGold(90);
           assertEquals(100, yasin.getGold());
       } catch ( IllegalArgumentException e )
       {
           fail ();
       }


    }@Test
    void addGoldNegative() {
       try {

           rami.addGold ( -50 );
           assertEquals ( -1, rami.getGold ( ) );
       } catch (IllegalArgumentException e )
       {
           assertTrue ( true);
       }
    }

    @Test
    void addToInventoryPositive() {
        try{
            yasin.addToInventory("new item");
            assertEquals("[new item]", yasin.getInventory().toString());
        } catch ( IllegalArgumentException e){
            fail ();
        }


    } @Test
    void addToInventoryNegative() {
      try{
          rami.addToInventory(" ");
        assertEquals(0, rami.getInventory().size());
      } catch(IllegalArgumentException e){
          assertTrue ( true );
      }
        assertEquals("[]", rami.getInventory().toString());
    }
}