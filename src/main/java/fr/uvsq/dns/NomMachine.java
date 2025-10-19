package fr.uvsq.dns;

public class NomMachine {
    private final String nomQualifie;

    public NomMachine(String nomQualifie) {
        if (nomQualifie == null || !nomQualifie.contains(".")) {
            throw new IllegalArgumentException("Nom qualifié invalide");
        }
        this.nomQualifie = nomQualifie;
    }

    public String getNom() {
        return nomQualifie.substring(0, nomQualifie.indexOf('.'));
    }

    public String getDomaine() {
        return nomQualifie.substring(nomQualifie.indexOf('.') + 1);
    }

    @Override
    public String toString() {
        return nomQualifie;
    }
}
