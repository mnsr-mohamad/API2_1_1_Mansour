package mvp.presenter;


import Classes.Cours;
import Classes.Formateur;
import Classes.Local;
import Classes.SessionCours;
import mvp.model.CoursSpecial;
import mvp.model.DAO;
import mvp.model.DAOSessionCours;
import mvp.model.SessionCoursSpecial;
import mvp.view.SessionCoursViewInterface;
import mvp.presenter.CoursPresenter;
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

    public void update(SessionCours sessioncours) {
        SessionCours sc = model.update(sessioncours);
        if (sc == null) view.affMsg("mise à jour infructueuse");
        else view.affMsg("mise à jour effectuée : " + sc);

    }


    public void add(SessionCours sessioncours) {
        Cours cr = coursPresenter.selectionner();
        Local lo = localPresenter.selectionner();
        int nbr = lo.getPlaces();
        if(sessioncours.getNbreInscrits() <= nbr) {
            sessioncours.setCours(cr);
            sessioncours.setLocal(lo);
            SessionCours sc = model.add(sessioncours);
            add_infos(sc);


            if (sc != null && sessioncours.getNbreInscrits() <= nbr) view.affMsg("création de :" + sc);
            else
                view.affMsg("erreur de création car mauvais données lors de la session ou le nombre d'inscrits dépasse la capacité du local ");
            List<SessionCours> sessionCours2 = model.getAll();
            view.setListDatas(sessionCours2);
        }
        else{
            System.out.println("erreur de création car mauvais données lors de la session ou le nombre d'inscrits dépasse la capacité du local");
        }
    }


    public void remove(SessionCours sessioncours) {
        remove_infos(sessioncours);
        boolean ok = model.remove(sessioncours);
        if (ok) view.affMsg("session du cours effacé");
        else view.affMsg("session du cours  non effacé");
        List<SessionCours> sessioncours2 = model.getAll();

    }

    public List<SessionCours> getAll() {
        return model.getAll();
    }

    public void search(int id_SessionCours) {
        SessionCours sc = model.read(id_SessionCours);
        if (sc == null) view.affMsg("recherche infructueuse");
        else view.affMsg(sc.toString());
    }

    public void add_infos(SessionCours sess) {
        boolean repet = true;
        int totalHeuresFormateurs = 0;
        int heuresCours = sess.getCours().getHeures();
        System.out.println(" ");
        System.out.println("Le nombre d'heures à attribuer à un ou plusieurs formateurs  pour ce cours est de "+heuresCours+" heures");
        System.out.println();
        do{

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

        }
        while(repet);


        System.out.println("Total des heures des formateurs encodés : " + totalHeuresFormateurs);

        if (totalHeuresFormateurs != heuresCours) {
            ((SessionCoursSpecial) model).supp_infos(sess);
            ((SessionCoursSpecial) model).supp_sess(sess);
            System.out.println("Le totalHeuresFormateurs est soit supérieur ou inférieur à l'heure du cours, la session n'a pas été crée ");

        }
    }

    public void remove_infos(SessionCours sess) {
        Boolean verif = ((SessionCoursSpecial) model).remove_infos(sess);
        if (verif) view.affMsg("Infos effacé");
        else view.affMsg("Infos non effacé");
    }

    public void form_heures(SessionCours sess){
        int nbr = ((SessionCoursSpecial)model).form_heures(sess);
        if(nbr>0){
            view.affMsg("Le total d'heures de formateur pour cette session est de : "+nbr);
        }
        else view.affMsg("Fonctions ne retourne rien ");

    }

    public void verif_heures(SessionCours sess,int heures){

        boolean verif = ((SessionCoursSpecial)model).verif_heures(sess,heures);

        if(verif==true){
            view.affMsg("Le nombre donné d'heures de cours peut encore être ajouté à une session de cours");
        }
        else{
            view.affMsg("Impossible de rajouter ces heures a cette session");
        }
    }


}
