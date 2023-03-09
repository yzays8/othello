import java.awt.*;
import javax.swing.*;

public class WindowIndicator extends JPanel {
  JLabel text;
  int fontSize;
  String tempText;

  WindowIndicator() {
    text = new JLabel();
    fontSize = 20;
    tempText = "";

    text.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
    text.setSize(text.getPreferredSize());
    text.setForeground(Color.BLUE);
    add(text);
  }

  public void displayText(String text) {
    tempText = text;
    this.text.setText(text);
  }

  // link given text and the text shown before by displayText(), and display it
  public void displayLinkedText(String text) {
    this.text.setText(tempText + text);
  }
}
