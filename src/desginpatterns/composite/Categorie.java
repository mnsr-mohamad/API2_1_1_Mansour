package desginpatterns.composite;

import java.util.HashSet;
import java.util.Set;

public class Categorie extends Element{
    private String nom;
    private Set<Element> elts=new HashSet<>();

    public Categorie(int id,String nom){
        super(id);
        this.nom=nom;
    }

    @Override
    public String toString() {
        StringBuilder aff= new StringBuilder(getId()+" "+nom+"\n");

        for(Element e:elts){
            aff.append(e+"\n");
        }
        return aff+"Nombre total d'heures pour le cours de  " +nom +" = "+nbreHeures()+"\n";
    }

    @Override
    public int nbreHeures() {
        int somme=0;
        for(Element pc:elts){
            somme+=pc.nbreHeures();
        }
        return somme;
    }


    public Set<Element> getElts() {
        return elts;
    }

}
