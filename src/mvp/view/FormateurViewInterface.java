package mvp.view;

import Classes.Cours;
import Classes.Formateur;
import Classes.SessionCours;
import mvp.presenter.FormateurPresenter;

import java.util.Comparator;
import java.util.List;

public interface FormateurViewInterface {

    public void setPresenter(FormateurPresenter presenter);

    public void setListDatas(List<Formateur> formateur, Comparator<Formateur> cmp);

    public void affMsg(String msg);

    public void affList(List infos);

    public Formateur selectionner(List<Formateur> formateur);

    public boolean repet(List<Formateur> formateur);

    public int nbreheures();

}
