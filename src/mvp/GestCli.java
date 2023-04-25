package mvp;

import mvp.model.*;
import mvp.presenter.CoursPresenter;
import mvp.presenter.FormateurPresenter;
import mvp.presenter.LocalPresenter;
import mvp.presenter.SessionCoursPresenter;
import mvp.view.*;

import java.util.Scanner;

public class GestCli {

    public static void main(String[] args) {
        DAOCours cm = new CoursModelDB();
        CoursViewInterface cv = new CoursViewConsole();
        CoursPresenter cp = new CoursPresenter(cm,cv);//création et injection de dépendance


        DAOFormateur fm = new FormateurModelDB();
        FormateurViewInterface fv = new FormateurViewConsole();
        FormateurPresenter fp = new FormateurPresenter(fm,fv);//création et injection de dépendance


        DAOLocal lo = new LocalModelDB();
        LocalViewInterface lv = new LocalViewConsole();
        LocalPresenter lp= new LocalPresenter(lo,lv);


        DAOSessionCours ss = new SessionCoursModelDB();
        SessionCoursViewInterface sv = new SessionCoursViewConsole();
        SessionCoursPresenter sp= new SessionCoursPresenter(ss,sv);


        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1.Cours\n2.Formateur\n3.Local\n4.SessionCours\n5.Fin");

            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    cp.start();
                    break;
                case 2:
                    fp.start();
                    break;
                case 3:
                    lp.start();
                    break;
                case 4:
                    sp.setCoursPresenter(cp);
                    sp.setLocalPresenter(lp);
                    sp.setFormateurPresenter(fp);
                    sp.start();
                    break;
                case 5:

                    System.exit(0);
            }
        } while (true);
    }


}

