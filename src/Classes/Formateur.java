package Classes;

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
    private int id_Formateur;
    /**
     * mail unique du formateur
     */
    private String mail;
    /**
     * nom du formateur
     */
    private String nom;
    /**
     * prenom du formateur
     */
    private String prenom;


    /**
     * constructeur paramétré
     *
     * @param id_Formateur identifiant du formateur
     * @param mail         mail du formateur
     * @param nom          nom du formateur
     * @param prenom       prenom du formateur
     */
    public Formateur(int id_Formateur, String mail, String nom, String prenom) throws Exception {
        if (mail.trim().equals("")) {
            throw new Exception("Le champ 'mail' est vide.");
        }

        // Expression régulière pour valider le format de l'adresse email
        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (!mail.matches(regexEmail)) {
            throw new Exception("Format d'adresse email invalide");
        }

        if (nom.trim().equals("")) {
            throw new Exception("Le champ 'nom' est vide.");
        }

        // Expression régulière pour valider le format du nom (lettres et espaces)
        String regexNom = "[A-Za-z\\s]+";

        if (!nom.matches(regexNom)) {
            throw new Exception("Format de nom invalide");
        }

        if (prenom.trim().equals("")) {
            throw new Exception("Le champ 'prenom' est vide.");
        }

        // Expression régulière pour valider le format du prénom (lettres et espaces)
        String regexPrenom = "[A-Za-z\\s]+";

        if (!prenom.matches(regexPrenom)) {
            throw new Exception("Format de prénom invalide");
        }

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
