package mvp.model;

import Classes.Formateur;
import Classes.SessionCours;

import java.util.List;

public interface FormateurSpecial {


    public List<Formateur> form_encode();
    List<Formateur> gest_Formateur_dispo(SessionCours sess);



}
