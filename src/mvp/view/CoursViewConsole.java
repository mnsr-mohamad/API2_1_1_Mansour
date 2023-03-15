package mvp.view;

import Classes.Cours;
import mvp.presenter.CoursPresenter;

import java.util.List;
import java.util.Scanner;

public class CoursViewConsole implements CoursViewInterface{
    private CoursPresenter presenter;
    private List<Cours>lc;
    private Scanner sc = new Scanner(System.in);
    public CoursViewConsole() {

    }

    @Override
    public void setPresenter(CoursPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Cours> cours) {

        this.lc=cours;
        int i=1;
        for(Cours cr: lc){
            System.out.println((i++)+"."+cr);
        }
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:"+msg);
    }


    public void menu(){
        do{
            System.out.println("1.ajout 2.retrait 3.fin");

            int ch = sc.nextInt();
            sc.skip("\n");
            switch(ch){
                case 1: ajouter();
                    break;
                case 2 : retirer();
                    break;
                case 3 : System.exit(0);
            }
        }while(true);
    }

    private void retirer() {
        System.out.println("numéro de ligne : ");
        int nl =  sc.nextInt()-1;
        sc.skip("\n");
        if (nl >= 0) {
            Cours cours = lc.get(nl);
            presenter.removeCours(cours);
        }
    }

    private void ajouter() {
        System.out.print("matière : ");
        String matiere = sc.nextLine();
        System.out.print("nombres d'heures : ");
        int heures= sc.nextInt();
        presenter.addCours(new Cours(0,matiere,heures));
    }
}
