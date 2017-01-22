package net.mcfr.mvc;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import net.mcfr.Launcher;

public class Controller extends MouseAdapter implements ActionListener, KeyListener {

  private static Controller controller;

  private boolean patchInProgress;
  private Point initialClick;

  private Saver saver;

  private Controller() {
    this.saver = new Saver(new File(Launcher.MCFR_INFOS.getGameDir() + "profile.properties"));
  }

  public boolean getState() {
    return this.patchInProgress;
  }

  public String getSavedUsername() {
    return this.saver.get("username");
  }

  public static Controller getController() {
    if (controller == null) {
      controller = new Controller();
    }
    return controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Panel panel = Panel.getPanel();
    switch (e.getActionCommand()) {
    case "patch":
      panel.setFieldsEnabled(false);

      if (panel.getUsername().trim().length() == 0 || panel.getPassword().length() == 0) {
        JOptionPane.showMessageDialog(null, "Veuillez saisir un pseudonyme et un mot de passe valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        panel.setFieldsEnabled(true);
        return;
      }

      new Thread() {
        @Override
        public void run() {
          try {
            panel.setLabelProgression("Authentification en cours...");
            Launcher.auth(panel.getUsername(), panel.getPassword());
          } catch (AuthenticationException e) {
            JOptionPane.showMessageDialog(null, "<html>Impossible de se connecter :<br />" + e.getErrorModel().getErrorMessage() + "</html>",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            panel.setFieldsEnabled(true);
            panel.setLabelProgression("En attente...");
            return;
          }
          Controller.this.saver.set("username", panel.getUsername());
          //          try {
          //              Launcher.update(panel);
          //          } catch (Exception e) {
          //            Launcher.interruptThread();
          //            JOptionPane.showMessageDialog(null, "<html>Impossible de mettre le jeu à jour :<br />" + e + "</html>", "Erreur",
          //                JOptionPane.ERROR_MESSAGE);
          //            panel.setFieldsEnabled(true);
          //            panel.setLabelProgression("En attente...");
          //            return;
          //          }

          //          try {
          //              panel.setLabelProgression("Le jeu va être lancé...");
          //              Launcher.launch();
          //          } catch (IOException | LaunchException e) {
          //            JOptionPane.showMessageDialog(null, "<html>Impossible de lancer le jeu :<br />" + e + "</html>", "Erreur", JOptionPane.ERROR_MESSAGE);
          //            panel.setFieldsEnabled(true);
          //            panel.setLabelProgression("En attente...");
          //            return;
          //          }
        }
      }.start();

      break;
    case "close":
      if (!getState() || getState() && JOptionPane.showConfirmDialog(Frame.getFrame(), "Le patch est en cours. Êtes vous sûr(e) de vouloir quitter ?",
          "", JOptionPane.YES_NO_OPTION) == 0) {
        System.exit(0);
      }
      break;
    case "reduce":
      Frame.getFrame().setExtendedState(JFrame.ICONIFIED);
      break;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.initialClick = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Frame frame = Frame.getFrame();
    int thisX = frame.getLocation().x;
    int thisY = frame.getLocation().y;

    int xMoved = thisX + e.getX() - (thisX + this.initialClick.x);
    int yMoved = thisY + e.getY() - (thisY + this.initialClick.y);

    int X = thisX + xMoved;
    int Y = thisY + yMoved;
    frame.setLocation(X, Y);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == '\n') {
      actionPerformed(new ActionEvent(e.getSource(), e.getID(), "patch"));
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }
}