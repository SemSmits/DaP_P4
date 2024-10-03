package ovchip.dao;

import ovchip.domain.OVChipkaart;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
}
