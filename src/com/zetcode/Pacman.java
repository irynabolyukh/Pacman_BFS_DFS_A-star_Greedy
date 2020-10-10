package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pacman extends JFrame {

    public Pacman() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

//        PathSearcherGreedy searcherBFS = new PathSearcherGreedy();
//        searcherBFS.searchForPath();
//        System.out.println("ALL VISITED:");
//        for(int i=0;i<searcherBFS.path.size();i++){
//            System.out.println(searcherBFS.path.get(i));
//        }
//        System.out.println("\n\nRIGHT PATH:");
//        for(int i=0;i<searcherBFS.path_stack.size();i++){
//            System.out.println(searcherBFS.path_stack.get(i));
//        }
//
        EventQueue.invokeLater(() -> {

            Pacman ex = new Pacman();
            ex.setVisible(true);
        });
    }
}
