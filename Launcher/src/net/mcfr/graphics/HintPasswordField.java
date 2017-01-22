package net.mcfr.graphics;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

/**
 * Champ de texte formatté pour la saisie de mots de passe.
 *
 * @author Minecraft-France
 *
 */
public class HintPasswordField extends JPasswordField implements FocusListener {
  private static final long serialVersionUID = -7984753639981198024L;

  private String hint;
  private boolean showingHint;

  public HintPasswordField(String hint) {
    super(hint);
    this.hint = hint;
    this.showingHint = true;
    super.addFocusListener(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    if (this.getText().isEmpty()) {
      super.setText("");
      this.showingHint = false;
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (this.getText().isEmpty()) {
      super.setText(this.hint);
      this.showingHint = true;
    }
  }

  @Override
  public String getText() {
    return !this.showingHint ? String.copyValueOf(super.getPassword()) : "";
  }
}