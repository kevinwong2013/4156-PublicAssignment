package models;

public class GameBoard {

  private Player p1;

  private Player p2;

  private boolean gameStarted;

  private int turn; // 1 or 2

  private char[][] boardState; // "O" for p1, "X" for p2

  private int winner;

  private boolean isDraw;

  /** GameBoard constructor. */
  public GameBoard() {
    p1 = new Player('X', 1);
    // p2 = new Player('O', 2);
    gameStarted = false;
    turn = 1;
    boardState = new char[3][3];
    // Arrays.fill(boardState, null);
    winner = 0;
    isDraw = false;
  }

  /** Duplicate initializer besides the constructor. */
  public void initializeGameBoard() {
    p1 = new Player('X', 1);
    // p2 = new Player('O', 2);
    gameStarted = false;
    turn = 1;
    boardState = new char[3][3];
    // Arrays.fill(boardState, null);
    winner = 0;
    isDraw = false;
  }

  public void initializePlayer1(char p1Type) {
    p1.changePlayerType(p1Type);
  }

  public void initializePlayer2(char p2Type) {
    p2 = new Player(p2Type, 2);
  }

  public char getP1Type() {
    return this.p1.getPlayerType();
  }

  public int getCurrentTurn() {
    return this.turn;
  }

  /** Return the stored player object. */
  public Player getPlayerObject(int playerID) {
    if (playerID == 1) {
      return this.p1;
    } else {
      return this.p2;
    }
  }

  /**
   * Check the winner and save in "winner", where 1 = p1 | 2 = p2 | 0 = no winner.
   */
  public void checkWinner() {
    int rowLength = 3;
    int colLength = 3;
    int p1Score = 0;
    int p2Score = 0;
    char p1 = this.p1.getPlayerType();
    char p2 = this.p2.getPlayerType();
    this.winner = 0;
    // Check the rows
    for (int i = 0; i < rowLength; i++) {
      p1Score = 0;
      p2Score = 0;
      for (int j = 0; j < colLength; j++) {
        if (this.boardState[i][j] == p1) {
          p1Score += 1;
        }
        if (this.boardState[i][j] == p2) {
          p2Score += 1;
        }
        if (p1Score == rowLength) {
          this.winner = 1;
          break;
        }
        if (p2Score == rowLength) {
          this.winner = 2;
          break;
        }
      }
    }
    // Check the columns
    for (int j = 0; j < colLength; j++) {
      p1Score = 0;
      p2Score = 0;
      for (int i = 0; i < rowLength; i++) {
        if (this.boardState[i][j] == p1) {
          p1Score += 1;
        }
        if (this.boardState[i][j] == p2) {
          p2Score += 1;
        }
      }
      if (p1Score == colLength) {
        this.winner = 1;
        break;
      }
      if (p2Score == colLength) {
        this.winner = 2;
        break;
      }
    }
    // Check the diagonals
    p1Score = 0;
    p2Score = 0;
    for (int i = 0; i < rowLength; i++) {
      if (this.boardState[i][i] == p1) {
        p1Score += 1;
      }
      if (this.boardState[i][i] == p2) {
        p2Score += 1;
      }
    }
    if (p1Score == rowLength) {
      this.winner = 1;
    }
    if (p2Score == rowLength) {
      this.winner = 2;
    }

    p1Score = 0;
    p2Score = 0;
    for (int i = 0; i < rowLength; i++) {
      if (this.boardState[i][rowLength - i - 1] == p1) {
        p1Score += 1;
      }
      if (this.boardState[i][rowLength - i - 1] == p2) {
        p2Score += 1;
      }
    }
    if (p1Score == rowLength) {
      this.winner = 1;
    }
    if (p2Score == rowLength) {
      this.winner = 2;
    }
  }

  /** Check if the game is draw, incompleted game returns false. */
  public void checkDraw() {
    this.isDraw = false;
    this.checkWinner();
    int unfilledSpace = 0;
    if (this.winner == 0) {
      for (int i = 0; i < this.boardState[0].length; i++) {
        for (int j = 0; j < this.boardState[1].length; j++) {
          if (this.boardState[i][j] == '\u0000') {
            unfilledSpace++;
          }
        }
      }
      if (unfilledSpace == 0) {
        this.isDraw = true;
      }
    }
  }

  public void startGame() {
    this.gameStarted = true;
  }

  /** Make a move according to the input Move object and update GameBoard. */
  public void makeMove(Move playerMove) {
    Player p = playerMove.getPlayer();
    int x = playerMove.getX();
    int y = playerMove.getY();
    if (p.getPlayerId() == this.turn) {
      if (this.boardState[x][y] != 'O' && this.boardState[x][y] != 'X') {
        this.boardState[x][y] = p.getPlayerType();
        this.checkDraw();
        this.turn = 3 - this.turn;
      }
    }
  }

}

