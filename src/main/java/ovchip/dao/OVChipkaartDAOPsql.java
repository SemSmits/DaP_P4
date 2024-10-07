package ovchip.dao;

import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaartNummer());
            ps.setDate(2, ovChipkaart.getGeldigTot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ? WHERE kaart_nummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, ovChipkaart.getGeldigTot());
            ps.setInt(2, ovChipkaart.getKlasse());
            ps.setDouble(3, ovChipkaart.getSaldo());
            ps.setInt(4, ovChipkaart.getKaartNummer());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ovChipkaart.getKaartNummer());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reiziger.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaartNummer(rs.getInt("kaart_nummer"));
                ovChipkaart.setGeldigTot(rs.getDate("geldig_tot"));
                ovChipkaart.setKlasse(rs.getInt("klasse"));
                ovChipkaart.setSaldo(rs.getDouble("saldo"));
                ovChipkaart.setReiziger(reiziger);

                ovChipkaarten.add(ovChipkaart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ovChipkaarten;
    }


}





