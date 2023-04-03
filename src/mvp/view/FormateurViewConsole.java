package mvp.view;

import Classes.Formateur;
import mvp.presenter.FormateurPresenter;

import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.affListe;
import static utilitaires.Utilitaire.choixElt;

public class FormateurViewConsole implements FormateurViewInterface{


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
    public void setListDatas(List<Formateur> formateur) {

        this.lf = formateur;
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
        int nl = choixElt(lf);
        Formateur formateur = lf.get(nl - 1);
        presenter.removeFormateur(formateur);

    }

    private void ajouter() {
        System.out.print("mail : ");
        String mail = sc.nextLine();
        System.out.print("nom : ");
        String nom = sc.nextLine();
        System.out.print("prenom : ");
        String prenom = sc.nextLine();
        presenter.addFormateur(new Formateur(0,mail , nom, prenom));
    }

    private void modifier() {
        int nl = choixElt(lf);
        Formateur formateur = lf.get(nl - 1);
        String mail = modifyIfNotBlank("mail", formateur.getMail());
        String nom = modifyIfNotBlank("nom", formateur.getNom());
        String prenom = modifyIfNotBlank("prenom", formateur.getPrenom());
        presenter.update(new Formateur(formateur.getId_Formateur(), mail,nom,prenom));
        lf = presenter.getAll();//rafraichissement
        affListe(lf);

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
}
