package fr.uvsq.dns;

public class Commande {
    private final String nom;       // le type de la commande (ex: "add", "getip")
    private final String[] args;    // les arguments (ex: ["serveur3.uvsq.fr", "192.168.0.3"])

    public Commande(String ligne) {
        String[] parties = ligne.trim().split("\\s+");
        this.nom = parties[0];
        this.args = new String[parties.length - 1];
        System.arraycopy(parties, 1, this.args, 0, parties.length - 1);
    }

    public String getNom() {
        return nom;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nom);
        for (String arg : args) {
            sb.append(" ").append(arg);
        }
        return sb.toString();
    }
}

