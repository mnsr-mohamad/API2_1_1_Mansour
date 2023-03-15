package mvp.model;

import Classes.Cours;

import java.util.List;

public interface DAOCours {

    Cours addCours (Cours cours);

    boolean removeCours(Cours cours);

    List<Cours> getCours();
}