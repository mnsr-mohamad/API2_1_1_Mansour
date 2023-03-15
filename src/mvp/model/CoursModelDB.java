package mvp.model;

import Classes.Cours;
import myconnections.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursModelDB implements  DAOCours{
    private Connection dbConnect;

    public CoursModelDB(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.err.println("erreur de connexion");
            System.exit(1);
        }
    }

    @Override
    public Cours addCours(Cours cours) {
        String query1 = "insert into APICOURS (matière,heures) values(?,?)";
        String query2 = "select id_cours from APICOURS where matière= ? and  heures=? ";
        try(PreparedStatement pstm1= dbConnect.prepareStatement(query1);
            PreparedStatement pstm2= dbConnect.prepareStatement(query2);
        ){
            pstm1.setString(1,cours.getMatiere());
            pstm1.setInt(2,cours.getHeures());

            int n = pstm1.executeUpdate();
            if(n==1){
                pstm2.setString(1,cours.getMatiere());
                pstm2.setInt(2,cours.getHeures());

                ResultSet rs= pstm2.executeQuery();
                if(rs.next()){
                    int id_cours= rs.getInt(1);
                    cours.setId_Cours(id_cours);
                    return cours;
                }
                else {
                    System.err.println("record introuvable");
                    return null;
                }
            }
            else return null;

        } catch (SQLException e) {
            System.err.println("erreur sql :"+e);
            return null;
        }
    }

    @Override
    public boolean removeCours(Cours cours) {
        String query = "delete from APICOURS where id_cours = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,cours.getId_Cours());
            int n = pstm.executeUpdate();
            if(n!=0) return true;
            else return false;

        } catch (SQLException e) {
            System.err.println("erreur sql :"+e);
            return false;
        }
    }

    @Override
    public List<Cours> getCours() {
        List<Cours> lc = new ArrayList<>();
        String query="select * from APICOURS";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int id_cours = rs.getInt(1);
                String matiere = rs.getString(2);
                int heures = rs.getInt(3);
                Cours cr = new Cours(id_cours,matiere,heures);
                lc.add(cr);
            }
            return lc;
        } catch (SQLException e) {
            System.err.println("erreur sql :"+e);
            return null;
        }
    }

}
