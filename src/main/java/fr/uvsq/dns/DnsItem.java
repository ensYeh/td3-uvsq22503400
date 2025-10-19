package fr.uvsq.dns;

public class DnsItem {
    private final AdresseIP adresseIP;
    private final NomMachine nomMachine;

    public DnsItem(AdresseIP adresseIP, NomMachine nomMachine) {
        if (adresseIP == null || nomMachine == null) {
            throw new IllegalArgumentException("Adresse IP et NomMachine ne peuvent pas être null");
        }
        this.adresseIP = adresseIP;
        this.nomMachine = nomMachine;
    }

    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    public NomMachine getNomMachine() {
        return nomMachine;
    }

    @Override
    public String toString() {
        return adresseIP.toString() + " " + nomMachine.toString();
    }
}
