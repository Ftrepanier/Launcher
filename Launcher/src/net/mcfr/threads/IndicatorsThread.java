package net.mcfr.threads;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.mcfr.Launcher;
import net.mcfr.Server;
import net.mcfr.mvc.Frame;
import net.mcfr.mvc.Panel;

public class IndicatorsThread extends Thread {
  private Panel panel;

  /**
   * Initialise les {@code JLabel}s liés aux indicateurs de serveur.
   */
  public IndicatorsThread(Panel panel) {
    this.panel = panel;
  }

  @Override
  public void run() {
    Server roleplayServer = new Server(Server.ROLEPLAY);
    Server freebuildServer = new Server(Server.FREEBUILD);

    JLabel nbJoueursRp = new JLabel(roleplayServer.getPlayerCount(), SwingConstants.RIGHT);
    JLabel nbJoueursBas = new JLabel(freebuildServer.getPlayerCount(), SwingConstants.RIGHT);

    JLabel capaciteRP = new JLabel(" / " + roleplayServer.getCapacity(), SwingConstants.LEFT);
    JLabel capaciteBas = new JLabel(" / " + freebuildServer.getCapacity(), SwingConstants.LEFT);

    JLabel etatRp = new JLabel("Hors ligne");
    etatRp.setVisible(false);
    JLabel etatBas = new JLabel("Hors ligne");
    etatBas.setVisible(false);

    this.panel.add(nbJoueursRp);
    this.panel.add(nbJoueursBas);
    this.panel.add(capaciteRP);
    this.panel.add(capaciteBas);
    this.panel.add(etatRp);
    this.panel.add(etatBas);

    Dimension size = nbJoueursRp.getPreferredSize();
    nbJoueursRp.setFont(new Font(Launcher.TYPEFACE, 0, 13));
    nbJoueursRp.setForeground(new Color(103, 191, 101));
    nbJoueursRp.setBounds(332, 520, 21, size.height);

    size = capaciteRP.getPreferredSize();
    capaciteRP.setFont(new Font(Launcher.TYPEFACE, 0, 13));
    capaciteRP.setForeground(new Color(180, 153, 136));
    capaciteRP.setBounds(353, 520, 42, size.height);

    size = etatRp.getPreferredSize();
    etatRp.setFont(new Font(Launcher.TYPEFACE, 2, 13));
    etatRp.setForeground(new Color(208, 103, 82));
    etatRp.setBounds(332, 520, 70, size.height);

    size = nbJoueursBas.getPreferredSize();
    nbJoueursBas.setFont(new Font(Launcher.TYPEFACE, 0, 13));
    nbJoueursBas.setForeground(new Color(103, 191, 101));
    nbJoueursBas.setBounds(332, 536, 21, size.height);

    size = capaciteBas.getPreferredSize();
    capaciteBas.setFont(new Font(Launcher.TYPEFACE, 0, 13));
    capaciteBas.setForeground(new Color(180, 153, 136));
    capaciteBas.setBounds(353, 536, 42, size.height);

    size = etatBas.getPreferredSize();
    etatBas.setFont(new Font(Launcher.TYPEFACE, 2, 13));
    etatBas.setForeground(new Color(208, 103, 82));
    etatBas.setBounds(332, 536, 70, size.height);

    if (!roleplayServer.isOnline()) {
      nbJoueursRp.setVisible(false);
      capaciteRP.setVisible(false);
      etatRp.setVisible(true);
    }
    if (!freebuildServer.isOnline()) {
      nbJoueursBas.setVisible(false);
      capaciteBas.setVisible(false);
      etatBas.setVisible(true);
    }
    Frame.getFrame().revalidate();
    Frame.getFrame().repaint();
  }
}