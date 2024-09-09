package org.example.pathsgame.ui.views.game.ingamesettingview;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.utility.SceneHandler;

/**
 * The StoryInformationView class provides a user interface to display information about a story.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class StoryInformationView {
  private static StoryInformationView instance;

  /**
   * Private constructor for the SStoryInformationView class to prevent instantiation.
   * Only getInstance method can be used to get the single instance of this class.
   */
  private StoryInformationView() {
  }

  /**
   * The method is used to get the instance of the class.
   * If the instance does not exist, a new one is created.
   *
   * @return The single instance of the StoryInformationView.
   */
  public static synchronized StoryInformationView getInstance() {
    if (instance == null) {
      instance = new StoryInformationView();
    }
    return instance;
  }

  /**
   * The method creates a scene that contains all the story information like story title, file name
   * , file location, and a table view of the dead links in the story.
   *
   * @param storyTitle   The title of the story.
   * @param fileName     The name of the file.
   * @param fileLocation The location of the file.
   * @param deadLinks    The observable list of dead links in the story.
   * @param goBackButton The button to navigate back.
   * @return A scene containing the story information.
   */
  public Scene getStoryInformationPanel(String storyTitle, String fileName, String fileLocation,
                                        ObservableList<Link> deadLinks, Button goBackButton) {
    AnchorPane infoPane = new AnchorPane();
    GridPane informationBox = new GridPane();
    infoPane.getChildren().clear();
    informationBox.setHgap(10);
    informationBox.setVgap(10);
    Label title = new Label("Story Title: ");
    Label file = new Label("File Name: ");
    Label location = new Label("File Location: ");
    Text storyTitleText = new Text(storyTitle);
    Text fileLocationText = new Text(fileLocation);
    Text fileNameText = new Text(fileName);
    informationBox.add(goBackButton, 0, 0);
    informationBox.add(title, 0, 1);
    informationBox.add(storyTitleText, 1, 1);
    informationBox.add(file, 0, 2);
    informationBox.add(fileNameText, 1, 2);
    informationBox.add(location, 0, 3);
    informationBox.add(fileLocationText, 1, 3);
    TableView<Link> deadLinksTableView = new TableView<>();
    deadLinksTableView.setItems(deadLinks);
    TableColumn<Link, String> linkText = new TableColumn<>("Link text");
    TableColumn<Link, String> linkReference = new TableColumn<>("Link reference");
    deadLinksTableView.getColumns().setAll(linkText, linkReference);
    linkText.setCellValueFactory(new PropertyValueFactory<>("text"));
    linkReference.setCellValueFactory(new PropertyValueFactory<>("reference"));
    infoPane.getChildren().addAll(informationBox, deadLinksTableView);
    AnchorPane.setTopAnchor(informationBox, 10.0);
    AnchorPane.setLeftAnchor(informationBox, 10.0);
    AnchorPane.setTopAnchor(deadLinksTableView, 170.0);
    AnchorPane.setLeftAnchor(deadLinksTableView, 10.0);
    AnchorPane.setRightAnchor(deadLinksTableView, 10.0);
    AnchorPane.setBottomAnchor(deadLinksTableView, 10.0);
    SceneHandler.getInstance().setScene(new Scene(infoPane, 800, 800),
        "storyInformation");
    return SceneHandler.getInstance().getScenes().get("storyInformation");
  }

}
