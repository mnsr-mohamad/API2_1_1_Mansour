package mvp.model;

import Classes.Cours;
import Classes.Formateur;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormateurModelDB implements DAOFormateur {
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
    public Formateur addFormateur(Formateur formateur) {
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
    public boolean removeFormateur(Formateur formateur) {
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
    public List<Formateur> getFormateur() {
        List<Formateur> lf = new ArrayList<>();
        String query = "select * from APIFORMATEUR";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_formateur = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                Formateur fr = new Formateur(id_formateur, mail, nom, prenom);
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
    public Formateur updateFormateur(Formateur formateur) {
        String query = "update APIFORMATEUR set mail =?,nom=?, prenom=? where id_formateur = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, formateur.getMail());
            pstm.setString(2, formateur.getNom());
            pstm.setString(3, formateur.getPrenom());
            pstm.setInt(4, formateur.getId_Formateur());
            int n = pstm.executeUpdate();
            if (n != 0) return readFormateur(formateur.getId_Formateur());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }

    @Override
    public Formateur readFormateur(int id_Formateur) {
        String query = "select * from APIFORMATEUR where id_formateur = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_Formateur);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                Formateur fr = new Formateur(id_Formateur, mail, nom, prenom);

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


}
