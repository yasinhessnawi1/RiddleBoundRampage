package org.example.pathsgame.entities.events;

import static org.example.pathsgame.utility.ConstantStrings.STORY_LOGGER;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.example.pathsgame.utility.CheckValid;

/**
 * The Passage class represents a small part of a story, a section of text.
 * It can be connected to other passages through links.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.02.23)
 */
public class Passage {
  // The title of the Passage.
  private String title;
  // The content of the Passage.
  private String content;
  // The list of links that connect this Passage to other Passages.
  private final List<Link> links;

  /**
   * Constructs a new Passage with a given title and content.
   *
   * @param title   The title of the Passage.
   * @param content The content of the Passage.
   */
  public Passage(String title, String content) {
    setTitle(title);
    setContent(content);
    links = new ArrayList<>();
    Logger.getLogger(STORY_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");

  }

  /**
   * sets the value of content.
   *
   * @param content the new content value
   * @throws IllegalArgumentException if content is empty string
   */
  public void setContent(String content) throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(content, "Content");
      this.content = content;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Content is not valid");
    }
  }

  /**
   * sets the value of title.
   *
   * @param title the new title value
   * @throws IllegalArgumentException if title is empty string
   */
  public void setTitle(String title)
      throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(title, "Title");
      this.title = title;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
    }
  }

  /**
   * Gets the title of the Passage.
   *
   * @return The title of the Passage.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the content of the Passage.
   *
   * @return The content of the Passage.
   */
  public String getContent() {
    return content;
  }

  /**
   * Add a link to this Passage.
   *
   * @param link The link to add to this Passage.
   * @return True if the link was successfully added, false otherwise.
   * @throws IllegalArgumentException if link object is not valid.
   */
  public boolean addLink(Link link)
      throws NullPointerException {
    try {
      CheckValid.checkObjectAndThrowException(link, "Link");
      return links.add(link);
    } catch (NullPointerException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Link object is not valid");
    }
  }

  /**
   * Gets the links associated with this Passage.
   *
   * @return The links associated with this Passage.
   */
  public List<Link> getLinks() {
    return links;
  }

  /**
   * Return true if this Passage has one or more links, false otherwise.
   *
   * @return True if this Passage has one or more links, false otherwise.
   */
  public boolean hasLink() {
    return !links.isEmpty();
  }

  /**
   * Gets the String representation of this Passage.
   *
   * @return The String representation of this Passage.
   */
  @Override
  public String toString() {
    return "Title: " + title + "\nContent: " + content + "\nLinks: " + links;
  }

  /**
   * Determines if this Passage is equal to another object.
   *
   * @param object The object to compare to.
   * @return True if the two objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object object) {
    // check if the object being compared is of type Passage.
    if (object instanceof Passage passage) {
      // Compare the title, content, and links of both Passage objects.
      return title.equals(passage.title) && content.equals(passage.content)
          && links.equals(passage.links);
    } else {
      // Return false if the object being compared is not of type Passage.
      return false;
    }
  }

  /**
   * Generates a hash code for this Passage.
   *
   * @return The hash code for this Passage.
   */
  @Override
  public int hashCode() {
    int resulting = title.hashCode();
    resulting = 31 * resulting + content.hashCode();
    resulting = 31 * resulting + links.hashCode();
    return resulting;
  }
}
