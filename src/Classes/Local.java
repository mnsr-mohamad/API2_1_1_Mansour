package Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * classe Local du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 * @see SessionCours
 */
public class Local {
    /**
     * identifiant du Local
     */
    private int id_Local;
    /**
     * sigle du local
     */
    private String sigle;
    /**
     * nombre de places
     */
    private int places;
    /**
     * description du local
     */
    private String description;
    /**
     * liste des sessions de cours
     */
    private List<SessionCours> session = new ArrayList<>();


    /**
     * constructeur paramétré
     *
     * @param id_Local    identifiant du local
     * @param sigle       sigle du local
     * @param places      nombre de places du local
     * @param description description du local
     */
    public Local(int id_Local, String sigle, int places, String description) {
        this.id_Local = id_Local;
        this.sigle = sigle;
        this.places = places;
        this.description = description;

    }

    public Local(String sigle) {
        this.sigle = sigle;
    }

    public Local() {

    }

    /**
     * getter id_local identifiant du local
     *
     * @return identifiant du local
     */
    public int getId_Local() {
        return id_Local;
    }


    /**
     * setter id_local
     *
     * @param id_Local identifiant du local
     */

    public void setId_Local(int id_Local) {
        this.id_Local = id_Local;
    }

    /**
     * getter sigle sigle du local
     *
     * @return sigle du local
     */

    public String getSigle() {
        return sigle;
    }

    /**
     * setter sigle
     *
     * @param sigle sigle du local
     */

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    /**
     * getter places nombre de places  du local
     *
     * @return places du local
     */

    public int getPlaces() {
        return places;
    }

    /**
     * setter places
     *
     * @param places nombre de places du local
     */

    public void setPlaces(int places) {
        this.places = places;
    }

    /**
     * getter description description du local
     *
     * @return description du local
     */

    public String getDescription() {
        return description;
    }

    /**
     * setter places
     *
     * @param description description du local
     */

    public void setDescription(String description) {
        this.description = description;
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
        return "Local{" +
                "id_Local=" + id_Local +
                ", sigle='" + sigle + '\'' +
                ", places=" + places +
                ", description='" + description + '\'' +
                ", session=" + session +
                '}';
    }
}
