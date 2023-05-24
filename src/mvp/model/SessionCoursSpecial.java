package mvp.model;

import Classes.Formateur;
import Classes.Infos;
import Classes.SessionCours;

public interface SessionCoursSpecial {

    public boolean add_infos(SessionCours sess, Formateur form,int nh);
    public boolean remove_infos(SessionCours sess);

    int getTotalHeuresFormateurs(SessionCours sess);

    void supp_infos(SessionCours sess);

    void supp_sess(SessionCours sess);

    public int form_heures(SessionCours sess);

    public boolean verif_heures(SessionCours sess,int heures);
}
