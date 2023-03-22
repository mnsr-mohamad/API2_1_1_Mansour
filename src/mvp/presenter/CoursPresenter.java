package mvp.presenter;

import Classes.Cours;
import mvp.model.DAOCours;
import mvp.view.CoursViewInterface;

import java.util.List;

public class CoursPresenter {

    private DAOCours model;
    private CoursViewInterface view;

    public CoursPresenter(DAOCours model, CoursViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<Cours> cours = model.getCours();
        view.setListDatas(cours);
    }
    public void update(Cours cours) {
        Cours cr = model.updateCours(cours);
        if (cr == null) view.affMsg("mise à jour infructueuse");
        else view.affMsg("mise à jour effectuée : " + cr);

    }


    public void addCours(Cours cours) {
        Cours cr = model.addCours(cours);
        if (cr != null) view.affMsg("création de :" + cr);
        else view.affMsg("erreur de création");
        List<Cours> cours2 = model.getCours();
        view.setListDatas(cours2);
    }


    public void removeCours(Cours cours) {
        boolean ok = model.removeCours(cours);
        if (ok) view.affMsg("cours effacé");
        else view.affMsg("cours non effacé");
        List<Cours> cours2 = model.getCours();

    }

    public List<Cours> getAll() {
        return model.getCours();
    }

    public void search(int id_Cours) {
        Cours cr = model.readCours(id_Cours);
        if(cr==null) view.affMsg("recherche infructueuse");
        else view.affMsg(cr.toString());
    }
}
