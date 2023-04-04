package mvp.view;

import Classes.Local;
import mvp.presenter.LocalPresenter;

import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.affListe;
import static utilitaires.Utilitaire.choixElt;

public class LocalViewConsole implements LocalViewInterface{

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

        presenter.addLocal(new Local(0,sigle , places, description));
    }

    private void modifier() {
        int nl = choixElt(ll);
        Local local = ll.get(nl - 1);
        String sigle = modifyIfNotBlank("sigle", local.getSigle());
        int places = Integer.parseInt(modifyIfNotBlank("places", ""+local.getPlaces()));
        String descritpion = modifyIfNotBlank("description", local.getDescription());
        presenter.update(new Local(local.getId_Local(), sigle,places,descritpion));
        ll = presenter.getAll();//rafraichissement
        affListe(ll);

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









}
