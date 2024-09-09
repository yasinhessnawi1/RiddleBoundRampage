package org.example.pathsgame.ui.controllers.game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.factories.GoBackButtonFactory;
import org.example.pathsgame.ui.controllers.homepage.HomePageController;
import org.example.pathsgame.ui.views.game.ingamesettingview.StoryInformationView;
import org.example.pathsgame.utility.NavigationBetweenSceneData;
import org.example.pathsgame.utility.SceneHandler;

/**
 * The StoryInformationController class is responsible for controlling user interactions on the
 * story information view. It provides information about the selected story and updates the
 * corresponding view.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class StoryInformationController {
  private static StoryInformationController instance;
  private final Story story;
  private final StoryInformationView view;

  /**
   * Private constructor for StoryInformationController.
   * Initializes the view for this controller and fetches the selected story.
   */
  private StoryInformationController() {
    view = StoryInformationView.getInstance();
    story = CharacterChoosePageController.getInstance().getStory();
  }

  /**
   * Singleton pattern getInstance method. Returns the instance of this class, creating it if
   * necessary.
   *
   * @return the instance of this class.
   */
  public static synchronized StoryInformationController getInstance() {
    if (instance == null) {
      instance = new StoryInformationController();
    }
    return instance;
  }

  /**
   * Populates and displays the story information view on the stage.
   * Includes event handlers for the view components and displays the view on the stage.
   *
   * @param stage The stage on which to display the view.
   */
  public void showStoryInformation(Stage stage) {
    Button goBackButton =
        GoBackButtonFactory.getInstance(stage, NavigationBetweenSceneData.getInstance(),
            SceneHandler.getInstance().getScenes()).getButton();
    goBackButton.setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      stage.close();
    });
    ObservableList<Link> deadLinks = FXCollections.observableArrayList(story.getBrokenLinks());
    stage.setScene(view.getStoryInformationPanel(story.getTitle(), HomePageController.getInstance()
            .getFileName(),
        HomePageController.getInstance().getFilePath(), deadLinks, goBackButton));
    stage.show();
  }

}
