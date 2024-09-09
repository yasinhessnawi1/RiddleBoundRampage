package org.example.pathsgame.entities.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Singleton class that handles reading quiz data from a file.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class QuizFileHandler {
  private static QuizFileHandler instance;

  /**
   * Private constructor for singleton pattern.
   */
  private QuizFileHandler() {
  }

  /**
   * Returns the instance of the QuizFileHandler.
   *
   * @return The singleton instance of the QuizFileHandler.
   */
  public static synchronized QuizFileHandler getInstance() {
    if (instance == null) {
      instance = new QuizFileHandler();
    }
    return instance;
  }

  /**
   * Reads quiz data from the file located at the provided file path.
   * Each quiz in the file should be represented by four lines: the question, the correct answer,
   * the wrong answer, and an empty line. If an of these are missing, this method stops reading
   * the file and returns the quizzes it has read so far.
   *
   * @param filePath the path to the file to read quiz data from.
   * @return A list of quizzes read from the file.
   * @throws IOException If there's an issue reading from the file.
   */
  public List<Quiz> readQuizFromFile(String filePath) throws IOException {

    List<Quiz> quizList = new ArrayList<>();

    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
      assert inputStream != null;
      try (InputStreamReader streamReader = new InputStreamReader(inputStream);
           BufferedReader reader = new BufferedReader(streamReader)) {
        while (true) {
          String quizQuestion = reader.readLine();
          String quizAnswer = reader.readLine();
          String wrongAnswer = reader.readLine();
          String emptyLine = reader.readLine();
          if (quizQuestion == null || quizAnswer == null || wrongAnswer == null
              || emptyLine == null) {
            break;
          }
          quizQuestion = quizQuestion.trim();
          quizAnswer = quizAnswer.trim();
          wrongAnswer = wrongAnswer.trim();

          Quiz quiz = new Quiz(quizQuestion, quizAnswer, wrongAnswer);
          quizList.add(quiz);
        }
        return quizList;
      }
    } catch (IOException | NoSuchElementException e) {
      throw new IOException("Error reading quiz file.");
    }
  }

}
