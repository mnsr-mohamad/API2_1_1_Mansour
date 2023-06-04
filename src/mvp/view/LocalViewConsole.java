package mvp.view;

import Classes.Cours;
import Classes.Local;
import Classes.SessionCours;
import mvp.presenter.LocalPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class LocalViewConsole implements LocalViewInterface {

    private LocalPresenter presenter;
    private List<Local> ll;
    private Scanner sc = new Scanner(System.in);

    public LocalViewConsole() {

    }


    @Override
    public void setPresenter(LocalPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Local> local) {
        this.ll = local;
        affListe(ll);
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
    public Local selectionner(List<Local> lo) {
        int n = choixListe(lo);
        Local local = lo.get(n - 1);
        return local;
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
        List<Local> c = presenter.getAll();
        affList(c);
        int nl = choixElt(ll);
        Local local = ll.get(nl - 1);
        presenter.removeLocal(local);

    }

    private void ajouter() {
        System.out.print("sigle : ");
        String sigle = sc.nextLine();
        System.out.print("places : ");
        int places = sc.nextInt();
        sc.skip("\n");
        System.out.print("description : ");
        String description = sc.nextLine();
        try {
            presenter.add(new Local(0, sigle, places, description));

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout" + e.getMessage());

        }

    }

    private void modifier() {
        //List<Local> c = presenter.getAll();
        affList(ll);
        int nl = choixElt(ll);
        Local local = ll.get(nl - 1);
        String sigle = modifyIfNotBlank("sigle", local.getSigle());
        int places = Integer.parseInt(modifyIfNotBlank("places", "" + local.getPlaces()));
        String descritpion = modifyIfNotBlank("description", local.getDescription());
        try{
            presenter.update(new Local(local.getId_Local(), sigle, places, descritpion));
            ll = presenter.getAll();
        }
        catch (Exception e) {
            System.out.println("Erreur lors de l'ajout" + e.getMessage());

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
        System.out.println("id du local : ");
        int id_local = sc.nextInt();
        presenter.search(id_local);
    }

    public void specialSGBD() {

        do {
            int ch = choixListe(Arrays.asList("Inserer un local et recuperer son ID","Vérifier un local", "menu principal"));

            switch (ch) {
                case 1:
                    presenter.insert_local();
                    break;
                case 2:
                    List<Local> c = presenter.getAll();
                    affList(c);
                    int choix = choixElt(ll);
                    Local lo = ll.get(choix - 1);
                    System.out.println("Vous avez choisi le local " + lo);
                    presenter.verif_local(lo);
                case 3 :
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);


    }

}
