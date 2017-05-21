package net.mcfr;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import net.mcfr.mvc.Frame;
import net.mcfr.mvc.Panel;

public class Launcher {
  public static final String VERSION = "1.5.5";
  public static final String MCFR_URL = "http://www.minecraft-fr.net/";
  public static final Dimension WINDOW_DIMENSION = new Dimension(850, 606);
  public static final String TYPEFACE = "Arial";

  public static final GameInfos MCFR_INFOS = new GameInfos("mcfr", new GameVersion("1.10.2", GameType.V1_8_HIGHER),
      new GameTweak[] { GameTweak.FORGE });

  private static AuthInfos authInfos;
  private static Thread t;

  public static void auth(String username, String password) throws AuthenticationException {
    Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
    AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
    authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());
    System.out.println(response.getSelectedProfile().getName() + " " + response.getAccessToken() + " " + response.getSelectedProfile().getId());
  }

  public static void update(Panel panel) throws Exception {
    panel.setLabelProgression("Vérification des fichiers...");
    SUpdate su = new SUpdate(Launcher.MCFR_URL + "launcher/supdate/", MCFR_INFOS.getGameDir());
    su.addApplication(new FileDeleter());

    t = new Thread() {
      private int val;
      private int max;

      @Override
      public void run() {
        while (!isInterrupted()) {
          this.val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
          this.max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);

          if (this.max != 0)
            panel.setLabelProgression("Téléchargement en cours : " + BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload()
                + " (" + this.val * 100 / this.max + " %).");
        }
      }
    };

    t.start();
    su.start();
    t.interrupt();
  }

  public static void interruptThread() {
    t.interrupt();
  }

  public static void launch() throws IOException, LaunchException {
    ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(MCFR_INFOS, GameFolder.BASIC, authInfos);
    profile.getVmArgs().addAll(Arrays.asList(Frame.getFrame().getRamSelector().getRamArguments()));
    ExternalLauncher launcher = new ExternalLauncher(profile);
    try {
      launcher.launch();
      System.exit(0);
    } catch (LaunchException e) {
      e.printStackTrace();
    }
  }

  public static String readJsonFromUrl(String url) {
    try {
      URLConnection conn = new URL(url).openConnection();

      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      StringBuffer sb = new StringBuffer();
      String line;
      while ((line = rd.readLine()) != null)
        sb.append(line);
      rd.close();

      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}