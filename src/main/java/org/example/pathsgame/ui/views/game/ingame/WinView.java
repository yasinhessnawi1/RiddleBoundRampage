package org.example.pathsgame.ui.views.game.ingame;

/**
 * The WinView class is a singleton that defines the scene displayed when the player wins the
 * game.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class WinView extends GameEndView {
  private static WinView instance;

  /**
   * Constructor for the WinView class.
   */
  private WinView() {
    super("/images/logos/won_game.png");
  }

  /**
   * Static method to get the single instance of the WinView class.
   *
   * @return The single instance of the WinView class.
   */
  public static synchronized WinView getInstance() {
    if (instance == null) {
      instance = new WinView();
    }
    return instance;
  }
}
