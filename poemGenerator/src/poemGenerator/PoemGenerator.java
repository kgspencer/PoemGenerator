package poemGenerator;

import java.util.*;
import java.io.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class PoemGenerator extends Application {
  // Name of text file with rules in Backus-Naur form
  public static final String TXTFILE = "language.txt";
  
  private HBox hbox;
  private VBox vbox;
  private Text poem;
  private static List<String> rules;
  private Font titleFont = Font.loadFont(getClass().getResource("/resources/font/Inter/static/Inter-SemiBold.ttf").toExternalForm(), 50);
  private Font bodyFont = Font.loadFont(getClass().getResource("/resources/font/Inter/static/Inter-Medium.ttf").toExternalForm(), 20);

  /**
   * Processes the text file and launches the GUI
   * @param args Mandatory arguments
   * @throws FileNotFoundException Throws if the file does not exist
   */
  public static void main (String[] args) throws FileNotFoundException{
      rules = new ArrayList<String>();
      Scanner input = new Scanner(new File(TXTFILE));
      
      // Processes text file line by line
      while(input.hasNextLine()) {
          String next = input.nextLine().trim();
          if (next.length() > 0) {
              rules.add(next);
          } 
      }
      
      launch(args);
  }
  
  /**
   * Sets the stage with basic elements
   */
  @Override public void start(Stage stage) throws Exception, FileNotFoundException {     
      Group root = new Group();
      Scene scene = new Scene(root, 900, 600, Color.web("#2921AF"));
      hbox = new HBox();
      vbox = new VBox(10);
      
      stage.setTitle("poem generator");
      
      hbox.setLayoutX(30);
      hbox.setLayoutY(20);
      
      Text title = new Text("POEM GENERATOR");
      title.setFont(titleFont);
      title.setFill(Color.WHITE);
      
      Button generateBtn = new Button("new poem");
      generateBtn.setFont(bodyFont);
      generateBtn.setTextFill(Color.web("#2921AF"));
      generateBtn.setStyle("-fx-background-color: #ffffff");
      generateBtn.setOnMouseClicked(this::generatePoem); // on mouse click event, generatePoem
      
      vbox.getChildren().add(title);
      vbox.getChildren().add(generateBtn);
      
      root.getChildren().add(hbox);
      hbox.getChildren().add(vbox);
      
      stage.setScene(scene);
      stage.show();
  }
  
  /**
   * Called when the button is clicked for a new poem. Removes old poem from display
   * if it exists and displays a new one.
   * @param e Click event
   */
  private void generatePoem(MouseEvent e) {
      // Remove old poem from display
      if (vbox.getChildren().contains(poem)) {
          vbox.getChildren().remove(poem);
      }
      
      poem = new Text();
      poem.setFill(Color.WHITE);
      poem.setFont(bodyFont);
      
      Poem poemSkeleton = new Poem(rules);
      for (String line : poemSkeleton.getLines()) {
          poem.setText(poem.getText() + "\n" + line);
      }
      vbox.getChildren().add(poem);  
  }
}