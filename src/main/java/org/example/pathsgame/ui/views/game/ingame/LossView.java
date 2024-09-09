package org.example.pathsgame.ui.views.game.ingame;

/**
 * The LossView class is a singleton that defines the scene displayed when the player loses the
 * game.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class LossView extends GameEndView {
  private static LossView instance;

  /**
   * Constructor for the LossView class.
   */
  private LossView() {
    super("/images/logos/lost_game.png");
  }

  /**
   * Static method to get the single instance of the LossView class.
   *
   * @return The single instance of the LossView class.
   */
  public static synchronized LossView getInstance() {
    if (instance == null) {
      instance = new LossView();
    }
    return instance;
  }

}
