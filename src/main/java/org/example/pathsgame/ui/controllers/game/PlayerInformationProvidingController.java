package org.example.pathsgame.ui.controllers.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.actions.Action;
import org.example.pathsgame.entities.actions.InventoryAction;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;
import org.example.pathsgame.entities.goals.Goal;
import org.example.pathsgame.entities.goals.GoalsSavingHandler;
import org.example.pathsgame.entities.goals.GoldGoal;
import org.example.pathsgame.entities.goals.HealthGoal;
import org.example.pathsgame.entities.goals.InventoryGoal;
import org.example.pathsgame.entities.goals.ScoreGoal;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.ui.views.game.beforegameview.PlayerInformationProvidingView;
import org.example.pathsgame.utility.SceneHandler;

/**
 * The PlayerInformationProvidingController class is responsible for handling user interactions on
 * the player information providing page. It processes player information, manages goals, and
 * updates the corresponding view.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class PlayerInformationProvidingController {
  private final PlayerInformationProvidingView view;
  private static PlayerInformationProvidingController playerInformationProvidingControllerInstance;
  private static final String PLAYER_CONSTANT_NAME = "playerRegistration";

  /**
   * Private constructor for PlayerInformationProvidingController.
   * Initializes the view for this controller.
   */
  private PlayerInformationProvidingController() {
    view = PlayerInformationProvidingView.getInstance();
  }

  /**
   * Singleton pattern getInstance method. Returns the instance of this class, creating it if
   * necessary.
   *
   * @return The instance of this class.
   */
  public static synchronized PlayerInformationProvidingController getInstance() {
    if (playerInformationProvidingControllerInstance == null) {
      playerInformationProvidingControllerInstance = new PlayerInformationProvidingController();
    }
    return playerInformationProvidingControllerInstance;
  }

  /**
   * Sets the player information panel on the stage.
   * Includes event handlers for the view components and displays the view on the stage.
   *
   * @param stage The stage on which to display the view.
   * @param story The Story context for which the player information is being provided.
   */
  public void getPlayerInformationPanel(Stage stage, Story story) {

    view.getInformationSetUp();
    view.getEnabledGoalsCheckBox().selectedProperty().addListener(
        (observable, oldValue, newValue) -> disableGoals(!newValue));
    disableGoals(true);

    BorderPane informationBox = view.getInformationPane(getInventoryActions(story), stage);
    SceneHandler.getInstance().setScene(new Scene(informationBox),
        PLAYER_CONSTANT_NAME);
    stage.setScene(SceneHandler.getInstance().getScenes().get(PLAYER_CONSTANT_NAME));
    onNextButtonClicked(stage);
    onBackButtonClicked(stage);
  }

  /**
   * sets the on action for the next button when clicked, and sets the stage where the next scene.
   *
   * @param stage the stage where the next scene will appear
   */
  private void onNextButtonClicked(Stage stage) {

    view.getNextButton().setOnAction(e -> {
      PathGameApplication.getButtonSound().play();
      try {
        getStoryGoals();
        CharacterChoosePageController.getInstance()
            .getCharacterChoosePage(stage, view.getPlayerNameText().getText(),
                view.getPlayerHealthText().getText(),
                view.getPlayerGoldText().getText());
      } catch (IllegalArgumentException exception) {
        AlertFactory.getInstance().getErrorAlert("Error", "Not Valid Goals",
                "Something went wrong when creating the goal, check your input and try again.")
            .showAndWait();
      }

    });
  }

  /**
   * Sets the on go back button clicked action, to set the previous scene in the stage.
   *
   * @param stage the stage where the previous scene will appear
   */
  private void onBackButtonClicked(Stage stage) {

    view.getBackButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      stage.setScene(SceneHandler.getInstance().getScenes().get("homePage"));
    });
  }

  /**
   * Collect all inventory actions in the story context.
   *
   * @param story The story context in which to look for inventory actions
   * @return A set of all inventory actions.
   */
  private static Set<String> getInventoryActions(Story story) {
    Set<String> inventoryActions = new HashSet<>();
    for (Passage passage : story.getPassages()) {
      for (Link link : passage.getLinks()) {
        if (link.getActions() != null) {
          for (Action action : link.getActions()) {
            if (action instanceof InventoryAction) {
              inventoryActions.add(action.toString().replace("Inventory = ", ""));
            }
          }
        }
      }
    }
    return inventoryActions;
  }

  /**
   * Collects all goals from the view, creating Goal objects based on the inputs.
   *
   * @return A list of all goals collected from the view.
   */
  public List<Goal> getStoryGoals() {
    List<Goal> goals = new ArrayList<>();
    if (view.getEnabledGoalsCheckBox().isSelected()) {
      if (view.getMaxGoldText().getText() != null && !view.getMaxGoldText().getText().isEmpty()) {
        GoldGoal goldGoal = new GoldGoal(Integer.parseInt(view.getMaxGoldText().getText()));
        goals.add(goldGoal);
      }
      if (view.getMaxHealthText().getText() != null && !view.getMaxHealthText().getText()
          .isEmpty()) {
        HealthGoal healthGoal =
            new HealthGoal(Integer.parseInt(view.getMaxHealthText().getText()));
        goals.add(healthGoal);
      }
      if (view.getMaxScoreText().getText() != null && !view.getMaxScoreText().getText()
          .isEmpty()) {
        ScoreGoal scoreGoal =
            new ScoreGoal(Integer.parseInt(view.getMaxScoreText().getText()));
        goals.add(scoreGoal);
      }
      if (view.getPlayerInventoryText().getCheckModel().getCheckedItems() != null
          && !view.getPlayerInventoryText().getCheckModel().getCheckedItems().isEmpty()) {
        ObservableList<String> selectedItems =
            view.getPlayerInventoryText().getCheckModel().getCheckedItems();
        ArrayList<String> serializedCheckedItems = new ArrayList<>(selectedItems);

        InventoryGoal inventoryGoals = new InventoryGoal(serializedCheckedItems);
        goals.add(inventoryGoals);
      }
    }
    return goals;
  }

  /**
   * Disables or enables the goals input fields on the view.
   *
   * @param disable true to disable the input fields, false to enable them.
   */
  public void disableGoals(boolean disable) {
    view.getMaxHealthText().setDisable(disable);
    view.getMaxGoldText().setDisable(disable);
    view.getMaxScoreText().setDisable(disable);
    view.getPlayerInventoryText().setDisable(disable);
  }

}
