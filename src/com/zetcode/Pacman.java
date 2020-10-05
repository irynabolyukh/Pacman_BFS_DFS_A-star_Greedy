package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pacman extends JFrame {

//    public Pacman() {
//
//        initUI();
//    }
//
//    private void initUI() {
//
//        add(new Board());
//
//        setTitle("Pacman");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(380, 420);
//        setLocationRelativeTo(null);
//    }

    public static void main(String[] args) {

        PathSearcherDFS searcherDFS = new PathSearcherDFS();
        searcherDFS.searchForPath();
        System.out.println("ALL VISITED:");
        for(int i=0;i<searcherDFS.path.size();i++){
            System.out.println(searcherDFS.path.get(i));
        }
        System.out.println("\n\nRIGHT PATH:");
        for(int i=0;i<searcherDFS.path_stack.size();i++){
            System.out.println(searcherDFS.path_stack.get(i));
        }
//        EventQueue.invokeLater(() -> {
//
//            Pacman ex = new Pacman();
//            ex.setVisible(true);
//        });
    }
}
