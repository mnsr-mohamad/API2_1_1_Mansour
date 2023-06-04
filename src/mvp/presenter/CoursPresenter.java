package mvp.presenter;

import Classes.Cours;
import Classes.Formateur;
import Classes.SessionCours;
import mvp.model.CoursSpecial;
import mvp.model.DAO;
import mvp.view.CoursViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class CoursPresenter {

    private DAO<Cours> model;
    private CoursViewInterface view;
    protected Comparator<Cours> cmp;
    private static final Logger logger = LogManager.getLogger(CoursPresenter.class);

    public CoursPresenter(DAO<Cours> model, CoursViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);


    }

    public void start() {
        view.setListDatas(getAll());
    }

    public Cours selectionner() {
        logger.info("Appel de la sélection de cours");
        Cours cr = view.selectionner(model.getAll());
        return cr;
    }

    public void update(Cours cours) {
        Cours cr = model.update(cours);
        if (cr == null) {
            view.affMsg("Mise à jour infructueuse");
        } else {
            view.affMsg("Mise à jour effectuée : " + cr);
        }
    }

    public void add(Cours cours) {
        Cours cr = model.add(cours);
        if (cr != null) {
            view.affMsg("Création de : " + cr);
        } else {
            view.affMsg("Erreur de création");
        }
    }

    public void remove(Cours cours) {
        boolean ok = model.remove(cours);
        if (ok) {
            view.affMsg("Cours effacé");
        } else {
            view.affMsg("Cours non effacé, car il est utilisé dans une autre table dans la base de donnée");
        }

    }

    public List<Cours> getAll() {
        return model.getAll();
    }

    public void search(int id_Cours) {
        Cours cr = model.read(id_Cours);
        if (cr == null) {
            view.affMsg("Recherche infructueuse");
        } else {
            view.affMsg(cr.toString());
        }
    }

    public void FormateursCours(Cours cours) {
        List<Formateur> lcf = ((CoursSpecial) model).FormateursCours(cours);
        if (lcf == null || lcf.isEmpty()) {
            view.affMsg("Aucun formateur trouvé");
        } else {
            view.affList(lcf);
        }
    }

    public void SessionsEntreDate(Cours cours) {
        List<SessionCours> lcs = ((CoursSpecial) model).SessionsEntreDate(cours);
        if (lcs == null || lcs.isEmpty()) {
            view.affMsg("Aucune session trouvée");
        } else {
            view.affList(lcs);
        }
    }

    public void SessionsParLocal(Cours cours) {
        List<SessionCours> lcs = ((CoursSpecial) model).SessionsParLocal(cours);
        if (lcs == null || lcs.isEmpty()) {
            view.affMsg("Aucune session trouvée");
        } else {
            view.affList(lcs);
        }
    }
}
