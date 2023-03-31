package mvp.model;

import Classes.Cours;
import Classes.Formateur;
import Classes.SessionCours;

import java.util.List;

public interface CoursSpecial {

    public List<Formateur> FormateursCours(Cours cours);

    public List<SessionCours> SessionsParLocal(Cours cours);

    public List<SessionCours> SessionsEntreDate(Cours cours);
}
