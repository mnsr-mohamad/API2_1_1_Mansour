package mvp.presenter;


import Classes.Cours;
import Classes.SessionCours;
import mvp.model.DAOSessionCours;
import mvp.view.SessionCoursViewInterface;

import java.util.List;

public class SessionCoursPresenter {


    private DAOSessionCours model;
    private SessionCoursViewInterface view;

    public SessionCoursPresenter(DAOSessionCours model, SessionCoursViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<SessionCours> sessionCours = model.getSessionCours();
        view.setListDatas(sessionCours);
    }
    public void update(SessionCours sessioncours) {
        SessionCours sc = model.updateSessionCours(sessioncours);
        if (sc == null) view.affMsg("mise à jour infructueuse");
        else view.affMsg("mise à jour effectuée : " + sc);

    }


    public void addSessionCours(SessionCours sessioncours) {
        SessionCours sc = model.addSessionCours(sessioncours);
        if (sc != null) view.affMsg("création de :" + sc);
        else view.affMsg("erreur de création");
        List<SessionCours> sessionCours2 = model.getSessionCours();
        view.setListDatas(sessionCours2);
    }


    public void removeSessionCours(SessionCours sessioncours) {
        boolean ok = model.removeSessionCours(sessioncours);
        if (ok) view.affMsg("session du cours effacé");
        else view.affMsg("session du cours  non effacé");
        List<SessionCours> sessioncours2 = model.getSessionCours();

    }

    public List<SessionCours> getAll() {
        return model.getSessionCours();
    }

    public void search(int id_SessionCours ) {
        SessionCours sc  = model.readSessionCours(id_SessionCours);
        if(sc==null) view.affMsg("recherche infructueuse");
        else view.affMsg(sc.toString());
    }


}
