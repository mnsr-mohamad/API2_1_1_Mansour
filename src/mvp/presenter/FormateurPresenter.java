package mvp.presenter;

import Classes.Formateur;
import mvp.model.DAOFormateur;
import mvp.view.FormateurViewInterface;

import java.util.List;

public class FormateurPresenter {

    private DAOFormateur model;
    private FormateurViewInterface view;

    public FormateurPresenter(DAOFormateur model, FormateurViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<Formateur> formateur = model.getFormateur();
        view.setListDatas(formateur);
    }
    public void update(Formateur formateur) {
        Formateur fr = model.updateFormateur(formateur);
        if (fr == null) view.affMsg("mise à jour infructueuse");
        else view.affMsg("mise à jour effectuée : " + fr);

    }


    public void addFormateur(Formateur formateurs) {
        Formateur fr = model.addFormateur(formateurs);
        if (fr != null) view.affMsg("création de :" + fr);
        else view.affMsg("erreur de création");
        List<Formateur> formateur2 = model.getFormateur();
        view.setListDatas(formateur2);
    }


    public void removeFormateur(Formateur formateur) {
        boolean ok = model.removeFormateur(formateur);
        if (ok) view.affMsg("formateur effacé");
        else view.affMsg("formateur non effacé");
        List<Formateur> formateur2 = model.getFormateur();
        view.setListDatas(formateur2);

    }

    public List<Formateur> getAll() {
        return model.getFormateur();
    }

    public void search(int id_Formateur) {
        Formateur fr = model.readFormateur(id_Formateur);
        if(fr==null) view.affMsg("recherche infructueuse");
        else view.affMsg(fr.toString());
    }
}
