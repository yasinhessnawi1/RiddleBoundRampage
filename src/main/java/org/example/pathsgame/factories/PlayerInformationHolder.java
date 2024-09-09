package org.example.pathsgame.factories;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.ui.views.game.beforegameview.PlayerInformationProvidingView;
import org.example.pathsgame.ui.views.game.ingamesettingview.GameSettingView;

/**
 * PlayerInformationHolder is a singleton class that manages and displays the player's in-game
 * information. This includes the player's health, gold, score, and name. It can update these
 * values and also reset them when the game restarts.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class PlayerInformationHolder {
  private static PlayerInformationHolder instance;
  private Text healthText;
  private Text goldText;
  private Text scoreText;
  private Player player;
  private static final String TRANSPARENT_STYLE = "-fx-background-color: transparent";
  private static final String SIZE_STYLE = "-fx-font-size: 40px;";

  /**
   * Private constructor for PlayerInformationHolder. Prevents instantiation from outside the class.
   */
  private PlayerInformationHolder() {
  }

  /**
   * Returns the single instance of the PlayerInformationHolder.
   *
   * @return The single instance of the PlayerInformationHolder.
   */
  public static synchronized PlayerInformationHolder getInstance() {
    if (instance == null) {
      instance = new PlayerInformationHolder();
    }
    return instance;
  }

  /**
   * Sets the player whose information is to be held.
   *
   * @param player The player to set.
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Returns the current player whose information is being held.
   *
   * @return The current player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns a BorderPane containing the player's information panel on the left and the game's
   * settings box on the right.
   *
   * @return A BorderPane containing the player's information panel ond the gams's settings box.
   */
  public BorderPane getInGameTopBox() {

    BorderPane topHolder = new BorderPane();
    topHolder.setLeft(getInGamePlayerInformationPanel(player));
    topHolder.setRight(GameSettingView.getInstance().getSettingBox());
    return topHolder;
  }

  /**
   * Updates the player's information panel with the player's current health, gold and score.
   *
   * @param player The player whose information is to be updated.
   */
  public void updatePlayerInformationPanel(Player player) {
    healthText.setText(String.valueOf(player.getHealth()));
    goldText.setText(String.valueOf(player.getGold()));
    scoreText.setText(String.valueOf(player.getScore()));
  }

  /**
   * Updates the player's information when the game restarts.
   * This includes resetting the player's score to 0 and updating the health and gold from the
   * PlayerInformationProvidingView.
   *
   * @param player The player whose information is to be updated.
   */
  public void updatePlayerInformationWhenRestarting(Player player) {
    healthText.setText(String.valueOf(
        PlayerInformationProvidingView.getInstance().getPlayerHealthText().getText()));
    goldText.setText(
        String.valueOf(PlayerInformationProvidingView.getInstance().getPlayerGoldText().getText()));
    scoreText.setText(String.valueOf(0));
    player.setScore(0);
    player.setGold(Integer.parseInt(
        PlayerInformationProvidingView.getInstance().getPlayerGoldText().getText()));
    player.setHealth(Integer.parseInt(
        PlayerInformationProvidingView.getInstance().getPlayerHealthText().getText()));
  }

  /**
   * Returns an HBox containing the player's in-game information panel.
   * This includes the player's health, gold, score, and name.
   *
   * @param player The player whose information is to be displayed.
   * @return An HBox containing the player's in-game information panel.
   */
  public HBox getInGamePlayerInformationPanel(Player player) {

    ImageView playerImage =
        new ImageView(new Image(getClass().getResourceAsStream(
            "/images/animatedicons/gamer.gif")));
    playerImage.setStyle(TRANSPARENT_STYLE);
    playerImage.setFitHeight(50);
    playerImage.setFitWidth(50);
    Text name = new Text(player.getName());
    name.setStyle(SIZE_STYLE);

    HBox playerNameBox = new HBox();
    playerNameBox.getChildren().addAll(name, playerImage);
    playerNameBox.setSpacing(10);

    HBox playerHealthBox = new HBox();
    playerHealthBox.setSpacing(10);
    ImageView playerHealth =
        new ImageView(new Image(getClass().getResourceAsStream(
            "/images/animatedicons/heartbeat.gif")));
    playerHealth.setStyle(TRANSPARENT_STYLE);
    playerHealth.setFitHeight(50);
    playerHealth.setFitWidth(50);
    healthText = new Text(String.valueOf(player.getHealth()));
    healthText.setStyle(SIZE_STYLE);
    playerHealthBox.getChildren().addAll(healthText, playerHealth);

    HBox playerGoldBox = new HBox();
    playerGoldBox.setSpacing(10);
    ImageView playerGold =
        new ImageView(new Image(getClass().getResourceAsStream(
            "/images/animatedicons/dollar.gif")));
    playerGold.setStyle(TRANSPARENT_STYLE);
    playerGold.setFitHeight(50);
    playerGold.setFitWidth(50);
    goldText = new Text(String.valueOf(player.getGold()));
    goldText.setStyle(SIZE_STYLE);
    playerGoldBox.getChildren().addAll(goldText, playerGold);

    HBox playerScoreBox = new HBox();
    playerScoreBox.setSpacing(10);
    ImageView playerScore =
        new ImageView(new Image(getClass().getResourceAsStream(
            "/images/animatedicons/award.gif")));
    playerScore.setStyle(TRANSPARENT_STYLE);
    playerScore.setFitHeight(50);
    playerScore.setFitWidth(50);
    scoreText = new Text(String.valueOf(player.getScore()));
    scoreText.setStyle(SIZE_STYLE);
    playerScoreBox.getChildren().addAll(scoreText, playerScore);

    HBox playerInformationBox = new HBox();
    playerInformationBox.setSpacing(40);
    playerInformationBox.getChildren()
        .addAll(playerNameBox, playerHealthBox, playerGoldBox, playerScoreBox);

    return playerInformationBox;
  }
}
