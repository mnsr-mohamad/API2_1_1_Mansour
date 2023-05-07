package desginpatterns.builder;

import Classes.Cours;
import Classes.Infos;
import Classes.Local;

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
     * @param scb objet de la classe SessionCoursBuilder contenant les informations d'initialisation
     */

    private SessionCours(SessionCoursBuilder scb) {
        this.id_SessionCours = scb.id_SessionCours;
        this.dateDebut = scb.dateDebut;
        this.dateFin = scb.dateFin;
        this.nbreInscrits = scb.nbreInscrits;
        this.cours = scb.cours;
        this.local = scb.local;

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
     * getter dateDebut date du debut de la session
     *
     * @return date du debut de la session
     */

    public LocalDate getDateDebut() {
        return dateDebut;
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
     * getter nbreInscrits nombre d'élèves inscrits
     *
     * @return nombre d'élèves inscrits
     */

    public int getNbreInscrits() {
        return nbreInscrits;
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
     * getter local
     *
     * @return le local
     */

    public Local getLocal() {
        return local;
    }


    /**
     * getter Infos
     *
     * @return liste des informations
     */

    public List<Infos> getInfo() {
        return info;
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
                ", info=" + info +
                '}';
    }

    public static class SessionCoursBuilder {


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

        public SessionCoursBuilder setId_SessionCours(int id_SessionCours) {
            this.id_SessionCours = id_SessionCours;
            return this;
        }

        public SessionCoursBuilder setDateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }

        public SessionCoursBuilder setDateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
            return this;
        }

        public SessionCoursBuilder setNbreInscrits(int nbreInscrits) {
            this.nbreInscrits = nbreInscrits;
            return this;
        }

        public SessionCoursBuilder setCours(Cours cours) {
            this.cours = cours;
            return this;
        }

        public SessionCoursBuilder setLocal(Local local) {
            this.local = local;
            return this;
        }

        public SessionCours build() throws Exception {

            if (id_SessionCours < 0) {
                throw new Exception("L'ID de la session ne peut pas être négatif");
            }

            if (dateDebut.isAfter(dateFin)) {
                throw new Exception("La date de début ne peut pas être après la date de fin ");
            }

            if (dateFin.isBefore(dateDebut)) {
                throw new Exception("La date de fin ne peut pas être avant la date de début ");
            }

            if (nbreInscrits < 0) {
                throw new Exception("Le nombre d'inscrits ne peut pas être négatif");
            }

            if (cours == null) {
                throw new Exception("Un cours ne peut pas être null");
            }

            return new SessionCours(this);
        }


    }
}