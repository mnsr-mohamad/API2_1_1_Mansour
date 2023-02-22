package exercicesJDBC;

import myconnections.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class exo1 {

    public static void main(String args[]) {

        Connection dbConnect1 = DBConnection.getConnection();
        if (dbConnect1 == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        Scanner sc = new Scanner(System.in);
        try (PreparedStatement pstm = dbConnect1.prepareStatement(
                "SELECT * FROM APIFORMATEUR WHERE NOM = ? AND PRENOM = ? ")) {
            System.out.print("Nom du formateur recherché :");
            String nomrech = sc.nextLine();
            System.out.print("Prénom du formateur recherché : ");
            String pnomrech = sc.nextLine();
            pstm.setString(1, nomrech);
            pstm.setString(2, pnomrech);
            boolean trouve = false;
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    trouve = true;
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String mail = rs.getString("MAIL");
                    System.out.println(nom + " " + prenom + " " + mail);
                }
                if (!trouve) {
                    System.out.println("Formateur inconnu");
                }
            }
        }
        catch (SQLException e) {
            System.out.println("erreur SQL =" + e);
        }

    }
}
