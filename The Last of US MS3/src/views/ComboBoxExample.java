package views;
import javax.swing.*;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;

import java.awt.FlowLayout;
import java.util.ArrayList;

public class ComboBoxExample {
    public static void main(String[] args) {
        ArrayList<Integer> heroes = new ArrayList<>();
        heroes.add(1);
        heroes.add(2);
        heroes.add(3);
        
        System.out.println(heroes);
        heroes.add(25);
        System.out.println(heroes);

    }

   
}
