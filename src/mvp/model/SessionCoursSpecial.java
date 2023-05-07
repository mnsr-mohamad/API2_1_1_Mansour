package mvp.model;

import Classes.Formateur;
import Classes.Infos;
import Classes.SessionCours;

public interface SessionCoursSpecial {

    public boolean add_infos(SessionCours sess, Formateur form,int nh);
    public boolean remove_infos(SessionCours sess);

}
