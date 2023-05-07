package desginpatterns.observer;

import Classes.Cours;
import Classes.Local;

import java.time.LocalDate;

public class Ecole {

    public static void main(String[] args) {
        Cours c = new Cours(1,"Art",4);
        Cours c2 = new Cours(2,"Compta",8);

        Local l = new Local(1,"F78",20,"Moyenne taille");
        Local l2 = new Local(2,"A14",30,"Grande taille");
        Local l3 = new Local(3,"D22",10,"Petite taille");

        SessionCours sc1 = new SessionCours(1, LocalDate.of(2023,05,07),LocalDate.of(2023,05,15),15,c,l);
        SessionCours sc2 = new SessionCours(2, LocalDate.of(2023,05,15),LocalDate.of(2023,05,25),24,c2,l2);

        Formateur fo1 = new Formateur(1,"mailform@hotmail.com","Jean","Dujardin");
        Formateur fo2 = new Formateur(2,"mailform2@hotmail.com","Marc","Pierre");

        sc1.addObserver(fo1);
        sc1.addObserver(fo2);
        sc2.addObserver(fo1);

        sc1.setLocal(l3);
        sc2.setDateDebut(LocalDate.of(2023,05,20));
    }
}
