package org.example.pathsgame.entities.story;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.example.pathsgame.entities.actions.GoldAction;
import org.example.pathsgame.entities.actions.HealthAction;
import org.example.pathsgame.entities.actions.InventoryAction;
import org.example.pathsgame.entities.actions.ScoreAction;
import org.example.pathsgame.entities.events.Link;
import org.example.pathsgame.entities.events.Passage;

/**
 * This class handles reading and writing Story objects from a file.
 * The file follows a simple custom format, where passages are separated by "::" and links are
 * specified as title and reference separated by parentheses. Links may have
 * one or more action associated with them.
 *
 * @author Rami and Yasin.
 * @version 0.1 (12.03.23)
 */
public class StoryFileHandler {

  // This constant specified the file extension used for the story file.
  private static final String FILE_PATH = ".paths";
  private static StoryFileHandler instance;

  /**
   * A private constructor prevents the instantiation of the class directly.
   */
  private StoryFileHandler() {
  }

  /**
   * A synchronized method that returns a single instance of this class StoryFileHandler.
   * If the instance is null, a new instance is created and returned.
   *
   * @return A single instance of the StoryFileHandler class
   */
  public static synchronized StoryFileHandler getInstance() {
    if (instance == null) {
      instance = new StoryFileHandler();
    }
    return instance;
  }

  /**
   * Saves the given story to a file with the specified file path.
   * If the file path does not end with the ".path" extension, it is added automatically.
   *
   * @param filePath the path of the file to write to.
   * @param story    the story to save.
   * @return true if story was successfully saved, false otherwise.
   * @throws IOException if an I/O error occurs while writing to the file.
   */
  public boolean saveStoryToFile(String filePath, Story story) throws IOException {
    // Check if the file path ends with ".path", add it if it doesn't.
    String[] fileParts = filePath.split("\\.");
    if (Arrays.stream(fileParts).count() < 2) {
      filePath += FILE_PATH;
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      // Write the story title to the file.
      writer.write(story.getTitle() + "\n");
      writer.newLine();

      // Write the opening passage to the file.
      writer.write("::" + story.getOpeningPassage().getTitle() + "\n");
      writer.write(story.getOpeningPassage().getContent() + "\n");

      // Write each link in the opening passage to the file.
      for (Link link : story.getOpeningPassage().getLinks()) {
        writer.write(link.toString() + "\n");
      }
      writer.newLine();

      // Write each passage (other than the opening passage) to the file.
      for (Passage passage : story.getPassages()) {
        if (passage.equals(story.getOpeningPassage())) {
          continue;
        }
        writer.write("::" + passage.getTitle() + "\n");
        writer.write(passage.getContent() + "\n");

        // Write each link in the passage to the file.
        for (Link link : passage.getLinks()) {
          writer.write(link.toString() + "\n");
        }
        writer.newLine();
      }
      return true;
    } catch (IOException e) {
      throw new IllegalArgumentException("There was an error while saving the story");
    }
  }

  /**
   * This method reads a passage and its links from the provided scanner. if a passage has already
   * been partially read, it is added to the list of passages before a new one is read. Passages
   * are identified by lines starting with "::".
   *
   * @param passages       A list to which read passages are added.
   * @param scanner        A scanner from which lines of story file are read.
   * @param line           The current line being read.
   * @param passageContent A StringBuilder for building the content of a passage.
   * @return The passage that was read.
   */
  private static Passage getCurrentPassage(Scanner scanner,
                                           String line, StringBuilder passageContent,
                                           List<Passage> passages) {
    // Read the title and content of the passage.
    String passageTitle = line.substring(2);
    // Read the content of the passage until a link or new passage is found.
    if (!line.startsWith("::")) {
      throw new InputMismatchException("The passage must begin with ::");
    }
    passageContent.setLength(0);
    while (scanner.hasNextLine()) {
      line = scanner.nextLine().trim();
      if (!line.startsWith("[") && !line.startsWith("::") && !line.isEmpty() && !line.isBlank()
          && !line.endsWith(")") && !line.contains("]") &&
          !line.contains("(")) {
        passageContent.append(line).append("\n");
      } else {
        break;
      }
    }
    Passage currentPassage = new Passage(passageTitle, passageContent.toString().trim());

    // Handle the links if the line starts with "[".
    if (line.startsWith("[") && line.endsWith(")") && line.contains("]") && line.contains("(")) {
      line = getLinksInPassage(currentPassage, scanner, line);
    } else if (!line.startsWith("[") && line.endsWith(")") && line.contains("]") &&
        line.contains("(")) {
      throw new InputMismatchException("Invalid link line" + line);
    }
    passages.add(currentPassage);

    // Handle the links if the line starts with "::".
    if (line != null && line.startsWith("::")) {
      getCurrentPassage(scanner, line, passageContent, passages);
    } else if (!line.startsWith("::") && !line.isEmpty() && !line.isBlank()) {
      throw new InputMismatchException("Invalid line: " + line);
    }
    return currentPassage;
  }

  /**
   * This method reads the links of a passage from the provided scanner. Links are identified by
   * lines starting with "[".
   *
   * @param currentPassage The current passage being read.
   * @param scanner        A scanner from which lines of a story file are read.
   * @param line           The current line being read.
   */
  private static String getLinksInPassage(Passage currentPassage, Scanner scanner, String line) {

    while (line != null && line.startsWith("[") && currentPassage != null) {
      currentPassage.addLink(parseLink(line));
      if (scanner.hasNextLine()) {
        line = scanner.nextLine().trim();
      } else {
        line = null;
      }
    }
    return line;
  }

  /**
   * Reads a story from a file with the specified file path.
   * If the file path does not end with the ".path" extension, it is added automatically.
   *
   * @param filePath the path of the file to read from.
   * @return the story read from the file, or null if an error occurred while reading the file.
   * @throws IOException if an I/O error occurs while writing to the file.
   */
  public Story readStoryFromFile(String filePath) throws IOException {
    // List of known extensions.
    List<String> knownExtensions = Arrays.asList(FILE_PATH, ".txt", ".csv", ".json");

    // Check if file path ends with a known extension.
    String finalFilePath = filePath;
    boolean hasKnownExtension = knownExtensions.stream().anyMatch(finalFilePath::endsWith);

    // If the file path doesn't have a known extension, add the default extension ".paths".
    if (!hasKnownExtension) {
      filePath += FILE_PATH;
    }

    // Initialize variables for the story, current passage being read, and list of passages.
    Passage currentPassage = null;
    List<Passage> passages = new ArrayList<>();

    try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
      // Read the first line of the file as the title of the story.
      String storyTitle = scanner.nextLine().trim();

      String line;
      StringBuilder passageContent = new StringBuilder();
      while (scanner.hasNextLine()) {
        line = scanner.nextLine().trim();

        //
        if (line.startsWith("::") && !line.isEmpty()) {
          if (currentPassage != null) {
            passages.add(currentPassage);
          }
          currentPassage = getCurrentPassage(scanner, line, passageContent, passages);
        } else if (!line.startsWith("::") && !line.isEmpty()) {
          throw new IllegalStateException("Passage title not found");
        }
      }
      // Add the last passage to the list.
      if (currentPassage != null) {
        passages.add(currentPassage);
      }
      return constructStory(passages, storyTitle);
    } catch (IOException e) {
      // Print the stack trace if an error occurs.
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * This method constructs a Story object from a list of passages and a title. The first passage
   * in the list is used as the opening passage of the story, and the rest are added as subsequent
   * passages.
   *
   * @param passages   A list of passages to add to the story.
   * @param storyTitle The title of the story.
   * @return The constructed Story.
   */
  private Story constructStory(List<Passage> passages, String storyTitle) {
    Story story;
    story = new Story(storyTitle, passages.get(0));
    for (int i = 1; i < passages.size(); i++) {
      story.addPassage(passages.get(i));
    }
    // Return the story read from the file.
    return story;
  }


  /**
   * Parses a link from the specified link string.
   *
   * @param linkString the string representing the link.
   * @return the link parsed from string.
   */
  private static Link parseLink(String linkString) {
    // Split the link string by opening parentheses and store the parts in an array.
    String[] parts = linkString.split("\\(");

    // Get the title of the link from the first part and remove any leading or trailing whitespace.
    String titleOfLink = parts[0].replaceAll("[\\[\\]]", "").trim();

    // Initialize a variable for the reference of the link and set it to an empty string.
    String referenceOfLink = "";

    // If the link has any reference,
    // extract it from the second part and remove any trailing parenthesis.
    if (parts.length > 1) {
      referenceOfLink = parts[1].substring(0, parts[1].length() - 1);
    }

    // Create a new Link object with the title and reference extracted from the link string.
    Link link = new Link(titleOfLink, referenceOfLink);

    // If the link contains any action, add them to the link.
    if (parts.length >= 2) {
      for (int i = 2; i < parts.length; i++) {

        // Split each action by equals sign and store the parts in the array.
        String[] actionParts = parts[i].split("=");

        // Get the action type from the first part
        // and remove any leading or trailing whitespace.
        String actionType = actionParts[0].trim();

        // Get the action type from the second part
        // and remove any trailing parenthesis or whitespace.
        String actionValue = actionParts[1].substring(0, actionParts[1].length() - 1).trim();

        // Add any actions associated with the link, depending on the action type.
        switch (actionType) {
          case "Gold":
            link.addAction(new GoldAction(Integer.parseInt(actionValue)));
            break;
          case "Health":
            link.addAction(new HealthAction(Integer.parseInt(actionValue)));
            break;
          case "Inventory":
            link.addAction(new InventoryAction(actionValue));
            break;
          case "Score":
            link.addAction(new ScoreAction(Integer.parseInt(actionValue)));
            break;
          default:
            // Handle unsupported action type.
        }
      }
    }
    // Return the parsed link object.
    return link;
  }
}



