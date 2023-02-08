package Classes;

import java.util.ArrayList;
import java.util.List;

public class Local {

    private int id_Local;
    private String sigle;
    private int places;
    private String description;
    private List<SessionCours> session=new ArrayList<>();

    public Local(int id_Local, String sigle, int places, String description) {
        this.id_Local = id_Local;
        this.sigle = sigle;
        this.places = places;
        this.description = description;

    }

    public int getId_Local() {
        return id_Local;
    }

    public void setId_Local(int id_Local) {
        this.id_Local = id_Local;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SessionCours> getSession() {
        return session;
    }

    public void setSession(List<SessionCours> session) {
        this.session = session;
    }
}
