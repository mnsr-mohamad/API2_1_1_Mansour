package mvp;

import mvp.model.CoursModelDB;
import mvp.model.DAOCours;
import mvp.presenter.CoursPresenter;
import mvp.view.CoursViewConsole;
import mvp.view.CoursViewInterface;

public class GestCli {

    public static void main(String[] args) {
        //DAOClient cm = new ClientModel();
        DAOCours cm = new CoursModelDB();
         CoursViewInterface cv = new CoursViewConsole();
        //CoursViewInterface cv = new CoursViewGraph();
        CoursPresenter cp = new CoursPresenter(cm,cv);//création et injection de dépendance
        cp.start();

    }
}

