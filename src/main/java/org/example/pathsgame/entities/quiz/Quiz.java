package org.example.pathsgame.entities.quiz;

/**
 * The Quiz class represents a single quiz item, which includes a question, a right
 * answer, and a wrong answer.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class Quiz {
  private final String question;
  private final String rightAnswer;
  private final String wrongAnswer;

  /**
   * Initializes a new instance of the Quiz class with the provided question, right answer, and
   * wrong answer.
   *
   * @param question    The quiz question.
   * @param rightAnswer The correct answer for the quiz question.
   * @param wrongAnswer The wrong answer for the quiz question.
   */
  public Quiz(String question, String rightAnswer, String wrongAnswer) {
    this.question = question;
    this.rightAnswer = rightAnswer;
    this.wrongAnswer = wrongAnswer;
  }

  /**
   * Returns the question associated with this Quiz.
   *
   * @return A String representing the quiz question.
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Returns the correct answer for this Quiz.
   *
   * @return A String representing the correct answer for this Quiz.
   */
  public String getRightAnswer() {
    return rightAnswer;
  }

  /**
   * Returns the wrong answer for this Quiz.
   *
   * @return A String representing the wrong answer for this Quiz.
   */
  public String getWrongAnswer() {
    return wrongAnswer;
  }

}
