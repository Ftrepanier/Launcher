package net.mcfr.graphics;

import java.awt.event.MouseEvent;

public class OptionButton extends McFrButton {
  private static final long serialVersionUID = -7423569439172586340L;

  public OptionButton() {
    super("options.png", 778, 14, 26, 26);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    changeImage("options_actif.png");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    changeImage("options.png");
  }
}