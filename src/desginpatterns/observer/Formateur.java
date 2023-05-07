package desginpatterns.observer;

import java.util.Objects;

/**
 * classe Formateur du projet
 *
 * @author Mansour Mohamad
 * @version 1.0
 */
public class Formateur extends Observer{
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

    /**
     * égalité de deux formateur basée sur l'id
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formateur form = (Formateur) o;
        return id_Formateur == form.id_Formateur;
    }

    /**
     * calcul du hashcode du formateur basé sur l'id
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id_Formateur);
    }


    /**
     * méthode toString
     *
     * @return informations complètes du taxi
     */
    @Override
    public String toString() {
        return "Formateur{" +
                "id_Formateur=" + id_Formateur +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    @Override
    public void update(String msg) {
        System.out.println(prenom+" "+nom+" a été informé"+msg);
    }
}
