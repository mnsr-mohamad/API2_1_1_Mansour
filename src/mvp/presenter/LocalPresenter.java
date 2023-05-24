package mvp.presenter;

import Classes.Local;
import mvp.model.DAO;
import mvp.model.LocalSpecial;
import mvp.view.LocalViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LocalPresenter {

    private DAO<Local> model;
    private LocalViewInterface view;

    private static final Logger logger = LogManager.getLogger(LocalPresenter.class);

    public LocalPresenter(DAO<Local> model, LocalViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<Local> local = model.getAll();
        view.setListDatas(local);
    }

    public Local selectionner() {
        logger.info("Appel de la sélection du local");
        Local lc = view.selectionner(model.getAll());
        return lc;
    }

    public void update(Local local) {
        Local lo = model.update(local);
        if (lo == null) {
            view.affMsg("Mise à jour infructueuse");
        } else {
            view.affMsg("Mise à jour effectuée : " + lo);
        }
    }

    public void add(Local locals) {
        Local lo = model.add(locals);
        if (lo != null) {
            view.affMsg("Création de : " + lo);
        } else {
            view.affMsg("Erreur de création");
        }
        List<Local> local2 = model.getAll();
        view.setListDatas(local2);
    }

    public void removeLocal(Local local) {
        boolean ok = model.remove(local);
        if (ok) {
            view.affMsg("Local effacé");
        } else {
            view.affMsg("Local non effacé");
        }
        List<Local> local2 = model.getAll();
        view.setListDatas(local2);
    }

    public List<Local> getAll() {
        return model.getAll();
    }

    public void search(int id_Local) {
        Local lo = model.read(id_Local);
        if (lo == null) {
            view.affMsg("Recherche infructueuse");
        } else {
            view.affMsg(lo.toString());
        }
    }

    public void insert_local() {
        int nbr = ((LocalSpecial) model).insert_local();
        if (nbr != 0) {
            view.affMsg("L'ID du local inséré est : " + nbr);
        } else {
            view.affMsg("Le local n'a pas été inséré");
        }
    }

    public void verif_local(Local lo) {
        boolean verif = ((LocalSpecial) model).verif_local(lo);

        if (!verif) {
            view.affMsg("Le local est disponible");
        } else {
            view.affMsg("Le local n'est pas disponible");
        }
    }
}
