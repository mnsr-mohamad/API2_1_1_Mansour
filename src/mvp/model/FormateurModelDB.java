package mvp.model;

import Classes.Cours;
import Classes.Formateur;
import Classes.SessionCours;
import myconnections.DBConnection;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FormateurModelDB implements DAO<Formateur>, FormateurSpecial {
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(FormateurModelDB.class);

    public FormateurModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }

    @Override
    public Formateur add(Formateur formateur) {
        String query1 = "insert into APIFORMATEUR (mail,nom,prenom) values(?,?,?)";
        String query2 = "select id_formateur from APIFORMATEUR where mail= ? and  nom=? and prenom=? ";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, formateur.getMail());
            pstm1.setString(2, formateur.getNom());
            pstm1.setString(3, formateur.getPrenom());

            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, formateur.getMail());
                pstm2.setString(2, formateur.getNom());
                pstm2.setString(3, formateur.getPrenom());

                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_formateur = rs.getInt(1);
                    formateur.setId_Formateur(id_formateur);
                    return formateur;
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
    public boolean remove(Formateur formateur) {
        String query = "delete from APIFORMATEUR where id_formateur = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, formateur.getId_Formateur());
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
    public List<Formateur> getAll() {
        List<Formateur> lf = new ArrayList<>();
        String query = "select * from APIFORMATEUR";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_formateur = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                Formateur fr = null;
                try {
                    fr = new Formateur(id_formateur, mail, nom, prenom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lf.add(fr);
            }
            return lf;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return null;
        }
    }

    @Override
    public Formateur update(Formateur formateur) {
        String query = "update APIFORMATEUR set mail =?,nom=?, prenom=? where id_formateur = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, formateur.getMail());
            pstm.setString(2, formateur.getNom());
            pstm.setString(3, formateur.getPrenom());
            pstm.setInt(4, formateur.getId_Formateur());
            int n = pstm.executeUpdate();
            if (n != 0) return read(formateur.getId_Formateur());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }

    @Override
    public Formateur read(int id_Formateur) {
        String query = "select * from APIFORMATEUR where id_formateur = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_Formateur);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                Formateur fr = null;
                try {
                    fr = new Formateur(id_Formateur, mail, nom, prenom);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return fr;

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
    public List<Formateur> form_encode() {
        List<Formateur> formateurs = new ArrayList<>();

        try (
             CallableStatement cs = dbConnect.prepareCall("{ ? = call form_encode }")) {
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.executeQuery();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    int id_formateur = rs.getInt(1);
                    String mail = rs.getString(2);
                    String nom = rs.getString(3);
                    String prenom = rs.getString(4);

                    try {
                        Formateur formateur = new Formateur(id_formateur, mail, nom, prenom);
                        formateurs.add(formateur);
                    } catch (Exception e) {
                        System.out.println(" "+e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());

        }

        return formateurs;


    }

    @Override
    public List<Formateur> gest_Formateur_dispo(SessionCours sess) {
        List<Formateur> lf = new ArrayList<>();
        String query = "select * FROM APIFORMATEUR where id_formateur NOT IN (SELECT F.id_formateur FROM APIINFOS I INNER JOIN APISESSIONCOURS SC on I.id_sessioncours = SC.id_sessioncours INNER JOIN APIFORMATEUR F on I.id_formateur = F.id_formateur WHERE I.id_sessioncours = ?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, sess.getId_SessionCours());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id_formateur = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);

                Formateur f = null;
                try {
                    f = new Formateur(id_formateur, mail, nom, prenom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lf.add(f);
            }
            return lf;
        } catch (SQLException e) {
            //System.out.println("Erreur sql : "+e);
            logger.error("Erreur SQL : " + e);
            return null;
        }
    }


}
