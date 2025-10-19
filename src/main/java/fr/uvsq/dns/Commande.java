package fr.uvsq.dns;

public class Commande {
    private final String nom;       // le type de la commande (ex: "add", "getip", "ls")
    private final String[] args;    // les arguments (ex: ["uvsq.fr"] ou ["-a", "uvsq.fr"])

    public Commande(String ligne) {
        // Supprime les espaces en trop et découpe correctement
        String[] parties = ligne.trim().split("\\s+");
        if (parties.length == 0 || parties[0].isEmpty()) {
            this.nom = "";
            this.args = new String[0];
        } else {
            this.nom = parties[0].toLowerCase(); // ✅ tout en minuscule
            this.args = new String[parties.length - 1];
            System.arraycopy(parties, 1, this.args, 0, parties.length - 1);
        }
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
