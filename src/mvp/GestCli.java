package mvp;

import mvp.model.CoursModelDB;
import mvp.model.DAOCours;
import mvp.model.DAOFormateur;
import mvp.model.FormateurModelDB;
import mvp.presenter.CoursPresenter;
import mvp.presenter.FormateurPresenter;
import mvp.view.CoursViewConsole;
import mvp.view.CoursViewInterface;
import mvp.view.FormateurViewConsole;
import mvp.view.FormateurViewInterface;

public class GestCli {

    public static void main(String[] args) {
        /*DAOCours cm = new CoursModelDB();
        CoursViewInterface cv = new CoursViewConsole();
        CoursPresenter cp = new CoursPresenter(cm,cv);//création et injection de dépendance
        cp.start();*/

        DAOFormateur fm = new FormateurModelDB();
        FormateurViewInterface fv = new FormateurViewConsole();
        FormateurPresenter fp = new FormateurPresenter(fm,fv);//création et injection de dépendance
        fp.start();

    }
}

