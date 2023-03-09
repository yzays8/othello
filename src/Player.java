import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Player {
  String name;
  char piece;
  boolean isAbleToPutPiece;

  Player(char color) {
    System.out.print("Enter your name: ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      this.name = reader.readLine();
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
    piece = color;
    isAbleToPutPiece = true;
  }

  @Override
  public String toString() {
    return name;
  }
}