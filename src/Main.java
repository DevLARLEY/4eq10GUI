import javax.swing.*;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Main {

    public static JFrame jframe = new JFrame("4eq10");
    public static String exclude = null;
    public static String start = null;
    public static ArrayList solutionList = new ArrayList<>();
    public static ScriptEngineManager manager = new ScriptEngineManager();
    public static ScriptEngine engine = manager.getEngineByName("js");
    public static JTextArea textArea = new JTextArea();
    public static boolean autoLog = false;
    public static int runcount = 0;
    public static JTextField jTextField = new JTextField();
    public static JTextField jTextField2 = new JTextField();

    public static void main(String[] args) throws ScriptException {
        ArrayList<String> output = new ArrayList<>();

        JFrame jframe = new JFrame("4eq10GUI");
        jframe.setResizable(false);
        textArea.setEditable(false);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jpanel = new JPanel();
        JPanel jpanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton jbutton = new JButton();
        JButton jbutton2 = new JButton();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Options");
        helpMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JMenuItem helpItem6 = new JMenuItem();
        helpItem6.setText("Save output as ...");
        helpItem6.setToolTipText("<html><font face=\"sansserif\">Saves the output of<br>the program in a file.</font></html>");
        helpItem6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu.add(helpItem6);
        JMenuItem helpItem3 = new JMenuItem();
        helpItem3.setText("About");
        helpItem3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu.add(helpItem3);
        JMenuItem helpItem5 = new JMenuItem();
        helpItem5.setText("Exit");
        helpItem5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu.add(helpItem5);
        menuBar.add(helpMenu);

        JMenu helpMenu2 = new JMenu("Logging");
        helpMenu2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JMenuItem helpItem4 = new JMenuItem();
        helpItem4.setText("Auto-Log: " + (autoLog ? "Enabled" : "Disabled"));
        helpItem4.setToolTipText("<html><font face=\"sansserif\">Click to " + (!autoLog ? "Enable" : "Disable") + ".<br>Enables whether the program should<br>automatically log the output in a logfile.</font></html>");
        helpItem4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu2.add(helpItem4);
        menuBar.add(helpMenu2);

        jTextField.setForeground(Color.LIGHT_GRAY);
        jTextField.setText("1234");
        jTextField.setColumns(4);
        jTextField.setToolTipText("<html><font face=\"sansserif\">Enter 4 numbers</font></html>");

        jTextField2.setForeground(Color.LIGHT_GRAY);
        jTextField2.setText("+-*/");
        jTextField2.setColumns(4);
        jTextField2.setToolTipText("<html><font face=\"sansserif\">Valid operators to exclude are:<br>+ - * /</font></html>");

        jbutton.setText("Calculate");
        jbutton.setToolTipText("<html><font face=\"sansserif\">Calculate solutions for<br>the input parameters.</font></html>");
        jbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jbutton.setForeground(Color.BLACK);
        jbutton.setBackground(Color.WHITE);

        jbutton2.setText("Clear");
        jbutton2.setToolTipText("<html><font face=\"sansserif\">Clear the output field.</font></html>");
        jbutton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jbutton2.setForeground(Color.BLACK);
        jbutton2.setBackground(Color.WHITE);

        jframe.add(BorderLayout.CENTER, jpanel);

        jpanelBottom.add(jTextField);
        jpanelBottom.add(jTextField2);
        jpanelBottom.add(jbutton);
        jpanelBottom.add(jbutton2);

        jframe.add(BorderLayout.SOUTH, jpanelBottom);
        jframe.add(BorderLayout.NORTH, menuBar);

        JTextArea textArea = new JTextArea(11, 24);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        jpanel.add(scrollPane);

        jTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if(jTextField.getText().contains("1234") && jTextField.getForeground().equals(Color.LIGHT_GRAY)){
                    jTextField.setText(null);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(jTextField.getText().isEmpty()){
                    jTextField.setText("1234");
                    jTextField.setForeground(Color.LIGHT_GRAY);
                }else{
                    jTextField.setForeground(Color.BLACK);
                }
            }
        });

        jTextField2.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if(jTextField2.getText().contains("+-*/") && jTextField2.getForeground().equals(Color.LIGHT_GRAY)){
                    jTextField2.setText(null);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(jTextField2.getText().isEmpty()){
                    jTextField2.setText("+-*/");
                    jTextField2.setForeground(Color.LIGHT_GRAY);
                }else{
                    jTextField2.setForeground(Color.BLACK);
                }
            }
        });

        helpItem4.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        autoLog = !autoLog;
                        updateAutoLog(helpItem4);
                    }
                }
        );

        helpItem5.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jframe.dispose();
                    }
                }
        );

        helpItem6.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String text = textArea.getText();
                        int size = 0;
                        for(String str : text.split("\n")){
                            size++;
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Save output as ...");
                        fileChooser.setToolTipText("<html><font face=\"sansserif\">Select the directory you want to save<br>to and enter your desired file name.</font></html>");
                        fileChooser.setApproveButtonText("Save file at specified location");
                        fileChooser.setFileHidingEnabled(false);
                        fileChooser.setMultiSelectionEnabled(false);
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                        fileChooser.setSelectedFile(new File("out.txt"));
                        if(size > 1){
                            if(fileChooser.showSaveDialog(jframe) == JFileChooser.APPROVE_OPTION){
                                String status = writeTextToFile(fileChooser.getSelectedFile(), text);
                                if(!status.equals("success")){
                                    JOptionPane.showConfirmDialog(jframe, "Saving failed:\n" + status, "4eq10GUI", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }else{
                            int result;
                            if(text.length() > 0){
                                result = JOptionPane.showConfirmDialog(jframe, "There is basically nothing to save.\nDo you still want to save?", "4eq10GUI", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            }else{
                                result = JOptionPane.showConfirmDialog(jframe, "There is nothing to save.\nDo you still want to save?", "4eq10GUI", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            }
                            if(result == JOptionPane.YES_OPTION){
                                if(fileChooser.showSaveDialog(jframe) == JFileChooser.APPROVE_OPTION){
                                    String status = writeTextToFile(fileChooser.getSelectedFile(), text);
                                    if(!status.equals("success")){
                                        JOptionPane.showConfirmDialog(jframe, "Saving failed:\n" + status, "4eq10GUI", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }
                    }
                }
        );

        JFrame jframe2 = new JFrame("About");
        jframe2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jframe2.setResizable(false);
        JLabel j2label = new JLabel();
        j2label.setBorder(new EmptyBorder(10, 20, 0, 20));
        j2label.setText("by LARLEY © 2022-2023");
        JLabel j2label2 = new JLabel();
        j2label2.setBorder(new EmptyBorder(0, 20, 10, 20));
        j2label2.setText("https://github.com/DevLARLEY");
        jframe2.add(BorderLayout.NORTH, j2label);
        jframe2.add(BorderLayout.SOUTH, j2label2);

        helpItem3.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jframe2.pack();
                        jframe2.setLocationRelativeTo(null);
                        jframe2.setVisible(true);
                    }
                }
        );

        jbutton2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        textArea.setText("");
                    }
                }
        );

        jbutton.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        String text = jTextField.getText();
                        if(text.length() == 4){
                            if(areOnlyNumbers(text)){
                                start = text.charAt(0) + "#" + text.charAt(1) + "#" + text.charAt(2) + "#" + text.charAt(3);
                                exclude = jTextField2.getText();
                                textArea.setText("");
                                output.clear();
                                long before = System.nanoTime();
                                if(jTextField.getForeground().equals(Color.LIGHT_GRAY)){
                                    JOptionPane.showMessageDialog(null, "Please enter 4 digits!", "4eq10GUI", JOptionPane.ERROR_MESSAGE);
                                    before = System.nanoTime();
                                }else{
                                    if(jTextField2.getText().contains("+-*/")){
                                        if(!jTextField2.getForeground().equals(Color.LIGHT_GRAY)){
                                            JOptionPane.showMessageDialog(null, "You can't enter\nmore than 3 excludes!", "4eq10GUI", JOptionPane.ERROR_MESSAGE);
                                            before = System.nanoTime();
                                        }else{
                                            exclude = "";
                                            before = System.nanoTime();
                                            output.addAll(calculate());
                                        }
                                    }else{
                                        if(!jTextField2.getForeground().equals(Color.LIGHT_GRAY)){
                                            before = System.nanoTime();
                                            output.addAll(calculate());
                                        }
                                    }
                                }
                                long after = System.nanoTime();
                                for(String out : output){
                                    textArea.setText(textArea.getText() + "Solution found! [ " + out + " ]\n");
                                }
                                if(autoLog){
                                    String startString = "===START===[" + text.charAt(0) + text.charAt(1) + text.charAt(2) + text.charAt(3) + "]===[" + exclude.replace("#", "") + "]===";
                                    if(!stringIsInFile(startString, "4eq10.log")){
                                        FileOutputStream fos;
                                        try{
                                            fos = new FileOutputStream("4eq10.log", true);
                                            fos.write((startString + "\r\n").getBytes());
                                            for(String out : output){
                                                fos.write((out + "\r\n").getBytes());
                                            }
                                            String endString = "===END===[" + text.charAt(0) + text.charAt(1) + text.charAt(2) + text.charAt(3) + "]===[" + exclude.replace("#", "") + "]===";
                                            fos.write((endString + "\r\n").getBytes());
                                            fos.close();
                                        }catch (IOException ex){
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                }
                                long diff = (long) ((after-before)/1000000.0);
                                if(output.size() == 1){
                                    textArea.setText(textArea.getText() + "Found " + output.size() + " solution in " + diff + "ms!");
                                }else{
                                    textArea.setText(textArea.getText() + "Found " + output.size() + " solutions in " + diff + "ms!");
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "Please only enter digits!", "4eq10GUI", JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Please enter 4 digits!", "4eq10GUI", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        );

        jframe.pack();
        jframe.setLocation(dim.width/2-jframe.getSize().width/2, dim.height/2-jframe.getSize().height/2);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

    public static String writeTextToFile(File file, String text){
        try (PrintWriter out = new PrintWriter(file.getAbsoluteFile())) {
            out.println(text);
        } catch (FileNotFoundException ex) {
            return ex.getMessage();
        }
        return "success";
    }

    public static boolean stringIsInFile(String string, String fileName){
        File file = new File(fileName);
        try{
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.equals(string))
                    return true;
            }
        }catch(FileNotFoundException ignore){}
        return false;
    }

    public static void updateAutoLog(JMenuItem item){
        item.setText("Auto-Log: " + (autoLog ? "Enabled" : "Disabled"));
        item.setToolTipText("<html><font face=\"sansserif\">Click to " + (!autoLog ? "Enable" : "Disable") + ".<br>Enables whether the program should<br>automatically log the output in a logfile.</font></html>");
    }

    public static boolean areOnlyNumbers(String string){
        for(char ch : string.toCharArray()){
            if(!Character.isDigit(ch)){
                return false;
            }
        }
        return true;
    }

    public static ArrayList calculate(){
        solutionList.clear();
        if(start != null){
            for(int a = 0; a < 4; a++){
                for(int b = 0; b < 4; b++){
                    switch (a) {
                        case 0:
                            switch (b) {
                                case 1:
                                    start = start.substring(2, 3) + start.substring(1, 2) + start.substring(0, 1) + start.substring(3, start.length());
                                    break;
                                case 2:
                                    start = start.substring(4, 5) + start.substring(1, 4) + start.substring(0, 1) + start.substring(5, start.length());
                                    break;
                                case 3:
                                    start = start.substring(6, start.length()) + start.substring(1, 6) + start.substring(0, 1);
                                    break;
                            }
                            break;
                        case 1:
                            switch (b) {
                                case 0:
                                    start = start.substring(2, 3) + start.substring(1, 2) + start.substring(0, 1) + start.substring(3, start.length());
                                    break;
                                case 2:
                                    start = start.substring(0, 2) + start.substring(4, 5) + start.substring(3, 4) + start.substring(2, 3) + start.substring(5, start.length());
                                    break;
                                case 3:
                                    start = start.substring(0, 2) + start.substring(6, start.length()) + start.substring(3, 4) + start.substring(4, 6) + start.substring(2, 3);
                                    break;
                            }
                            break;
                        case 2:
                            switch (b) {
                                case 0:
                                    start = start.substring(4, 5) + start.substring(1, 2) + start.substring(2, 4) + start.substring(0, 1) + start.substring(5, start.length());
                                    break;
                                case 1:
                                    start = start.substring(0, 2) + start.substring(4, 5) + start.substring(3, 4) + start.substring(2, 3) + start.substring(5, start.length());
                                    break;
                                case 3:
                                    start = start.substring(0, 4) + start.substring(6, start.length()) + start.substring(5, 6) + start.substring(4, 5);
                                    break;
                            }
                            break;
                        case 3:
                            switch (b) {
                                case 0:
                                    start = start.substring(6, start.length()) + start.substring(1, 6) + start.substring(0, 1);
                                    break;
                                case 1:
                                    start = start.substring(0, 2) + start.substring(6, start.length()) + start.substring(3, 6) + start.substring(2, 3);
                                    break;
                                case 2:
                                    start = start.substring(0, 4) + start.substring(6, start.length()) + start.substring(5, 6) + start.substring(4, 5);
                                    break;
                            }
                            break;
                    }
                    for(int e = 0; e < 4; e++){
                        switch (e) {
                            case 0:
                                if(!exclude.contains("+")){
                                    start = start.substring(0, 1) + "+" + start.substring(2, start.length());
                                    break;
                                }
                            case 1:
                                if(!exclude.contains("-")){
                                    start = start.substring(0, 1) + "-" + start.substring(2, start.length());
                                    break;
                                }
                            case 2:
                                if(!exclude.contains("*")){
                                    start = start.substring(0, 1) + "*" + start.substring(2, start.length());
                                    break;
                                }
                            case 3:
                                if(!exclude.contains("/")){
                                    start = start.substring(0, 1) + "/" + start.substring(2, start.length());
                                    break;
                                }
                        }
                        for(int f = 0; f < 4; f++){
                            switch (f) {
                                case 0:
                                    if(!exclude.contains("+")){
                                        start = start.substring(0, 3) + "+" + start.substring(4, start.length());
                                        break;
                                    }
                                case 1:
                                    if(!exclude.contains("-")){
                                        start = start.substring(0, 3) + "-" + start.substring(4, start.length());
                                        break;
                                    }
                                case 2:
                                    if(!exclude.contains("*")){
                                        start = start.substring(0, 3) + "*" + start.substring(4, start.length());
                                        break;
                                    }
                                case 3:
                                    if(!exclude.contains("/")){
                                        start = start.substring(0, 3) + "/" + start.substring(4, start.length());
                                        break;
                                    }
                            }
                            for(int g = 0; g < 4; g++){
                                switch (g) {
                                    case 0:
                                        if(!exclude.contains("+")){
                                            start = start.substring(0, 5) + "+" + start.substring(6, start.length());
                                            break;
                                        }
                                    case 1:
                                        if(!exclude.contains("-")){
                                            start = start.substring(0, 5) + "-" + start.substring(6, start.length());
                                            break;
                                        }
                                    case 2:
                                        if(!exclude.contains("*")){
                                            start = start.substring(0, 5) + "*" + start.substring(6, start.length());
                                            break;
                                        }
                                    case 3:
                                        if(!exclude.contains("/")){
                                            start = start.substring(0, 5) + "/" + start.substring(6, start.length());
                                            break;
                                        }
                                }
                                for(int h = 0; h < 8; h++){
                                    switch (h) {
                                        case 0:
                                            break;
                                        case 1:
                                            if(!exclude.contains("X")){
                                                start = "(" + start.substring(0, 3) + ")" + start.substring(3, 4) + "(" + start.substring(4, 7) + ")";
                                                break;
                                            }
                                        case 2:
                                            if(!exclude.contains("X")){
                                                start = start.substring(0, 6) + start.substring(7, 10);
                                                break;
                                            }
                                        case 3:
                                            if(!exclude.contains("X")){
                                                start = start.substring(1, 4) + start.substring(5, 6) + "(" + start.substring(6, 9) + ")";
                                                break;
                                            }
                                        case 4:
                                            if(!exclude.contains("X")){
                                                start = start.substring(0, 2) + "(" + start.substring(2, 4) + start.substring(5, 6) + ")" + start.substring(6, 8);
                                                break;
                                            }
                                        case 5:
                                            if(!exclude.contains("X")){
                                                start = start.substring(0, 6) + start.substring(7, 9) + ")";
                                                break;
                                            }
                                        case 6:
                                            if(!exclude.contains("X")){
                                                start = "(" + start.substring(0, 2) + start.substring(3, 6) + ")" + start.substring(6, 8);
                                                break;
                                            }
                                        case 7:
                                            if(!exclude.contains("X")){
                                                start = start.substring(1, 6) + start.substring(7, 9);
                                                break;
                                            }
                                    }
                                    String sol = null;
                                    try{
                                        sol = engine.eval(start).toString();
                                    }catch(ScriptException ex){
                                        JOptionPane.showConfirmDialog(jframe, "The ScriptEngine encountered an\nError while calculating.", "4eq10GUI", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                    }
                                    if(sol != null){
                                        if(sol.contains("NaN") || sol.contains("Infinity")){
                                            sol = "0";
                                        }
                                    }
                                    Double solutuo = Double.parseDouble(sol);
                                    runcount += 1;
                                    if(solutuo == 10){
                                        if(!solutionList.contains(start)){
                                            solutionList.add(start);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return solutionList;
    }
}
