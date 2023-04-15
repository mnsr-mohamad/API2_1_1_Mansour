package mvp.presenter;

import Classes.Cours;
import Classes.Local;
import mvp.model.DAOLocal;
import mvp.view.LocalViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LocalPresenter {

    private DAOLocal model;
    private LocalViewInterface view;

    private static final Logger logger = LogManager.getLogger(LocalPresenter.class);

    public LocalPresenter(DAOLocal model, LocalViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<Local> local = model.getLocal();
        view.setListDatas(local);
    }

    public Local selectionner(){
        logger.info("Appel de selection local");
        Local lc = view.selectionner(model.getLocal());
        return lc;
    }
    public void update(Local local) {
        Local fr = model.updateLocal(local);
        if (fr == null) view.affMsg("mise à jour infructueuse");
        else view.affMsg("mise à jour effectuée : " + fr);

    }


    public void addLocal(Local locals) {
        Local lo = model.addLocal(locals);
        if (lo != null) view.affMsg("création de :" + lo);
        else view.affMsg("erreur de création");
        List<Local> local2 = model.getLocal();
        view.setListDatas(local2);
    }


    public void removeLocal(Local local) {
        boolean ok = model.removeLocal(local);
        if (ok) view.affMsg("local effacé");
        else view.affMsg("local non effacé");
        List<Local> local2 = model.getLocal();
        view.setListDatas(local2);

    }

    public List<Local> getAll() {
        return model.getLocal();
    }

    public void search(int id_Local) {
        Local lo = model.readLocal(id_Local);
        if(lo==null) view.affMsg("recherche infructueuse");
        else view.affMsg(lo.toString());
    }
}
