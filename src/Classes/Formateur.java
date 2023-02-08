package Classes;

public class Formateur {

    private int id_Formateur;
    private String mail;
    private String nom;
    private String prenom;

    public Formateur(int id_Formateur, String mail, String nom, String prenom) {
        this.id_Formateur = id_Formateur;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId_Formateur() {
        return id_Formateur;
    }

    public void setId_Formateur(int id_Formateur) {
        this.id_Formateur = id_Formateur;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
