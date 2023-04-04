package mvp;

import mvp.model.*;
import mvp.presenter.CoursPresenter;
import mvp.presenter.FormateurPresenter;
import mvp.presenter.LocalPresenter;
import mvp.view.*;

public class GestCli {

    public static void main(String[] args) {
        /*DAOCours cm = new CoursModelDB();
        CoursViewInterface cv = new CoursViewConsole();
        CoursPresenter cp = new CoursPresenter(cm,cv);//création et injection de dépendance
        cp.start();*/

        /*DAOFormateur fm = new FormateurModelDB();
        FormateurViewInterface fv = new FormateurViewConsole();
        FormateurPresenter fp = new FormateurPresenter(fm,fv);//création et injection de dépendance
        fp.start();*/

        DAOLocal lo = new LocalModelDB();
        LocalViewInterface lv = new LocalViewConsole();
        LocalPresenter lp= new LocalPresenter(lo,lv);
        lp.start();

    }
}

