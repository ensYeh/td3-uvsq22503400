package fr.uvsq.dns;

import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class DnsTUI {
    private final Scanner scanner = new Scanner(System.in);

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
            // 🔹 Lecture du fichier de configuration
            Config config = new Config("config.properties");
            String cheminFichierDns = config.get("dns.file");
            dns = new Dns(cheminFichierDns);

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
            String nom = cmd.getNom().toLowerCase();
            String[] args = cmd.getArgs();

            switch (nom) {
                case "getip":
                    if (args.length != 1) {
                        affiche("Usage : getip <nom_machine>");
                        break;
                    }
                    try {
                        NomMachine nomMachine = new NomMachine(args[0]);
                        DnsItem item = dns.getItem(nomMachine);
                        if (item == null)
                            affiche("Nom machine introuvable.");
                        else
                            affiche("Adresse IP : " + item.getAdresseIP());
                    } catch (IllegalArgumentException ex) {
                        affiche("Nom machine invalide.");
                    }
                    break;

                case "getnom":
                    if (args.length != 1) {
                        affiche("Usage : getnom <adresse_ip>");
                        break;
                    }
                    try {
                        AdresseIP ip = new AdresseIP(args[0]);
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
                    if (args.length != 2) {
                        affiche("Usage : add <nom_machine> <adresse_ip>");
                        break;
                    }
                    try {
                        NomMachine nomMachine = new NomMachine(args[0]);
                        AdresseIP ip = new AdresseIP(args[1]);
                        dns.addItem(ip, nomMachine);
                        affiche("Ajout effectué.");
                    } catch (IllegalArgumentException ex) {
                        affiche("Nom machine ou adresse IP invalide.");
                    } catch (Exception ex) {
                        affiche("Erreur : " + ex.getMessage());
                    }
                    break;

                case "ls":
                    boolean triParAdresse = false;
                    String domaine;

                    if (args.length == 1) {
                        domaine = args[0];
                    } else if (args.length == 2 && args[0].equals("-a")) {
                        triParAdresse = true;
                        domaine = args[1];
                    } else {
                        affiche("Usage : ls [-a] <domaine>");
                        break;
                    }

                    var items = dns.getItems(domaine);
                    if (items.isEmpty()) {
                        affiche("Aucun élément trouvé pour ce domaine.");
                    } else {
                        if (triParAdresse) {
                            items.sort(Comparator.comparing(d -> d.getAdresseIP().toString()));
                        } else {
                            items.sort(Comparator.comparing(d -> d.getNomMachine().getNom()));
                        }
                        for (DnsItem di : items) {
                            affiche(di.getAdresseIP() + " " + di.getNomMachine());
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
