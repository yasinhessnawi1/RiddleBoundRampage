package org.example.pathsgame.ui.controllers.game;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.entities.actions.GoldAction;
import org.example.pathsgame.entities.actions.HealthAction;
import org.example.pathsgame.entities.actions.InventoryAction;
import org.example.pathsgame.entities.actions.ScoreAction;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.characters.PlayerFileHandler;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.entities.game.Game;
import org.example.pathsgame.entities.goals.GoalsSavingHandler;
import org.example.pathsgame.entities.goals.GoldGoal;
import org.example.pathsgame.entities.goals.HealthGoal;
import org.example.pathsgame.entities.goals.InventoryGoal;
import org.example.pathsgame.entities.goals.ScoreGoal;
import org.example.pathsgame.entities.quiz.Quiz;
import org.example.pathsgame.entities.quiz.QuizFileHandler;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.entities.story.StoryFileHandler;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.factories.InGamePopUpTexts;
import org.example.pathsgame.factories.PlayerInformationHolder;
import org.example.pathsgame.ingameimageshandlers.InventoryComponents;
import org.example.pathsgame.ingameimageshandlers.InventoryItems;
import org.example.pathsgame.ingameimageshandlers.PlacesPictures;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.setup_costumize.PassageBox;
import org.example.pathsgame.setup_costumize.TextStyler;
import org.example.pathsgame.ui.views.game.ingame.GameView;
import org.example.pathsgame.ui.views.game.ingame.InGameLayout;
import org.example.pathsgame.ui.views.game.ingame.QuizView;

/**
 * GameController is the main controller class that handles the game play logic. It maintains the
 * state of the game, player, and the current passage the player is in, GameController also manages
 * the transition between different game states.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GameController {
  private static GameController instance;
  private final GameView view;
  private final Player player;
  private final Game game;
  private Passage currentPassage;
  private final LinkedList<Passage> passageHistory;
  private final InGamePopUpTexts popUpTexts;
  private Story story;
  private final Random random;
  private static final String ERROR = "Error";
  /**
   * Constructor for GameController.
   * Initializes the view for this GameController, the game, the player, the story and the
   * current passage.
   *
   * @param game  The game instance to be controlled.
   * @param stage The stage on which to display the game.
   */
  public GameController(Game game, Stage stage, String spritePath) {

    view = new GameView(stage, spritePath);
    this.game = game;
    this.player = game.getPlayer();
    popUpTexts = InGamePopUpTexts.getInstance();
    story = game.getStory();
    currentPassage = story.getOpeningPassage();
    random = new Random();
    passageHistory = new LinkedList<>();

  }


  /**
   * Singleton pattern getInstance method. Returns the instance of this class, creating it if
   * necessary.
   *
   * @return The instance of this class.
   */
  public static synchronized GameController getInstance() {

    return instance;
  }

  /**
   * Sets the GameController instance. This method is synchronized to ensure thread safety.
   *
   * @param instance The instance of this class.
   */
  public static synchronized void setInstance(GameController instance) {
    GameController.instance = instance;
  }

  /**
   * Sets up and returns the game scene for a specified passage.
   *
   * @param passage The passage instance to display in the scene.
   * @return The Scene object representing the game scene.
   */
  public Scene getIngameScene(Passage passage) {

    InGameLayout.getInstance().getRightVbox().getChildren().clear();

    InGameLayout.getInstance().getRightVbox().getChildren().clear();
    InGameLayout.getInstance().getBottomView().getChildren().clear();
    InGameLayout.getInstance().addToBottomView(view.getMosnterImageView());
    view.setMonsterRunning();
    InGameLayout.getInstance().addToBottomView(view.getCharacterImageView());
    view.setMainCharacterRunning();
    PlacesPictures placesPictures = getPlacePicturesFromPassageContent(passage.getContent());
    String filename = (placesPictures != null) ? placesPictures.getFileName() : null;
    Scene roomScene = InGameLayout.getInstance().getRoomViewScene(filename);
    addLinkButtons(passage);
    String linkButtonStyleClassPath =
        getClass().getResource("/style/linkButtonStyle.css").toExternalForm();
    String popUpTextStyleClassPath =
        getClass().getResource("/style/PopUpText.css").toExternalForm();
    roomScene.getStylesheets().add(linkButtonStyleClassPath);
    roomScene.getStylesheets().add(popUpTextStyleClassPath);
    updateRightViewForInventory(passage);
    updateInventoryWhenContinuing();
    return roomScene;
  }


  /**
   * Adds the available links from a passage to the scene as interactive buttons.
   *
   * @param passage The passage instance whose links are to be displayed.
   */
  public void addLinkButtons(Passage passage) {

    List<Quiz> quizList = new ArrayList<>();
    try {
      quizList = QuizFileHandler.getInstance().readQuizFromFile("/quiz/quizeFile.csv");

    } catch (IOException e) {
      AlertFactory.getInstance().getErrorAlert(ERROR, ERROR,
          "Error while reading quiz file!");
    }
    passageHistory.push(passage);
    InGameLayout.getInstance().getBottomView().getChildren().clear();
    InGameLayout.getInstance().getCenterView().getChildren().clear();
    Node passageTitleBox = TextStyler.getInstance().styleText(passage.getTitle(), 80);
    InGameLayout.getInstance().getCenterView().getChildren().add(passageTitleBox);
    InGameLayout.getInstance().addToBottomView(view.getMosnterImageView());
    view.setMonsterRunning();
    InGameLayout.getInstance().addToBottomView(view.getCharacterImageView());
    view.setMainCharacterRunning();
    InGameLayout.getInstance()
        .addToBottomView(PassageBox.getInstance().getPassageBox(passage.getContent()));
    VBox linkButtons = new VBox(10);
    for (Link link : passage.getLinks()) {
      linkButtons.setAlignment(Pos.CENTER);
      Button linkTitleButton = ButtonCustomizer.getInstance().getEffect(link.getText());
      onLinkButtonClicked(link, linkTitleButton);
      linkButtons.getChildren().addAll(linkTitleButton);
    }
    if (!currentPassage.hasLink()) {
      Button endGameButton = ButtonCustomizer.getInstance().getEffect("End the Game");
      onWinEndGameButtonClicked(endGameButton);
      linkButtons.getChildren().addAll(endGameButton);
    }
    addAndHandleQuiz(quizList, linkButtons);
  }

  /**
   * Handles the action of the button that ends the game if the player wins.
   *
   * @param endGameButton teh button that will be used to apply the action.
   */
  private void onWinEndGameButtonClicked(Button endGameButton) {

    endGameButton.setOnAction(theEnd -> {
      PathGameApplication.getButtonSound().play();
      view.showWinScene();
    });
  }

  /**
   * handles the actions of the links.
   *
   * @param link            the link that will be used to apply the actions.
   * @param linkTitleButton the link button that will be used to apply the actions.
   */
  private void onLinkButtonClicked(Link link, Button linkTitleButton) {

    linkTitleButton.setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      Passage targetPassage = game.go(link);
      if (targetPassage != null) {
        currentPassage = targetPassage;
        getIngameScene(targetPassage);
        applyActionsLinks(link);
      }
    });
  }

  /**
   * Adds quizzes to the link buttons, It also sets the event handlers for quiz answers.
   *
   * @param quizList    the list of quizzes to be added.
   * @param linkButtons The container where the quizzes are to be added.
   */
  private void addAndHandleQuiz(List<Quiz> quizList, VBox linkButtons) {

    int index = random.nextInt(quizList.size());
    Quiz quiz = quizList.get(index);
    List<String> answers = new ArrayList<>();
    answers.add(quiz.getRightAnswer());
    answers.add(quiz.getWrongAnswer());
    Collections.shuffle(answers);
    InGameLayout.getInstance().addToBottomView(QuizView.getInstance()
        .getQuizSetUp(quiz.getQuestion(), answers.get(0), answers.get(1)));
    InGameLayout.getInstance().addToBottomView(linkButtons);
    linkButtons.getChildren().forEach(node -> {
      if (node instanceof Button) {
        node.setDisable(true);
      }
    });
    handleFirstAnswer(linkButtons, quiz);
    handleSecondAnswer(linkButtons, quiz);
  }

  /**
   * Handles the event of the first answer in the quiz, It adds appropriate behaviour to the link
   * buttons.
   *
   * @param linkButtons The container where the quizzes are located.
   * @param quiz        whose first answer is to be handled.
   */
  private void handleFirstAnswer(VBox linkButtons, Quiz quiz) {
    QuizView.getInstance().getAnswer1Text().setOnAction(e -> {
      if (QuizView.getInstance().getAnswer1Text().getText().equals(quiz.getRightAnswer())) {
        linkButtons.getChildren().forEach(node -> {
          if (node instanceof Button) {
            node.setDisable(false);
          }
        });
      } else {
        view.setMainCharacterFighting();
        view.setMonsterFighting();
        view.switchCharacterPositions();
        getFightingResult();
        QuizView.getInstance().getAnswer1Text().setDisable(true);
      }
    });
  }

  /**
   * Handles the event of the second answer in the quiz, It adds appropriate behaviour to the link
   * buttons.
   *
   * @param linkButtons The container where the quizzes are located.
   * @param quiz        whose second answer is to be handled.
   */
  private void handleSecondAnswer(VBox linkButtons, Quiz quiz) {
    QuizView.getInstance().getAnswer2Text().setOnAction(e -> {
      if (QuizView.getInstance().getAnswer2Text().getText().equals(quiz.getRightAnswer())) {
        linkButtons.getChildren().forEach(node -> {
          if (node instanceof Button) {
            node.setDisable(false);
          }
        });
      } else {
        view.setMainCharacterFighting();
        view.setMonsterFighting();
        view.switchCharacterPositions();
        getFightingResult();
        QuizView.getInstance().getAnswer2Text().setDisable(true);
      }
    });
  }

  /**
   * Handles the actions associated with a specific link.
   *
   * @param link The link whose actions are to be applied.
   */
  private void applyActionsLinks(Link link) {
    List<Action> actions = link.getActions();
    for (Action action : actions) {
      if (action instanceof GoldAction goldAction) {
        goldAction.execute(player);
        popUpTexts.getPopUpText(goldAction.getGold() + " Gold");
      } else if (action instanceof HealthAction healthAction) {
        healthAction.execute(player);
        popUpTexts.getPopUpText(healthAction.getHealth() + " Health");
      } else if (action instanceof ScoreAction scoreAction) {
        scoreAction.execute(player);
        popUpTexts.getPopUpText(scoreAction.getPoints() + " Score");
      }
      PlayerInformationHolder.getInstance().setPlayer(player);
    }
    PlayerInformationHolder.getInstance().updatePlayerInformationPanel(player);
    gameProgressCheck();
  }

  private void gameProgressCheck() {

    if (game.isGoalFulfilled(GoldGoal.class)) {
      popUpTexts.getPopUpText(" + Gold Goal Fulfilled");
    }
    if (game.isGoalFulfilled(HealthGoal.class)) {
      popUpTexts.getPopUpText(" + Health Goal Fulfilled");
    }
    if (game.isGoalFulfilled(ScoreGoal.class)) {
      popUpTexts.getPopUpText(" + Score Goal Fulfilled");
    }
    if (game.isGoalFulfilled(InventoryGoal.class)) {
      popUpTexts.getPopUpText(" + Inventory Goal Fulfilled");
    }
    if (game.checkAllGoalsFulfilled() && !game.getGoals().isEmpty()) {
      view.showWinScene();
    }
    if (player.getHealth() < 0 || player.getGold() < 0 || player.getScore() < 0) {
      view.showLossScene();
    }
  }

  /**
   * Returns the associated picture for the specified passage content.
   *
   * @param content The content of the passage.
   * @return The associated PlacesPictures instance.
   */
  private PlacesPictures getPlacePicturesFromPassageContent(String content) {
    String contentToLowerCase = content.toLowerCase();
    for (PlacesPictures place : PlacesPictures.values()) {
      if (contentToLowerCase.contains(place.name().toLowerCase())) {
        return place;
      }
    }
    return null;
  }

  /**
   * Returns the current passage of the game.
   *
   * @return the current passage instance.
   */
  public Passage getCurrentPassage() {
    return currentPassage;
  }

  /**
   * Handles the result of a combat scenario in the game.
   * Determines the outcome and updates the game state accordingly.
   */
  private void getFightingResult() {
    int randomNum = random.nextInt(2);
    if (randomNum == 0) {
      HealthAction healthAction = new HealthAction(-10);
      healthAction.execute(player);
      popUpTexts.getPopUpText(" -10 Health");
      PlayerInformationHolder.getInstance().updatePlayerInformationPanel(player);
    } else {
      popUpTexts.getPopUpText("You won the fight");
    }
  }

  /**
   * Updates the inventory view based on the actions associated with the links in the current
   * passage.
   *
   * @param passage The current passage instance.
   */
  private void updateRightViewForInventory(Passage passage) {

    InGameLayout.getInstance().getRightVbox().getChildren().clear();
    for (Link link : passage.getLinks()) {
      for (Action action : link.getActions()) {
        if (action instanceof InventoryAction inventoryAction) {
          String actionString = inventoryAction.toString().toLowerCase();
          String itemName = actionString.replace("inventory = ", "").trim();
          String nodeItemName = itemName;
          for (InventoryItems component : InventoryItems.values()) {
            if (itemName.contains(component.name().toLowerCase())) {
              nodeItemName = component.name();
              break;
            }
          }
          Node itemNode = getInventoryViewItem(nodeItemName);
          inventoryItemHandler(inventoryAction, itemNode, itemName);
        }
      }
    }
  }

  /**
   * Handles inventory item events.
   *
   * @param inventoryAction The inventory action.
   * @param itemNode The item node in the inventory view.
   * @param finalItemName The final name of the item.
   */
  private void inventoryItemHandler(InventoryAction inventoryAction, Node itemNode,
                                    String finalItemName) {
    itemNode.setOnMouseClicked(event -> {
      if (isItemInInventory(itemNode.getId())) {
        AlertFactory.getInstance().getErrorAlert("Item exists",
            "Item Already in Inventory",
            "You already have this " + finalItemName.toLowerCase()
                + " in your inventory. You can't pick it up again.").showAndWait();
      } else {
        PathGameApplication.getButtonSound().play();
        inventoryAction.execute(player);
        PlayerInformationHolder.getInstance().setPlayer(player);
        PlayerInformationHolder.getInstance().updatePlayerInformationPanel(player);
        InGameLayout.getInstance().addToLeftView(itemNode);
        InGameLayout.getInstance().getRightVbox().getChildren().remove(itemNode);
        // Add a new click handler to the item in the left panel.
        itemNode.setOnMouseClicked(
            leftEvent -> removeItemHandler(finalItemName.toLowerCase(),
                itemNode));
      }
    });
    InGameLayout.getInstance().addToRightView(itemNode);
  }

  /**
   * Returns an inventory view item based on the given itemName.
   *
   * @param itemName The name of the item.
   * @return The node representing the item in the inventory view.
   */
  private Node getInventoryViewItem(String itemName) {
    ImageView imageView = InventoryComponents.getInstance().getRoomComponentsView(itemName);
    if (imageView != null) {
      imageView.setId(itemName);
      return imageView;
    } else {
      // Create a Text object to represent the item.
      Text itemText = TextStyler.getInstance().styleText(itemName, 20);
      itemText.setId(itemName);
      return itemText;
    }
  }

  /**
   * Checks if the item already exists in the inventory.
   *
   * @param itemId The id of the item.
   * @return True if the item exists in the inventory. False otherwise.
   */
  private boolean isItemInInventory(String itemId) {
    AtomicBoolean exists = new AtomicBoolean(false);
    InGameLayout.getInstance().getLeftVbox().getChildren().forEach(node -> {
      if (node instanceof ImageView && (node.getId().equals(itemId))) {
        exists.set(true);

      }
    });
    return exists.get();
  }

  /**
   * Handles the removal of an item from inventory.
   *
   * @param itemName The name of the item.
   * @param itemNode The node representing the item in the inventory view.
   */
  private void removeItemHandler(String itemName, Node itemNode) {
    PathGameApplication.getButtonSound().play();
    // Create an alert.
    AlertFactory alertFactory = AlertFactory.getInstance();
    Alert confirmationAlert = alertFactory.getConfirmationAlert("Confirm Action",
        "Item Removal Confirmation",
        "Do you want to remove this " + itemName
            + " from the list of inventory?");
    // Show the alert and wait for user action.
    Optional<ButtonType> result = confirmationAlert.showAndWait();
    result.filter(
            response -> response.getButtonData() == ButtonBar.ButtonData.YES)
        .ifPresent(response -> {
          InGameLayout.getInstance().getLeftVbox().getChildren().remove(itemNode);
          player.removeFromInventory(itemName);
        });
  }

  /**
   * Updates inventory when the game continues.
   */
  public void updateInventoryWhenContinuing() {

    InGameLayout.getInstance().getLeftVbox().getChildren().clear();
    for (String item : player.getInventory()) {
      String nodeItemName = item;

      // Loop through the InventoryItems.
      for (InventoryItems component : InventoryItems.values()) {
        if (item.contains(component.name().toLowerCase())) {
          // If the item from inventory contains the name from enum, use the enum name for node.
          nodeItemName = component.name();
          break;
        }
      }

      Node itemNode = getInventoryViewItem(nodeItemName);
      InGameLayout.getInstance().addToLeftView(itemNode);
      itemNode.setOnMouseClicked(leftEvent -> {
        PathGameApplication.getButtonSound().play();
        // Create an alert.
        AlertFactory alertFactory = AlertFactory.getInstance();
        Alert confirmationAlert = alertFactory.getConfirmationAlert("Confirm Action",
            "Item Removal Confirmation",
            "Do you want to remove this " + item.toLowerCase()
                + " from the list of inventory:");
        // Show the alert and wait for user action.
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        result.filter(
                response -> response.getButtonData() == ButtonBar.ButtonData.YES)
            .ifPresent(response -> {
              InGameLayout.getInstance().getLeftVbox().getChildren().remove(itemNode);
              player.removeFromInventory(item.toLowerCase());
            });
      });
    }
  }

  /**
   * Tries to save the current state of the game.
   */
  public void tryToSaveGame() throws IllegalArgumentException {
    try {
      deleteOldPassages();
      saveGame();
    } catch (IllegalStateException e) {
      Alert alert = AlertFactory.getInstance()
          .getConfirmationAlert(ERROR, "Error while saving the story",
              "In order"
                  + "to save a story the system delete the old passages you went through. At the "
                  + "moment "
                  + "this can't be done due the need of one of the old passages to be in the story."
                  + "Please try making some more progress and then save again."
                  + "click Yes if you wish to continue any way ");
      alert.showAndWait();
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.YES) {
        saveGame();
      }
    }

  }

  /**
   * Deletes old passages from the passage history.
   */
  public void deleteOldPassages() {
    ListIterator<Passage> iterator =
        passageHistory.listIterator(passageHistory.size()); // starts at the end
    while (iterator.hasPrevious()) {
      Passage passage = iterator.previous();
      if (!passage.equals(currentPassage)) {
        story.removePassage(story.getLinks().get(passage.getTitle()));
        iterator.remove();
      }
    }
  }

  /**
   * Saves the current game state.
   */
  private void saveGame() {
    Story storyToSave = new Story(story.getTitle(), getCurrentPassage());
    story.getPassages().forEach(storyToSave::addPassage);
    story = storyToSave;
    try {
      String userHome = System.getProperty("user.home");
      String destinationPath = userHome + "/Riddlebound rampage/saved_stories/";
      String playerDestinationPath = userHome + "/Riddlebound rampage/players/";
      String goalsDestinationPath = userHome + "/Riddlebound rampage/goals/";
      if (!new File(playerDestinationPath).exists()) {
        new File(playerDestinationPath).mkdirs();
      }
      if (!new File(goalsDestinationPath).exists()) {
        new File(goalsDestinationPath).mkdirs();
      }
      if (!new File(destinationPath).exists()) {
        new File(destinationPath).mkdirs();
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
      String currentDateTime =
          ZonedDateTime.now(Clock.system(TimeZone.getDefault().toZoneId())).format(formatter);
      StoryFileHandler.getInstance().saveStoryToFile(
          destinationPath + player.getName() + "," + story.getTitle()
              + "," + currentDateTime + ".paths", storyToSave);
      PlayerFileHandler.getInstance()
          .savePlayer(player,
              playerDestinationPath + player.getName() + ","
                  + story.getTitle()
                  + ","
                  + currentDateTime + ".player");
      GoalsSavingHandler.getInstance().serializeAndSaveGoalList(game
              .getGoals(),
          goalsDestinationPath + player.getName() + ","
              + story.getTitle() + "," + currentDateTime + ".goals");
    } catch (IOException io) {
      AlertFactory.getInstance().getErrorAlert(ERROR, "Problem with saving",
          "There was an error with saving the Game").showAndWait();
    }
  }
}



