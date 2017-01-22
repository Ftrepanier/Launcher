package net.mcfr.graphics;

import java.awt.event.MouseEvent;

public class LaunchButton extends McFrButton {
  private static final long serialVersionUID = -8080022105547755625L;

  private boolean isActivated;

  public LaunchButton() {
    super("bouton_inactif.png", 654, 488, 192, 110);
  }

  @Override
  public void mouseClicked(MouseEvent event) {
    this.isActivated = true;
  }

  @Override
  public void mouseEntered(MouseEvent event) {
    changeImage("bouton_actif.png");
  }

  @Override
  public void mouseExited(MouseEvent event) {
    if (!this.isActivated) {
      changeImage("bouton_inactif.png");
    }
  }
}