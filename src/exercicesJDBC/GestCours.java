package exercicesJDBC;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

import Classes.Cours;
import myconnections.DBConnection;
public class GestCours {
    private Scanner sc = new Scanner(System.in);
    private Connection dbConnect;

    public void gestion() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        do {
            System.out.println("1.ajout\n2.recherche\n3.modification\n4.suppression\n5.tous\n6.fin");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    modification();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    tous();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }




    public void ajout() {
        System.out.print("Matière : ");
        String matière = sc.nextLine();
        System.out.print("Heures : ");
        int heures= sc.nextInt();

        String query1 = "insert into APICOURS(matière,heures) values(?,?)";
        String query2 = "select id_cours from APICOURS where matière= ? and heures =? ";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setString(1,matière);
            pstm1.setInt(2,heures);
            int n = pstm1.executeUpdate();
            System.out.println(n+" ligne insérée");
            if(n==1){
                pstm2.setString(1,matière);
                pstm2.setInt(2,heures);
                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int id_cours= rs.getInt(1);
                    System.out.println("id_cours = "+id_cours);
                }
                else System.out.println("record introuvable");
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }


    public void recherche() {

        System.out.println("id du cours  ");
        int idrech = sc.nextInt();
        String query = "select * from APICOURS  where id_cours = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String matiere = rs.getString(2);
                int heures = rs.getInt(3);

                Cours cr = new Cours(idrech,matiere,heures,null);
                System.out.println(cr);
                opSpeciales();
            }
            else System.out.println("record introuvable");
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }

    }

    public void modification() {
        System.out.println("id du cours recherché");
        int idrech = sc.nextInt();
        sc.skip("\n");
        System.out.println("nouvelle matière ");
        String nmatiere = sc.nextLine();
        String query = "update APICOURS set matière=? where id_cours = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,nmatiere);
            pstm.setInt(2,idrech);
            int n = pstm.executeUpdate();
            if(n!=0) System.out.println(n+ "ligne mise à jour");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

   public void suppression() {
        System.out.println("id du cours recherché  ");
        int idrech = sc.nextInt();
        String query = "delete from APICOURS where id_cours  = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            int n = pstm.executeUpdate();
            if(n!=0) System.out.println(n+ "ligne supprimée");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }

    }

   private void tous() {
        String query="select * from APICOURS";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int id_cours = rs.getInt(1);
                String matiere = rs.getString(2);
                String heures = rs.getString(3);

                System.out.printf("ID : %d Matière : %s  Nombres d'heures : %s \n", id_cours, matiere, heures);
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
   private void opSpeciales() {
        do {
            System.out.println("1.Formateur par cours\n2.factures non payees\n3.factures en retard\n4.factures payees\n5.produits achetés\n6.menu principal");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    formateurParCours();
                    break;
                case 2:
                    //factNonPayees(client);
                    break;
                case 3:
                    //factRetard(client);
                    break;
                case 4:
                   // factPayees(client);
                    break;
                case 5:
                    //produits(client);
                    break;

                case 6: return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }



private void formateurParCours(){
    String query = "SELECT DISTINCT NOM,PRENOM "+"FROM APIFORMATEUR INNER JOIN API_INFOS ON APIFORMATEUR.ID_FORMATEUR=APIINFOS.ID_FORMATEUR JOIN APISESSIONCOURS ON APISESSIONCOURS.ID_SESSIONSCOURS=APIINFOS.ID_SESSIONSCOURS JOIN APICOURS ON APICOURS.ID_COURS=APISESSIONCOURS=ID_COURS WHERE APICOURS.ID_COURS =?";
    try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
        System.out.print("ID du cours recherché (0 pour arrêter): ");
        int nr = sc.nextInt();
        while (nr!=0 ) {
            pstm.setInt(1, nr);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    System.out.println(nr + ":" + nom + " " + prenom);
                }
                if (!trouve)
                    System.out.println("Id du formateur inconnu ou pas de cours");
            }
            System.out.print("No du client recherche(0 pour stopper) :");
            nr = sc.nextInt();
        }
    } catch (SQLException e) {
        System.out.println("erreur SQL =" + e);
    }

}
   /* private void factPayees(Client client) {
        String query = "select * from APICOMFACT where idclient = ? AND  DATEPAYEMENT IS NOT NULL order by IDCOMMANDE ";
        rechercheCommandes(client,query);
    }
    private void factRetard(Client client) {
        String query = "select * from APICOMFACT where idclient = ?  AND  DATEPAYEMENT IS NULL AND DATEFACTURATION + 30 < SYSDATE order by IDCOMMANDE";
        rechercheCommandes(client,query);
    }

    private void factNonPayees(Client client) {
        String query = "select * from APICOMFACT where idclient = ? AND DATEFACTURATION IS NOT NULL AND DATEPAYEMENT IS NULL order by IDCOMMANDE ";
        rechercheCommandes(client,query);
    }


    private void commandes(Client client) {

        String query = "select * from APICOMFACT where idclient = ? order by IDCOMMANDE";
        rechercheCommandes(client,query);
    }
    private void rechercheCommandes(Client client,String query){
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,client.getIdclient());
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int nc = rs.getInt(1);
                Integer nf = rs.getInt(2);//permet d'éviter une erreur si n° de facture nul
                LocalDate dateCom = rs.getDate(3)==null? null: rs.getDate(3).toLocalDate();
                LocalDate dateFact = rs.getDate(4)==null? null: rs.getDate(3).toLocalDate();
                LocalDate datePay= rs.getDate(5)==null? null: rs.getDate(3).toLocalDate();
                BigDecimal montant = rs.getBigDecimal(6);
                char etat = rs.getString(7).charAt(0);
                ComFact cf = new ComFact(nc,nf,dateCom,etat,montant,client) ;
                cf.setDateFacturation(dateFact);
                cf.setDatePayement(datePay);
                System.out.println(cf);
            }
            if(!trouve) System.out.println("aucune commande trouvée");
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
    private void produits(Client client) {
        String query="select * from prodcli where idclient = ? order by numprod";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,client.getIdclient());
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int idprod = rs.getInt(2);
                String numprod = rs.getString(3);
                String descr = rs.getString(4);
                Produit pr = new Produit(idprod,numprod,descr,null,0,0);
                System.out.println(pr);
            }
            if(!trouve) System.out.println("aucune commande trouvée");
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
*/
    public static void main(String[] args) {

        GestCours g = new GestCours();
        g.gestion();
    }


    /*Scanner sc=new Scanner(System.in);
    Connection dbConnect1 = DBConnection.getConnection();
    String query = "SELECT NOM,PRENOM "+"FROM APIFORMATEUR INNER JOIN API_INFOS ON APIFORMATEUR.ID_FORMATEUR=APIINFOS.ID_FORMATEUR JOIN APISESSIONCOURS ON APISESSIONCOURS.ID_SESSIONSCOURS=APIINFOS.ID_SESSIONSCOURS JOIN APICOURS ON APICOURS.ID_COURS=APISESSIONCOURS=ID_COURS WHERE APICOURS.NOM =?";

    try(PreparedStatement pstm =dbConnect1.prepareStatement(query)){
        System.out.print("No de client recherché (0 pour arrêter): ");
        int nr = sc.nextInt();
        while (nr != 0) {
            pstm.setInt(1, nr);
            try (ResultSet rs = pstm.executeQuery()) {
                boolean trouve = false;
                while (rs.next()) {
                    trouve = true;
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    System.out.println(nr + ":" + nom + " " + prenom + " " + numCom);
                }
                if (!trouve)
                    System.out.println("numero de client inconnu ou pas de commande/facture");
            }
            System.out.print("No du client recherche(0 pour stopper) :");
            nr = sc.nextInt();
        }
    } catch (SQLException e) {
        System.out.println("erreur SQL =" + e);
    }*/



}
