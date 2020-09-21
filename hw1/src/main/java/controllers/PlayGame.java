package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import java.io.IOException;
import java.util.Queue;
import models.GameBoard;
import models.Message;
import models.Move;
import models.Player;
import org.eclipse.jetty.websocket.api.Session;


class PlayGame {

  private static final int PORT_NUMBER = 8080;

  private static Javalin app;

  /**
   * Main method of the application.
   * 
   * @param args Command line arguments
   */
  public static void main(final String[] args) {

    app = Javalin.create(config -> {
      config.addStaticFiles("/public");
    }).start(PORT_NUMBER);

    // Test Echo Server.
    app.post("/echo", ctx -> {
      ctx.result(ctx.body());
    });

    // Get new game and redirect to tictactoe.html
    app.get("/", ctx -> {
      ctx.redirect("/tictactoe.html");
    });

    app.get("/newgame", ctx -> {
      ctx.redirect("/tictactoe.html");
    });

    // Create GameBoard Object and GSON builders.
    GameBoard gameBoard = new GameBoard();
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    // Add player 2 and mark start of game
    app.get("/joingame", ctx -> {
      if (gameBoard.getP1Type() == 'O') {
        gameBoard.initializePlayer2('X');
      } else {
        gameBoard.initializePlayer2('O');
      }
      gameBoard.startGame();
      String gameBoardJson = gson.toJson(gameBoard);
      sendGameBoardToAllPlayers(gameBoardJson);
      ctx.redirect("/tictactoe.html?p=2");
    });

    // get parameter to initialize gameBoard
    app.post("/startgame", ctx -> {
      String initialParam = ctx.body();
      char p1Type = initialParam.charAt(initialParam.length() - 1);
      gameBoard.initializeGameBoard();
      gameBoard.initializePlayer1(p1Type);

      // Send out message to share link
      Message msg = new Message(0);
      String msgJson = gson.toJson(msg);
      ctx.result(msgJson);
    });

    // get parameter to extract move coordinates
    app.post("/move/:playerId", ctx -> {
      String playerID = ctx.pathParam("playerId");
      Player player = gameBoard.getPlayerObject(Integer.parseInt(playerID));
      // Check move is made by the correct player
      if (player.getPlayerId() != gameBoard.getCurrentTurn()) {
        Message msg = new Message(2);
        String msgJson = gson.toJson(msg);
        ctx.result(msgJson);
      } else {
        String move = ctx.body();
        try {
          // Create move object and update gameBoard
          Move playerMove = new Move(player, move);
          gameBoard.makeMove(playerMove);
          String gameBoardJson = gson.toJson(gameBoard);
          sendGameBoardToAllPlayers(gameBoardJson);
        } catch (Exception e) {
          Message msg = new Message(1);
          String msgJson = gson.toJson(msg);
          ctx.result(msgJson);
          // System.out.println("Invalid Move" + e);
        }
      }
    });

    // Web sockets - DO NOT DELETE or CHANGE
    app.ws("/gameboard", new UiWebSocket());
  }

  /**
   * Send message to all players.
   * 
   * @param gameBoardJson Gameboard JSON
   * @throws IOException Websocket message send IO Exception
   */
  private static void sendGameBoardToAllPlayers(final String gameBoardJson) {
    Queue<Session> sessions = UiWebSocket.getSessions();
    for (Session sessionPlayer : sessions) {
      try {
        sessionPlayer.getRemote().sendString(gameBoardJson);
      } catch (IOException e) {
        // Add logger here, this is optional
      }
    }
  }

  public static void stop() {
    app.stop();
  }
}
