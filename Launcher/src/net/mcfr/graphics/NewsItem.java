package net.mcfr.graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.StringJoiner;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.mcfr.Launcher;
import net.mcfr.mvc.Frame;

public final class NewsItem extends JPanel {
  private static final long serialVersionUID = 4945960939928674419L;

  /**
   * Charge les news depuis la base de données.
   *
   * @param box
   *          {@code Layout} où positionner les news.
   */
  public static void loadNews(Box box) {
    new Thread() {
      @Override
      public void run() {
        try {
          JSONArray array = (JSONArray) new JSONParser().parse(Launcher.readJsonFromUrl(Launcher.MCFR_URL + "news/json/5"));
          array.forEach(o -> {
            JSONObject news = (JSONObject) o;
            box.add(Box.createVerticalStrut(15));
            box.add(new NewsItem(news.get("titre").toString(), news.get("resume").toString(), Integer.parseInt(news.get("id").toString())));
          });
        } catch (ParseException e) {
          e.printStackTrace();
        }
        box.add(Box.createVerticalStrut(15));
        Frame.getFrame().revalidate();
        Frame.getFrame().repaint();
      }
    }.start();
  }

  public NewsItem(String sTitle, String text, final int index) {
    super(null);
    setPreferredSize(new Dimension(461, 113));

    boolean secondLineBegan = false;
    StringJoiner line1 = new StringJoiner(" ");
    StringJoiner line2 = new StringJoiner(" ");

    for (String str : text.split(" ")) {
      if (line1.length() < 70 - str.length() - 1 && !secondLineBegan) {
        line1.add(str);
      } else if (line2.length() < 70 - str.length() - 1) {
        line2.add(str);
        secondLineBegan = true;
      } else {
        break;
      }
    }

    JLabel title = new JLabel(sTitle);
    title.setFont(new Font(Launcher.TYPEFACE, 1, 14));
    title.setForeground(new Color(241, 151, 110));
    title.setBounds(50, 10, 600, title.getPreferredSize().height);

    JLabel line1Lbl = new JLabel(line1.toString());
    line1Lbl.setFont(new Font(Launcher.TYPEFACE, 0, 12));
    line1Lbl.setForeground(new Color(84, 79, 79));
    line1Lbl.setBounds(50, 40, 600, line1Lbl.getPreferredSize().height);

    JLabel line2Lbl = new JLabel(line2.toString());
    line2Lbl.setFont(new Font(Launcher.TYPEFACE, 0, 12));
    line2Lbl.setForeground(new Color(84, 79, 79));
    line2Lbl.setBounds(50, 56, 600, line2Lbl.getPreferredSize().height);

    JLabel link = new JLabel("...");
    link.setBounds(435, 85, 60, link.getPreferredSize().height);
    link.setFont(new Font(Launcher.TYPEFACE, 1, 14));
    link.setForeground(new Color(84, 79, 79));
    link.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          Runtime.getRuntime().exec("cmd /c start http://www.minecraft-fr.net/news/redirect-" + index);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        Frame.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        Frame.getFrame().setCursor(Cursor.getDefaultCursor());
      }
    });

    JLabel icon = new JLabel(new ImageIcon(Frame.getImage("new.png")));
    icon.setBounds(8, 41, 30, 29);

    add(icon);
    add(title);
    add(line1Lbl);
    add(line2Lbl);
    add(link);

    setVisible(true);
  }

  @Override
  public void paintComponent(Graphics g2) {
    g2.drawImage(Frame.getImage("news_cadre.png"), 0, 0, 461, 113, null);
  }
}