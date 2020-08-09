import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class P1GUI {
    JFrame frame;
    JPanel panel;
    FlowLayout flow;
    GridLayout grid;
    ButtonGroup group;
    JTextField textField; //Given that the nature of this project, a textfield is required
    Stack numberStack;
    String express;
    boolean expressFlag;
    public P1GUI(){
        numberStack = new Stack();
        expressFlag = false;
    }


    public void generateWindow (List<String> windowComps)
    {
        frame = new JFrame(windowComps.get(0));
        frame.setSize(Integer.parseInt(windowComps.get(1)), Integer.parseInt(windowComps.get(2)));
    }

    public void endWindow()
    {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void generatePanel()
    {
        panel = new JPanel();
    }

    public void endPanel()
    {
        frame.add(panel);

    }

    public void generateFlow(int proccess) {
        flow = new FlowLayout();
        if (proccess == 1) //Frame
            frame.setLayout( flow);
        else if(proccess ==2) //Panel
            panel.setLayout(flow);
    }

    public void generateGrid(int proccess, List<String> gridComp) {
        int len = gridComp.size();
        if (len == 2)
        {
            grid = new GridLayout(Integer.parseInt(gridComp.get(0)),Integer.parseInt(gridComp.get(1)));
            if (proccess == 1) //Frame
                frame.setLayout( grid);
            else if(proccess ==2) //Panel
                panel.setLayout(grid);
        }
        else if  (len == 4)
        {
            grid = new GridLayout(Integer.parseInt(gridComp.get(0)),Integer.parseInt(gridComp.get(1)),Integer.parseInt(gridComp.get(2)),Integer.parseInt(gridComp.get(3)));
            if (proccess == 1) //Frame
                frame.setLayout( grid);
            else if(proccess ==2) //Panel
                panel.setLayout(grid);
        }

    }

    public void generateButton(int proccess, String text){
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evaluate(text);
                // display/center the jdialog when the button is pressed
                //JDialog d = new JDialog(frame, button.getText(), true);
                //d.setLocationRelativeTo(frame);
                //d.setVisible(true);
            }
        });
        if(proccess == 1)
            frame.add(button);
        else if (proccess == 2)
            panel.add(button);

    }

    public void generateLabel(int proccess, String text)
    {
        JLabel label = new JLabel(text);
        if(proccess == 1)
            frame.add(label);
        else if (proccess == 2)
            panel.add(label);

    }

    public void generateTextField(int proccess, int num){
        textField = new JTextField(num);
        textField.setText("0");
        if(proccess == 1)
            frame.add(textField);
        else if (proccess == 2)
            panel.add(textField);

    }

    public void generateRadio(int proccess, String text){
        JRadioButton rButton= new JRadioButton(text);
        group.add(rButton);
        if(proccess == 1)
            frame.add(rButton);
        else if (proccess == 2)
            panel.add(rButton);
    }

    public void generateGroup(){
         group = new ButtonGroup();
    }

    private void evaluate(String element)
    {
        String regexSymbol = "\\+|\\-|\\*|\\/|\\=";
        String regexAdd = "\\+";
        String regexMultiply = "\\*";
        String regexSubtract = "\\-";
        String regexDivide = "\\/";
        String regexEquals = "\\=";
        String regexNum = "\\d+$";

        if (element.matches(regexNum))
        {
            if(!numberStack.empty() && !expressFlag)
            {
                String tempElement = numberStack.pop().toString() + element;
                numberStack.push(tempElement);
                textField.setText(tempElement);
            }
            else
            {
                numberStack.push(element);
                textField.setText(element);
            }
            expressFlag = false;
        }

        if(element.matches(regexSymbol))
        {

            expressFlag = true;
            if (numberStack.size() == 2 && element.matches(regexEquals))
            {
                Equations eq = new Equations();
                int result;
                int num2 = Integer.parseInt((String) numberStack.pop());
                int num1 = Integer.parseInt((String) numberStack.pop());
                if (express.matches(regexAdd))
                {
                    result = eq.addition(num1,num2);
                    textField.setText(result+"");
                }
                else if (express.matches(regexSubtract))
                {
                    result = eq.subtract(num1,num2);
                    textField.setText(result+"");
                }
                else if (express.matches(regexMultiply))
                {
                    result = eq.multiply(num1,num2);
                    textField.setText(result+"");
                }
                else if (express.matches(regexDivide))
                {
                    if (num2 == 0)
                    {
                        textField.setText("Cannot Divide by 0");

                    }
                    else
                    {
                        result = eq.divide(num1,num2);
                        textField.setText(result+"");
                    }


                }
            }
            else
            {
                express = element;
            }
        }

    }


}
