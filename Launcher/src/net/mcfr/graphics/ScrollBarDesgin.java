package net.mcfr.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import net.mcfr.mvc.Frame;

public class ScrollBarDesgin extends BasicScrollBarUI {
  private Image imageThumb, imageTrack;

  public ScrollBarDesgin() {
    this.imageThumb = Frame.getImage("scroll.png");
    this.imageTrack = Frame.getImage("scrollback.png");
  }

  @Override
  protected JButton createDecreaseButton(int orientation) {
    return getInvisibleButton();
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    return getInvisibleButton();
  }

  private JButton getInvisibleButton() {
    JButton btn = new JButton();
    btn.setVisible(false);
    return btn;
  }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    g.translate(thumbBounds.x, thumbBounds.y);
    ((Graphics2D) g).drawImage(this.imageThumb, -4, 5, null);
  }

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    g.translate(trackBounds.x + 10, trackBounds.y);
    ((Graphics2D) g).drawImage(this.imageTrack, AffineTransform.getScaleInstance(1, (double) trackBounds.height / this.imageTrack.getHeight(null)),
        null);
    g.translate(-trackBounds.x, -trackBounds.y);
  }
}