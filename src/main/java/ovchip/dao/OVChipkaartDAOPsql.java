package ovchip.dao;

import ovchip.domain.OVChipkaart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection con;

    public OVChipkaartDAOPsql(Connection connection) {
        this.con = connection;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        try {
            String query = "INSERT INTO ovchipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        return false;
    }
}
