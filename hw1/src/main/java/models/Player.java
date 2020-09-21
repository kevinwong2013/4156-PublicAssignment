package models;

public class Player {

  private char type; // "X" or "O"

  private int id; // 1 or 2

  Player(char inputType, int inputId) {
    type = inputType;
    id = inputId;
  }

  public void changePlayerType(char inputType) {
    this.type = inputType;
  }

  public char getPlayerType() {
    return this.type;
  }

  public int getPlayerId() {
    return this.id;
  }
}
