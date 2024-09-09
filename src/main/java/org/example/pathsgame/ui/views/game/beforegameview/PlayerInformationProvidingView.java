package org.example.pathsgame.ui.views.game.beforegameview;

import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.example.pathsgame.factories.GoBackButtonFactory;
import org.example.pathsgame.setup_costumize.BackgroundSetUp;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.setup_costumize.TextStyler;
import org.example.pathsgame.ui.views.homepage.HomePageView;
import org.example.pathsgame.utility.NavigationBetweenSceneData;
import org.example.pathsgame.utility.SceneHandler;

/**
 * The PlayerInformationProvidingView class is a singleton class that manages the UI view where the
 * player provides information about characters.
 * It includes text fields for the player to enter their name, health, gold, and other related
 * information. This class is part of the main view in the game's user interface.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class PlayerInformationProvidingView {
  private Text playerName;
  private Text playerHealth;
  private Text playerGold;
  private Text playerInventory;
  private TextField playerNameText;
  private TextField playerHealthText;
  private TextField playerGoldText;

  private CheckComboBox<String> playerInventoryText;

  private Button nextButton;

  private Text maxHealth;

  private Text maxScore;

  private Text maxGold;

  private TextField maxHealthText;

  private TextField maxScoreText;

  private TextField maxGoldText;

  private CheckBox enabledGoalsCheckBox;

  private static PlayerInformationProvidingView playerInformationProvidingViewInstance;

  private Button backButton;

  /**
   * Private constructor for the PlayerInformationProvidingView.
   * Sets up the information field for the player to fill in.
   */
  private PlayerInformationProvidingView() {

    getInformationSetUp();
  }

  /**
   * Provides the singleton instance of the PlayerInformationProvidingView.
   * If the instance does not already exist, it creates one.
   *
   * @return The instance of the PlayerInformationProvidingView.
   */
  public static synchronized PlayerInformationProvidingView getInstance() {
    if (playerInformationProvidingViewInstance == null) {
      playerInformationProvidingViewInstance = new PlayerInformationProvidingView();
    }
    return playerInformationProvidingViewInstance;
  }

  /**
   * Sets up the information fields for the player to fill in.
   */
  public void getInformationSetUp() {
    playerName = TextStyler.getInstance().styleText("Name: ", 20);
    playerHealth = TextStyler.getInstance().styleText("Health: ", 20);
    playerGold = TextStyler.getInstance().styleText("Gold: ", 20);
    playerInventory = TextStyler.getInstance().styleText("Inventory: ", 20);
    playerNameText = new TextField();
    playerNameText.setPromptText("Type your name here");
    playerHealthText = new TextField();
    playerHealthText.setPromptText("Type your health here");
    playerGoldText = new TextField();
    playerGoldText.setPromptText("Type your gold here");
    playerInventoryText = new CheckComboBox<>();
    playerInventoryText.setTitle("Choose your inventory here");
    nextButton = new Button();
    ButtonCustomizer.getInstance().getEffect(nextButton, "next.png",
        150, 120);
    maxHealth = TextStyler.getInstance().styleText("Max Health: ", 20);
    maxScore = TextStyler.getInstance().styleText("Max Score: ", 20);
    maxGold = TextStyler.getInstance().styleText("Max Gold: ", 20);
    maxHealthText = new TextField();
    maxHealthText.setPromptText("Type your Health Goal here");
    maxScoreText = new TextField();
    maxScoreText.setPromptText("Type your Score Goal here");
    maxGoldText = new TextField();
    maxGoldText.setPromptText("Type your Gold Goal here");
    enabledGoalsCheckBox = TextStyler.getInstance().styleCheckBox("Enable Goals", 20);
  }

  /**
   * Creates and returns a BorderPane containing the UI components for the player to provide their
   * information.
   *
   * @param inventoryActions Set of actions related to inventory the player can choose from.
   * @return The information pane containing player input fields.
   */
  public BorderPane getInformationPane(Set<String> inventoryActions, Stage stage) {

    ImageView informationPageName = new ImageView(
        new Image(getClass()
            .getResourceAsStream("/images/logos/information_providing_name.png")));
    informationPageName.setPreserveRatio(true);
    informationPageName.setFitWidth(600);
    informationPageName.setFitHeight(600);
    HBox buttonBox = new HBox();
    backButton = GoBackButtonFactory.getInstance(stage,
        NavigationBetweenSceneData.getInstance(),
        SceneHandler.getInstance().getScenes()).getButton();
    buttonBox.getChildren().addAll(backButton, nextButton);
    buttonBox.setSpacing(300);
    buttonBox.setAlignment(Pos.CENTER);

    HBox nameBox = new HBox();
    nameBox.getChildren().add(informationPageName);
    nameBox.setAlignment(Pos.CENTER);
    BorderPane informationPane = new BorderPane();
    informationPane.setBottom(buttonBox);
    informationPane.setTop(nameBox);
    informationPane.setCenter(getPlayerInformationInputPane(inventoryActions));
    informationPane.setBackground(BackgroundSetUp.getInstance()
        .getBackground("/images/backgrounds/player_information_page.png"));
    informationPane.prefWidthProperty().bind(HomePageView.getInstance().getStage().widthProperty());
    informationPane.prefHeightProperty()
        .bind(HomePageView.getInstance().getStage().heightProperty());
    return informationPane;
  }

  /**
   * Creates and returns a GridPane containing the UI components for the player to input their
   * information.
   *
   * @param inventoryActions Set of actions related to inventory the player can choose from.
   * @return The information box containing player input fields.
   */
  public GridPane getPlayerInformationInputPane(Set<String> inventoryActions) {
    GridPane informationBox = new GridPane();
    informationBox.setHgap(10);
    informationBox.setVgap(10);
    informationBox.add(playerName, 0, 0);
    informationBox.add(playerNameText, 1, 0);
    informationBox.add(playerHealth, 0, 1);
    informationBox.add(playerHealthText, 1, 1);
    informationBox.add(playerGold, 0, 2);
    informationBox.add(playerGoldText, 1, 2);

    playerInventoryText.getItems().clear();
    playerInventoryText.getItems().addAll(inventoryActions);
    informationBox.add(enabledGoalsCheckBox, 0, 4);
    informationBox.add(playerInventory, 0, 5);
    informationBox.add(playerInventoryText, 1, 5);
    informationBox.add(maxHealth, 0, 6);
    informationBox.add(maxHealthText, 1, 6);
    informationBox.add(maxScore, 0, 7);
    informationBox.add(maxScoreText, 1, 7);
    informationBox.add(maxGold, 0, 8);
    informationBox.add(maxGoldText, 1, 8);
    informationBox.setAlignment(Pos.CENTER);
    return informationBox;
  }

  /**
   * Returns the TextField for the player's name.
   *
   * @return The TextField for the player's name.
   */
  public TextField getPlayerNameText() {
    return playerNameText;
  }

  /**
   * Returns the TextField for the player's health.
   *
   * @return The TextField for the player's health.
   */
  public TextField getPlayerHealthText() {
    return playerHealthText;
  }

  /**
   * Returns the TextField for the player's gold.
   *
   * @return The TextField for the player's gold.
   */
  public TextField getPlayerGoldText() {
    return playerGoldText;
  }

  /**
   * Returns the CheckComboBox for the player's inventory.
   *
   * @return The CheckComboBox for the player's inventory.
   */
  public CheckComboBox<String> getPlayerInventoryText() {
    return playerInventoryText;
  }

  /**
   * Returns the Button for proceeding to the next phase.
   *
   * @return The Button for proceeding to the next phase.
   */
  public Button getNextButton() {
    return nextButton;
  }

  /**
   * Returns the TextField for the player's max health goal.
   *
   * @return The TextField for the player's max health goal.
   */
  public TextField getMaxHealthText() {
    return maxHealthText;
  }

  /**
   * Returns the TextField for the player's max score goal.
   *
   * @return The TextField for the player's max score goal.
   */
  public TextField getMaxScoreText() {
    return maxScoreText;
  }

  /**
   * Returns the TextField for the player's max gold goal.
   *
   * @return The TextField for the player's max gold goal.
   */
  public TextField getMaxGoldText() {
    return maxGoldText;
  }

  /**
   * Returns the CheckBox for enabling/disabling goals.
   *
   * @return The CheckBox for enabling/disabling goals.
   */
  public CheckBox getEnabledGoalsCheckBox() {

    return enabledGoalsCheckBox;
  }

  /**
   * Returns the Button for going back to the previous scene.
   *
   * @return the go back button
   */
  public Button getBackButton() {

    return backButton;
  }
}
