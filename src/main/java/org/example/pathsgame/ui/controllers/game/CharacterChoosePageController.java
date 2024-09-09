package org.example.pathsgame.ui.controllers.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.game.Game;
import org.example.pathsgame.entities.goals.Goal;
import org.example.pathsgame.entities.goals.GoalsSavingHandler;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.factories.PlayerInformationHolder;
import org.example.pathsgame.setup_costumize.GameMusicHandler;
import org.example.pathsgame.ui.controllers.homepage.HomePageController;
import org.example.pathsgame.ui.views.game.beforegameview.CharacterChoosePageView;

/**
 * Controller for the character choose page. This class is responsible for handling user
 * interactions when choosing a character, setting up the Player object, and managing the
 * corresponding view.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class CharacterChoosePageController {
  private static CharacterChoosePageController instance;

  private final CharacterChoosePageView view;

  private Player player;

  private Game game;

  private Story story;

  private AtomicInteger currentSpriteIndex;

  private List<String> spritePaths;

  private Stage stage;

  /**
   * Default constructor. Initializes the view and fetches the Story instance from
   * HomePageController.
   */
  private CharacterChoosePageController() {

    view = CharacterChoosePageView.getInstance();
  }

  /**
   * Singleton pattern getInstance method. Returns the instance of this class, creating it if
   * necessary.
   *
   * @return The instance of this class.
   */
  public static synchronized CharacterChoosePageController getInstance() {

    if (instance == null) {
      instance = new CharacterChoosePageController();
    }
    return instance;
  }

  /**
   * Getter for the player.
   *
   * @return The Player object.
   */
  public Player getPlayer() {

    return player;
  }

  /**
   * Setter for the player.
   *
   * @param player The player instance to be set.
   */
  public void setPlayer(Player player) {

    this.player = player;
  }

  /**
   * Getter for the game.
   *
   * @return The Game instance.
   */
  public Game getGame() {

    return game;
  }

  /**
   * Setter for the game.
   *
   * @param game The Game instance to be set.
   */
  public void setGame(Game game) {

    this.game = game;
  }

  /**
   * Generates the character choice page. Uses player's data (name, health, gold) to create or
   * update a Player object, sets up character sprite and handles user interaction for
   * character selection.
   *
   * @param stage        The stage instance on which the view is presented.
   * @param playerName   The name of the player.
   * @param playerHealth The health of the player.
   * @param playerGold   The gold of the player.
   */
  public void getCharacterChoosePage(Stage stage, String playerName, String playerHealth,
                                     String playerGold) {

    this.stage = stage;
    try {
      player =
          new Player.PlayerBuilder().setName(playerName).setHealth(Integer.parseInt(playerHealth))
              .setScore(0).setGold(Integer.parseInt(playerGold))
              .build();
      PlayerInformationHolder.getInstance().setPlayer(player);
      spritePaths = new ArrayList<>();
      spritePaths.add("/images/sprite/player_stand.png");
      spritePaths.add("/images/sprite/soldier_stand.png");
      spritePaths.add("/images/sprite/female_stand.png");
      spritePaths.add("/images/sprite/adventurer_stand.png");
      currentSpriteIndex = new AtomicInteger(0);
      view.getCharacterChoosePageView(stage);
      // Pass the first sprite ImageView to the setUpSprite method
      view.setUpSprite(spritePaths.get(0), view.getSpriteImageView());
      onSpriteImageClicked();
      onNextButtonClicked();
      onPrevButtonClicked();
    } catch (IllegalArgumentException e) {
      AlertFactory.getInstance().getErrorAlert("Player Creation Failed",
          "Invalid input", "Something went wrong while crating "
              + "the character check you input or check if the sprite images exists").showAndWait();
    }

  }

  /**
   * The method is called when the "Sprite Image" button is clicked.
   * It sets the main character image in the game, and starts the game.
   */
  private void onSpriteImageClicked() {
    view.setUpSprite(spritePaths.get(currentSpriteIndex.get()), view.getSpriteImageView())
        .setOnMouseClicked(event -> {
          // Get the image path from the custom property and set it as the spritePath
          view.setSpritePath((String) view.getSpriteImageView().getProperties().get("imagePath"));
          // Perform any action you want when a sprite is selected
          if (getStory() != null) {
            GameController gameController = new GameController(game,
                stage, view.getSpritePath());
            GameController.setInstance(gameController);
            stage.setScene(gameController.getIngameScene(game.begin()));
            player.setPlayerSpritePath(
                view.getSpritePath());
            GameMusicHandler.getInstance().playMusic("/music/in_game.mp3");
          }
        });
  }

  /**
   * handles user interaction for the next button action when clicked.
   */
  private void onNextButtonClicked() {

    view.getNextButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      currentSpriteIndex.incrementAndGet();
      if (currentSpriteIndex.get() >= spritePaths.size()) {
        currentSpriteIndex.set(0);
      }
      // Update the spriteImageView with the new image
      view.setUpSprite(spritePaths.get(currentSpriteIndex.get()),
          view.getSpriteImageView());
    });
  }

  /**
   * handles user interaction for the previous button action when clicked.
   */
  private void onPrevButtonClicked() {

    view.getPrevButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      currentSpriteIndex.decrementAndGet();
      if (currentSpriteIndex.get() < 0) {
        currentSpriteIndex.set(spritePaths.size() - 1);
      }
      // Update the spriteImageView with the new image
      view.setUpSprite(spritePaths.get(currentSpriteIndex.get()),
          view.getSpriteImageView());
    });
  }

  /**
   * Getter for the story. Create a new Game instance with the player, story and story's goals,
   * sets it to the game field and returns the story.
   *
   * @return The Story object.
   */
  public Story getStory() {

    List<Goal> storyGoals;
    storyGoals = PlayerInformationProvidingController.getInstance().getStoryGoals();
    story = HomePageController.getInstance().getStory();
    game = new Game(player, story, storyGoals);
    setGame(game);
    return story;
  }

  /**
   * Setter for the story.
   *
   * @param story The story instance to be set.
   */
  public void setStory(Story story) {

    this.story = story;
  }
}
