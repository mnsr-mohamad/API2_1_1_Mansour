package mvp.model;

import Classes.*;
import myconnections.DBConnection;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessionCoursModelDB implements DAO<SessionCours>,SessionCoursSpecial{

    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(SessionCoursModelDB.class);


    public SessionCoursModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }


    @Override
    public SessionCours add(SessionCours sessionCours) {
        String query1 = "INSERT INTO APISESSIONCOURS (dateDebut, dateFin, nbreInscrits, id_Local, id_Cours) VALUES (?, ?, ?, ?, ?)";
        String query2 = "SELECT max(id_SessionCours) FROM APISESSIONCOURS WHERE id_Local = ? AND id_Cours = ?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setDate(1, Date.valueOf(sessionCours.getDateDebut()));
            pstm1.setDate(2, Date.valueOf(sessionCours.getDateFin()));
            pstm1.setInt(3, sessionCours.getNbreInscrits());
            pstm1.setInt(4, sessionCours.getLocal().getId_Local());
            pstm1.setInt(5, sessionCours.getCours().getId_Cours());

            int n = pstm1.executeUpdate();
            if (n == 1) {

                pstm2.setInt(1, sessionCours.getLocal().getId_Local());
                pstm2.setInt(2, sessionCours.getCours().getId_Cours());

                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_SessionCours = rs.getInt(1);
                    sessionCours.setId_SessionCours(id_SessionCours);
                    return sessionCours;
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
    public boolean remove(SessionCours sessionCours) {
        String query = "delete from APISESSIONCOURS where id_sessioncours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, sessionCours.getId_SessionCours());
            int n = pstm.executeUpdate();
            if (n != 0) return true;
            else return false;

        } catch (SQLException e) {
            logger.error("erreur sql :" + e);
            return false;
        }
    }

    @Override
    public List<SessionCours> getAll() {
        List<SessionCours> lsc = new ArrayList<>();
        String query = "select * from APISESSIONCOURS";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_sessioncours = rs.getInt(1);
                LocalDate dateDebut = rs.getDate(2).toLocalDate();
                LocalDate dateFin = rs.getDate(3).toLocalDate();
                int nbreInscrits = rs.getInt(4);
                int id_local = rs.getInt(5);
                int id_cours = rs.getInt(6);
                Local local = getLocalById(id_local);
                Cours cours = getCoursById(id_cours);
                SessionCours sc = null;
                try {
                    sc = new SessionCours(id_sessioncours, dateDebut, dateFin, nbreInscrits, cours, local);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lsc.add(sc);
            }
            return lsc;
        } catch (SQLException e) {
            logger.error("erreur sql :" + e);
            return null;
        }
    }

    private Local getLocalById(int id) {
        String query = "SELECT * FROM APILOCAL WHERE id_local = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int id_local = rs.getInt("id_local");
                String sigle = rs.getString("sigle");
                int places = rs.getInt("places");
                String description = rs.getString("descriptions");
                Local local = null;
                try {
                    local = new Local(id_local,sigle, places, description);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return local;
            } else {
                logger.error("Le local avec l'id " + id + " n'existe pas.");
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération du local avec l'id " + id + " : " + e.getMessage());
            return null;
        }
    }


    public Cours getCoursById(int id) {
        String query = "SELECT * FROM APICOURS WHERE id_cours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int id_cours = rs.getInt("id_cours");
                String matiere = rs.getString("matière");
                int heures = rs.getInt("heures");
                Cours cours = null;
                try {
                    cours = new Cours(id_cours, matiere, heures);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return cours;
            } else {
                logger.error("Le cours avec l'id " + id + " n'existe pas.");
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération du cours avec l'id " + id + " : " + e.getMessage());
            return null;
        }
    }
    @Override
    public SessionCours update(SessionCours sessionCours) {
        String query = "update APISESSIONCOURS set dateDebut=?, dateFin=?, nbreinscrits = ? where id_sessioncours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setDate(1, Date.valueOf(sessionCours.getDateDebut()));
            pstm.setDate(2, Date.valueOf(sessionCours.getDateFin()));
            pstm.setInt(3, sessionCours.getNbreInscrits());
            pstm.setInt(4, sessionCours.getId_SessionCours());
            int n = pstm.executeUpdate();
            if (n != 0) return read(sessionCours.getId_SessionCours());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }

    @Override
    public SessionCours read(int id_sessioncours) {
        String query = "SELECT * FROM APISESSIONCOURS WHERE id_sessioncours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_sessioncours);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                LocalDate dateDebut = rs.getDate("dateDebut").toLocalDate();
                LocalDate dateFin = rs.getDate("dateFin").toLocalDate();
                int nbreInscrits = rs.getInt("nbreInscrits");
                int idLocal = rs.getInt("id_local");
                int idCours = rs.getInt("id_cours");

                Local local = getLocalById(idLocal);
                Cours cours = getCoursById(idCours);

                SessionCours sc = null;
                try {
                    sc = new SessionCours(id_sessioncours, dateDebut, dateFin, nbreInscrits, cours, local);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return sc;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL : " + e);
            return null;
        }
    }

    @Override
    public boolean add_infos(SessionCours sess, Formateur form,int nh) {
        String query = "INSERT INTO APIINFOS (id_formateur,id_sessioncours,nh) VALUES (?,?,?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, form.getId_Formateur());
            pstm.setInt(2, sess.getId_SessionCours());
            pstm.setInt(3, nh);
            int n = pstm.executeUpdate();
            if (n != 0) return true;
            else return false;
        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur SQL : " + e);
            return false;
        }
    }

    @Override
    public boolean remove_infos(SessionCours sess) {
        String query = "DELETE FROM APIINFOS WHERE id_sessioncours=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, sess.getId_SessionCours());
            int n = pstm.executeUpdate();
            if (n != 0) return true;
            else return false;

        } catch (SQLException e) {
            logger.error("erreur sql :" + e);
            return false;
        }
    }



    @Override
    public int getTotalHeuresFormateurs(SessionCours sess) {
        int totalHeuresFormateurs = 0;
        String query = "SELECT SUM(nh) FROM APIINFOS WHERE id_sessioncours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, sess.getId_SessionCours());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                totalHeuresFormateurs = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL : " + e);
        }
        return totalHeuresFormateurs;
    }

    @Override
    public void supp_infos(SessionCours sess) {
        String query = "DELETE FROM APIINFOS WHERE id_sessioncours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, sess.getId_SessionCours());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Suppression des données réussie pour la session de cours : " + sess.getId_SessionCours());
            } else {
                logger.info("Aucune donnée à supprimer pour la session de cours : " + sess.getId_SessionCours());
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de la suppression des données : " + e);
        }
    }

    @Override
    public void supp_sess(SessionCours sess) {
        String query = "DELETE FROM APISESSIONCOURS  WHERE id_sessioncours = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, sess.getId_SessionCours());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Suppression des données réussie pour la session de cours : " + sess.getId_SessionCours());
            } else {
                logger.info("Aucune donnée à supprimer pour la session de cours : " + sess.getId_SessionCours());
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de la suppression des données : " + e);
        }


    }

    @Override
    public int form_heures(SessionCours sess) {
        try (CallableStatement cs = dbConnect.prepareCall("{ ? = call form_heures(?) }")) {
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setInt(2, sess.getId_SessionCours());
            cs.execute();

            int total_heures = cs.getInt(1);
            return total_heures;
        } catch (SQLException e) {
            System.out.println("erreur SQL = " + e);
            return 0;
        } catch (Exception e) {
            System.out.println("Exception " + e);
            return 0;
        }
    }

    @Override
    public boolean verif_heures(SessionCours sess, int heures) {
        boolean heuresSuffisantes = false;

        try (
             CallableStatement cs = dbConnect.prepareCall("{ ? = call verif_heures(?, ?) }")) {
            cs.registerOutParameter(1, OracleTypes.PLSQL_BOOLEAN);
            cs.setInt(2, sess.getId_SessionCours());
            cs.setInt(3, heures);
            cs.executeQuery();

            heuresSuffisantes = cs.getBoolean(1);
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
        }

        return heuresSuffisantes;


    }


}
