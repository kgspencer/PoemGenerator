// From a given text file, constructs a poem.
package poemGenerator;

import java.util.*;   

public class Poem {
  private TreeMap<String, List<String[]>> rules;
  private List<String> lines;

  /**
   * Constructs a Poem over the rules in Backus Naur Form. 
   * @param contents
   */
  public Poem(List<String> contents) {
      if (contents.isEmpty()) {
          throw new IllegalArgumentException();
      }
              
      rules = new TreeMap<String, List<String[]>>();
      String[] components;
      String rule = "";
       
      for (String line : contents) {
          // Splits rule from possible expressions
          String[] terms = line.split("::=");
          rule = terms[0];
          // Throws exception if there are two of the same rule
          if (rules.containsKey(rule)) {
              throw new IllegalArgumentException();   
          }
          List<String[]> expressions = new ArrayList<>(); // Potential expressions
          
          // Considers each possible expression and splits it into its components
          for (String option : terms[1].split("\\|")) {
              option.trim();
              components = option.split("\\s+");
              expressions.add(components);
          }
          rules.put(rule, expressions);
      }
      
      writePoem();
  }
  
  /*
   * Adds to lines so that each entry corresponds to one line of poetry
   */
  private void writePoem() {
      lines = new ArrayList<>();
      
      for (int i = 0; i < 10; i++) { // Creates 10 lines, potential to change from hardcoded val
          lines.add(lineHelper("<sentence>"));
      }
  }
  
  /**
   * Creates one line of poetry by interpreting Backus Naur form and randomly choosing between entries
   * @param nonTerminal An entry on the left-hand side; essentially a rule to be replaced by expressions
   * @return The completed line
   */
  private String lineHelper(String nonTerminal) {
      String line = "";
      
      if (!rules.containsKey(nonTerminal)) {
          line += nonTerminal + " ";
          
      } else {
          int numOptions = rules.get(nonTerminal).size();
          int optionPosition = (int)(Math.random() * numOptions);
          String[] components = rules.get(nonTerminal).get(optionPosition);
          for (int i = 0; i < components.length; i++) {
              line += lineHelper(components[i]);
          }
      }
      return line;
  }
  
  /**
   * Getter for the constructed lines
   * @return The complete lines of the poem, where each line is one entry
   */
  public List<String> getLines() {
      return lines;
  }

  /**
   * Checks if the map contains the given rule
   * @param rule The given rule to be checked
   * @return True if the map contains the given rule
   */
  public boolean containsRule (String rule) {
    return false;
  }
}