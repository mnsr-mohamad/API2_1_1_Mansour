package mvp.presenter;

import Classes.Cours;
import Classes.Formateur;
import Classes.Local;
import Classes.SessionCours;
import mvp.model.DAO;
import mvp.model.SessionCoursSpecial;
import mvp.view.SessionCoursViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SessionCoursPresenter {

    private DAO<SessionCours> model;
    private SessionCoursViewInterface view;
    private static final Logger logger = LogManager.getLogger(SessionCoursPresenter.class);
    private CoursPresenter coursPresenter;
    private LocalPresenter localPresenter;
    private FormateurPresenter formateurPresenter;

    public void setCoursPresenter(CoursPresenter coursPresenter) {
        this.coursPresenter = coursPresenter;
    }

    public void setLocalPresenter(LocalPresenter localPresenter) {
        this.localPresenter = localPresenter;
    }

    public void setFormateurPresenter(FormateurPresenter formateurPresenter) {
        this.formateurPresenter = formateurPresenter;
    }

    public SessionCoursPresenter(DAO<SessionCours> model, SessionCoursViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<SessionCours> sessionCours = model.getAll();
        view.setListDatas(sessionCours);
    }

    public void update(SessionCours sessionCours) {
        SessionCours sc = model.update(sessionCours);
        if (sc == null) view.affMsg("Mise à jour infructueuse");
        else view.affMsg("Mise à jour effectuée : " + sc);

    }

    public void add(SessionCours sessionCours) {
        boolean verif;
        Cours cr = coursPresenter.selectionner();
        Local lo = localPresenter.selectionner();
        int nbr = lo.getPlaces();
        if (sessionCours.getNbreInscrits() <= nbr) {
            sessionCours.setCours(cr);
            sessionCours.setLocal(lo);
            SessionCours sc = model.add(sessionCours);
            verif = add_infos(sc);

            if (sc != null  && verif == true) {
                view.affMsg("Création de : " + sc);
            }

            else{
                if(verif==false){
                    view.affMsg("Le total des heures des formateurs est soit supérieur ou inférieur à l'heure du cours, la session n'a pas été créée");
                }
                else{
                    view.affMsg("Erreur de création car mauvaises données lors de la session ou le nombre d'inscrits dépasse la capacité du local");
                    //List<SessionCours> sessionCours2 = model.getAll();
                    //view.setListDatas(sessionCours2);
                }

            }
        } else {
            view.affMsg("Erreur de création, le nombre d'inscrits dépasse la capacité du local choisi");
        }
    }

    public void remove(SessionCours sessionCours) {
        remove_infos(sessionCours);
        boolean ok = model.remove(sessionCours);
        if (ok) view.affMsg("Session du cours effacée");
        else view.affMsg("Session du cours non effacée");
        //List<SessionCours> sessionCours2 = model.getAll();
        //view.setListDatas(sessionCours2);

    }

    public List<SessionCours> getAll() {
        return model.getAll();
    }

    public void search(int id_SessionCours) {
        SessionCours sc = model.read(id_SessionCours);
        if (sc == null) view.affMsg("Recherche infructueuse");
        else view.affMsg(sc.toString());
    }

    public boolean add_infos(SessionCours sess) {
        boolean choix = true;
        boolean repet = true;
        int totalHeuresFormateurs = 0;
        int heuresCours = sess.getCours().getHeures();
        System.out.println(" ");
        System.out.println("Le nombre d'heures à attribuer à un ou plusieurs formateurs pour ce cours est de " + heuresCours + " heures");
        System.out.println();
        do {
            int nh = formateurPresenter.nbreheure();
            Formateur fo = formateurPresenter.selectionner(sess);
            totalHeuresFormateurs += nh; // Ajouter nh au total des heures des formateurs
            System.out.println("Heures totales : " + totalHeuresFormateurs);

            Boolean verif = ((SessionCoursSpecial) model).add_infos(sess, fo, nh);
            if (verif) {
                view.affMsg("Infos ajoutées");
            } else {
                view.affMsg("Erreur lors de l'ajout des infos");
            }
            repet = formateurPresenter.repet(sess);


        } while (repet);

        System.out.println("Total des heures des formateurs encodées : " + totalHeuresFormateurs);

        if (totalHeuresFormateurs != heuresCours) {
            ((SessionCoursSpecial) model).supp_infos(sess);
            ((SessionCoursSpecial) model).supp_sess(sess);
            choix = false;
        }
        return choix;
    }

    public void remove_infos(SessionCours sess) {
        Boolean verif = ((SessionCoursSpecial) model).remove_infos(sess);
        if (verif) view.affMsg("Infos effacées");
        else view.affMsg("Infos non effacées");
    }

    public void form_heures(SessionCours sess) {
        int nbr = ((SessionCoursSpecial) model).form_heures(sess);
        if (nbr > 0) {
            view.affMsg("Le total d'heures de formateur pour cette session est de : " + nbr);
        } else {
            view.affMsg("La fonction ne retourne rien");
        }
    }

    public void verif_heures(SessionCours sess, int heures) {
        boolean verif = ((SessionCoursSpecial) model).verif_heures(sess, heures);

        if (verif) {
            view.affMsg("Le nombre donné d'heures de cours peut encore être ajouté à une session de cours");
        } else {
            view.affMsg("Impossible de rajouter ces heures à cette session");
        }
    }
}
