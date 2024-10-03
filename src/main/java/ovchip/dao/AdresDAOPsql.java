package ovchip.dao;

import ovchip.domain.Adres;
import ovchip.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {

    private Connection con;

    public AdresDAOPsql(Connection connection) {
        this.con = connection;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO Adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getReiziger().getId());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE Adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, adres.getPostcode());
            preparedStatement.setString(2, adres.getHuisnummer());
            preparedStatement.setString(3, adres.getStraat());
            preparedStatement.setString(4, adres.getWoonplaats());
            preparedStatement.setInt(5, adres.getReiziger().getId());
            preparedStatement.setInt(6, adres.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM Adres WHERE adres_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM Adres WHERE reiziger_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Adres(
                        resultSet.getInt("adres_id"),
                        resultSet.getString("postcode"),
                        resultSet.getString("huisnummer"),
                        resultSet.getString("straat"),
                        resultSet.getString("woonplaats")
                );
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<>();
        try{
            String query = "SELECT * FROM Adres";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Adres adres = new Adres(
                        resultSet.getInt("adres_id"),
                        resultSet.getString("postcode"),
                        resultSet.getString("huisnummer"),
                        resultSet.getString("straat"),
                        resultSet.getString("woonplaats")
                );
                adressen.add(adres);
            }
        } catch (SQLException e) {
            return null;
        }
        return adressen;
    }
}
