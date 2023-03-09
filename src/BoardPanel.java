import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
  BoardManager boardManager;
  Graphics g;
  int width;
  int height;
  int gridSize;
  int xLowerLimit;
  int xUpperLimit;
  int yLowerLimit;
  int yUpperLimit;
  int columnNumber;
  int rowNumber;
  int r;
  final int d = 2; // subtle position adjustment

  BoardPanel(int width, int height, BoardManager boardManager) {
    setPreferredSize(new Dimension(width, height));
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    this.width = width;
    this.height = height;
    this.boardManager = boardManager;
    g = this.getGraphics();
    gridSize = 70;
    xLowerLimit = 0;
    xUpperLimit = xLowerLimit + gridSize * 8;
    yLowerLimit = 0;
    yUpperLimit = yLowerLimit + gridSize * 8;
    r = gridSize - 5;
  }

  @Override
  public void paintComponent(Graphics g) {
    // innermost background
    g.setColor(Color.black);
    g.fillRect(0, 0, width, height);

    // background of board (green)
    g.setColor(new Color(0, 170, 85));
    g.fillRect(xLowerLimit, yLowerLimit, xUpperLimit - xLowerLimit, yUpperLimit - yLowerLimit);

    // dot in four places
    g.setColor(Color.black);
    g.fillRect(xLowerLimit+gridSize*2-d, yLowerLimit+gridSize*2-d, 5, 5);
    g.fillRect(xLowerLimit+gridSize*2-d, yLowerLimit+gridSize*6-d, 5, 5);
    g.fillRect(xLowerLimit+gridSize*6-d, yLowerLimit+gridSize*2-d, 5, 5);
    g.fillRect(xLowerLimit+gridSize*6-d, yLowerLimit+gridSize*6-d, 5, 5);

    // draw grid line
    g.setColor(Color.black);
    for (int i = 0; i < 9; i++) {
      g.drawLine(xLowerLimit, yLowerLimit+gridSize*i, xUpperLimit, yLowerLimit+gridSize*i);   // horizontal line
      g.drawLine(xLowerLimit+gridSize*i, yLowerLimit, xLowerLimit+gridSize*i, yUpperLimit);   // vertical line
    }

    // place pieces
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (boardManager.board[y][x] == '*') {
          // mark the candidate spaces on which a piece can be placed
          g.setColor(new Color(255, 222, 173, 95));
          g.fillRect(xLowerLimit+x*gridSize+d, yLowerLimit+y*gridSize+d, gridSize-2*d, gridSize-2*d);
        } else if(boardManager.board[y][x] == 'W') {
          g.setColor(Color.white);
          g.fillOval(xLowerLimit+x*gridSize+d, yLowerLimit+y*gridSize+d, r, r);
        } else if(boardManager.board[y][x] == 'B') {
          g.setColor(Color.black);
          g.fillOval(xLowerLimit+x*gridSize+d, yLowerLimit+y*gridSize+d, r, r);
        }
      }
    }
  }

  public void getPositionFromCoordinate(int x, int y) {
    for (int i = xLowerLimit, count = 0; i <= xUpperLimit; i += gridSize, count++) {
      if ((x >= i) && (x < i + gridSize)) {
        columnNumber = count;
      }
    }
    for (int i = yLowerLimit, count = 0 ; i <= yUpperLimit; i += gridSize, count++) {
      if ((y >= i) && (y < i + gridSize)) {
        rowNumber = count;
      }
    }
  }

  // place a piece where the column/row number refers
  public void placePiece() {
    repaint();
  }
}
