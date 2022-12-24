import javax.swing.*;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.border.EmptyBorder;

public class Main {

    public static JFrame jframe = new JFrame("4eq10");
    public static String exclude = null;
    public static String start = null;
    public static ArrayList solutionList = new ArrayList<>();
    public static ScriptEngineManager manager = new ScriptEngineManager();
    public static ScriptEngine engine = manager.getEngineByName("js");
    public static JTextArea textArea = new JTextArea();
    public static int runcount = 0;
    public static void main(String[] args) throws ScriptException {

        ArrayList<String> output = new ArrayList<>();

        JFrame jframe = new JFrame("4eq10");
        jframe.setResizable(false);
        textArea.setEditable(false);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jpanel = new JPanel();
        JPanel jpanelBottom = new JPanel();
        JLabel jlabel1 = new JLabel();
        JLabel jlabel2 = new JLabel();
        JButton jbutton = new JButton();
        JButton jbutton2 = new JButton();
        JTextField jTextField = new JTextField();
        JTextField jTextField2 = new JTextField();
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Options");
        helpMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JMenuItem helpItem2 = new JMenuItem();
        helpItem2.setText("Help");
        helpItem2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu.add(helpItem2);
        JMenuItem helpItem3 = new JMenuItem();
        helpItem3.setText("About");
        helpItem3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu.add(helpItem3);
        menuBar.add(helpMenu);
        jTextField.setColumns(4);
        jTextField2.setColumns(5);
        jlabel1.setText("Numbers: ");
        jlabel2.setText("Exclude: ");
        jbutton.setText("Calculate");
        jbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jbutton2.setText("Clear");
        jbutton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jframe.add(BorderLayout.CENTER, jpanel);
        jpanelBottom.add(jlabel1);
        jpanelBottom.add(jTextField);
        jpanelBottom.add(jlabel2);
        jpanelBottom.add(jTextField2);
        jpanelBottom.add(jbutton);
        jpanelBottom.add(jbutton2);
        jframe.add(BorderLayout.SOUTH, jpanelBottom);
        jframe.add(BorderLayout.NORTH, menuBar);
        JTextArea textArea = new JTextArea(11, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        jpanel.add(scrollPane);

        JFrame jframe3 = new JFrame("Help");
        jframe3.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jframe3.setResizable(false);
        JLabel j3label = new JLabel();
        j3label.setText("Enter your Numbers in: \"Numbers\"");
        j3label.setBorder(new EmptyBorder(20, 20, 0, 20));
        JLabel j3label2 = new JLabel();
        j3label2.setText("Enter the operators to exclude in \"Exclude\"");
        j3label2.setBorder(new EmptyBorder(0, 20, 20, 20));
        jframe3.add(BorderLayout.NORTH, j3label);
        jframe3.add(BorderLayout.SOUTH, j3label2);

        helpItem2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jframe3.pack();
                        jframe3.setVisible(true);
                    }
                }
        );

        JFrame jframe2 = new JFrame("About");
        jframe2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jframe2.setResizable(false);
        JLabel j2label = new JLabel();
        j2label.setBorder(new EmptyBorder(10, 20, 0, 20));
        j2label.setText("by LARLEY © 2022");
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
                        if(jTextField.getText().length() == 4){
                            start = jTextField.getText().charAt(0) + "#" + jTextField.getText().charAt(1) + "#" + jTextField.getText().charAt(2) + "#" + jTextField.getText().charAt(3);
                            exclude = jTextField2.getText();
                            textArea.setText("");
                            output.clear();
                            output.addAll(calculate());
                            for(String out : output){
                                textArea.setText(textArea.getText() + "Solution found! [ " + out + " ]\n");
                            }
                            textArea.setText(textArea.getText() + output.size() + " Solutions found!\n");
                        }else{
                            textArea.setText(textArea.getText() + "Please fill out all Number fields!\n");
                        }
                    }
                }
        );

        jframe.pack();
        jframe.setVisible(true);
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
                                    try {
                                        sol = engine.eval(start).toString();
                                    } catch (ScriptException ex) {
                                        System.out.println("error");
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