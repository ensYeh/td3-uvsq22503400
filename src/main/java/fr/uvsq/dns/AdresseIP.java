package fr.uvsq.dns;

import java.util.Objects;

public class AdresseIP {
    private final String valeur;

    public AdresseIP(String valeur) {
        if (!valide(valeur)) {
            throw new IllegalArgumentException("Adresse IP invalide : " + valeur);
        }
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }

    private boolean valide(String ip) {
        String[] parties = ip.split("\\.");
        if (parties.length != 4) return false;
        try {
            for (String p : parties) {
                int n = Integer.parseInt(p);
                if (n < 0 || n > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdresseIP)) return false;
        AdresseIP that = (AdresseIP) o;
        return valeur.equals(that.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    @Override
    public String toString() {
        return valeur;
    }
}
