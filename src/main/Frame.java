package main;

import javax.swing.*;
 
public class Frame {
 
        public Frame() throws InterruptedException{
                JFrame frame = new JFrame();
                frame.add(new Board());
                frame.setTitle("2-D Test Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1200,750);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
        }
        public static void main(String[] args) throws InterruptedException{
                new Frame();
        }
}