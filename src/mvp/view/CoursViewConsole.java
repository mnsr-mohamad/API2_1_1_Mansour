package mvp.view;

import Classes.Cours;
import mvp.presenter.CoursPresenter;
import utilitaires.Utilitaire;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

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
        affListe(lc);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" + msg);
    }

    @Override
    public void affList(List infos) {
        affListe(infos);
    }

    @Override
    public Cours selectionner(List<Cours> lc) {
        int n = choixListe(lc);
        Cours cours = lc.get(n - 1);
        return cours;
    }


    public void menu() {
        do {
            List<String> loptions = Arrays.asList("ajout", "retrait", "modifier", "recherche", "special", "fin");
            int ch = Utilitaire.choixListe(loptions);
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
                    special();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    private void retirer() {
        List<Cours> c = presenter.getAll();
        affList(c);
        int nl = choixElt(lc);
        Cours cours = lc.get(nl - 1);
        presenter.remove(cours);

    }

    private void ajouter() {
        System.out.print("matière : ");
        String matiere = sc.nextLine();

        System.out.print("nombres d'heures : ");
        int heures = sc.nextInt();
        try {
            presenter.add(new Cours(0, matiere, heures));
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
        }

    }

    private void modifier() {
        List<Cours> c = presenter.getAll();
        affList(c);
        int nl = choixElt(lc);
        Cours cours = lc.get(nl - 1);
        String matiere = modifyIfNotBlank("matiere", cours.getMatiere());
        int heures = Integer.parseInt(modifyIfNotBlank("heures", "" + cours.getHeures()));
        try {
            presenter.update(new Cours(cours.getId_Cours(), matiere, heures));
            lc = presenter.getAll();//rafraichissement
            affListe(lc);
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
        }

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

    public void special() {

            System.out.println("Choisissez un cours : ");
            List<Cours> c = presenter.getAll();
            affList(c);
            int choix = choixElt(lc);
            Cours cr = lc.get(choix - 1);
            System.out.println("Vous avez choisi le cours " + cr);

            do {
                int ch = choixListe(Arrays.asList("Formateurs par cours", "Afficher toutes les sessions du cours avec le local", "Afficher toutes les sessions du cours avec le local comprise entre 2 dates ", "Menu principal"));

                switch (ch) {
                    case 1:
                        presenter.FormateursCours(cr);
                        break;
                    case 2:
                        presenter.SessionsParLocal(cr);
                        break;
                    case 3:
                        presenter.SessionsEntreDate(cr);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Choix invalide, recommencez.");
                }
            } while (true);
        }


}
