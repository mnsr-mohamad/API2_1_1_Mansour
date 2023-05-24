package mvp.model;

import Classes.Local;
import myconnections.DBConnection;
import oracle.jdbc.internal.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocalModelDB implements DAO<Local>, LocalSpecial {

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
    public Local add(Local local) {
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
    public boolean remove(Local local) {
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
    public List<Local> getAll() {
        List<Local> ll = new ArrayList<>();
        String query = "select * from APILOCAL";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_local = rs.getInt(1);
                String sigle = rs.getString(2);
                int places = rs.getInt(3);
                String descriptions = rs.getString(4);
                Local lc = null;
                try {
                    lc = new Local(id_local, sigle, places, descriptions);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    public Local update(Local local) {
        String query = "update APILOCAL set  sigle=?, places=?, descriptions=? where id_local = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, local.getSigle());
            pstm.setInt(2, local.getPlaces());
            pstm.setString(3, local.getDescription());
            pstm.setInt(4, local.getId_Local());
            int n = pstm.executeUpdate();
            if (n != 0) return read(local.getId_Local());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }

    @Override
    public Local read(int id_Local) {
        String query = "select * from APILOCAL where id_local = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_Local);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String sigle = rs.getString(2);
                int places = rs.getInt(3);
                String descriptions = rs.getString(4);
                Local lc = null;
                try {
                    lc = new Local(id_Local, sigle, places, descriptions);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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


    @Override
    public int insert_local() {
        int generatedId = 0;

        try (CallableStatement cs = dbConnect.prepareCall("{ call insert_local(?, ?, ?, ?) }")) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Sigle: ");
            String sigle = sc.nextLine();

            System.out.print("Places: ");
            int places = sc.nextInt();

            sc.nextLine();

            System.out.print("Description: ");
            String description = sc.nextLine();

            cs.setString(1, sigle);
            cs.setInt(2, places);
            cs.setString(3, description);
            cs.registerOutParameter(4, OracleTypes.NUMBER);
            cs.executeUpdate();

            generatedId = cs.getInt(4);
            return generatedId;
        } catch (SQLException e) {
            System.out.println("erreur SQL = " + e);
            return 0;
        } catch (Exception e) {
            System.out.println("Exception " + e);
            return 0;
        }


    }

    @Override
    public boolean verif_local(Local lo) {
        try (
                CallableStatement cs = dbConnect.prepareCall("{ ? = call verif_local(?, ?, ?) }")) {
            cs.registerOutParameter(1, OracleTypes.PLSQL_BOOLEAN);
            cs.setInt(2, lo.getId_Local());


            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez la date de début (DD-MM-YYYY) : ");
            String v_date_debut = scanner.nextLine();
            System.out.print("Entrez la date de fin (DD-MM-YYYY) : ");
            String v_date_fin = scanner.nextLine();

            cs.setString(3, v_date_debut);
            cs.setString(4, v_date_fin);
            cs.execute();

            boolean result = cs.getBoolean(1);

            return result;
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
            return false;
        }
    }

}
