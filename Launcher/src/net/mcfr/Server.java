package net.mcfr;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Server {

  public static final String ROLEPLAY = "roleplay";
  public static final String FREEBUILD = "freeBuild";
  private static String serversState;

  private int playerCount;
  private int capacity;
  private boolean online;

  public Server(String label) {
    initServersState();

    try {
      JSONObject obj = (JSONObject) new JSONParser().parse(serversState);
      if (this.online = ((JSONObject) obj.get(label)).get("status").equals("online")) {
        this.playerCount = Integer.parseInt(((JSONObject) obj.get(label)).get("onlinePlayers").toString());
        this.capacity = Integer.parseInt(((JSONObject) obj.get(label)).get("maxPlayers").toString());
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return le nombre de joueurs présents sur le serveur.
   */
  public String getPlayerCount() {
    return this.playerCount + "";
  }

  /**
   * @return la capacité de joueurs du serveur.
   */
  public String getCapacity() {
    return this.capacity + "";
  }

  /**
   * @return {@code true} si le serveur est en ligne, {@code false} sinon.
   */
  public boolean isOnline() {
    return this.online;
  }

  private static void initServersState() {
    if (serversState == null) {
      serversState = Launcher.readJsonFromUrl(Launcher.MCFR_URL + "utils/online");
    }
  }
}