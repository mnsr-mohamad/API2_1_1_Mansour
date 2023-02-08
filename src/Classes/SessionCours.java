package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessionCours {

    private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nbreInscrits;
    private Cours cours;
    private Local local;
    private List<Infos> info=new ArrayList<>();

    public SessionCours(int id, LocalDate dateDebut, LocalDate dateFin, int nbreInscrits, Cours cours, Local local) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbreInscrits = nbreInscrits;
        this.cours = cours;
        this.local = local;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbreInscrits() {
        return nbreInscrits;
    }

    public void setNbreInscrits(int nbreInscrits) {
        this.nbreInscrits = nbreInscrits;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public List<Infos> getInfo() {
        return info;
    }

    public void setInfo(List<Infos> info) {
        this.info = info;
    }
}
