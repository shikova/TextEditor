package com.shikova;

import javax.swing.*;

/**
 * Created by walid on 1/3/2017.
 */
public class Text_Area extends JScrollPane {
    private JTextArea textArea;

    public Text_Area() {
        super();
        textArea = new JTextArea();
        setViewportView(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
