package desginpatterns.builder;

import Classes.Cours;
import Classes.Local;

import java.time.LocalDate;

public class Ecole {


    public static void main(String[] args) {

    Local l= new Local(1,"F3",20,"Moyen local");
    Cours c= new Cours(1,"Art",4);

        try {
            SessionCours sc = new SessionCours.SessionCoursBuilder().
                    setId_SessionCours(1).
                    setDateDebut(LocalDate.now()).
                    setNbreInscrits(25).
                    setDateFin(LocalDate.of(2023,5,17)).
                    setCours(c).
                    setLocal(l).
                    build();
            System.out.println(sc);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }

        try {
            SessionCours sc2 = new SessionCours.SessionCoursBuilder().
                    setId_SessionCours(2).
                    setDateDebut(LocalDate.now()).
                    setNbreInscrits(-15).
                    setDateFin(LocalDate.of(2023,5,17)).
                    setCours(c).
                    build();
            System.out.println(sc2);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }


        /*try {
            SessionCours sc3 = new SessionCours.SessionCoursBuilder().
                    setId_SessionCours(-3).
                    setDateDebut(LocalDate.of(2023,5,25)).
                    setNbreInscrits(25).
                    setDateFin(LocalDate.of(2023,5,17)).
                    setCours(c).
                    setLocal(l).
                    build();
            System.out.println(sc3);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }*/

        try {
            SessionCours sc4 = new SessionCours.SessionCoursBuilder().
                    setId_SessionCours(4).
                    setDateDebut(LocalDate.now()).
                    setNbreInscrits(25).
                    setDateFin(LocalDate.of(2023,5,17)).
                    setCours(null).
                    setLocal(l).
                    build();
            System.out.println(sc4);
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}