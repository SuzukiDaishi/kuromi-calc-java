
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/// FIXME: culcMemoryが "123---1234" のような場合や "134+123-" の場合は計算ができないよ
/// FIXME: JavaScriptを使わないで計算させる

public class Dentaku extends JFrame {
    
    JPanel contentPane = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JTextField resultText = new JTextField("");

    String culcMemory = "";
    
    Dentaku(String title) {
        setTitle(title);
        setSize(300, 250);
        setLocationRelativeTo(null);
        contentPane.setLayout(borderLayout1);
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        resultText.setEditable(false);
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(resultText, BorderLayout.NORTH);
        
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(5, 4));
        contentPane.add(keyPanel, BorderLayout.CENTER);

        keyPanel.add(new JButton(""), 0);
        keyPanel.add(new JButton(""), 1);
        keyPanel.add(new DeleteButton(resultText), 2);
        keyPanel.add(new ClearButton(this), 3);
        keyPanel.add(new NamberButton(7, resultText), 4);
        keyPanel.add(new NamberButton(8, resultText), 5);
        keyPanel.add(new NamberButton(9, resultText), 6);
        keyPanel.add(new FormulaButton(this, "/"), 7);
        keyPanel.add(new NamberButton(4, resultText), 8);
        keyPanel.add(new NamberButton(5, resultText), 9);
        keyPanel.add(new NamberButton(6, resultText), 10);
        keyPanel.add(new FormulaButton(this, "*"), 11);
        keyPanel.add(new NamberButton(1, resultText), 12);
        keyPanel.add(new NamberButton(2, resultText), 13);
        keyPanel.add(new NamberButton(3, resultText), 14);
        keyPanel.add(new FormulaButton(this, "-"), 15);
        keyPanel.add(new NamberButton(0, resultText), 16);
        keyPanel.add(new JButton(""), 17);
        keyPanel.add(new FormulaButton(this, "+"), 18);
        keyPanel.add(new EqualButton(this), 19);
    }
    
}

class EqualButton extends JButton implements ActionListener {

    Dentaku dentaku;

    EqualButton(Dentaku dentaku) {
        super("=");
        this.dentaku = dentaku;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String culcStr = dentaku.culcMemory + dentaku.resultText.getText();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        System.out.println(culcStr);
        try {
            var res = engine.eval(culcStr).toString();
            dentaku.resultText.setText(res);
        } catch (ScriptException e1) {
        }
    }
}

class NamberButton extends JButton implements ActionListener {
    JTextField outTextField;
    int number;

    NamberButton(int number, JTextField outTextField) {
        super(""+number);
        this.outTextField = outTextField;
        this.addActionListener(this);
        this.number = number;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var str = outTextField.getText();
        outTextField.setText(str+number);
    }
}

class DeleteButton extends JButton implements ActionListener {
    JTextField outTextField;

    DeleteButton(JTextField outTextField) {
        super("del");
        this.outTextField = outTextField;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.outTextField.setText("");
    }
}

class FormulaButton extends JButton implements ActionListener {
    Dentaku dentaku;
    String formula;

    FormulaButton(Dentaku dentaku, String formula) {
        super(formula);
        this.dentaku = dentaku;
        this.formula = formula;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dentaku.culcMemory += dentaku.resultText.getText() + formula ;
        System.out.println(dentaku.culcMemory);
        dentaku.resultText.setText("");
    }   
}

class ClearButton extends JButton implements ActionListener { 
    Dentaku dentaku;

    ClearButton(Dentaku dentaku) {
        super("C");
        this.dentaku = dentaku;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dentaku.culcMemory = "" ;
        dentaku.resultText.setText("");
    }
}

