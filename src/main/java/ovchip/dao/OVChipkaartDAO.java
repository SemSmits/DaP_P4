package ovchip.dao;

import ovchip.domain.Adres;
import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
}

