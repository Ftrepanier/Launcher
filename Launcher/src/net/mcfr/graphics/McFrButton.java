package net.mcfr.graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import net.mcfr.mvc.Frame;

public abstract class McFrButton extends JButton implements MouseListener {

  private static final long serialVersionUID = -8743932830990851182L;
  private Image img;

  public McFrButton(String path, int x, int y, int width, int height) {
    setOpaque(false);
    setBorder(null);
    changeImage(path);
    addMouseListener(this);
    this.setBounds(x, y, width, height);
  }

  public void changeImage(String path) {
    this.img = Frame.getImage(path);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public final void paintComponent(Graphics g) {
    g.drawImage(this.img, 0, 0, getWidth(), getHeight(), this);
  }
}
