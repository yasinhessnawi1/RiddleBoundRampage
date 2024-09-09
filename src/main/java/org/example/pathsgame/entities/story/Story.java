package org.example.pathsgame.entities.story;

import static org.example.pathsgame.utility.ConstantStrings.STORY_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.utility.CheckValid;

/**
 * Class representing a non-linear narrative story, consisting of a collection of passages.
 *
 * @author Rami.
 * @version 0.1 (13.02.23).
 */
public class Story {
  // Title of the Story.
  private String title;
  // Map containing the passage in the story, where the key is a Link object.
  private final Map<Link, Passage> passages;
  // The opening passage of the story.
  private final Map<String, Link> links;
  private final Passage openingPassage;
  private static final Logger logger = Logger.getLogger(STORY_LOGGER);

  /**
   * Constructs a story object with the given title and passage.
   *
   * @param title          the title of the story.
   * @param openingPassage the first passage in the story.
   */
  public Story(String title, Passage openingPassage) {
    setTitle(title);
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
    this.links = new HashMap<>();
    addPassage(openingPassage);
    logger.info("Object of the class " + this.getClass().getName() + " was created");
  }

  /**
   * Add the given passage to the story, with a link based on its title.
   *
   * @param passage the passage to add to the story.
   */
  public void addPassage(Passage passage) throws IllegalArgumentException,
      NullPointerException {
    try {
      Link link = new Link(passage.getTitle(), passage.getTitle());
      passages.put(link, passage);
      links.put(passage.getTitle(), link);
    } catch (IllegalArgumentException e) {
      logger.warning("Invalid argument provided : " + passage.getTitle() + ". (Error from: "
          + this.getClass().getName() + ")");
      throw e;
    } catch (NullPointerException e) {
      logger.warning(e.getMessage());
      throw new NullPointerException("The passage object is null");
    }
  }

  /**
   * Gets the title of the story.
   *
   * @return the title of the story.
   */
  public String getTitle() {
    return title;
  }

  /**
   * set's the value of title.
   *
   * @param title the new value of title
   * @throws IllegalArgumentException if the title is an empty string
   */
  private void setTitle(String title) throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(title, "Title");
      this.title = title;
    } catch (IllegalArgumentException e) {
      logger.warning(e.getMessage());
      throw new IllegalStateException("The story title is invalid");
    }
  }

  /**
   * Gets the opening passage of the story.
   *
   * @return the opening passage of the story.
   */
  public Passage getOpeningPassage() {
    return openingPassage;
  }

  /**
   * Gets the passage corresponding to the given link.
   *
   * @param link the link to the desired passage.
   * @return the passage corresponding to the given link, or null if no such passage exists.
   */
  public Passage getPassage(Link link) throws NullPointerException {
    try {
      CheckValid.checkObjectAndThrowException(link, "Link");
      Iterator<Passage> iterator = passages.values().iterator();
      Passage passage1 = iterator.next();
      while (iterator.hasNext()) {
        if (iterator.next().getLinks().contains(link)) {
          return passage1;
        }
      }
      return passage1;
    } catch (NullPointerException e) {
      logger.warning(e.getMessage());
      throw new NullPointerException("The Link object is null");
    }
  }

  /**
   * Gets a collection of all passages in the story.
   *
   * @return a collection of all passages in the story.
   */
  public Collection<Passage> getPassages() {
    return passages.values();
  }

  /**
   * Gets a list of all broken links in the story.
   * A link is broken when there is no passage linked to it.
   *
   * @return list of all broken links in the story.
   */
  public List<Link> getBrokenLinks() {

    List<Link> listOfBrokenLinks = new ArrayList<>();
    Set<Link> alreadyCheckedLinks = new HashSet<>();
    links.forEach((passage, link) -> {
      if (passage == null) {
        listOfBrokenLinks.add(link);
      }
    });

    Set<String> passageTitles = passages.values().stream()
        .map(Passage::getTitle)
        .collect(Collectors.toSet());
    passages.forEach((link, passage) -> {
      if (passage == null) {
        // If passage is null, add link to broken links and skip to next iteration
        listOfBrokenLinks.add(link);
        return;
      }
      List<Link> passageLinks = passage.getLinks();
      if (passage.hasLink()) {
        passageLinks.stream()
            .filter(alreadyCheckedLink -> !alreadyCheckedLinks.contains(
                alreadyCheckedLink))
            .forEach(linkToCheck -> {
              alreadyCheckedLinks.add(linkToCheck);
              if (!passageTitles.contains(linkToCheck.getReference())) {
                listOfBrokenLinks.add(linkToCheck);
              }
            });
      }
    });
    return listOfBrokenLinks;
  }

  /**
   * Removes a passage from list of passages if no other passage is linked to it.
   *
   * @param link the key in hashMap of the passage to be removed.
   */
  public void removePassage(Link link) {
    Passage passageToBeRemoved = passages.get(link);
    if (passageToBeRemoved == null) {
      throw new NullPointerException("There is no such passage");
    }
    boolean canBeRemoved = passages.values().stream()
        .noneMatch(passage -> passage.getLinks().stream().anyMatch(link1 -> link1.getReference()
            .equals(passageToBeRemoved.getTitle())));

    if (canBeRemoved) {
      passages.remove(link);
      links.remove(link.getText());
    } else {
      throw new IllegalStateException("The passage cannot be removed");
    }
  }

  /**
   * Returns a map of all links in the story, keyed by their string representation.
   *
   * @return a map of all links in the story keyed by their string representation.
   */
  public Map<String, Link> getLinks() {
    return links;
  }
}
