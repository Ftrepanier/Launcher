package net.mcfr.mvc;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import net.mcfr.Launcher;

public class Frame extends JFrame {
  private static final long serialVersionUID = 4216557400970688456L;
  private final RamSelector rs;
  private static Frame frame;

  private Frame() {
    super("Mc-Fr");
    if (!isMac()) {
      setUndecorated(true);
    }
    setSize(Launcher.WINDOW_DIMENSION);
    setLocationRelativeTo(null);
    setIconImage(Frame.getImage("favicon.png"));
    this.rs = new RamSelector(new File(Launcher.MCFR_INFOS.getGameDir(), "/ram.txt"));
  }

  public static Frame getFrame() {
    if (frame == null) {
      frame = new Frame();
      frame.add(Panel.getPanel());
    }
    return frame;
  }

  public static Image getImage(String name) {
    return new ImageIcon(Frame.class.getClassLoader().getResource("resources/" + name)).getImage();
  }

  public static void main(String[] args) {
    getFrame().setVisible(true);
  }

  public RamSelector getRamSelector() {
    return this.rs;
  }

  public static boolean isMac() {
    return System.getProperty("os.name").toLowerCase().contains("mac");
  }
}