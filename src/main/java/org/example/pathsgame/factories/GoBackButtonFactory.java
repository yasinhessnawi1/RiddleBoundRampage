package org.example.pathsgame.factories;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.utility.NavigationBetweenSceneData;

/**
 * GoBackButtonFactory is a singleton factory class that creates a "Go Back" button for navigation
 * in the game. This class uses the NavigationBetweenSceneData to keep track of the navigation
 * history to navigate back.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GoBackButtonFactory {
  private Button goBackButton;
  private final NavigationBetweenSceneData navigationData;
  private final Stage stage;
  private final HashMap<String, Scene> scenes;
  private static GoBackButtonFactory instance;

  /**
   * Private constructor for GoBackButtonFactory, initializes the stage, navigationData, and scenes.
   *
   * @param stage          The stage on which the scene are displayed.
   * @param navigationData The data structure keeping track of the navigation history.
   * @param scenes         A map of scenes names to Scene objects.
   */
  private GoBackButtonFactory(Stage stage, NavigationBetweenSceneData navigationData,
                              HashMap<String, Scene> scenes) {
    this.stage = stage;
    this.navigationData = navigationData;
    this.scenes = scenes;
    this.goBackButton = createButton();
  }

  /**
   * Creates a new instance of the GoBackButtonFactory or returns an existing one.
   *
   * @param stage          The stage on which the scene are displayed.
   * @param navigationData The data structure keeping track of the navigation history.
   * @param scenes         A map of scenes names to Scene objects.
   * @return The single instance of the GoBackButtonFactory.
   */
  public static synchronized GoBackButtonFactory
                            getInstance(Stage stage,
                                        NavigationBetweenSceneData navigationData,
                                        Map<String, Scene> scenes) {
    if (instance == null) {
      instance = new GoBackButtonFactory(stage, navigationData, (HashMap<String, Scene>) scenes);
    }
    return instance;
  }

  /**
   * Creates a "Go Back" button with a customized look and an action listener that triggers
   * navigation back.
   *
   * @return A "Go Back" button.
   */
  private Button createButton() {
    Button button = new Button();
        ButtonCustomizer.getInstance().getEffect(button,
            "back.png", 190, 140);

    button.setOnAction(event -> goBack());
    return button;
  }

  /**
   * Returns a "Go Back" button. If the button does not exist, it is created first.
   *
   * @return A "Go Back" button
   */
  public Button getButton() {
    goBackButton = createButton();
    return goBackButton;
  }

  /**
   * Triggers the action to go back to the previous scene, if any, in the navigation history.
   */
  private void goBack() {
    if (!navigationData.getNavigationHistory().isEmpty()) {
      String previousScene = navigationData.pop();
      stage.setScene(scenes.get(previousScene));
    }
  }
}


