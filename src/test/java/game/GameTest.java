package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.entities.game.Game;
import org.example.pathsgame.entities.goals.Goal;
import org.example.pathsgame.entities.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Story story;
    private Game game;
    private Passage passage;
    private Player yasin;
    List< Goal > goals;
    private Passage passage2;
    private Link link;


    private Story story1;
    private Game game1;
    private Passage passage1;
    private Link link1;


    @BeforeEach
    void setUp ( ) {
        yasin = new Player.PlayerBuilder().setName("yasin").setHealth(10).setGold(10).setHealth(10).build();
        goals = new ArrayList<> ( );
        passage = new Passage ( "new Passage", "test passage" );
        passage2 = new Passage ( "second new passage", "second test passage" );
        story = new Story ( "new story", passage );
        story.addPassage ( passage );
        story.addPassage ( passage2 );
        game = new Game ( yasin, story, goals );


        try {
            passage1 = new Passage ( "", "" );
            fail ( );
        } catch ( IllegalArgumentException e ) {
            assertTrue ( true );
        }

        try{
            story1 = new Story ( "new story", passage1 );
        } catch ( NullPointerException e ){
            assertTrue ( true );
        }

        try {
            story1.addPassage ( passage );
            fail ( );
        } catch ( NullPointerException e ) {
            assertTrue ( true );
        }
        try {
            game1 = new Game ( yasin, story1, goals );
        }catch ( NullPointerException e )
        {
            assertTrue ( true );
        }

        link = new Link ( passage.getTitle (), passage.getTitle ());




    }

    @Test
    void beginPositive ( ) {
        Passage openingPassage = game.begin ( );
        assertEquals ( "new Passage", openingPassage.getTitle () );
        assertSame ( passage, game.begin () );
    }

    @Test
    void beginNegative ( ) {
       try {
           assertNotSame ( passage1, game1.begin ( ) );
           fail ();
       }catch ( NullPointerException e ){
           assertTrue ( true );
       }
    }

    @Test
    void goPositive() {
        Passage result = game.go ( link );
        assertNotNull ( result );

    }
    @Test
    void goNegative() {
       try {
           Passage result = game1.go ( link1 );
           assertNull ( result );
       }catch ( NullPointerException e ){
           assertTrue ( true );
       }

    }

}