package net.mcfr.mvc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import net.mcfr.Launcher;
import net.mcfr.graphics.CloseButton;
import net.mcfr.graphics.GiwiIcon;
import net.mcfr.graphics.HintPasswordField;
import net.mcfr.graphics.HintTextField;
import net.mcfr.graphics.LaunchButton;
import net.mcfr.graphics.NewsItem;
import net.mcfr.graphics.OptionButton;
import net.mcfr.graphics.ReduceButton;
import net.mcfr.graphics.ScrollBarDesgin;
import net.mcfr.threads.IndicatorsThread;

public class Panel extends JPanel {
  private static final long serialVersionUID = -764606094142159965L;
  private static Panel panel;

  private JLabel progression;
  private HintTextField usernameField;
  private HintPasswordField passwordField;

  private Panel() {
    super(null);

    JLabel version = new JLabel("V " + Launcher.VERSION);
    version.setFont(new Font(Launcher.TYPEFACE, 0, 8));
    version.setForeground(new Color(180, 153, 136));
    version.setBounds(820, 590, 600, version.getPreferredSize().height);

    add(version);

    initButtons();
    initConnectionFields();
    initDragAndDrop();
    initGiwi();
    initNewsArea();
    new IndicatorsThread(this).start();
    initProgression();
  }

  public static Panel getPanel() {
    if (panel == null)
      panel = new Panel();
    return panel;
  }

  /**
   * Initialise les boutons.
   */
  private void initButtons() {
    LaunchButton patchButton = new LaunchButton();
    CloseButton closeButton = new CloseButton();
    ReduceButton reduceButton = new ReduceButton();
    OptionButton optionButton = new OptionButton();

    patchButton.addActionListener(Controller.getController());
    patchButton.setActionCommand("patch");
    closeButton.addActionListener(Controller.getController());
    closeButton.setActionCommand("close");
    reduceButton.addActionListener(Controller.getController());
    reduceButton.setActionCommand("reduce");
    optionButton.addActionListener(Controller.getController());
    optionButton.setActionCommand("options");

    add(patchButton);
    add(closeButton);
    add(reduceButton);
    add(optionButton);
  }

  /**
   * Initialise les champs de connexion.
   */
  private void initConnectionFields() {
    this.usernameField = new HintTextField("Adresse mail/Pseudonyme");
    this.usernameField.setFont(new Font(Launcher.TYPEFACE, 0, 12));
    this.usernameField.setForeground(new Color(156, 153, 151));
    this.usernameField.setCaretColor(new Color(156, 153, 151));
    this.usernameField.setBounds(457, 504, 150, 15);
    this.usernameField.setBorder(null);
    this.usernameField.setOpaque(false);
    this.usernameField.addKeyListener(Controller.getController());

    String username = Controller.getController().getSavedUsername();
    if (username != null)
      this.usernameField.setText(username);

    this.passwordField = new HintPasswordField("Mot de passe");
    this.passwordField.setFont(new Font(Launcher.TYPEFACE, 0, 12));
    this.passwordField.setForeground(new Color(156, 153, 151));
    this.passwordField.setCaretColor(new Color(156, 153, 151));
    this.passwordField.setBounds(457, 544, 150, 15);
    this.passwordField.setBorder(null);
    this.passwordField.setOpaque(false);
    this.passwordField.addKeyListener(Controller.getController());

    add(this.usernameField);
    add(this.passwordField);
  }

  /**
   * Initialise le système de drag'n'drop sur toute la fenêtre.
   */
  private void initDragAndDrop() {
    addMouseListener(Controller.getController());
    addMouseMotionListener(Controller.getController());
  }

  /**
   * Initialise le {@code JLabel} lié aux giwis.
   */
  private void initGiwi() {
    JLabel giwi = new JLabel();
    giwi.setIcon(new GiwiIcon(new Random().nextInt(4) + 1));
    giwi.setBounds(579, 157, 233, 313);
    add(giwi);
  }

  /**
   * Initialise la zone de news.
   */
  private void initNewsArea() {
    JPanel newsPanel = new JPanel();
    newsPanel.setVisible(true);
    newsPanel.setBackground(new Color(235, 234, 230));
    newsPanel.setBounds(29, 206, 505, 246);

    Box box = Box.createVerticalBox();

    NewsItem.loadNews(box);
    JScrollPane news = new JScrollPane(box);

    news.setBackground(new Color(235, 234, 230));

    news.setBorder(null);
    news.setVisible(true);
    news.setBounds(32, 206, 500, 246);
    news.setWheelScrollingEnabled(true);
    news.setOpaque(false);

    news.getVerticalScrollBar().setUI(new ScrollBarDesgin());
    news.getVerticalScrollBar().setUnitIncrement(10);
    news.getVerticalScrollBar().setBackground(new Color(235, 234, 230));

    UIManager.put("ScrollBarUI", "net.minecraft.graphics.LoginForm");

    add(news);

    JLabel estompateur = new JLabel(new ImageIcon(Frame.getImage("fondtransp.png")));
    estompateur.setBounds(29, 415, 488, 37);
    estompateur.setVisible(true);
    JPanel glass = (JPanel) Frame.getFrame().getGlassPane();

    glass.setVisible(true);
    glass.setLayout(null);
    glass.add(estompateur);
  }

  /**
   * Initialise le {@code JLabel} lié à la progression.
   */
  private void initProgression() {
    this.progression = new JLabel("En attente...");
    this.progression.setFont(new Font(Launcher.TYPEFACE, 0, 12));
    this.progression.setForeground(new Color(180, 153, 136));
    this.progression.setBounds(12, 582, 600, this.progression.getPreferredSize().height);

    add(this.progression);
  }

  @Override
  public void paintComponent(Graphics g2) {
    g2.drawImage(Frame.getImage("fond_launcher.png"), 0, 0, Launcher.WINDOW_DIMENSION.width, Launcher.WINDOW_DIMENSION.height, null);
  }

  public void setLabelProgression(String label) {
    this.progression.setText(label);
  }

  public void setFieldsEnabled(boolean enabled) {
    this.usernameField.setEnabled(enabled);
    this.passwordField.setEnabled(enabled);
  }

  public String getUsername() {
    return this.usernameField.getText();
  }

  public String getPassword() {
    return this.passwordField.getText();
  }
}