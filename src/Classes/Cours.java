package Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * classe Cours du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 * @see SessionCours
 */
public class Cours {
    /**
     * identifiant du cours
     */
    private int id_Cours;
    /**
     * matiere du cours
     */
    private String matiere;
    /**
     * nombre d'heure des cours
     */
    private int heures;
    /**
     * liste des sessions du cours
     */
    private List<SessionCours> session = new ArrayList<>();

    /**
     * constructeur paramétré
     *
     * @param id_Cours identifiant du cours
     * @param matiere  nom du de la matière du cours
     * @param heures   nombre d'heure du cours
     */
    public Cours(int id_Cours, String matiere, int heures) {
        this.id_Cours = id_Cours;
        this.matiere = matiere;
        this.heures = heures;

    }


    /**
     * getter id_Cours
     *
     * @return identifiant du cours
     */
    public int getId_Cours() {
        return id_Cours;
    }

    /**
     * setter id_cours
     *
     * @param id_Cours identifiant du cours
     */

    public void setId_Cours(int id_Cours) {
        this.id_Cours = id_Cours;
    }

    /**
     * getter matiere
     *
     * @return matiere
     */
    public String getMatiere() {
        return matiere;
    }

    /**
     * setter matiere
     *
     * @param matiere matiere
     */

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    /**
     * getter heures
     *
     * @return heures
     */

    public int getHeures() {
        return heures;
    }

    /**
     * setter heures
     *
     * @param heures heures
     */

    public void setHeures(int heures) {
        this.heures = heures;
    }

    /**
     * getter SessionCours
     *
     * @return liste des sessions du cours
     */
    public List<SessionCours> getSession() {
        return session;
    }

    /**
     * setter SessionCours
     *
     * @param session liste des sessions du cours
     */
    public void setSession(List<SessionCours> session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id_Cours=" + id_Cours +
                ", matiere='" + matiere + '\'' +
                ", heures=" + heures +
                '}';
    }
}
