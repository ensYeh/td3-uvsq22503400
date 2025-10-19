package fr.uvsq.dns;

import java.io.IOException;

public class DnsTUI {
    private final java.util.Scanner scanner = new java.util.Scanner(System.in);

    // Lit la prochaine ligne de commande
    public String nextLine() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    // Affiche un message
    public void affiche(String message) {
        System.out.println(message);
    }

    // La boucle principale du programme
    public void start() {
        Dns dns;
        try {
            dns = new Dns("src/main/resources/dns.txt");
        } catch (IOException e) {
            affiche("Erreur de chargement du fichier DNS : " + e.getMessage());
            return;
        }

        while (true) {
            String ligne = nextLine();
            if (ligne.equalsIgnoreCase("exit") || ligne.equalsIgnoreCase("quit")) {
                affiche("Au revoir !");
                break;
            }

            Commande cmd = new Commande(ligne);

            switch (cmd.getNom().toLowerCase()) {
                case "getip":
                    if (cmd.getArgs().length != 1) {
                        affiche("Usage : getip <nom_machine>");
                        break;
                    }
                    try {
                        NomMachine nom = new NomMachine(cmd.getArgs()[0]);
                        DnsItem item = dns.getItem(nom);
                        if (item == null)
                            affiche("Nom machine introuvable.");
                        else
                            affiche("Adresse IP : " + item.getAdresseIP());
                    } catch (IllegalArgumentException ex) {
                        affiche("Nom machine invalide.");
                    }
                    break;

                case "getnom":
                    if (cmd.getArgs().length != 1) {
                        affiche("Usage : getnom <adresse_ip>");
                        break;
                    }
                    try {
                        AdresseIP ip = new AdresseIP(cmd.getArgs()[0]);
                        DnsItem item = dns.getItem(ip);
                        if (item == null)
                            affiche("Adresse IP introuvable.");
                        else
                            affiche("Nom machine : " + item.getNomMachine());
                    } catch (IllegalArgumentException ex) {
                        affiche("Adresse IP invalide.");
                    }
                    break;

                case "add":
                    if (cmd.getArgs().length != 2) {
                        affiche("Usage : add <nom_machine> <adresse_ip>");
                        break;
                    }
                    try {
                        NomMachine nom = new NomMachine(cmd.getArgs()[0]);
                        AdresseIP ip = new AdresseIP(cmd.getArgs()[1]);
                        dns.addItem(ip, nom);
                        affiche("Ajout effectué.");
                    } catch (IllegalArgumentException ex) {
                        affiche("Nom machine ou adresse IP invalide.");
                    } catch (Exception ex) {
                        affiche("Erreur : " + ex.getMessage());
                    }
                    break;

                case "list":
                    if (cmd.getArgs().length != 1) {
                        affiche("Usage : list <domaine>");
                        break;
                    }
                    String domaine = cmd.getArgs()[0];
                    var items = dns.getItems(domaine);
                    if (items.isEmpty()) {
                        affiche("Aucun élément trouvé pour ce domaine.");
                    } else {
                        for (DnsItem di : items) {
                            affiche(di.toString());
                        }
                    }
                    break;

                default:
                    affiche("Commande inconnue : " + cmd.getNom());
            }
        }
    }

    // Méthode main pour lancer la TUI
    public static void main(String[] args) {
        new DnsTUI().start();
    }
}
