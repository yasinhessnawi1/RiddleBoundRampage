package org.example.pathsgame.ui.controllers.game;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.game.Game;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.factories.PlayerInformationHolder;
import org.example.pathsgame.ui.controllers.homepage.HomePageController;
import org.example.pathsgame.ui.views.game.ingame.InGameLayout;
import org.example.pathsgame.ui.views.game.ingamesettingview.GameSettingView;
import org.example.pathsgame.ui.views.homepage.HomePageView;
import org.example.pathsgame.utility.SceneHandler;

/**
 * Handles the setting buttons actions.
 *
 * @author yasin
 * @author rami
 * verion 0.1
 */
public class GameSettingController {
  private static GameSettingController instance;

  private final GameSettingView view;

  private GameSettingController() {

    view = GameSettingView.getInstance();

  }

  /**
   * singleton to unsure that only one object of theis class is made.
   *
   * @return the object of the class
   */
  public static synchronized GameSettingController getInstance() {

    if (instance == null) {
      instance = new GameSettingController();

    }
    return instance;
  }

  /**
   * handels the click action of the help button
   */
  public void onHelpButtonClicked() {

    view.getHelp().setOnAction(e -> {
      HomePageController.getInstance().onHelpButtonClicked(view.getHelp());
    });
  }

  /**
   * Method called when the "Save Progress" button is clicked.
   * It handles the event by saving the game progress and displaying a success or error message.
   */
  public void onSaveProgressClicked() {

    view.getSaveProgress().setOnAction(e -> {
      PathGameApplication.getButtonSound().play();
      try {
        GameController.getInstance().tryToSaveGame();
        AlertFactory.getInstance()
            .getInformationAlert("Information", "Game Saved",
                "Game was  Saved Successfully ")
            .showAndWait();
      } catch (IllegalArgumentException illegalArgumentException) {
        AlertFactory.getInstance().getErrorAlert("Error", "Error saving game",
            "Error saving game"
                + "this can be because there is passages cannot be deleted or error creating the "
                + "new "
                + "story file").showAndWait();
        illegalArgumentException.printStackTrace();
      }
    });
  }

  /**
   * Method is called when the "Exit Game" button is clicked.
   * It asks the user for confirmation before exiting the game.
   */
  public void onExitGameClicked(Button backToHome) {

    backToHome.setOnAction(e -> {
      PathGameApplication.getButtonSound().play();
      AlertFactory.getInstance().getConfirmationAlert("Exit Game",
              "Are you sure you want to exit"
                  + " the game?", "All unsaved progress will be lost.").showAndWait()
          .ifPresent(response -> {
            if (response == ButtonType.YES) {
              PathGameApplication.getButtonSound().play();
              HomePageView.getInstance().getStage()
                  .setScene(SceneHandler.getInstance().getScenes().get("homePage"));
              view.addMusicButton();
              HomePageController.getInstance().addMusicButton();
              InGameLayout.getInstance().getLeftVbox().getChildren().clear();
              if (view.getSettingDialog() != null) {
                view.getSettingDialog().close();
              }
            }
          });
    });

  }

  /**
   * The method is called when the "Change Game Color" button is clicked.
   * It changes the color of the using the GameColorChangerController.
   */
  public void onChangeColorClicked() {

    view.getChangeGameColor().setOnAction(actionEvent -> {
      PathGameApplication.getButtonSound().play();
      GameColorChangerController.getInstance().changeColor();
    });
  }

  /**
   * Defines the action to take when the "Restart Game" button is clicked.
   */
  public void onRestartGameClicked(Button restartButton) {

    restartButton.setOnAction(e -> {
      PathGameApplication.getButtonSound().play();
      PlayerInformationProvidingController.getInstance().getStoryGoals();
      Game gameRestarted = CharacterChoosePageController
          .getInstance().getGame();
      HomePageView.getInstance().getStage()
          .setScene(GameController.getInstance()
              .getIngameScene(gameRestarted.begin()));
      InGameLayout.getInstance().getLeftVbox().getChildren().clear();
      PlayerInformationHolder.getInstance()
          .setPlayer(CharacterChoosePageController.getInstance().getPlayer());
      PlayerInformationHolder.getInstance()
          .updatePlayerInformationWhenRestarting(CharacterChoosePageController.getInstance()
              .getPlayer());
      CharacterChoosePageController.getInstance().getPlayer().getInventory().clear();
      InGameLayout.getInstance().getLeftVbox().getChildren().clear();
      if (view.getSettingDialog().isShowing() && view.getSettingDialog() != null) {
        view.getSettingDialog().close();
      }
    });
  }

  /**
   * This method to display all broken links in the given story.
   */
  public void showStoryInformation() {

    view.getStoryInformation().setOnAction(actionEvent -> {
      PathGameApplication.getButtonSound().play();
      StoryInformationController.getInstance().showStoryInformation(new Stage());
    });
  }

}
