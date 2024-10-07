package ovchip;

import ovchip.dao.*;
import ovchip.domain.Adres;
import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;

        // Start verbinding met de database
        conn = startConnection();

        // Test de verbinding door een query uit te voeren
        // testConnection(conn);

        // Maak een AdresDAO aan en test de CRUD-operaties
        AdresDAO adresDAO = new AdresDAOPsql(conn);
        ReizigerDAO reizigerDAO = new ReizigerDAOPsql(conn, adresDAO);
        OVChipkaartDAOPsql ovChipkaartDAO = new OVChipkaartDAOPsql(conn);
        testOVChipkaartDAO(reizigerDAO, ovChipkaartDAO);

        // Sluit de verbinding met de database
        closeConnection(conn);

    }


    // Maakt verbinding met de database
    private static Connection startConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Spidermanenbatman2023";
        return DriverManager.getConnection(url);
    }

    // Sluit de connectie met de database
    private static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }


    // Test de connectie met de database door middel van een query
    private static void testConnection(Connection con) throws SQLException {
        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        System.out.println("Alle reizigers:");
        while (set.next()) {
            int reizigerId = set.getInt("reiziger_id");
            String voorletters = set.getString("voorletters");
            String tussenvoegsel = set.getString("tussenvoegsel");
            String achternaam = set.getString("achternaam");
            String geboortedatum = set.getString("geboortedatum");

            String volledigeNaam;
            if (tussenvoegsel != null) {
                volledigeNaam = voorletters + ". " + tussenvoegsel + " " + achternaam;
            } else {
                volledigeNaam = voorletters + ". " + achternaam;
            }

            System.out.printf("#%d: %s (%s)\n", reizigerId, volledigeNaam, geboortedatum);
        }
    }


    public static void testAdresDAO(ReizigerDAO reizigerDAO, AdresDAO adresDAO) throws SQLException {
        try {

            Class.forName("org.postgresql.Driver");

            Reiziger reiziger1 = new Reiziger(1, "G", "van", "Rijn", new Date(102, 8, 17));
            Adres adres1 = new Adres(1, "3511 LX", "37", "Oudegracht", "Utrecht");
            reiziger1.setAdres(adres1);
            adres1.setReiziger(reiziger1);
            adresDAO.save(adres1);
            reizigerDAO.save(reiziger1);

            Reiziger reiziger2 = new Reiziger(2, "B", "van", "Rijn", new Date(102, 9, 22));
            Adres adres2 = new Adres(2, "3521 AL", "6A", "Vismarkt", "Utrecht");
            reiziger2.setAdres(adres2);
            adres2.setReiziger(reiziger2);
            adresDAO.save(adres2);
            reizigerDAO.save(reiziger2);

            Reiziger reiziger3 = new Reiziger(3, "H", "", "Lubben", new Date(98, 7, 11));
            Adres adres3 = new Adres(3, "6707 AA", "375", "Hoofdstraat", "Wageningen");
            reiziger3.setAdres(adres3);
            adres3.setReiziger(reiziger3);
            adresDAO.save(adres3);
            reizigerDAO.save(reiziger3);

            Reiziger reiziger4 = new Reiziger(4, "F", "", "Memari", new Date(102, 11, 3));
            Adres adres4 = new Adres(4, "3817 CH", "4", "Koningstraat", "Amersfoort");
            reiziger4.setAdres(adres4);
            adres4.setReiziger(reiziger4);
            adresDAO.save(adres4);
            reizigerDAO.save(reiziger4);

            Reiziger reiziger5 = new Reiziger(5, "G", "", "Piccardo", new Date(102, 11, 3));
            Adres adres5 = new Adres(5, "3572 WP", "22", "Maliebaan", "Utrecht");
            reiziger5.setAdres(adres5);
            adres5.setReiziger(reiziger5);
            adresDAO.save(adres5);
            reizigerDAO.save(reiziger5);

            List<Reiziger> allReizigers = reizigerDAO.findAll();
            for (Reiziger reiziger : allReizigers) {
                System.out.println(reiziger);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testOVChipkaartDAO(ReizigerDAO reizigerDAO, OVChipkaartDAO ovChipkaartDAO) throws SQLException {

        Reiziger reiziger = new Reiziger(6000, "Piet", "", "Bakker", java.sql.Date.valueOf("1995-05-15"));
        reizigerDAO.save(reiziger);

        OVChipkaart ov = new OVChipkaart(54321, java.sql.Date.valueOf("2027-01-01"), 1, 30.00, reiziger);
        ov.setReiziger(reiziger);
        ovChipkaartDAO.save(ov);
        reiziger.addOVChipkaart(ov);


        List<OVChipkaart> ovChipkaarten = ovChipkaartDAO.findByReiziger(reiziger);
        System.out.println("OVChipkaarten voor Reiziger " + reiziger.getId() + ": " + ovChipkaarten);


        ov.setSaldo(60.00);
        ovChipkaartDAO.update(ov);
        List<OVChipkaart> ovChipkaartenAfterUpdating = ovChipkaartDAO.findByReiziger(reiziger);
        System.out.println("OVChipkaarten na updaten voor Reiziger " + reiziger.getId() + ": " + ovChipkaartenAfterUpdating);

        ovChipkaartDAO.delete(ov);
        List<OVChipkaart> ovChipkaartenAfterDeletion = ovChipkaartDAO.findByReiziger(reiziger);
        System.out.println("OVChipkaarten na verwijderen voor Reiziger " + reiziger.getId() + ": " + ovChipkaartenAfterDeletion);


        reizigerDAO.delete(reiziger);
    }

}

