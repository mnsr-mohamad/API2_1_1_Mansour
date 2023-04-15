package mvp.presenter;

import Classes.Cours;
import Classes.Formateur;
import Classes.SessionCours;
import mvp.model.CoursSpecial;
import mvp.model.DAOCours;
import mvp.view.CoursViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CoursPresenter {

    private DAOCours model;
    private CoursViewInterface view;

    private static final Logger logger = LogManager.getLogger(CoursPresenter.class);

    public CoursPresenter(DAOCours model, CoursViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        List<Cours> cours = model.getCours();
        view.setListDatas(cours);
    }

    public Cours selectionner(){
        logger.info("Appel de selection cours");
        Cours cr = view.selectionner(model.getCours());
        return cr;
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


    public void FormateursCours (Cours cours){
        List<Formateur> lcf = ((CoursSpecial)model).FormateursCours(cours);
        if(lcf==null || lcf.isEmpty()) view.affMsg("aucun formateur trouvé");
        else view.affList(lcf);
    }

    public void SessionsEntreDate (Cours cours){
        List<SessionCours> lcs = ((CoursSpecial)model).SessionsEntreDate(cours);
        if(lcs==null || lcs.isEmpty()) view.affMsg("aucune sessions trouvée");
        else view.affList(lcs);
    }

    public void SessionsParLocal (Cours cours){
        List<SessionCours> lcs = ((CoursSpecial)model).SessionsParLocal(cours);
        if(lcs==null || lcs.isEmpty()) view.affMsg("aucune sessions trouvée");
        else view.affList(lcs);
    }




}
