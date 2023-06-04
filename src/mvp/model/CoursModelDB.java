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

public class CoursModelDB implements DAO<Cours>,CoursSpecial {
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
    public Cours add(Cours cours) {
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
    public boolean remove(Cours cours) {
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
    public List<Cours> getAll() {
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

        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lc;
    }


    @Override
    public Cours update(Cours cours) {
        String query = "update APICOURS set matière =?,heures=? where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, cours.getMatiere());
            pstm.setInt(2, cours.getHeures());
            pstm.setInt(3, cours.getId_Cours());
            int n = pstm.executeUpdate();
            if (n != 0) return read(cours.getId_Cours());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }


    @Override
    public Cours read(int id_cours) {
        String query = "select * from APICOURS where id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_cours);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String matiere = rs.getString(2);
                int heures = rs.getInt(3);
                Cours cr = null;
                try {
                    cr = new Cours(id_cours, matiere, heures);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @Override
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
                Formateur formateur = null;
                try {
                    formateur = new Formateur(idFormateur, mail, nom, prenom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        List<SessionCours> sessions = new ArrayList<>();
        String query = "SELECT DISTINCT s.*, l.sigle, l.places, l.descriptions " +
                "FROM APISessionCours s " +
                "JOIN APILocal l ON s.id_Local = l.id_Local " +
                "LEFT JOIN APIInfos i ON s.id_SessionCours = i.id_SessionCours " +
                "WHERE s.id_Cours = ? ";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int idSessionCours = rs.getInt("id_SessionCours");
                LocalDate dateDebut = rs.getDate("dateDebut").toLocalDate();
                LocalDate dateFin = rs.getDate("dateFin").toLocalDate();
                int nbreInscrits = rs.getInt("nbreInscrits");
                int idLocal = rs.getInt("id_Local");
                String sigle = rs.getString("sigle");
                int places = rs.getInt("places");
                String description = rs.getString("descriptions");
                Local local = null;
                try {
                    local = new Local(idLocal, sigle, places, description);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SessionCours session = null;
                try {
                    session = new SessionCours(idSessionCours, dateDebut, dateFin, nbreInscrits, cours, local);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sessions.add(session);
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL : " + e);
            return null;
        }

        return sessions;
    }



    @Override
    public List<SessionCours> SessionsEntreDate(Cours cours) {
        List<SessionCours> sessions = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez la date de début (aaaa-mm-jj) : ");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine());

        System.out.print("Entrez la date de fin (aaaa-mm-jj) : ");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine());

        String query = "SELECT s.*, l.sigle, l.places, l.descriptions " +
                "FROM APISessionCours s " +
                "JOIN APILocal l ON s.id_Local = l.id_Local " +
                "LEFT JOIN APIInfos i ON s.id_SessionCours = i.id_SessionCours " +
                "WHERE s.id_Cours = ? AND s.dateDebut >= ? AND s.dateDebut <= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, cours.getId_Cours());
            pstm.setDate(2, Date.valueOf(dateDebut));
            pstm.setDate(3, Date.valueOf(dateFin));
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int idSessionCours = rs.getInt("id_SessionCours");
                LocalDate sessionDateDebut = rs.getDate("dateDebut").toLocalDate();
                LocalDate sessionDateFin = rs.getDate("dateFin").toLocalDate();
                int nbreInscrits = rs.getInt("nbreInscrits");
                int idLocal = rs.getInt("id_Local");
                String sigle = rs.getString("sigle");
                int places = rs.getInt("places");
                String description = rs.getString("descriptions");
                Local local = null;
                try {
                    local = new Local(idLocal, sigle, places, description);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SessionCours session = null;
                try {
                    session = new SessionCours(idSessionCours, sessionDateDebut, sessionDateFin, nbreInscrits, cours, local);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sessions.add(session);
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL : " + e);
            return null;
        }

        return sessions;
    }


}
