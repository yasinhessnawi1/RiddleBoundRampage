package org.example.pathsgame.entities.events;

import static org.example.pathsgame.utility.ConstantStrings.STORY_LOGGER;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.utility.CheckValid;

/**
 * Link class represents a connection between passages in a story.
 * It consists of text that describes the choice or action the player can do,
 * a reference to the passage it connects to, and list of actions that can
 * effect the player's properties.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (12.02.23)
 */
public class Link {
  // The text description of the link.
  private String text;
  // The reference to the connected passage.
  private String reference;
  // The list of actions that can affect the player's properties.
  private final List<Action> actions;

  /**
   * Constructor for the link class.
   *
   * @param text      The text description of the link.
   * @param reference The reference to the connected passage.
   */

  public Link(String text, String reference) {
    setText(text);
    setReference(reference);
    this.actions = new ArrayList<>();
    Logger.getLogger(STORY_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");
  }

  /**
   * sets the value of reference.
   *
   * @param reference the new reference value
   * @throws IllegalArgumentException if reference is empty string
   */
  private void setReference(String reference) throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(reference, "Reference");
      this.reference = reference;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Reference is not valid");
    }
  }

  /**
   * sets the value of text.
   *
   * @param text the new text value
   * @throws IllegalArgumentException if text is empty string
   */

  private void setText(String text)
      throws IllegalArgumentException {
    try {
      CheckValid.checkStringAndThrowException(text, "Text");
      this.text = text;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("text is not valid");
    }
  }

  /**
   * Gets the text description of the link.
   *
   * @return The text description of the link.
   */
  public String getText() {
    return text;
  }

  /**
   * Gets the reference to the connected passage.
   *
   * @return The reference to the connected passage.
   */
  public String getReference() {
    return reference;
  }

  /**
   * Add an action to the list of actions.
   *
   * @param action The action to add the list.
   */
  public void addAction(Action action)
      throws NullPointerException {
    try {
      CheckValid.checkObjectAndThrowException(action, "Action object");
      actions.add(action);
    } catch (NullPointerException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("action object is not valid");
    }
  }

  /**
   * Gets the list of actions.
   *
   * @return The list of actions.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Gets a string representation of the link.
   *
   * @return A string representation of the link.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[")
        .append(text)
        .append("](")
        .append(reference)
        .append(")");
    for (Action action : actions) {
      sb.append("(")
          .append(action.toString())
          .append(")");
    }
    return sb.toString();
  }

  /**
   * Compares this link to another object for equality.
   *
   * @param object The object to compare to.
   * @return True if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object object) {
    // check if the object being compared is of type link.
    if (object instanceof Link link) {
      // returns true if the reference of the links is the same, then the links are identical.
      return reference.equals(link.reference);
    } else {
      // Return false if the object being compared is not of type Link.
      return false;
    }
  }

  /**
   * Generates a hash code for this link.
   *
   * @return The hash code for this link.
   */
  @Override
  public int hashCode() {
    int resulting = text.hashCode();
    resulting = 31 * resulting + reference.hashCode();
    resulting = 31 * resulting + actions.hashCode();
    return resulting;
  }
}
