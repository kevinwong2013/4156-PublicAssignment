package models;

public class Move {

  // modify the class write a constructor

  private Player player;

  private int moveX; // 0,1,2

  private int moveY;

  /** Constructor for Move object and throw exception if X or Y are out of range. */
  public Move(Player inputPlayer, String inputMove) throws Exception {
    player = inputPlayer;
    try {
      int x = Character.getNumericValue(inputMove.charAt(2));
      int y = Character.getNumericValue(inputMove.charAt(6));
      if (x >= 0 && x <= 2 && y >= 0 && y <= 2) {
        this.moveX = x;
        this.moveY = y;
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new Exception();
    }
  }

  public Player getPlayer() {
    return this.player;
  }

  public int getX() {
    return this.moveX;
  }

  public int getY() {
    return this.moveY;
  }

}
