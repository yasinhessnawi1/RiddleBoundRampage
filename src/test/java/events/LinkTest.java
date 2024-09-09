package events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.pathsgame.entities.actions.GoldAction;
import org.example.pathsgame.entities.actions.HealthAction;
import org.example.pathsgame.entities.actions.InventoryAction;
import org.example.pathsgame.entities.actions.ScoreAction;
import org.example.pathsgame.entities.events.Link;
import org.junit.jupiter.api.Test;

class LinkTest
{
    GoldAction goldAction;
    ScoreAction scoreAction;
    HealthAction healthAction;
    InventoryAction inventoryAction;
    Link link1 = new Link("text1", "reference1");
    Link link2 = new Link("text2", "reference2");
    Link link3 = new Link("text3", "reference3");
    Link link4 = new Link("text4", "reference4");
    Link link5 = new Link("text1", "reference1");

    @Test
    void getText()
    {
        assertEquals("text1", link1.getText());
    }

    @Test
    void getReference()
    {
        assertEquals("reference1", link1.getReference());
    }

    @Test
    void addActionPositive()
    {
        goldAction = new GoldAction(10);
        link1.addAction(goldAction);
        assertEquals(goldAction.toString(), link1.getActions().toString().subSequence(1,
                link1.getActions().toString().length() -1));


        scoreAction = new ScoreAction(10);
        link1.addAction(scoreAction);
        assertEquals(goldAction.toString() + ", " +  scoreAction.toString() ,
                link1.getActions().toString().subSequence(1,link1.getActions().toString().length() -1) );

        healthAction = new HealthAction(10);
        link1.addAction(healthAction);
        assertEquals(goldAction.toString() + ", " +  scoreAction.toString() + ", " +  healthAction.toString(),
                link1.getActions().toString().subSequence(1,link1.getActions().toString().length() -1));
    }
    @Test
    void addActionNegative()
    {
        try {
            goldAction = new GoldAction ( -10 );
            assertFalse(goldAction.toString().isEmpty());
        } catch ( IllegalArgumentException e ){
            assertTrue ( true );
        }
        try {
            link1.addAction ( goldAction );
            assertFalse(link1.getActions().isEmpty());
        }catch ( NullPointerException e ){
            assertTrue ( true );
        }
    }

    @Test
    void testEquals()
    {

	  assertEquals(link1, link5);
	  assertNotEquals(link1, link2);
    }

    @Test
    void testHashCode()
    {

	  assertEquals(link1.hashCode(), link5.hashCode());
        assertNotEquals(link1.hashCode(), link3.hashCode());
    }
}