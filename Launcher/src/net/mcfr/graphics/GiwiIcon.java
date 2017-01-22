package net.mcfr.graphics;

import javax.swing.ImageIcon;

import net.mcfr.mvc.Frame;

/**
 * Image 3D de personnages Minecraft.
 *
 * @author Minecraft-France
 *
 */
public class GiwiIcon extends ImageIcon {
  private static final long serialVersionUID = 6894691351755164555L;

  public GiwiIcon(int id) {
    super(Frame.getImage("giwis/giwi_" + id + ".png"));
  }
}