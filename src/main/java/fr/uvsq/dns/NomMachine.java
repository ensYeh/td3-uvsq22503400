package fr.uvsq.dns;

import java.util.Objects;

public class NomMachine {
    private final String nom;

    public NomMachine(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Extrait le domaine d'un nom de machine.
     * Par exemple, pour "serveur1.uvsq.fr" retourne "uvsq.fr".
     * Si pas de domaine (pas de '.'), retourne chaîne vide.
     */
    public String getDomaine() {
        int firstDot = nom.indexOf('.');
        if (firstDot == -1 || firstDot == nom.length() - 1) {
            return "";  // Pas de domaine
        }
        return nom.substring(firstDot + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomMachine)) return false;
        NomMachine that = (NomMachine) o;
        return nom.equalsIgnoreCase(that.nom); // ignore la casse si tu veux
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom.toLowerCase()); // ignore la casse si tu veux
    }

    @Override
    public String toString() {
        return nom;
    }
}
