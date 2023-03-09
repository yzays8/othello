public class Othello {
  Player player1, player2, playerNow;
  BoardManager boardManager;

  Othello() {
    player1 = new Player('W');
    player2 = new Player('B');
    playerNow = player1;
    boardManager = new BoardManager(this);
  }


  void showWinner(char winnerPiece) {
    Player winner;
    if (player1.piece == winnerPiece) {
      winner = player1;
    } else {
      winner = player2;
    }
    boardManager.window.indicator.displayText("The winner is " + winner + "!! Congratulations!!");
    boardManager.window.isAbleToClick = false;
    // boardManager.window.forceQuit();
  }

  void decideWinner() {
    int blackNumber = 0, whiteNumber = 0;
    for (int y = 0; y < 8; ++y) {
      for (int x = 0; x < 8; ++x) {
        if (boardManager.board[y][x] == 'B') {
          ++blackNumber;
        } else {
          ++whiteNumber;
        }
      }
    }
    System.out.println("Black: " + blackNumber + ", White: " + whiteNumber);
    if (blackNumber > whiteNumber) {
      showWinner('B');
    } else if (blackNumber < whiteNumber) {
      showWinner('W');
    } else {
      boardManager.window.indicator.displayText("Draw!");
      boardManager.window.isAbleToClick = false;
      // boardManager.window.forceQuit();
    }
  }

  boolean needToSkipTurn() {
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (boardManager.isAbleToPlacePiece(playerNow, x, y)) {
          playerNow.isAbleToPutPiece = true;
          return false;
        }
      }
    }
    playerNow.isAbleToPutPiece = false;
    return true;
  }

  void waitClick() {
    boardManager.window.indicator.displayLinkedText("Color:" + ((playerNow.piece == 'W') ? "White" : "Black") + " Click a grid.");
    // wait GUI click event after this call
  }

  void clickHandler(int x, int y) {
    if (boardManager.isAbleToPlacePiece(playerNow, x, y)) {
      boardManager.placePiece(playerNow, x, y);
      if (playerNow == player1) {
        playerNow = player2;
      } else {
        playerNow = player1;
      }
      playTurn();
    } else {
      boardManager.window.indicator.displayText("You can't place the piece here. ");
      waitClick();
    }
  }

  void playTurn() {
    if (player1.isAbleToPutPiece || player2.isAbleToPutPiece) {
      boardManager.markCandidatePlaces(playerNow);

      if (needToSkipTurn()) {
        boardManager. window.indicator.displayText(playerNow + " can't place the piece, so skipped.");
        if(playerNow == player1) {
          playerNow = player2;
        } else {
          playerNow = player1;
        }
        playTurn();
      } else {
        boardManager.window.indicator.displayText("It\'s " + playerNow + "\'s turn. ");
        waitClick();
      }
    } else {
      decideWinner();
    }
  }

  void playGame() {
    playTurn();
  }

  public static void main(String[] args) {
    Othello game = new Othello();
    game.playGame();
  }
}