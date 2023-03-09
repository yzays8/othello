import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
  BoardPanel boardPanel;
  WindowIndicator indicator;
  Othello game;
  BoardManager boardManager;

  boolean isAbleToClick;
  int xCoordinate, yCoordinate;

  GameWindow(String title, Othello game, BoardManager boardManager) {
    this.game = game;
    this.boardManager = boardManager;
    indicator = new WindowIndicator();
    isAbleToClick = false;

    // configure the master frame
    setTitle(title);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    // configure the board frame
    boardPanel = new BoardPanel(560, 560 + indicator.fontSize + 5, boardManager);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    boardPanel.addMouseListener(new MouseCheck());
    getContentPane().add(boardPanel);

    // configure the frame in which the indications are displayed
    getContentPane().add(indicator);

    pack();
  }

  public void forceQuit() {
    dispose();
  }

  // click event handling
  private class MouseCheck extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      if (isAbleToClick) {
        xCoordinate = e.getX();
        yCoordinate = e.getY();
        // if board is correctly clicked
        if (xCoordinate >= boardPanel.xLowerLimit && xCoordinate <= boardPanel.xUpperLimit &&
            yCoordinate >= boardPanel.yLowerLimit && yCoordinate <= boardPanel.yUpperLimit) {
          boardPanel.getPositionFromCoordinate(xCoordinate, yCoordinate);
          game.clickHandler(boardPanel.columnNumber, boardPanel.rowNumber);
        }
      }
    }
  }
}
