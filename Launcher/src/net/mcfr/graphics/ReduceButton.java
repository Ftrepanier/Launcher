package net.mcfr.graphics;

import java.awt.event.MouseEvent;

public class ReduceButton extends McFrButton {

  private static final long serialVersionUID = -8247752497928496181L;

  public ReduceButton() {
    super("reduire.png", 778, 14, 26, 26);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    changeImage("reduire_actif.png");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    changeImage("reduire.png");
  }
}