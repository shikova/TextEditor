
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by walid on 1/2/2017.
 */
public class TextEditor extends JFrame {
    private Text_Area text_area;
    private JMenuBar menuBar;
    private JLabel statuLabelString, label_LnCl;
    private File file;
    private boolean fileSaved;

    public TextEditor() {
        super(Constants.APP_NAME);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUpFileMenu();
        setUpEditMenu();
        setUpOptionMenu();
        setUpHelpMenu();
        setUpStatuBar();
        setUpCenter();
    }

    private void setUpOptionMenu()
    {

        //todo
        JMenu option=new JMenu("Option");
        JMenuItem  color=new JMenuItem("Color");
        JMenuItem font=new JMenuItem("Font");
        JMenuItem taille=new JMenuItem("Taille");

        option.add(color);
        option.add(font);
        option.add(taille);

        //listener

        color.addActionListener(e->changeColor());
        font.addActionListener(e->changeFont());
        taille.addActionListener(e->changeTaille());

        menuBar.add(option);

    }

    private void changeTaille() {

    }

    private void changeFont() {

    }

    private void changeColor() {

    }

    private void setUpEditMenu()
    {
        //todo
        JMenu edit=new JMenu("Edit");
        JMenuItem copy=new JMenuItem("Copy");
        JMenuItem paste=new JMenuItem("Paste");
        JMenuItem cute=new JMenuItem("Cute");
        JMenuItem selectAll=new JMenuItem("Select All");
        JMenuItem finde=new JMenuItem("Finde");
        JMenuItem replace=new JMenuItem("Replace");
        //add
        edit.add(copy);
        edit.add(paste);
        edit.add(cute);
        edit.add(selectAll);
        edit.add(finde);
        edit.add(replace);
        //addListener

        copy.addActionListener(e->copyText());
        paste.addActionListener(e->pasteText());
        cute.addActionListener(e->cuteText());
        selectAll.addActionListener(e->selectAllText());
        finde.addActionListener(e->findeText());
        replace.addActionListener(e->replaceText());
        menuBar.add(edit);
    }

    private void replaceText() {
    }

    private void cuteText() {

    }

    private void selectAllText() {
    }

    private void findeText() {
    }


    private void pasteText() {
    }

    private void copyText() {
    }

    private void setUpHelpMenu()
    {
        JMenu _help=new JMenu("Help");
        JMenuItem about=new JMenuItem("About");
        JMenuItem help=new JMenuItem("Help");
        
        //add
        _help.add(about);
        _help.add(help);
        
        //Listener
        
        about.addActionListener(e->aboutApp());
        help.addActionListener(e->helpReq());
        menuBar.add(_help);
        
    }

    private void helpReq() {
    }

    private void aboutApp() {
        JOptionPane.showMessageDialog(null,Constants.ABOUT_TEXT,"About",JOptionPane.INFORMATION_MESSAGE);
    }

    private void setUpStatuBar() {

        //Panel of status bar
        JPanel status = new JPanel();
        status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        status.setPreferredSize(new Dimension(getWidth(), 16));
        status.setLayout(new BorderLayout());
        statuLabelString = new JLabel();
        statuLabelString.setHorizontalAlignment(SwingConstants.LEFT);
        status.add(statuLabelString, BorderLayout.WEST);
        label_LnCl = new JLabel();
        label_LnCl.setHorizontalAlignment(SwingConstants.LEFT);
        status.add(label_LnCl, BorderLayout.EAST);
        add(status, BorderLayout.SOUTH);


    }

    private void setUpCenter() {
        text_area = new Text_Area();
        text_area.getTextArea().addCaretListener(e -> {
            JTextArea editArea = (JTextArea) e.getSource();
            int linenum = 1;
            int columnnum = 1;

            try {
                int caretpos = editArea.getCaretPosition();
                linenum = editArea.getLineOfOffset(caretpos);
                columnnum = caretpos - editArea.getLineStartOffset(linenum);
                linenum += 1;
                columnnum += 1;
            } catch (Exception er) {

            }
            updateLnCl(linenum, columnnum);
        });

        text_area.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setFileSaved(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setFileSaved(false);

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setFileSaved(false);

            }
        });
        add(text_area, BorderLayout.CENTER);
        this.setTitle("Untitled - " + Constants.APP_NAME);
        file = null;
        setFileSaved(false);
    }

    private void updateLnCl(int linenum, int columnnum) {

        label_LnCl.setText(String.valueOf(linenum) + ':' + String.valueOf(columnnum));
    }

//init the File Menu
    private void setUpFileMenu() {
        //init
        menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem _new = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem save_as = new JMenuItem("Save As");
        JMenuItem print = new JMenuItem("Print");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(open);
        file.add(_new);
        file.add(save);
        file.add(save_as);
        file.add(print);
        file.add(exit);
        //Actions
        open.addActionListener(e -> openFile());
        _new.addActionListener(e -> newFile());
        save.addActionListener(e -> saveFile());
        save_as.addActionListener(e -> saveFileAs("Save file as ..."));
        print.addActionListener(e -> printFile());

        exit.addActionListener(e -> {
            closeWindowTest();
        });
        //add
        menuBar.add(file);
        add(menuBar, BorderLayout.NORTH);
        addWindowListener(new WindowEvent());
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                text_area.getTextArea().setText(readFile(file));
                //ToDo change State.
                setFileSaved(true);
                this.setTitle(file.getName() + " - " + Constants.APP_NAME);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private String readFile(File file) throws FileNotFoundException {
        String S = "";

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            S += scanner.nextLine() + '\n';

        }
        return S;

    }

    private void newFile() {
        text_area.getTextArea().setText("");
        this.setTitle("Untitled - " + Constants.APP_NAME);
        file = null;
        //todo this.changed = true;
        setFileSaved(false);
    }

    private void saveFile() {

        if (!fileSaved) {
            if (JOptionPane.showConfirmDialog(null,
                    "the file has changed. You want to save it ?", "Save file",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                return;

        }
        if (file == null) {
            saveFileAs("Save");
            return;
        }


        String s = text_area.getTextArea().getText();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(s);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setFileSaved(true);
    }

    private void saveFileAs(String dialogtitle) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogtitle);
        if (dialog.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = dialog.getSelectedFile();
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(text_area.getTextArea().getText());
            setFileSaved(true);
            setTitle(file.getName() + " - " + Constants.APP_NAME);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "File not Found", JOptionPane.WARNING_MESSAGE);
        }


    }

    private void printFile() {
        try {
            boolean done = text_area.getTextArea().print();
            if (done) {
                JOptionPane.showMessageDialog(null, "Done Printing..", "Printer", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Printer", JOptionPane.WARNING_MESSAGE);

        }
    }

    private void setFileSaved(boolean fileSaved) {
        this.fileSaved = fileSaved;
        try {
            if (this.fileSaved) {
                statuLabelString.setText(Constants.STAT_FILE_SAVED);
            } else {
                statuLabelString.setText(Constants.STAT_FILE_NOT_SAVED);
            }
        } catch (Exception e) {
            //TODO
        }
    }

    private void closeWindowTest() {
        if (!fileSaved)
            saveFile();
        System.exit(0);
    }

    class WindowEvent implements WindowListener {
        @Override
        public void windowOpened(java.awt.event.WindowEvent e) {

        }

        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
        closeWindowTest();
        }

        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {

        }

        @Override
        public void windowIconified(java.awt.event.WindowEvent e) {

        }

        @Override
        public void windowDeiconified(java.awt.event.WindowEvent e) {

        }

        @Override
        public void windowActivated(java.awt.event.WindowEvent e) {

        }

        @Override
        public void windowDeactivated(java.awt.event.WindowEvent e) {

        }
    }
}































