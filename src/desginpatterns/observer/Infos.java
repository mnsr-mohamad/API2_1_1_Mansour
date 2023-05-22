package desginpatterns.observer;



/**
 * classe Infos du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 * @see Formateur
 */
public class Infos {
    /**
     * nombre d'heure
     */
    protected int nh;
    /**
     * infos du formateur
     */
    protected Formateur formateur;

    protected SessionCours sessions;

    /**
     * constructeur paramétré
     * @param nh nombre d'heure
     */

    public Infos(int nh, Formateur formateur, SessionCours sessions) {
        this.nh = nh;
        this.formateur = formateur;
        this.sessions = sessions;
    }

    /**
     * getter nh nombres d'heures
     *
     * @return identifiant du formateur
     */
    public int getNh() {
        return nh;
    }

    /**
     * setter nh
     *
     * @param nh nombres d'heures
     */
    public void setNh(int nh) {
        this.nh = nh;
    }

    /**
     * getter formateur
     *
     * @return le formateur
     */

    public Formateur getFormateur() {
        return formateur;
    }

    /**
     * setter formateur
     *
     * @param formateur formateur
     */

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }


    public SessionCours getSessions() {
        return sessions;
    }

    public void setSessions(SessionCours sessions) {
        this.sessions = sessions;
    }


    @Override
    public String toString() {
        return "Infos{" +
                "nh=" + nh +
                ", formateur=" + formateur +
                ", sessions=" + sessions +
                '}';
    }
}


