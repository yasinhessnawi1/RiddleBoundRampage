package events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassageTest
{
    Passage passage1;
    Passage passage2;
    Passage passage3;
    Link link1;
    Link link2;
    Link link3;
    Link link4;
    Link link5;
    @BeforeEach
   void setUp(){
        passage1 = new Passage("new Passage", "this is a test passage");
        passage2 = new Passage(" second new Passage", "this is a second  test passage");
        passage3 = new Passage(" second new Passage", "this is a second  test passage");
         link1 = new Link("text1", "reference1");
         link2 = new Link("text2", "reference2");
         link3 = new Link("text3", "reference3");
         link4 = new Link("text4", "reference4");
        passage1.addLink(link1);
        passage1.addLink(link2);
        passage1.addLink(link3);
        passage1.addLink(link4);
    }


    @Test
    void addLinkPositive()
    {
        assertEquals(link1.toString() + ", " + link2.toString() + ", "
                + link3.toString() + ", " + link4.toString()
                , passage1.getLinks().toString().subSequence(1, passage1.getLinks().toString().length() -1));
    }
    @Test
    void addLinkNegative()
    {
        try{
          link5 = new Link("", "");
          fail ();
        }catch ( IllegalArgumentException e ){
          assertTrue ( true );
        }
        try{
          passage1.addLink(link5);
          fail();
        }catch (IllegalArgumentException e){
          assertTrue ( true );
        }
    }

    @Test
    void hasLink()
    {
      assertTrue ( passage1.hasLink ( ) );
      assertFalse ( passage2.hasLink ( ) );

    }

    @Test
    void testEquals()
    {

	  assertNotEquals(passage1, passage2);
	  assertEquals(passage2, passage3);
    }

    @Test
    void testHashCode()
    {

	  assertNotEquals(passage1.hashCode(), passage2.hashCode());
      assertEquals(passage2.hashCode(), passage3.hashCode());
    }
}