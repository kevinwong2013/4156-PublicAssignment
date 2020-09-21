package models;

public class Message {

  private boolean moveValidity;

  private int code;

  private String message;

  /** An array to store the player messages. */
  private final String[] messages = new String[] {
      "Please share the link for second player to join: http://localhost:8080/joingame",
      "Invalid Move", "Please wait until the other player finish the move"};

  /** Constructor for a new Message object. */
  public Message(int code) {
    this.moveValidity = false;
    this.code = code;
    this.message = messages[code];
  }
}
