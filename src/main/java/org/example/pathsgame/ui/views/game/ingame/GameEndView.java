package org.example.pathsgame.ui.views.game.ingame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.pathsgame.factories.PlayerInformationHolder;
import org.example.pathsgame.setup_costumize.BackgroundSetUp;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.setup_costumize.TextStyler;
import org.example.pathsgame.ui.controllers.game.GameSettingController;
import org.example.pathsgame.ui.controllers.homepage.HomePageController;

/**
 * GameEndView class represents the view for the end of a game. It is the parent class for WinView
 * and LossView and provides shared functionality for setting up the scene, which includes the
 * display of the player statistics and control buttons for exiting or restarting the game.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GameEndView {
  protected static GameEndView instance;
  protected final HBox endViewBox;
  protected final BorderPane endViewBorderPane;
  protected final Scene endScene;
  protected final Button help;
  protected final Button exit;
  protected final Button restart;
  protected final Button goBackHome;
  protected VBox playerInfoBox;

  /**
   * Constructor that initializes the scene for the end of the game.
   *
   * @param logoPath The path to the logo image to be displayed at the end of the game.
   */
  protected GameEndView(String logoPath) {
    endViewBox = new HBox(20);
    endViewBorderPane = new BorderPane();
    endViewBox.setAlignment(Pos.CENTER);
    ImageView endImage = new ImageView(new Image(getClass()
        .getResourceAsStream(logoPath)));
    endViewBorderPane.setStyle("-fx-alignment: center");
    endViewBorderPane.setTop(endImage);
    VBox centerBox = new VBox(20);
    centerBox.setAlignment(Pos.CENTER);
    Text infoText = TextStyler.getInstance().styleText("Your character last stats are: ", 25);
    centerBox.getChildren().addAll(infoText, getPlayerInfoBox());
    endViewBorderPane.setCenter(centerBox);
    help = new Button();
    ButtonCustomizer.getInstance().getEffect(help,
        "setting_button_icons/help.png", 250, 250);
    exit =
        new Button();
    ButtonCustomizer.getInstance().getEffect(exit,
        "setting_button_icons/exit.png", 250, 250);
    restart = new Button();
    ButtonCustomizer.getInstance().getEffect(restart,
        "setting_button_icons/restart.png", 250, 250);
    goBackHome = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(goBackHome, "setting_button_icons/back_to_home.png",
            250, 250);
    onHelp();
    onExit();
    onRestart();
    onGoBackHome();
    endViewBox.getChildren().addAll(restart, goBackHome, help, exit);
    endViewBorderPane.setBottom(endViewBox);
    endViewBorderPane.setBackground(
        BackgroundSetUp.getInstance().getBackground("/images/backgrounds/lost_won_background.png"));
    endScene = new Scene(endViewBorderPane);
  }

  /**
   * creates and returns a VBox containing the player's stats.
   *
   * @return The VBox containing the player's stats.
   */
  protected VBox getPlayerInfoBox() {
    playerInfoBox = new VBox(20);
    playerInfoBox.setAlignment(Pos.CENTER);
    Text health = TextStyler.getInstance()
        .styleText("Health: " + PlayerInformationHolder.getInstance().getPlayer().getHealth(),
            25);
    Text score = TextStyler.getInstance()
        .styleText("Score: " + PlayerInformationHolder.getInstance().getPlayer().getScore(),
            25);
    Text gold = TextStyler.getInstance()
        .styleText("Gold: " + PlayerInformationHolder.getInstance().getPlayer().getGold(),
            25);
    Text inventory = TextStyler.getInstance()
        .styleText("Inventory: " + PlayerInformationHolder.getInstance()
                .getPlayer().getInventory(),
            25);
    playerInfoBox.getChildren().addAll(health, score, gold, inventory);
    return playerInfoBox;
  }

  /**
   * Sets up the action to be performed when the exit button is clicked.
   */
  public void onExit() {
    exit.setOnAction(e -> System.exit(0));
  }

  /**
   * Sets up the action to be performed when the help button is clicked.
   */
  private void onHelp() {

    help.setOnAction(e -> HomePageController.getInstance().onHelpButtonClicked(help));
  }

  /**
   * Sets up the action to be performed when the restart button is clicked.
   */
  public void onRestart() {

    GameSettingController.getInstance().onRestartGameClicked(restart);
  }

  /**
   * Sets up the action to be performed when the go back button is clicked.
   */
  public void onGoBackHome() {

    GameSettingController.getInstance().onExitGameClicked(goBackHome);
  }

  /**
   * Method to get the scene representing the end state.
   *
   * @return The end scene displayed when the player ends the game.
   */
  public Scene getEndScene() {
    return endScene;
  }
}
