package mvp.view;

import Classes.Cours;
import mvp.presenter.CoursPresenter;
import utilitaires.Utilitaire;

import java.util.List;
import java.util.Scanner;

public class CoursViewConsole implements CoursViewInterface {
    private CoursPresenter presenter;
    private List<Cours> lc;
    private Scanner sc = new Scanner(System.in);

    public CoursViewConsole() {

    }

    @Override
    public void setPresenter(CoursPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Cours> cours) {

        this.lc = cours;
        Utilitaire.affListe(lc);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" + msg);
    }


    public void menu() {
        do {
            System.out.println("1.ajout\n2.retrait\n3.modifier\n4.rechercher\n5.fin");

            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    retirer();
                    break;
                case 3:
                    modifier();
                    break;
                case 4:
                    rechercher();
                    break;
                case 5:
                    System.exit(0);
            }
        } while (true);
    }

    private void retirer() {
        int nl = Utilitaire.choixElt(lc);
        Cours cours = lc.get(nl - 1);
        presenter.removeCours(cours);

    }

    private void ajouter() {
        System.out.print("matière : ");
        String matiere = sc.nextLine();
        System.out.print("nombres d'heures : ");
        int heures = sc.nextInt();
        presenter.addCours(new Cours(0, matiere, heures));
    }

    private void modifier() {
        int nl = Utilitaire.choixElt(lc);
        Cours cours = lc.get(nl - 1);
        String matiere = modifyIfNotBlank("matiere", cours.getMatiere());
        int heures = Integer.parseInt(modifyIfNotBlank("heures", "" + cours.getHeures()));
        presenter.update(new Cours(cours.getId_Cours(), matiere, heures));
        lc = presenter.getAll();//rafraichissement
        Utilitaire.affListe(lc);

    }

    public String modifyIfNotBlank(String label, String oldValue) {
        System.out.println(label + " : " + oldValue);
        System.out.print("nouvelle valeur (enter si pas de changement) : ");
        String newValue = sc.nextLine();
        if (newValue.isBlank()) return oldValue;
        return newValue;
    }

    private void rechercher() {
        System.out.println("id du cours : ");
        int id_cours = sc.nextInt();
        presenter.search(id_cours);
    }



}
