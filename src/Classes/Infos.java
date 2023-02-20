package Classes;

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
    private int nh;
    /**
     * infos du formateur
     */
    private Formateur formateur;


    /**
     * constructeur paramétré
     * @param nh nombre d'heure
     */

    public Infos(int nh, Formateur formateur) {
        this.nh = nh;
        this.formateur = formateur;
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
}
