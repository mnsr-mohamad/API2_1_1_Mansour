package mvp.presenter;

import Classes.Formateur;
import Classes.SessionCours;
import mvp.model.DAO;
import mvp.model.FormateurSpecial;
import mvp.view.FormateurViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class FormateurPresenter {

    private DAO<Formateur> model;
    private FormateurViewInterface view;
    protected Comparator<Formateur> cmp;
    private static final Logger logger = LogManager.getLogger(FormateurPresenter.class);

    public FormateurPresenter(DAO<Formateur> model, FormateurViewInterface view, Comparator<Formateur> cmp) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
        this.cmp=cmp;
    }

    public void start() {
        List<Formateur> formateur = model.getAll();
        view.setListDatas(formateur,cmp);
    }

    public void update(Formateur formateur) {
        Formateur fr = model.update(formateur);
        if (fr == null) {
            view.affMsg("Mise à jour infructueuse");
        } else {
            view.affMsg("Mise à jour effectuée : " + fr);
        }
    }

    public void add(Formateur formateurs) {
        Formateur fr = model.add(formateurs);
        if (fr != null) {
            view.affMsg("Création de : " + fr);
        } else {
            view.affMsg("Erreur de création");
        }
        List<Formateur> formateur2 = model.getAll();
        view.setListDatas(formateur2,cmp);
    }

    public void remove(Formateur formateur) {
        boolean ok = model.remove(formateur);
        if (ok) {
            view.affMsg("Formateur effacé");
        } else {
            view.affMsg("Formateur non effacé");
        }
        List<Formateur> formateur2 = model.getAll();
        view.setListDatas(formateur2,cmp);
    }

    public List<Formateur> getAll() {
        List<Formateur> f = model.getAll();
        f.sort(cmp);

        return f;
    }

    public void search(int id_Formateur) {
        Formateur fr = model.read(id_Formateur);
        if (fr == null) {
            view.affMsg("Recherche infructueuse");
        } else {
            view.affMsg(fr.toString());
        }
    }

    public Formateur selectionner(SessionCours sess) {
        logger.info("Appel de la sélection :");
        Formateur fo = view.selectionner(((FormateurSpecial) model).gest_Formateur_dispo(sess));
        return fo;
    }

    public boolean repet(SessionCours sess) {
        logger.info("Passage de la boucle :");
        boolean verif = view.repet(((FormateurSpecial) model).gest_Formateur_dispo(sess));
        return verif;
    }

    public int nbreheure() {
        logger.info("Appel du nombre d'heures :");
        int heures = view.nbreheures();
        return heures;
    }

    public void form_encode() {
        List<Formateur> fo = ((FormateurSpecial) model).form_encode();
        if (fo == null || fo.isEmpty()) {
            view.affMsg("Aucun formateur trouvé");
        } else {
            view.affList(fo);
        }
    }
}
