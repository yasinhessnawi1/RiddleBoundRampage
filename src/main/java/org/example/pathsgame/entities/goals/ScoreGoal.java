package org.example.pathsgame.entities.goals;

import static org.example.pathsgame.utility.ConstantStrings.CHARACTERS_LOGGER;

import java.io.Serializable;
import java.util.logging.Logger;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.utility.CheckValid;

/**
 * The ScoreGoal class represents a goal related to the player's score.
 * This goal is fulfilled when the player's score is equal to or greater than
 * the minimum points specified in the constructor.
 *
 * @author Rami.
 * @version 0.1 (14.02.23).
 */
public class ScoreGoal implements Goal, Serializable {
  // The minimum points required to fulfill the goal.
  private int minimumPoints;

  /**
   * Constructs a new ScoreGoal object with the specified minimum points.
   *
   * @param minimumPoints The minimum points required to fulfill the goal.
   */
  public ScoreGoal(int minimumPoints) {

    setMinimumPoints(minimumPoints);
  }

  /**
   * set's the new minimumPoints value.
   *
   * @param minimumPoints the new minimumPoints value
   */
  private void setMinimumPoints(int minimumPoints) throws IllegalArgumentException {
    //throwing needs to be deleted when the error is handled
    try {
      CheckValid.checkIntAndThrowException(minimumPoints, "Minimum points");
      this.minimumPoints = minimumPoints;
    } catch (IllegalArgumentException e) {
      Logger.getLogger(CHARACTERS_LOGGER).warning(e.getMessage());
      throw new IllegalArgumentException("Not valid minimum points");
    }
  }

  /**
   * Returns a boolean value indicating whether the goal is fulfilled or not.
   *
   * @param player The player whose score is checked against the minimum points.
   * @return True if the player's score is equal to or greater than the minimum
   *         points, false otherwise.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getScore() >= minimumPoints;
  }

  /**
   * Returns a string representation of the ScourGoal object.
   *
   * @return a string representation of the ScourGoal object.
   */
  public String toString() {
    return "ScoreGoal = " + minimumPoints;
  }
}
