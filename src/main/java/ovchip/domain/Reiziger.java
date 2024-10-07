package ovchip.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaart = new ArrayList<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaart() {
        return ovChipkaart;
    }

    public void addOVChipkaart(OVChipkaart ov) {
        ovChipkaart.add(ov);
    }

    @Override
    public String toString() {
        String volledigeNaam = tussenvoegsel != null && !tussenvoegsel.isEmpty()
                ? voorletters + " " + tussenvoegsel + " " + achternaam
                : voorletters + " " + achternaam;

        String adresString = (adres != null) ? ", " + adres.toString() : "";

        return "Reiziger {" +
                "#" + id +
                " " + volledigeNaam +
                ", geb. " + geboortedatum +
                adresString +
                '}';
    }


    public void setOvChipkaart(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaart = ovChipkaarten;
    }
}
