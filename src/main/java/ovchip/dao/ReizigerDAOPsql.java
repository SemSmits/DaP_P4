package ovchip.dao;

import ovchip.domain.Adres;
import ovchip.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection con;
    private AdresDAO adresDAO;

    public ReizigerDAOPsql(Connection connection, AdresDAO adresDAO) {
        this.con = connection;
        this.adresDAO = adresDAO;
    }


    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try{
            String query = "INSERT INTO Reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, (java.sql.Date) reiziger.getGeboortedatum());
            preparedStatement.executeUpdate();

            if (reiziger.getAdres() != null) {
                adresDAO.save(reiziger.getAdres());
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            String query = "UPDATE Reiziger SET voorletter = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, (java.sql.Date) reiziger.getGeboortedatum());
            preparedStatement.setInt(5, reiziger.getId());
            preparedStatement.executeUpdate();

            if (reiziger.getAdres() != null) {
                adresDAO.save(reiziger.getAdres());
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            String query = "DELETE FROM Reiziger WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Reiziger reiziger = new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")
                );

                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);

                return reiziger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(Date geboortedatum) {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDate(1, new java.sql.Date(geboortedatum.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reiziger reiziger = new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")
                );

                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);

                reizigers.add(reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "SELECT * FROM reiziger";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reiziger reiziger = new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")
                );

                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);

                reizigers.add(reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reizigers;
    }
}
