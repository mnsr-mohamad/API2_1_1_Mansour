package mvp.view;

import Classes.Cours;
import Classes.Local;
import Classes.SessionCours;

import mvp.presenter.SessionCoursPresenter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class SessionCoursViewConsole implements SessionCoursViewInterface {

    private SessionCoursPresenter presenter;
    private List<SessionCours> ls;
    private Scanner sc = new Scanner(System.in);

    public SessionCoursViewConsole() {

    }

    @Override
    public void setPresenter(SessionCoursPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<SessionCours> sessioncours) {
        this.ls = sessioncours;
        affListe(ls);
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
        List<SessionCours> c = presenter.getAll();
        affList(c);
        int nl = choixElt(ls);
        SessionCours sessionCours = ls.get(nl - 1);
        presenter.remove(sessionCours);

    }

    private void ajouter() {
        System.out.print("Date de debut (yyyy-MM-dd) : ");
        String dateDebutStr = sc.nextLine();
        LocalDate dateDebut = LocalDate.parse(dateDebutStr);

        System.out.print("Date de fin (yyyy-MM-dd) : ");
        String dateFinStr = sc.nextLine();
        LocalDate dateFin = LocalDate.parse(dateFinStr);

        System.out.print("Nombre d'inscrits : ");
        int nbInscrits = sc.nextInt();
        sc.nextLine();
        try {
            presenter.add(new SessionCours(0, dateDebut, dateFin, nbInscrits));
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
        }
    }

    private void modifier() {
        List<SessionCours> c = presenter.getAll();
        affList(c);
        int nl = choixElt(ls);
        SessionCours sessioncours = ls.get(nl - 1);
        LocalDate dateDebut = LocalDate.parse(modifyIfNotBlank("date de debut", "" + sessioncours.getDateDebut()));
        LocalDate dateFin = LocalDate.parse(modifyIfNotBlank("date de fin", "" + sessioncours.getDateFin()));
        int nbreinscrits = Integer.parseInt(modifyIfNotBlank("nombre d'inscrits", "" + sessioncours.getNbreInscrits()));

        presenter.update(new SessionCours(sessioncours.getId_SessionCours(), dateDebut, dateFin, nbreinscrits));
        ls = presenter.getAll();
        affListe(ls);

    }

    public String modifyIfNotBlank(String label, String oldValue) {
        System.out.println(label + " : " + oldValue);
        System.out.print("nouvelle valeur (enter si pas de changement) : ");
        String newValue = sc.nextLine();
        if (newValue.isBlank()) return oldValue;
        return newValue;
    }

    private void rechercher() {
        System.out.println("id de la session : ");
        int id_sessioncours = sc.nextInt();
        presenter.search(id_sessioncours);

    }

    public void specialSGBD() {
        List<SessionCours> c = presenter.getAll();
        affList(c);
        int choix = choixElt(ls);
        SessionCours sce = ls.get(choix - 1);
        System.out.println("Vous avez choisi la sessions :  " + sce);
        do {
            int ch = choixListe(Arrays.asList("Nombre total d'heures des formateurs pour cette session", "Verifier si un nombre d'heures peut être ajouter a une session", "menu principal"));

            switch (ch) {
                case 1:
                    presenter.form_heures(sce);
                    break;
                case 2:
                    System.out.println("Entrez le nombre d'heures de cours que vous voulez ajouter à cette session : ");
                    int heures = sc.nextInt();
                    presenter.verif_heures(sce, heures);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);


    }


}
