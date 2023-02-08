package Classes;

public class Infos {

    private int nh;
    private Formateur formateur;

    public Infos(int nh, Formateur formateur) {
        this.nh = nh;
        this.formateur = formateur;
    }

    public int getNh() {
        return nh;
    }

    public void setNh(int nh) {
        this.nh = nh;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
}
