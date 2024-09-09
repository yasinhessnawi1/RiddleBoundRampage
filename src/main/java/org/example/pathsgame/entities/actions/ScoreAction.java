package org.example.pathsgame.entities.actions;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;

/**
 * The ScoreAction class implements the Action interface and represents a change
 * in the player's scour.
 * It contains the attribute points, which specifies the change in the player's score.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1 (13.02.23).
 */
public class ScoreAction implements Action {
  // The amount of points to be added to the player's score.
  private int points;

  /**
   * Constructor for ScoreAction class.
   *
   * @param points The change in the player's score.
   */
  public ScoreAction(int points) {
    setPoints(points);
    Logger.getLogger(CHARACTERS_LOGGER)
        .info("Object of the class " + this.getClass().getName() + " was created");

  }

  /**
   * sets the value of points.
   *
   * @param points the new points value
   * @throws IllegalArgumentException if the points to add are invalid.
   */
  private void setPoints(int points) throws IllegalArgumentException {
    try {
      this.points = points;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
    }
  }

  /**
   * Overridden method from the 'Action' interface.
   * Add the 'points' to the player's score.
   *
   * @param player The player whose score will be changed.
   * @throws IllegalArgumentException if the points to add are invalid.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    try {
      player.addScore(points);
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
      throw new IllegalStateException("Error when trying to add points to the score");
    }
  }

  /**
   * Returns a string representation of the ScoreAction object.
   *
   * @return a string representation of the ScoreAction object.
   */
  @Override
  public String toString() {
    return "Score = " + points;
  }

  /**
   * Returns the amount of score.
   *
   * @return the amount of score.
   */
  public int getPoints() {
    return points;
  }
}
