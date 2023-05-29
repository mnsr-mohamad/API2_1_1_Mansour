package mvp.view;

import Classes.Cours;
import Classes.Formateur;
import Classes.SessionCours;
import mvp.presenter.FormateurPresenter;

import java.util.*;

import static utilitaires.Utilitaire.*;

public class FormateurViewConsole implements FormateurViewInterface {


    private FormateurPresenter presenter;
    private List<Formateur> lf;
    private Scanner sc = new Scanner(System.in);

    public FormateurViewConsole() {

    }

    @Override
    public void setPresenter(FormateurPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Formateur> formateur, Comparator<Formateur> cmp) {

        this.lf = formateur;
        this.lf.sort(cmp);
        affListe(lf);
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
    public Formateur selectionner(List<Formateur> formateur) {
        int nl = choixListe(formateur);
        Formateur fo = formateur.get(nl - 1);
        return fo;
    }


    @Override
    public boolean repet(List<Formateur> formateur) {
        List string = new ArrayList(Arrays.asList("oui", "non"));
        System.out.println("Autre formateur : ");
        int choix = choixListe(string);
        if (!formateur.isEmpty()) {
            if (choix == 1) {
                return true;
            }
            return false;
        } else {
            System.out.println("Plus de formateur ");
            return false;
        }
    }

    @Override
    public int nbreheures() {
        System.out.println("Entrez le nombre d'heures du formateurs : ");
        int heures = sc.nextInt();
        return heures;
    }


    public void menu() {
        do {
            int ch = choixListe(Arrays.asList("ajout", "retrait", "modifier", "recherche", "specialSGBD", "fin"));

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
                    specialSGBD();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    private void retirer() {
        List<Formateur> c = presenter.getAll();
        affList(c);
        int nl = choixElt(lf);
        Formateur formateur = lf.get(nl - 1);
        presenter.remove(formateur);

    }

    private void ajouter() {
        System.out.print("mail : ");
        String mail = sc.nextLine();
        System.out.print("nom : ");
        String nom = sc.nextLine();
        System.out.print("prenom : ");
        String prenom = sc.nextLine();
        try {
            presenter.add(new Formateur(0, mail, nom, prenom));
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
        }

    }

    private void modifier() {
        List<Formateur> c = presenter.getAll();
        affList(c);
        int nl = choixElt(lf);
        Formateur formateur = lf.get(nl - 1);
        String mail = modifyIfNotBlank("mail", formateur.getMail());
        String nom = modifyIfNotBlank("nom", formateur.getNom());
        String prenom = modifyIfNotBlank("prenom", formateur.getPrenom());
        try {
            presenter.update(new Formateur(formateur.getId_Formateur(), mail, nom, prenom));
            lf = presenter.getAll();//rafraichissement
            affListe(lf);

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
        System.out.println("id du formateur : ");
        int id_formateur = sc.nextInt();
        presenter.search(id_formateur);
    }

    public void specialSGBD() {
        do {
            int ch = choixListe(Arrays.asList("Afficher tout les formateurs encodés", "menu principal"));

            switch (ch) {
                case 1:
                    presenter.form_encode();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);


    }
}
