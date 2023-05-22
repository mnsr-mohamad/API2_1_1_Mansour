package desginpatterns.builder;

/**
 * classe Formateur du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 */
public class Formateur {
    /**
     * identifiant du formateur
     */
    protected int id_Formateur;
    /**
     * mail unique du formateur
     */
    protected String mail;
    /**
     * nom du formateur
     */
    protected String nom;
    /**
     * prenom du formateur
     */
    protected String prenom;


    /**
     * constructeur paramétré
     *
     * @param id_Formateur identifiant du formateur
     * @param mail         mail du formateur
     * @param nom          nom du formateur
     * @param prenom       prenom du formateur
     */
    public Formateur(int id_Formateur, String mail, String nom, String prenom) {
        this.id_Formateur = id_Formateur;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * getter id_Formateur
     *
     * @return identifiant du formateur
     */
    public int getId_Formateur() {
        return id_Formateur;
    }

    /**
     * setter id_Formateur
     *
     * @param id_Formateur identifiant du formateur
     */

    public void setId_Formateur(int id_Formateur) {
        this.id_Formateur = id_Formateur;
    }

    /**
     * getter mail
     *
     * @return mail du formateur
     */

    public String getMail() {
        return mail;
    }

    /**
     * setter mail
     *
     * @param mail mail du formateur
     */

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * getter nom
     *
     * @return nom du formateur
     */

    public String getNom() {
        return nom;
    }

    /**
     * setter nom
     *
     * @param nom nom du formateur
     */

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter prenom
     *
     * @return prenom du formateur
     */

    public String getPrenom() {
        return prenom;
    }

    /**
     * setter prenom
     *
     * @param prenom nom du formateur
     */

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    @Override
    public String toString() {
        return "Formateur{" +
                "id_Formateur=" + id_Formateur +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
