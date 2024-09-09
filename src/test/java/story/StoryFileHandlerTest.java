package story;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import org.example.pathsgame.entities.actions.GoldAction;
import org.example.pathsgame.entities.actions.HealthAction;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.entities.story.StoryFileHandler;
import org.junit.jupiter.api.Test;

class StoryFileHandlerTest {


  @Test
  void saveStoryToFilePositive() throws IOException {

    Passage openingPassage = new Passage("Beginning",
        "You are in a small, dimly lit room. There is door in front of you.");
    Passage passage1 = new Passage("Another room",
        "The door opens to another room. You see a desk with a large, dusty book.");

    Link link = new Link("Try to open the door", "Another room");
    HealthAction healthAction = new HealthAction(10);
    GoldAction goldAction = new GoldAction(20);

    Player player = new Player.PlayerBuilder().setName("Rami").setHealth(100).setGold(0).setScore(0).build();
    healthAction.execute(player);
    goldAction.execute(player);
    link.addAction(healthAction);
    link.addAction(goldAction);


    Link link1 = new Link("Open the book", "The book of spells");
    Link link2 = new Link("Go back", "Beginning");

    link1.addAction(healthAction);

    openingPassage.addLink(link);
    passage1.addLink(link1);
    passage1.addLink(link2);

    Story story = new Story("My Story", openingPassage);
    story.addPassage(passage1);

    StoryFileHandler fileHandler =  StoryFileHandler.getInstance();
    boolean result = fileHandler.saveStoryToFile("test_story", story);
    assertTrue(result);

  }


  @Test
  void saveStoryToFileNegative() {

    Story story = null;
    try {
      story = new Story("My Story", null);
    } catch (NullPointerException e) {
      assertTrue(true);
    } finally {
      try {
        StoryFileHandler fileHandler =  StoryFileHandler.getInstance();
        assertFalse(fileHandler.saveStoryToFile("test", story));
      } catch (NullPointerException e) {
        assertTrue(true);
      } catch (IOException e) {
        assertTrue(true);
      }
    }
  }

  @Test
  void loadStoryFromFilePositive() throws IOException {

    Passage openingPassage = new Passage("Beginning",
        "You are in a small, dimly lit room. There is door in front of you.");
    Passage passage1 = new Passage("Another room",
        "The door opens to another room. You see a desk with a large, dusty book.");

    Link link = new Link("Try to open the door", "Another room");
    HealthAction healthAction = new HealthAction(10);
    GoldAction goldAction = new GoldAction(20);

	  Player player = new Player.PlayerBuilder().setName("Rami").setHealth(100).setGold(0).setScore(0).build();
    healthAction.execute(player);
    goldAction.execute(player);
    link.addAction(healthAction);
    link.addAction(goldAction);


    Link link1 = new Link("Open the book", "The book of spells");
    Link link2 = new Link("Go back", "Beginning");

    link1.addAction(healthAction);

    openingPassage.addLink(link);

    passage1.addLink(link1);

    passage1.addLink(link2);

    String storyTitle = "My Story";
    Story story = new Story(storyTitle, openingPassage);
    story.addPassage(passage1);

    StoryFileHandler fileHandler =  StoryFileHandler.getInstance();
    //boolean result = fileHandler.saveStoryToFile("test_story", story);
    //assertTrue(result);

    // Read the saved story from the file.
    Story readStory = fileHandler.readStoryFromFile("test_story");
    assertNotNull(readStory);
    assertEquals(story.getTitle(), readStory.getTitle());

    // Compare passages and links between the two stories.
    Passage readOpeningPassage = readStory.getOpeningPassage();
    assertNotNull(readOpeningPassage);
    assertEquals(openingPassage.getTitle(), readOpeningPassage.getTitle());
    assertEquals(openingPassage.getContent(), readOpeningPassage.getContent());
    assertEquals(openingPassage.getLinks().size(), readOpeningPassage.getLinks().size());

    assertEquals(passage1, story.getPassage(link2));

    Passage readPassage1 = readStory.getPassage(link1);
    assertNotNull(readPassage1);
    assertEquals(passage1.getTitle(), readPassage1.getTitle());
    assertEquals(passage1.getContent(), readPassage1.getContent());
    assertEquals(passage1.getLinks().size(), readPassage1.getLinks().size());

    Link readLink = readOpeningPassage.getLinks().get(0);
    assertNotNull(readLink);
    assertEquals(link.getText(), readLink.getText());
    assertEquals(link.getReference(), readLink.getReference());
    assertEquals(link.getActions().size(), readLink.getActions().size());

    Link readLink1 = readPassage1.getLinks().get(0);
    assertNotNull(readLink1);
    assertEquals(link1.getText(), readLink1.getText());
    assertEquals(link1.getReference(), readLink1.getReference());
    assertEquals(link1.getActions().size(), readLink1.getActions().size());

    Link readLink2 = readPassage1.getLinks().get(1);
    assertNotNull(readLink2);
    assertEquals(link2.getText(), readLink2.getText());
    assertEquals(link2.getReference(), readLink2.getReference());
    assertEquals(link2.getActions().size(), readLink2.getActions().size());
  }

  @Test
  void loadStoryToFileNegative() {

    StoryFileHandler fileHandler =  StoryFileHandler.getInstance();
    try {

      assertTrue((BooleanSupplier) fileHandler.readStoryFromFile("load-story-negative-test.json"));
	  fail();
    } catch (IOException e) {
      assertTrue(true);
    } catch (NoSuchElementException e) {
      assertTrue(true);
    }
  }
}