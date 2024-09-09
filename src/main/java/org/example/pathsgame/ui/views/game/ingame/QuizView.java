package org.example.pathsgame.ui.views.game.ingame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Singleton class that sets up the Quiz view in the application's UI.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class QuizView {
  private static QuizView quizViewInstance;
  private Button answer1Text;
  private Button answer2Text;

  /**
   * Private constructor for singleton pattern.
   */
  private QuizView() {
  }

  /**
   * Returns the instance of the QuizView.
   *
   * @return The singleton instance of the QuizView.
   */
  public static synchronized QuizView getInstance() {
    if (quizViewInstance == null) {
      quizViewInstance = new QuizView();
    }
    return quizViewInstance;
  }

  /**
   * Gets the button displaying the first answer.
   *
   * @return The button for the first answer.
   */
  public Button getAnswer1Text() {
    return answer1Text;
  }

  /**
   * Gets the button displaying the second answer.
   *
   * @return The button for the second answer.
   */
  public Button getAnswer2Text() {
    return answer2Text;
  }

  /**
   * Sets up the view for the quiz question and its two possible answers.
   * The question is split into two halves which are displayed of separate lines.
   * The answers are presented as buttons.
   *
   * @param question The quiz question.
   * @param answer1 The first possible answer.
   * @param answer2 The second possible answer.
   * @return A VBox containing the set up quiz question and answer buttons.
   */
  public VBox getQuizSetUp(String question, String answer1, String answer2) {
    String firstHalf = question.substring(0, question.length() / 2);
    String secondHalf = question.substring(question.length() / 2);
    Label questionText = new Label(firstHalf + "\n" + secondHalf);
    answer1Text = new Button(answer1);
    answer2Text = new Button(answer2);
    questionText.setId("questionLabel");
    VBox quizBox = new VBox();
    quizBox.setSpacing(10);
    quizBox.setAlignment(Pos.CENTER);
    HBox answerBox = new HBox();
    answerBox.setSpacing(10);
    answerBox.setAlignment(Pos.CENTER);
    answerBox.getChildren().addAll(answer1Text, answer2Text);
    quizBox.getChildren().addAll(questionText, answerBox);
    return quizBox;
  }
}
