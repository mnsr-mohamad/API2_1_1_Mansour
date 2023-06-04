package mvp.view;

import Classes.Cours;
import Classes.Formateur;
import mvp.presenter.CoursPresenter;
import utilitaires.Utilitaire;

import java.util.*;
import java.util.regex.Pattern;

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
        //this.lc.sort(cmp);
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
        List loptions = new ArrayList<>(Arrays.asList("ajout", "retrait", "modifier", "recherche", "special", "fin"));
        do {

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
            }
        } while (true);
    }

    private void retirer() {
        List<Cours> c = presenter.getAll();
        affList(c);
        int nl = choixElt(c);
        Cours cours = c.get(nl - 1);
        presenter.remove(cours);

    }

    private void ajouter() {
        //il faut appuyer 2 fois sur le choix 6 pour quitter le menu quand on utilise cette méthode
        try{
                System.out.print("matière : ");
                String matiere = sc.nextLine();
                System.out.print("nombres d'heures : ");
                int heures = sc.nextInt();
                sc.skip("\n");

                presenter.add(new Cours(0,matiere,heures));
                System.out.println("Cours ajouté ");

            } catch (Exception e) {
                System.out.println("Erreur  " + e.getMessage());
            }



        /*
        //méthode avec des regex avec chatGPT
        Scanner sc = new Scanner(System.in);
        String matiere;
        int heures;

        do {
            System.out.print("matière (0 pour quitter) : ");
            matiere = sc.nextLine();

            if (matiere.equals("0")) {
                System.out.println("Opération annulée.");
                return;
            }

            // Expression régulière pour valider le format de la matière
            String regexMatiere = "[A-Za-z\\s]+";

            if (!Pattern.matches(regexMatiere, matiere)) {
                System.out.println("Erreur : format de matière invalide");
                continue;//permet de revenir au début de la boucle pour permettre à l'utilisateur de saisir à nouveau les données jusqu'à ce qu'elles soient valides.
            }

            System.out.print("nombres d'heures (0 pour quitter) : ");
            String heuresStr = sc.nextLine();

            if (heuresStr.equals("0")) {
                System.out.println("Opération annulée.");
                return;
            }

            // Expression régulière pour valider le format des nombres d'heures (entier positif)
            String regexHeures = "\\d+";

            if (!Pattern.matches(regexHeures, heuresStr)) {
                System.out.println("Erreur : format de nombres d'heures invalide");
                continue;
            }

            heures = Integer.parseInt(heuresStr);
            break;

        } while (true);
        try {
            presenter.add(new Cours(0, matiere, heures));

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }*/
    }


    private void modifier() {
       // List<Cours> c = presenter.getAll();
        affList(lc);
        int nl = choixElt(lc);
        Cours cours = lc.get(nl - 1);
        do {
            try {
                String matiere = modifyIfNotBlank("matiere", cours.getMatiere());
                int heures = Integer.parseInt(modifyIfNotBlank("heures", "" + cours.getHeures()));
                cours.setMatiere(matiere);
                cours.setHeures(heures);
                break;
            } catch (Exception e) {
                System.out.println("erreur :" + e);
            }
        }
        while (true);
        presenter.update(cours);
        lc = presenter.getAll();//rafraichissement
        affListe(lc);
        /* String matiere = modifyIfNotBlank("matiere", cours.getMatiere());
        int heures = Integer.parseInt(modifyIfNotBlank("heures", "" + cours.getHeures()));
        try {
            presenter.update(new Cours(cours.getId_Cours(), matiere, heures));
            lc = presenter.getAll();//rafraichissement
            affListe(lc);
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
        }*/

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
