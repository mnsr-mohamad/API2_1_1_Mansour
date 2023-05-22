package desginpatterns.composite;

public class Ecole {
    public static void main(String[] args) {

        Cours c1 = new Cours(1, "Bio", 5);
        Cours c2 = new Cours(2, "Physique", 6);
        Cours c3 = new Cours(3, "Chimie", 4);

        Cours c4 = new Cours(4, "Foot", 1);
        Cours c5 = new Cours(5, "Basket", 3);

        Categorie ca1 = new Categorie(1, "Science");
        Categorie ca2 = new Categorie(2, "Sport");


        ca1.getElts().add(c1);
        ca1.getElts().add(c2);
        ca1.getElts().add(ca2);
        ca2.getElts().add(c3);
        ca2.getElts().add(c4);
        ca2.getElts().add(c5);




        System.out.println(ca1);


    }
}
