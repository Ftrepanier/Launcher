package net.mcfr.graphics;

import java.awt.event.MouseEvent;

/**
 * Bouton en forme de croix.
 *
 * @author Minecraft-France
 *
 */
public class CloseButton extends McFrButton {
  private static final long serialVersionUID = 5808661895785024380L;

  public CloseButton() {
    super("fermer.png", 806, 14, 26, 26);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    changeImage("fermer_actif.png");
  }

  @Override
  public void mouseExited(MouseEvent e) {
    changeImage("fermer.png");
  }
}