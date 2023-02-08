package Classes;
import java.util.ArrayList;
import java.util.List;

public class Cours {
    private int id;
    private String matiere;
    private int heures;
    private List<SessionCours> session=new ArrayList<>();

    public Cours(int id, String matiere, int heures, List<SessionCours> session) {
        this.id = id;
        this.matiere = matiere;
        this.heures = heures;
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public int getHeures() {
        return heures;
    }

    public void setHeures(int heures) {
        this.heures = heures;
    }

    public List<SessionCours> getSession() {
        return session;
    }

    public void setSession(List<SessionCours> session) {
        this.session = session;
    }
}
