package desginpatterns.observer;




import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe SessionCours du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 * @see Cours
 * @see Local
 * @see Infos
 */
public class SessionCours extends Subject {


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

    public SessionCours(int id_SessionCours, LocalDate dateDebut, LocalDate dateFin, int nbreInscrits, Cours cours, Local local) {
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
        notifyObservers();
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
        notifyObservers();
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

    /**
     * égalité de deux sessions de cours basée sur l'id
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionCours sc = (SessionCours) o;
        return id_SessionCours == sc.id_SessionCours;
    }

    /**
     * calcul du hashcode de la session sur l'id
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id_SessionCours);
    }


    /**
     * méthode toString
     *
     * @return informations complètes du taxi
     */
    @Override
    public String toString() {
        return "SessionCours{" +
                "id_SessionCours=" + id_SessionCours +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbreInscrits=" + nbreInscrits +
                ", cours=" + cours +
                ", local=" + local +
                ", info=" + info +
                '}';
    }

    @Override
    public String getNotification() {
        return " du changement du local et/ou date de début => ID : "+id_SessionCours+" - Date de début : "+dateDebut+" au local :  "+local;
    }

}