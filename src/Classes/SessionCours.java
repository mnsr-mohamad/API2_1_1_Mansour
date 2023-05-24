package Classes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * classe SessionCours du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 * @see Cours
 * @see Local
 * @see Infos
 */
public class SessionCours {


    /**
     * identifiant de la session
     */
    private int id_SessionCours;
    /**
     * date du debut du cours
     */
    private LocalDate dateDebut;
    /**
     * date de fin du cours
     */
    private LocalDate dateFin;
    /**
     * nombre d'inscrits
     */
    private int nbreInscrits;
    /**
     * Cours de la session
     */
    private Cours cours;
    /**
     * Local de la session
     */
    private Local local;
    /**
     * Liste des informations de la session
     */
    private List<Infos> info = new ArrayList<>();

    /**
     * constructeur paramétré
     *
     * @param id_SessionCours identifiant de la session
     * @param dateDebut       date de debut
     * @param dateFin         date de fin
     * @param nbreInscrits    nombre d'élèves inscrits
     */

    public SessionCours(int id_SessionCours, LocalDate dateDebut, LocalDate dateFin, int nbreInscrits, Cours cours, Local local) throws Exception {
        LocalDate currentDate = LocalDate.now();

       /* if (dateDebut.isBefore(currentDate)) {
            throw new Exception("La date de début ne peut pas être antérieure à la date d'aujourd'hui.");
        }

        /*if (dateFin.isBefore(dateDebut)) {
            throw new Exception("La date de fin ne peut pas être antérieure à la date de début.");
        }

        if (nbreInscrits < 0) {
            throw new Exception("Le nombre d'inscrits ne peut pas être négatif.");
        }

        if (cours == null) {
            throw new Exception("Le cours ne peut pas être null.");
        }

        if (local == null) {
            throw new Exception("Le local ne peut pas être null.");
        }*/

        this.id_SessionCours = id_SessionCours;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbreInscrits = nbreInscrits;
        this.cours = cours;
        this.local = local;
    }


    public SessionCours(int id_SessionCours, LocalDate dateDebut, LocalDate dateFin, int nbreInscrits) {
        this.id_SessionCours = id_SessionCours;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbreInscrits = nbreInscrits;


    }

    public SessionCours(int id_SessionCours, LocalDate dateDebut, LocalDate dateFin, int nbreInscrits, Local local) {
        this.id_SessionCours = id_SessionCours;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbreInscrits = nbreInscrits;
        this.local = local;

    }


    /**
     * getter id_SessionCours identifiant de la session
     *
     * @return identifiant de la session
     */
    public int getId_SessionCours() {
        return id_SessionCours;
    }

    /**
     * setter id_SessionCours
     *
     * @param id_SessionCours identifiant de la session
     */


    public void setId_SessionCours(int id_SessionCours) {
        this.id_SessionCours = id_SessionCours;
    }

    /**
     * getter dateDebut date du debut de la session
     *
     * @return date du debut de la session
     */

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * setter dateDebut
     *
     * @param dateDebut date de debut de la session
     */


    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * getter dateFin date de fin de la session
     *
     * @return date de fin de la session
     */

    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * setter dateFin
     *
     * @param dateFin date de fin de la session
     */


    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * getter nbreInscrits nombre d'élèves inscrits
     *
     * @return nombre d'élèves inscrits
     */

    public int getNbreInscrits() {
        return nbreInscrits;
    }

    /**
     * setter nbreInscrits
     *
     * @param nbreInscrits nombre d'élèves inscrits
     */


    public void setNbreInscrits(int nbreInscrits) {
        this.nbreInscrits = nbreInscrits;
    }

    /**
     * getter cours
     *
     * @return le cours
     */

    public Cours getCours() {
        return cours;
    }

    /**
     * setter cours
     *
     * @param cours cours
     */

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    /**
     * getter local
     *
     * @return le local
     */

    public Local getLocal() {
        return local;
    }

    /**
     * setter local
     *
     * @param local local
     */

    public void setLocal(Local local) {
        this.local = local;
    }

    /**
     * getter Infos
     *
     * @return liste des informations
     */

    public List<Infos> getInfo() {
        return info;
    }

    /**
     * setter SessionCours
     *
     * @param info liste des informations
     */

    public void setInfo(List<Infos> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "SessionCours{" +
                "id_SessionCours=" + id_SessionCours +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbreInscrits=" + nbreInscrits +
                ", cours=" + cours +
                ", local=" + local +
                '}';
    }
}
