package exercicesJDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

import Classes.Cours;
import Classes.Formateur;
import Classes.Local;
import Classes.SessionCours;
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
        int heures = sc.nextInt();

        String query1 = "insert into APICOURS(matière,heures) values(?,?)";
        String query2 = "select id_cours from APICOURS where matière= ? and heures =? ";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, matière);
            pstm1.setInt(2, heures);
            int n = pstm1.executeUpdate();
            System.out.println(n + " ligne insérée");
            if (n == 1) {
                pstm2.setString(1, matière);
                pstm2.setInt(2, heures);
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_cours = rs.getInt(1);
                    System.out.println("id_cours = " + id_cours);
                } else System.out.println("record introuvable");
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }


    public void recherche() {

        System.out.println("id du cours  ");
        int idrech = sc.nextInt();
        String query = "select * from APICOURS  where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, idrech);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String matiere = rs.getString(2);
                int heures = rs.getInt(3);

                Cours cr = new Cours(idrech, matiere, heures);
                System.out.println(cr);
                opSpeciales(cr);
            } else System.out.println("record introuvable");
        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modification() {
        System.out.println("id du cours recherché");
        int idrech = sc.nextInt();
        sc.skip("\n");
        System.out.println("nouvelle matière ");
        String nmatiere = sc.nextLine();
        String query = "update APICOURS set matière=? where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, nmatiere);
            pstm.setInt(2, idrech);
            int n = pstm.executeUpdate();
            if (n != 0) System.out.println(n + "ligne mise à jour");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

    public void suppression() {
        System.out.println("id du cours recherché  ");
        int idrech = sc.nextInt();
        String query = "delete from APICOURS where id_cours  = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, idrech);
            int n = pstm.executeUpdate();
            if (n != 0) System.out.println(n + "ligne supprimée");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }

    }

    private void tous() {
        String query = "select * from APICOURS";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_cours = rs.getInt(1);
                String matiere = rs.getString(2);
                String heures = rs.getString(3);

                System.out.printf("ID : %d Matière : %s  Nombres d'heures : %s \n", id_cours, matiere, heures);
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

    private void opSpeciales(Cours cours) {
        do {
            System.out.println("1.Formateur par cours\n2.Sessions par cours \n3.Sessions par cours entre 2 dates\n4.menu principal");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    formateurParCours(cours);
                    break;
                case 2:
                    sessionParCours(cours);
                    break;
                case 3:
                    System.out.print("Entrez la date de début (AAAA-MM-JJ) : ");
                    String dateDebutStr = sc.nextLine();
                    LocalDate dateDebut = LocalDate.parse(dateDebutStr);
                    System.out.print("Entrez la date de fin (AAAA-MM-JJ) : ");
                    String dateFinStr = sc.nextLine();
                    LocalDate dateFin = LocalDate.parse(dateFinStr);
                    sessionParCoursAvecDates(cours,dateDebut,dateFin);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }

    private void formateurParCours(Cours cours) {
        String query = "SELECT DISTINCT * FROM APIFORMATEUR INNER JOIN APIINFOS ON APIFORMATEUR.ID_FORMATEUR=APIINFOS.ID_FORMATEUR JOIN APISESSIONCOURS ON APISESSIONCOURS.ID_SESSIONCOURS=APIINFOS.ID_SESSIONCOURS JOIN APICOURS ON APICOURS.ID_COURS=APISESSIONCOURS.ID_COURS WHERE APICOURS.ID_COURS =?";
        rechercheCours(cours, query);
    }

    private void sessionParCours(Cours cours) {
        String query = "SELECT s.id_SessionCours, s.dateDebut, s.dateFin, s.nbreInscrits, l.sigle FROM APISessionCours s INNER JOIN APICours c ON s.id_Cours = c.id_Cours INNER JOIN APILocal l ON s.id_Local = l.id_Local WHERE c.id_Cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            ResultSet rs = pstm.executeQuery();
            boolean trouve = false;
            while (rs.next()) {
                trouve = true;
                int id_sess = rs.getInt(1);
                LocalDate dateDebut = rs.getDate(2) == null ? null : rs.getDate(2).toLocalDate();
                LocalDate dateFin = rs.getDate(3) == null ? null : rs.getDate(3).toLocalDate();
                int nbreInscrits = rs.getInt(4);
                String sigle = rs.getString(5);
                Local local = null;
                if (sigle != null) {
                    local = new Local();
                    local.setSigle(sigle);
                }
                SessionCours sr = null;
                try {
                    sr = new SessionCours(id_sess, dateDebut, dateFin, nbreInscrits, cours, local);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sr);
            }
            if (!trouve) System.out.println("aucune sessions trouvée");
        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }

    }

    private void sessionParCoursAvecDates(Cours cours, LocalDate dateDebut, LocalDate dateFin) {
        String query = "SELECT s.id_SessionCours, s.dateDebut, s.dateFin, s.nbreInscrits, l.sigle FROM APISessionCours s INNER JOIN APICours c ON s.id_Cours = c.id_Cours INNER JOIN APILocal l ON s.id_Local = l.id_Local WHERE c.id_Cours = ? AND s.dateDebut >= ? AND s.dateFin <= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            pstm.setDate(2, java.sql.Date.valueOf(dateDebut));
            pstm.setDate(3, java.sql.Date.valueOf(dateFin));
            ResultSet rs = pstm.executeQuery();
            boolean trouve = false;
            while (rs.next()) {
                trouve = true;
                int id_sess = rs.getInt("id_SessionCours");
                LocalDate debut = rs.getDate("dateDebut").toLocalDate();
                LocalDate fin = rs.getDate("dateFin").toLocalDate();
                int nbreInscrits = rs.getInt("nbreInscrits");
                Local local = new Local(rs.getString("sigle"));
                SessionCours sr = null;
                try {
                    sr = new SessionCours(id_sess, debut, fin, nbreInscrits, cours, local);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sr);
            }
            if (!trouve) {
                System.out.println("Aucune session trouvée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }


    }

    private void rechercheCours(Cours cours, String query) {
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            ResultSet rs = pstm.executeQuery();
            boolean trouve = false;
            while (rs.next()) {
                trouve = true;
                int nc = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                Formateur fo = null;
                try {
                    fo = new Formateur(nc, mail, nom, prenom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(fo);
            }
            if (!trouve) System.out.println("aucune formateur trouvé");
        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }

    }


    public static void main(String[] args) {

        GestCours g = new GestCours();
        g.gestion();
    }


}
