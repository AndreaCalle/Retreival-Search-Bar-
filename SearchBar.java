/*
Andrea Calle 
Using pagerank alorgthm 
this is the GUI Search bar 
must be completed 
1. need a section where word research result show
2. buttons need to connect the fumctions
3. will have a loading bar 
4. need title 
5. once connect need to mak the method none static 
*/

import javax.swing.*;
import java.awt.*;

class SearchBar{
      public static void main(String args[]){
           JFrame frame = new JFrame("My First GUI");
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setSize(300,300);
          JButton button1 = new JButton("Button 1");
          JButton button2 = new JButton("Button 2");


          JPanel panel = new JPanel(); // the panel is not visible in output
          JLabel label = new JLabel("Enter Text");
          JTextField tf = new JTextField(10); // accepts upto 10 characters
          JButton send = new JButton("Send");
          JButton reset = new JButton("Reset");
          panel.add(label); // Components Added using Flow Layout
          panel.add(label); // Components Added using Flow Layout
          panel.add(tf);
          panel.add(send);
          panel.add(reset);

          frame.getContentPane().add(BorderLayout.SOUTH, panel);
          frame.getContentPane().add(button1);
          frame.getContentPane().add(button2);
          frame.setVisible(true);
     }
}