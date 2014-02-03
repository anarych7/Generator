package Generator;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GeneratorPanel extends JFrame {

    private JPanel panelMain;
    private JCheckBox checkLowerLetters;
    private JCheckBox checkCapitalLetters;
    private JCheckBox checkNumbersLetters;
    private JCheckBox checkSpecialLetters;
    private JComboBox comboLenght;
    private JButton buttonGenerate;
    private JTextField passwdField;
    private JTextField md5Field;
    private JTextField sha1Field;
    private JTextField sha256Field;
    private Generator generator;
    private String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String NUMBERS_LETTERS = "1234567890";
    private String SPECIAL_LETTERS = "<>?:\\\"|{}~!@#$%^&*()_-+=";

    public GeneratorPanel() {
        super("Генератор паролей");
        setContentPane(panelMain);
        pack();
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        passwdField.setEditable(false);
        md5Field.setEditable(false);
        sha1Field.setEditable(false);
        sha256Field.setEditable(false);

        generator = new Generator();

        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String setLetters = "";
                if (checkLowerLetters.isSelected()) setLetters += LOWER_LETTERS;
                if (checkCapitalLetters.isSelected()) setLetters += CAPITAL_LETTERS;
                if (checkNumbersLetters.isSelected()) setLetters += NUMBERS_LETTERS;
                if (checkSpecialLetters.isSelected()) setLetters += SPECIAL_LETTERS;
                if (!setLetters.equals("")) {
                    int lenght = Integer.parseInt(comboLenght.getItemAt(comboLenght.getSelectedIndex()).toString());

                    String password = generator.getPassword(lenght, setLetters);
                    String md5 = generator.getHash(password, "MD5");
                    String sha1 = generator.getHash(password, "SHA-1");
                    String sha256 = generator.getHash(password, "SHA-256");

                    passwdField.setText(password);
                    md5Field.setText(md5);
                    sha1Field.setText(sha1);
                    sha256Field.setText(sha256);
                } else {
                    JOptionPane.showMessageDialog(null, "Не выбран ни один набор символов!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        passwdField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                copyToClipboardByClick(passwdField);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cancelSelection(passwdField);
            }
        });
        md5Field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                copyToClipboardByClick(md5Field);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cancelSelection(md5Field);
            }
        });
        sha1Field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                copyToClipboardByClick(sha1Field);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cancelSelection(sha1Field);
            }
        });
        sha256Field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                copyToClipboardByClick(sha256Field);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cancelSelection(sha256Field);
            }
        });

        int length = (int) Math.pow(3, 10);
        for (int index = 0; index < length; index++) {
            comboLenght.addItem(index + 1);
        }
        comboLenght.setSelectedIndex(16 - 1);

        setContentPane(this.panelMain);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(this.panelMain);
        buttonGenerate.requestFocus();
        setVisible(true);
    }

    private void copyToClipboardByClick(Object object) {
        JTextField jTextField = (JTextField) object;
        jTextField.selectAll();
        String passwd = jTextField.getText();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(passwd);
        clipboard.setContents(selection, selection);
    }

    private void cancelSelection(Object object) {
        JTextField jTextField = (JTextField) object;
        jTextField.setSelectionStart(passwdField.getText().length());
    }
}
