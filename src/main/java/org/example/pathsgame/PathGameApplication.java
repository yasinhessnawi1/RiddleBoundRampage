package org.example.pathsgame;

import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.pathsgame.ui.controllers.homepage.HomePageController;
import org.example.pathsgame.ui.views.homepage.HomePageView;
import org.example.pathsgame.utility.NavigationBetweenSceneData;
import org.example.pathsgame.utility.SceneHandler;

/**
 * The PathGameApplication class is the main entry point for the application.
 * It initializes and sets up the main stage and scenes of the application
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class PathGameApplication extends Application {

  /**
   * Default constructor for the PathGameApplication class.
   */
  public PathGameApplication() {
    //An empty constructor.
  }

  /**
   * The main entry point for all JavaFX applications.
   * The start method is called after the init method has returned, and after the system is ready
   * for the application to begin running.
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  public void start(Stage stage) {

    // Initialize the video page
    URL videoUrl = getClass().getResource("/videos/welcome.mp4");
    Media video = new Media(videoUrl.toString());
    MediaPlayer mediaPlayer = new MediaPlayer(video);
    MediaView mediaView = new MediaView(mediaPlayer);
    mediaView.setPreserveRatio(true);
    mediaPlayer.setOnReady(() -> {

      mediaPlayer.setRate(1);
      mediaView.fitHeightProperty().bind(stage.heightProperty());
      mediaView.fitWidthProperty().bind(stage.widthProperty());
      StackPane welcomeScene = new StackPane();
      welcomeScene.getChildren().addAll(mediaView);
      SceneHandler.getInstance().setScene(new Scene(welcomeScene), "welcomePage");
      stage.setScene(SceneHandler.getInstance().getScenes().get("welcomePage"));
      mediaPlayer.play();
    });

    // Switch to the home page after the video ends
    mediaPlayer.setOnEndOfMedia(() -> {
      Pane root = HomePageController.getInstance().getHomePageView(stage);
      String homePageString = "homePage";
      SceneHandler.getInstance().setScene(new Scene(root), homePageString);
      String cssPath = getClass()
          .getResource("/style/homePageComboBoxStyle.css").toExternalForm();
      SceneHandler.getInstance().getScenes().get(homePageString).getStylesheets().add(cssPath);
      HomePageView homePageView = HomePageView.getInstance();
      NavigationBetweenSceneData.getInstance().push(homePageString);
      NavigationBetweenSceneData.getInstance().storeData(homePageString, homePageView);
      stage.setScene(SceneHandler.getInstance().getScenes().get(homePageString));
    });

	  stage.setWidth(Screen.getPrimary().getBounds().getWidth());
	  stage.setHeight(Screen.getPrimary().getBounds().getHeight());
    stage.show();
  }

  /**
   * The launch method is a convenient static method to the application class that launches a
   * standalone application. It should be called within the main method.
   *
   * @param args The command line arguments passed to the application.
   *             An application may get these parameters using the Application.getParameters method.
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * The getButtonSound method is a helper method to get an AudioClip for a button click sound.
   *
   * @return An AudioClip that can be played when a button is clicked.
   */
  public static AudioClip getButtonSound() {
    return new AudioClip(PathGameApplication.class.getResource("/music/button.wav")
        .toString());
  }
}
