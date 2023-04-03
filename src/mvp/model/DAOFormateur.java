package mvp.model;

import Classes.Formateur;

import java.util.List;

public interface DAOFormateur {
    Formateur addFormateur (Formateur formateur);

    boolean removeFormateur(Formateur formateur);

    List<Formateur> getFormateur();

    Formateur updateFormateur(Formateur formateur);

    Formateur readFormateur(int id_Formateur);
}
