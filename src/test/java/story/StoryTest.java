package story;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.entities.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoryTest{
private Passage passage;
private Story story;
private Passage passage1;
private Story story1;
private Link link;
private Link link1;
  @BeforeEach
  void setUp ( ) {
    passage = new Passage ( "new passage", "test passage" );
    story = new Story ( "new story", passage );
    try {
      passage1 = new Passage ( "", "" );
    }catch ( IllegalArgumentException e ){
      assertTrue ( true );
    }
    try{
      story1 = new Story ( "", passage1 );
    } catch ( IllegalStateException e ){
      assertTrue ( true );
    }
    link = new Link ( passage.getTitle (),passage.getTitle () );
    try {
      link1 = new Link ( passage1.getTitle ( ), passage1.getTitle ( ) );
    }catch ( NullPointerException e ){
      assertTrue ( true );
    }

  }


  @Test
  void addPassagePositive ( ) {
    story.addPassage ( passage );
    assertEquals ( passage.toString (), story.getPassages ().toString().subSequence ( 1, story.getPassages ().toString ().length () -1 ) );

  }
  @Test
  void addPassageNegative ( ) {
   try {
     story.addPassage ( passage1 );
     assertEquals ( passage1.toString ( ), story1.getPassages ( ).toString ( )
         .subSequence ( 1, story1.getPassages ( ).toString ( ).length ( ) - 1 ) );
     fail ();
   }catch ( NullPointerException e ){
     assertTrue ( true );
   }
  }

  @Test
  void getPassagePositive ( ) {
    story.getPassage (link);
    assertEquals ( passage,story.getPassage (link) );

  }
  @Test
  void getPassageNegative ( ) {
    try {
      story.addPassage ( passage1 );
      assertEquals ( passage1.toString ( ), story1.getPassages ( ).toString ( )
          .subSequence ( 1, story1.getPassages ( ).toString ( ).length ( ) - 1 ) );
      fail ();
    }catch ( NullPointerException e ){
      assertTrue ( true );
    }
    try {
      story1.getPassage ( link1 );
    } catch ( NullPointerException e )
    {
      assertTrue ( true );

    }

  }
  @Test
  void removePassagePositive(){
    Passage opningPassage = new Passage ( "Opening passage", "This is the opening passage" );
    Passage middlePassage = new Passage ( "Middle passages", "this the middle passage" );
    Passage endPassage = new Passage ( "the ending of the story passage", "this is the ending of the story passage" );
    Story testStory = new Story ( "The story", opningPassage );
    testStory.addPassage(opningPassage);
    testStory.addPassage(middlePassage);
    testStory.addPassage(endPassage);
    Link link = new Link ( middlePassage.getTitle (), middlePassage.getTitle () );
    testStory.removePassage(link);
    assertFalse(testStory.getPassages().contains(middlePassage));
    assertEquals(testStory.getPassages().size(),2);
  }
  @Test
  void removePassageNegative(){
    Passage opningPassage = new Passage ( "Opening passage", "This is the opening passage" );
    Passage middlePassage = new Passage ( "Middle passages", "this the middle passage" );
    Passage endPassage =
        new Passage("the ending of the story passage", "this is the ending of the story passage");
    Story testStory = new Story("The story", opningPassage);
    testStory.addPassage(opningPassage);
    testStory.addPassage(middlePassage);
    testStory.addPassage(endPassage);
    middlePassage.addLink(new Link(endPassage.getTitle(), endPassage.getTitle()));
    middlePassage.addLink(new Link(opningPassage.getTitle(), opningPassage.getTitle()));
    opningPassage.addLink(new Link(middlePassage.getTitle(), middlePassage.getTitle()));
    endPassage.addLink(new Link(middlePassage.getTitle(), middlePassage.getTitle()));
    Link link = new Link(middlePassage.getTitle(), middlePassage.getTitle());
    try {
      testStory.removePassage(link);
      assertNotNull(testStory.getPassage(link));
      assertTrue(testStory.getPassages().contains(middlePassage));
      assertEquals(testStory.getPassages().size(), 3);
      fail();
    } catch (IllegalStateException e) {
      assertTrue(true);
    }
  }
  @Test
  void getBrokenLinksPositive() {

    Passage opningPassage = new Passage("Opening passage", "This is the opening passage");
    Passage middlePassage = new Passage("Middle passages", "this the middle passage");
    Link link = new Link("new Passage", "new Passage");
    Link link2 = new Link("Middle passages", "Middle passages");
    Link link1 = new Link("the ending of the story passage", "the ending of the story passage");
    Passage endPassage =
        new Passage("the ending of the story passage", "this is the ending of the story passage");
    Story testStory = new Story("The story", opningPassage);
    testStory.addPassage(middlePassage);
    testStory.addPassage(endPassage);
    opningPassage.addLink(link1);
    middlePassage.addLink(link1);
    endPassage.addLink(link);
    testStory.removePassage(link2);
    List<Link> brokenLinks = testStory.getBrokenLinks();
    assertEquals(1, brokenLinks.size());
  }
  @Test
  void getBrokenLinksNegative(){
    Passage opningPassage = new Passage ( "Opening passage", "This is the opening passage" );
    Passage middlePassage = new Passage ( "Middle passages", "this the middle passage" );
    Passage endPassage = new Passage ( "the ending of the story passage", "this is the ending of the story passage" );
    Story testStory = new Story ( "The story", opningPassage );
    testStory.addPassage(opningPassage);
    testStory.addPassage(middlePassage);
    testStory.addPassage(endPassage);
    List<Link> brokenLinks = testStory.getBrokenLinks();
    assertTrue(brokenLinks.isEmpty());
  }

}