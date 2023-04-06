package mvp.model;

import Classes.SessionCours;

import java.util.List;

public interface DAOSessionCours {

    SessionCours addSessionCours(SessionCours SessionCours);

    boolean removeSessionCours( SessionCours SessionCours);

    List<SessionCours> getSessionCours();

    SessionCours updateSessionCours(SessionCours SessionCours);

    SessionCours readSessionCours(int SessionCours);


}
