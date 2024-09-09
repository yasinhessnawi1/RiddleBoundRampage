package org.example.pathsgame.entities.game;

import static org.example.pathsgame.utility.ConstantStrings.STORY_LOGGER;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.entities.goals.Goal;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.utility.CheckValid;

/**
 * The Game class represents a game that the player can play. It consists of a
 * Player a Story, and a list of Goals that the player needs to fulfill.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (14.02.23)
 */
public class Game {
  // The player playing the game.
  private Player player;

  // The story that the player will experience.
  private Story story;

  private final Map<String, Passage> passages;

  // The goals that the player needs to fulfill in order to win game.
  private List<Goal> goals;


  /**
   * Constructor for the Game class.
   *
   * @param player The player playing the game.
   * @param story  The story that the player will experience.
   * @param goals  The goals that the player needs to fulfill in order to win game.
   */
  public Game(Player player, Story story, List<Goal> goals) {
    setPlayer(player);
    setStory(story);
    this.goals = goals;
    this.passages =
        story.getPassages().stream().collect(Collectors.toMap(Passage::getTitle, p -> p));
    Logger.getLogger(STORY_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");
  }

  /**
   * Set's the player in the game.
   *
   * @param player the player to set to the game
   * @throws NullPointerException if the player object is null
   */
  public void setPlayer(Player player) throws NullPointerException {
    try {
      CheckValid.checkObjectAndThrowException(player, "Player");
      this.player = player;
    } catch (NullPointerException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new NullPointerException("Player object is null");
    }

  }

  /**
   * Set's the story in the game.
   *
   * @param story the story to set to the game
   * @throws NullPointerException if the story object is null
   */
  public void setStory(Story story) throws NullPointerException {
    try {
      CheckValid.checkObjectAndThrowException(story, "Story");
      this.story = story;
    } catch (NullPointerException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new NullPointerException("Story object is null");
    }
  }

  /**
   * Get the player playing the game.
   *
   * @return The player playing the game.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Get the story that the player will experience.
   *
   * @return The story that the player will experience.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Get the goals that the player needs to fulfill in order to win the game.
   *
   * @return The goals that the player needs to fulfill in order to wim the game.
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Get the opening passage of the story.
   *
   * @return The opening passage of the story.
   */
  public Passage begin() throws NullPointerException {
    try {
      CheckValid.checkObjectAndThrowException(story.getOpeningPassage(), "Opening Passage");
      return story.getOpeningPassage();
    } catch (NullPointerException e) {
      Logger.getLogger(STORY_LOGGER).warning(e.getMessage());
      throw new NullPointerException("Opening Passage object is null");
    }
  }

  /**
   * Get the passage indicated by the provided link.
   *
   * @param link The link indicating the desired passage.
   * @return The passage indicated by the provided link.
   */
  public Passage go(Link link) {
    if (link == null || link.getReference() == null) {
      return null;
    }
    String targetReference = link.getReference();
    return passages.get(targetReference);
  }

  /**
   * Checks if the goal of a specified type has been fulfilled by the player.
   *
   * @param goalType The type of the goal to be checked.
   * @return True if the goal has been fulfilled by the player, false otherwise.
   */
  public boolean isGoalFulfilled(Class<? extends Goal> goalType) {
    return goals.stream()
        .filter(goalType::isInstance)
        .anyMatch(goal -> goal.isFulfilled(player));
  }

  /**
   * Checks if all the goals have been fulfilled by the player.
   *
   * @return True if all the goals have been fulfilled by the player, false otherwise.
   */
  public boolean checkAllGoalsFulfilled() {

    for (Goal goal : goals) {
      if (!goal.isFulfilled(player)) {
        return false;
      }
    }
    return true;
  }

  /**
   * setter for goals
   */
  public void setGoals(List<Goal> goals) {

    this.goals = goals;
  }
}
