/*
Andrea Calle 
Using pagerank alorgthm 
this is the GUI Search bar 
must be completed 
1. need a section where word research result show
2. buttons need to connect the fumctions
3. will have a loading bar 
4. need a radio buttom for 
5.create a text box for input 
*/
/*
not compile yet 
trying to use pagerank 
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.* ; 


class SearchBar{
      public static void main(String args[]){
          Main test = new Main() ; 
          //test.
          JFrame frame = new JFrame("Search query");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(300,300);
          JButton button1 = new JButton("search");
          //JButton button2 = new JButton("Button 2");
          JRadioButton check = new JRadioButton();

          JPanel panel = new JPanel(); // the panel is not visible in output
          JLabel label = new JLabel("Enter Text");
          JTextField tf = new JTextField(10); // accepts upto 10 characters
          JButton send = new JButton("Send");
     //textField.addActionListener(this); 

          JButton reset = new JButton("Reset");
          panel.add(label); // Components Added using Flow Layout
          panel.add(label); // Components Added using Flow Layout
          panel.add(tf);
          panel.add(send);
          panel.add(reset);

          send.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent evt) {
                    String text = tf.getText();
                    test.sendText(text); 
                    //System.out.println(text);

                } 
          }  );

          frame.getContentPane().add(BorderLayout.SOUTH, panel);
          frame.getContentPane().add(button1);
          frame.getContentPane().add(button2);
          frame.setVisible(true);
     }
/*
     public void actionPerformed(ActionEvent evt) {
          String text = tf.getText();
          System.out.println(text);
         // textArea.append(text + newline);
          //tf.selectAll();
      }
      */
}