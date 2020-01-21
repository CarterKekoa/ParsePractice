
/**
 * Author: Carter Mooring
 * Course: CPSC 326, Spring 2020
 * Asgnmt: HW 1
 *
 * Description: Simple program for practicing reading from a file,
 * parsing its contents, and printing the results.
 */

import java.util.*;
import java.io.*;


public class ElevatorSimulator {

  private BufferedReader buffer;           // buffered input stream reader
  private int elevatorCount;               // number of elevators
  private Vector<Integer> elevatorFloors;  // list of elevator locations

  /**
   * Create a new elevator simulator
   */
  public ElevatorSimulator(InputStream inStream) {
    this.elevatorCount = 0;
    this.elevatorFloors = new Vector<Integer>();
    this.buffer = new BufferedReader(new InputStreamReader(inStream));
  }

  /**
   * Returns next character in the stream. Gives -1 if end of file.
   */
  private int read() {
    try {
      int ch = buffer.read();
      return ch;
    } catch(IOException e) {
      error("read error");
    }
    return -1;
  }

  /**
   * Returns next character without removing it from the stream.
   */
  private int peek() {
    int ch = -1;
    try {
      buffer.mark(1);
      ch = read();
      buffer.reset();
    } catch(IOException e) {
      error("read error");
    }
    return ch;
  }

  /**
   * Read a sequence of white space characters.
   */
  private void readSpace() {
    int ch = peek();
    while (Character.isWhitespace(ch) && ch != -1) {
      read();
      ch = peek();
    }
  }

  /**
   * Read and return a sequence of characters (up to whitespace).
   */
  private String readString() {
    String str = "";
    int ch = peek();
    while(!Character.isWhitespace(ch) && ch != -1) {
      str += (char)read();
      ch = peek();
    }
    return str;
  }

  /**
   * Read a sequence of characters (digits) and return as an integer
   * value.
   */
  private int readInt() {
    String str = "";
    int ch = peek();
    while (!Character.isWhitespace(ch) && ch != -1) {
      str += (char)read();
      ch = peek();
    }
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      error("expecting integer, found '" + str + "'");
    }
    return -1;
  }

  /**
   * Print an error message and exit the program.
   */
  private void error(String msg) {
    System.out.println("Error: " + msg);
    System.exit(1);
  }

  /**
   * Builds and runs the simulation
   */
  public void run() {
    readSpace();
    // check if anything to read
    if (peek() == -1) {
      return;
    }
    // first statement must be the number of elevators
    String str = readString();
    if (!str.equals("elevators")) {
      error("expecting elevator, found '" + str + "'");
    }

    //get the number of elevators
    readSpace();  //read spaces following string
    elevatorCount = readInt();  //grab the number of elevators
    if(elevatorCount <= 0){
      error("expecting more elevators, found '" + elevatorCount + "'");
    }
    readSpace();  //read spaces following number of elevators
    for(int i = 0; i < elevatorCount; i++){
      elevatorFloors.add(1);
    }

    //the loop that sorts through the data and gets grabs the information
    while(peek() != -1) {
      readSpace();

      //read if moving up or down
      str = readString(); //read the new string that contains either up or down
      if(!str.equals("up") && !str.equals("down")){
        error("expecting up or down, found '" + str + "'");
      }
      readSpace(); //get spaces after direction

      //gets the elevator number and checks it is valid
      int elevatorNumber = readInt(); //contains the elevator number being used
      if(elevatorNumber <= 0 || elevatorNumber > elevatorCount){
        error("expecting a valid elevator number, found '" + elevatorNumber + "'");
      }
      readSpace(); //reads space after elevator number

      //gets the floor number and checks if it is valid
      int floorChange = readInt(); //contains the number of floors moved
      if(floorChange <= 0){
        error("expecting number of floors greater than 0, found '" + floorChange + "'");
      }

      //stores the elevators location for each line and updates if a later line changes it
      if(str.equals("up")){
        elevatorFloors.set(elevatorNumber - 1, elevatorFloors.get(elevatorNumber - 1) + floorChange);
      }else if(str.equals("down")) {
        elevatorFloors.set(elevatorNumber - 1, elevatorFloors.get(elevatorNumber - 1) - floorChange);
      }
      readSpace();
      System.out.println(elevatorFloors);
    }
    return;
  }

  /**
   * Return the number of elevators resulting after running the
   * simulation.
   */
  public int elevators() {
    return elevatorCount;
  }

  /**
   * Return the floor the given elevator is on after running the
   * simulation.
   */
  public int floor(int elevator_num) {
    return elevatorFloors.get(elevator_num - 1);
  }

}
