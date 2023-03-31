package mvp.model;

import Classes.Cours;
import Classes.Formateur;
import Classes.Local;
import Classes.SessionCours;
import myconnections.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoursModelDB implements DAOCours,CoursSpecial {
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(CoursModelDB.class);

    public CoursModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }

    @Override
    public Cours addCours(Cours cours) {
        String query1 = "insert into APICOURS (matière,heures) values(?,?)";
        String query2 = "select id_cours from APICOURS where matière= ? and  heures=? ";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, cours.getMatiere());
            pstm1.setInt(2, cours.getHeures());

            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, cours.getMatiere());
                pstm2.setInt(2, cours.getHeures());

                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_cours = rs.getInt(1);
                    cours.setId_Cours(id_cours);
                    return cours;
                } else {
                    logger.error("record introuvable");
                    //System.err.println("record introuvable");
                    return null;
                }
            } else return null;

        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return null;
        }
    }

    @Override
    public boolean removeCours(Cours cours) {
        String query = "delete from APICOURS where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            int n = pstm.executeUpdate();
            if (n != 0) return true;
            else return false;

        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return false;
        }
    }

    @Override
    public List<Cours> getCours() {
        List<Cours> lc = new ArrayList<>();
        String query = "select * from APICOURS";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_cours = rs.getInt(1);
                String matiere = rs.getString(2);
                int heures = rs.getInt(3);
                Cours cr = new Cours(id_cours, matiere, heures);
                lc.add(cr);
            }
            return lc;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return null;
        }
    }


    @Override
    public Cours updateCours(Cours cours) {
        String query = "update APICOURS set matière =?,heures=? where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, cours.getMatiere());
            pstm.setInt(2, cours.getHeures());
            pstm.setInt(3, cours.getId_Cours());
            int n = pstm.executeUpdate();
            if (n != 0) return readCours(cours.getId_Cours());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }


    @Override
    public Cours readCours(int id_cours) {
        String query = "select * from APICOURS where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_cours);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String matiere = rs.getString(2);
                int heures = rs.getInt(3);
                Cours cr = new Cours(id_cours, matiere, heures);

                return cr;

            } else {
                return null;
            }
        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        }
    }

  /*  public List<Formateur> FormateursCours(Cours cours) {
        List<Formateur> lf = new ArrayList<>();
        String query = "SELECT DISTINCT f.* FROM APIFORMATEUR f " +
                "JOIN APIINFOS i ON f.id_Formateur = i.id_Formateur " +
                "JOIN APISESSIONCOURS s ON i.id_SessionCours = s.id_SessionCours " +
                "WHERE s.id_Cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            ResultSet rs = pstm.executeQuery();
            boolean trouver = false;
            while (rs.next()) {
                trouver = true;
                int idform = rs.getInt(2);
                String mail = rs.getString(3);
                String nom = rs.getString(4);
                String prenom = rs.getString(5);
                Formateur fr = new Formateur(idform, mail, nom, prenom);
                lf.add(fr);

            }
            if (!trouver) {
                // System.out.println("aucun formateur trouvé");
                logger.error("Aucun formateur trouver");
                return null;
            }

        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        }


        return lf;

    }
*/

    public List<Formateur> FormateursCours(Cours cours) {
        List<Formateur> formateurs = new ArrayList<>();
        String query = "SELECT DISTINCT f.* FROM APIFormateur f " +
                "JOIN APIInfos i ON f.id_Formateur = i.id_Formateur " +
                "JOIN APISessionCours s ON i.id_SessionCours = s.id_SessionCours " +
                "WHERE s.id_Cours = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int idFormateur = rs.getInt("id_Formateur");//ne fonctionne pas quand j'utilise les index
                String mail = rs.getString("mail");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Formateur formateur = new Formateur(idFormateur, mail, nom, prenom);
                formateurs.add(formateur);
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL : " + e);
            return null;
        }

        return formateurs;
    }
    @Override
    public List<SessionCours> SessionsParLocal(Cours cours) {
        List<SessionCours> sc = new ArrayList<>();
        String query = "select * from SESSIONSETLOCAL where id_cours=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            ResultSet rs = pstm.executeQuery();
            boolean trouver = false;
            while (rs.next()) {
                trouver = true;
                int idsess = rs.getInt(2);
                LocalDate dateDebut = rs.getDate(3).toLocalDate();
                LocalDate dateFin = rs.getDate(4).toLocalDate();
                int nbreInscrits = rs.getInt(5);
                String sigle = rs.getString(6);
                int places = rs.getInt(7);
                String descriptions = rs.getString(8);
                Local local2 = new Local(0, sigle, places, descriptions);
                SessionCours sce = new SessionCours(idsess, dateDebut, dateFin, nbreInscrits, local2);
                sc.add(sce);


            }
            if (!trouver) {
                // System.out.println("aucun formateur trouvé");
                logger.error("Aucune session trouver");
                return null;
            }

        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        }


        return sc;

    }



    public List<SessionCours> SessionsEntreDate(Cours cours) {
        List<SessionCours> sce = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean trouver = false;
        String query = "select * from SESSIONSETLOCAL where id_cours=? and dateDebut between ? and ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            System.out.print("Date début : ");
            int j = sc.nextInt();
            int m = sc.nextInt();
            int a = sc.nextInt();
            LocalDate dd = LocalDate.of(a, m, j);
            pstm.setDate(2, java.sql.Date.valueOf(dd));
            System.out.print("Date fin : ");
            int j1 = sc.nextInt();
            int m1 = sc.nextInt();
            int a1 = sc.nextInt();
            LocalDate df = LocalDate.of(a1, m1, j1);
            pstm.setDate(3, java.sql.Date.valueOf(df));
            ResultSet rs = pstm.executeQuery();
            boolean trouve = false;
            while (rs.next()) {
                trouver = true;
                int idsess = rs.getInt(2);
                LocalDate dateDebut = rs.getDate(3).toLocalDate();
                LocalDate dateFin = rs.getDate(4).toLocalDate();
                int nbreInscrits = rs.getInt(5);
                String sigle = rs.getString(6);
                int places = rs.getInt(7);
                String descriptions = rs.getString(8);
                Local local2 = new Local(0, sigle, places, descriptions);
                SessionCours sces = new SessionCours(idsess, dateDebut, dateFin, nbreInscrits, local2);
                sce.add(sces);

            }
            if (!trouver) {
                // System.out.println("aucun formateur trouvé");
                logger.error("Aucune session trouver");
                return null;
            }

        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        }


        return sce;


    }


}
