package mvp.model;

import Classes.Formateur;
import Classes.Local;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalModelDB implements DAOLocal{

    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(LocalModelDB.class);

    public LocalModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }

    @Override
    public Local addLocal(Local local) {
        String query1 = "insert into APILOCAL (sigle,places,descriptions) values(?,?,?)";
        String query2 = "select id_local from APILOCAL where sigle= ? and  places=? and descriptions=? ";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, local.getSigle());
            pstm1.setInt(2, local.getPlaces());
            pstm1.setString(3, local.getDescription());

            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, local.getSigle());
                pstm2.setInt(2, local.getPlaces());
                pstm2.setString(3, local.getDescription());

                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_local = rs.getInt(1);
                    local.setId_Local(id_local);
                    return local;
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
    public boolean removeLocal(Local local) {
        String query = "delete from APILOCAL where id_local = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, local.getId_Local());
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
    public List<Local> getLocal() {
        List<Local> ll = new ArrayList<>();
        String query = "select * from APILOCAL";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_local = rs.getInt(1);
                String sigle = rs.getString(2);
                int places = rs.getInt(3);
                String descriptions = rs.getString(4);
                Local lc = new Local(id_local, sigle, places, descriptions);
                ll.add(lc);
            }
            return ll;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return null;
        }
    }

    @Override
    public Local updateLocal(Local local) {
        String query = "update APILOCAL set  sigle=?, places=?, descriptions=? where id_local = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, local.getSigle());
            pstm.setInt(2, local.getPlaces());
            pstm.setString(3, local.getDescription());
            pstm.setInt(4, local.getId_Local());
            int n = pstm.executeUpdate();
            if (n != 0) return readLocal(local.getId_Local());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }

    @Override
    public Local readLocal(int id_Local) {
        String query = "select * from APILOCAL where id_local = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_Local);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String sigle = rs.getString(2);
                int places = rs.getInt(3);
                String descriptions = rs.getString(4);
                Local lc = new Local(id_Local, sigle, places, descriptions);

                return lc;

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
