package mvp;

import Classes.Cours;
import Classes.Formateur;
import Classes.Local;
import Classes.SessionCours;
import mvp.model.*;
import mvp.presenter.CoursPresenter;
import mvp.presenter.FormateurPresenter;
import mvp.presenter.LocalPresenter;
import mvp.presenter.SessionCoursPresenter;
import mvp.view.*;
import utilitaires.Utilitaire;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GestCli {

    public static void main(String[] args) {
        DAO<Cours> cm = new CoursModelDB();
        CoursViewInterface cv = new CoursViewConsole();
        //CoursPresenter cp = new CoursPresenter(cm,cv,(c1,c2)->c1.getMatiere().compareTo(c2.getMatiere()));//création et injection de dépendance
        CoursPresenter cp = new CoursPresenter(cm,cv);

        DAO<Formateur> fm = new FormateurModelDB();
        FormateurViewInterface fv = new FormateurViewConsole();
        Comparator<Formateur> cmpl=(f1,f2)->f1.getNom().compareTo(f2.getNom());
        cmpl=cmpl.thenComparing((f1,f2)-> f1.getPrenom().compareTo(f2.getPrenom()));
        FormateurPresenter fp = new FormateurPresenter(fm,fv,cmpl);//création et injection de dépendance


        DAO<Local> lo = new LocalModelDB();
        LocalViewInterface lv = new LocalViewConsole();
        LocalPresenter lp= new LocalPresenter(lo,lv);


        DAO<SessionCours> ss = new SessionCoursModelDB();
        SessionCoursViewInterface sv = new SessionCoursViewConsole();
        SessionCoursPresenter sp= new SessionCoursPresenter(ss,sv);


        Scanner sc = new Scanner(System.in);
        boolean quitter = false;

        do {
            List<String> loptions = Arrays.asList("Cours","Formateur", "Local", "SessionCours", "Fin");
            int ch = Utilitaire.choixListe(loptions);

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
                    quitter = true;
                    break;
            }
        } while (!quitter);

    }


}

