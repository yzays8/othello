public class BoardManager {
  GameWindow window;
  char[][] board = new char[8][8];
  final static int[][] SEARCH_DIRECTIONS = {
    {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
  };

  BoardManager(Othello game) {
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        board[i][j] = 'E';
      }
    }
    board[3][3] = 'W';
    board[4][4] = 'W';
    board[3][4] = 'B';
    board[4][3] = 'B';

    window = new GameWindow("Othello Game", game, this);
    window.isAbleToClick = true;
  }

  boolean isInBoard(int x, int y) {
    if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
      return true;
    } else {
      return false;
    }
  }

  boolean foundReversiblePiece(Player player, char piece, int x, int y, int[] searchDirection) {
    char opponentPiece = (player.piece == 'W') ? 'B' : 'W';
    boolean foundOpponentPiece = false, isSandwiched = false;

    x += searchDirection[0];
    y += searchDirection[1];
    if (isInBoard(x, y) && (board[y][x] == opponentPiece)) {
      foundOpponentPiece = true;  // opponent's piece is next to mine
    }

    while (isInBoard(x, y)) {
      if (board[y][x] == piece) {
        isSandwiched = true;
      } else if ((board[y][x] == '*') || (board[y][x] == 'E')) {
        break;  // opponent's piece is not sandwiched by mine
      }
      x += searchDirection[0];
      y += searchDirection[1];
    }

    if (foundOpponentPiece && isSandwiched) {
      return true;
    } else {
      return false;
    }
  }

  void flipPiece(char piece, int x, int y) {
    if (piece == 'W') {
      board[y][x] = 'W';
    } else {
      board[y][x] = 'B';
    }
    window.boardPanel.placePiece();
  }

  boolean isAbleToPlacePiece(Player player, int x, int y) {
    if ((board[y][x] == 'B') || (board[y][x] == 'W')) {
      return false;
    }
    for (int i = 0; i < 8; i++) {
      if (foundReversiblePiece(player, player.piece, x, y, SEARCH_DIRECTIONS[i])) {
        return true;
      }
    }
    return false;
  }

  void flipPiecesInOneDirection(char piece, int x, int y, int[] searchDirection) {
    board[y][x] = piece;
    window.boardPanel.placePiece();
    x += searchDirection[0];
    y += searchDirection[1];
    for (;;) {
      if (board[y][x] == piece) {
        break;
      } else {
        flipPiece(piece, x, y);
      }
      x += searchDirection[0];
      y += searchDirection[1];
    }
  }

  void placePiece(Player player, int x, int y) {
    // place player's piece and flip all pieces that can be flipped
    for (int i = 0; i < 8; i++) {
      if (foundReversiblePiece(player, player.piece, x, y, SEARCH_DIRECTIONS[i])) {
        flipPiecesInOneDirection(player.piece, x, y, SEARCH_DIRECTIONS[i]);
      }
    }
  }

  // search places where a piece can be put
  void markCandidatePlaces(Player playerNow) {
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (board[y][x] == '*') {
          board[y][x] = 'E';
        }
        if ((board[y][x] == 'E') && isAbleToPlacePiece(playerNow, x, y)) {
          board[y][x] = '*';
        }
      }
    }
  }
}