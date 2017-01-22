package net.mcfr.mvc;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.mcfr.Launcher;

public class Frame extends JFrame {
  private static final long serialVersionUID = 4216557400970688456L;

  private static Frame frame;

  private Frame() {
    super("Mc-Fr");
    setUndecorated(true);
    setSize(Launcher.WINDOW_DIMENSION);
    setLocationRelativeTo(null);
    setIconImage(Frame.getImage("favicon.png"));
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
}