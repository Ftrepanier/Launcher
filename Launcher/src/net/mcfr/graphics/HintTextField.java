package net.mcfr.graphics;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {
  private static final long serialVersionUID = 4722760230423673235L;

  private String hint;
  private boolean showingHint;

  public HintTextField(String hint) {
    super(hint);
    this.hint = hint;
    this.showingHint = true;
    super.addFocusListener(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    if (getText().isEmpty()) {
      super.setText("");
      this.showingHint = false;
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (getText().isEmpty()) {
      super.setText(this.hint);
      this.showingHint = true;
    }
  }

  @Override
  public String getText() {
    return this.showingHint ? "" : super.getText();
  }

  @Override
  public void setText(String text) {
    super.setText(text);
    this.showingHint = false;
  }
}