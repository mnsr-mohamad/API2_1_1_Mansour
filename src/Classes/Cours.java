package Classes;
import java.util.ArrayList;
import java.util.List;

public class Cours {
    private int id_Cours;
    private String matiere;
    private int heures;
    private List<SessionCours> session=new ArrayList<>();

    public Cours(int id_Cours, String matiere, int heures, List<SessionCours> session) {
        this.id_Cours = id_Cours;
        this.matiere = matiere;
        this.heures = heures;
        this.session = session;
    }

    public int getId_Cours() {
        return id_Cours;
    }

    public void setId_Cours(int id_Cours) {
        this.id_Cours = id_Cours;
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
