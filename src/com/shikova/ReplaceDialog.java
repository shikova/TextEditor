package com.shikova;


import javax.swing.*;


public class ReplaceDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonreplace;
    private JButton buttonClose;
    private JButton buttonFind;
    private JCheckBox matchCase;
    private JTextField findText;
    private JTextField replaceText;
    private JButton replaceAllButton;
    private JTextArea textArea;
    private int index;
    public ReplaceDialog(TextEditor parent) {
        setContentPane(contentPane);
        setModal(true);
        setSize(400,250);
        this.textArea=parent.getText_area().getTextArea();
        matchCase.setSelected(false);
        index=0;

        addListener();
    }


    private void addListener()
    {
        buttonFind.addActionListener(e->find2());
        buttonreplace.addActionListener(e->replace());
        buttonClose.addActionListener(e->dispose());
        replaceAllButton.addActionListener(e->replaceAll());
    }

    private void replace() {

            if (!textArea.getSelectedText().equals(""))
                textArea.replaceSelection(replaceText.getText());
            else
                JOptionPane.showMessageDialog(this, "Nothing Found", "End of file",JOptionPane.INFORMATION_MESSAGE);
    }

    private void replaceAll() {
        if (matchCase.isSelected())
        {
           textArea.setText(textArea.getText().replaceAll(this.findText.getText(),this.replaceText.getText()));
         }
         else {
            textArea.setText(textArea.getText().toLowerCase()
                    .replaceAll(this.findText.getText().toLowerCase(),
                                this.replaceText.getText().toLowerCase()));
        }

    }

    private void find2() {
        if (matchCase.isSelected())
        {
            if (!textArea.getText().contains(findText.getText())) {
                JOptionPane.showMessageDialog(this, "Nothing Found", "End of file",JOptionPane.INFORMATION_MESSAGE);
            } else {
                int start = textArea.getText().indexOf(findText.getText(), index);
                index = start + findText.getText().length();
                textArea.select(start, index);
                if (!textArea.getSelectedText().equals(findText.getText())) {
                    textArea.select(-1, 0);
                    index=0;
                }
            }
        }else
        {
            if (!textArea.getText().toLowerCase().contains(findText.getText().toLowerCase())) {
                JOptionPane.showMessageDialog(this, "Nothing Found", "End of file",JOptionPane.INFORMATION_MESSAGE);
            } else {
                int start = textArea.getText().toLowerCase().indexOf(findText.getText().toLowerCase(), index);
                index = start + findText.getText().toLowerCase().length();
                textArea.select(start, index);
                if (!textArea.getSelectedText().toLowerCase().equals(findText.getText().toLowerCase())) {
                    textArea.select(-1, 0);
                    index=0;
                }
            }
        }

    }
}
