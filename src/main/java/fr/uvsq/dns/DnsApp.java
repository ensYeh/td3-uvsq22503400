package fr.uvsq.dns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DnsApp {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage : java fr.uvsq.dns.DnsApp <fichier_base_dns>");
            System.exit(1);
        }

        try {
            Dns dns = new Dns(args[0]);
            DnsTUI ui = new DnsTUI();  // Interface utilisateur pour afficher les résultats
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Entrez vos commandes (type 'exit' pour quitter):");

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Fermeture de l'application.");
                    break;
                }

                Commande commande = new Commande(ligne);
                String nomCommande = commande.getNom();

                switch (nomCommande) {
                    case "ls":
                        // Exemple : ls uvsq.fr
                        if (commande.getArgs().length == 1) {
                            String domaine = commande.getArgs()[0];
                            for (DnsItem item : dns.getItems(domaine)) {
                                ui.affiche(item.getAdresseIP() + " " + item.getNomMachine());
                            }
                        } else {
                            ui.affiche("Usage: ls <domaine>");
                        }
                        break;

                    case "add":
                        // Exemple : add 192.168.0.4 nouveau.uvsq.fr
                        if (commande.getArgs().length == 2) {
                            try {
                                AdresseIP ip = new AdresseIP(commande.getArgs()[0]);
                                NomMachine nom = new NomMachine(commande.getArgs()[1]);
                                dns.addItem(ip, nom);
                                ui.affiche("Ajout effectué.");
                            } catch (Exception e) {
                                ui.affiche("Erreur: " + e.getMessage());
                            }
                        } else {
                            ui.affiche("Usage: add <adresse_ip> <nom_machine>");
                        }
                        break;

                    case "getip":
                        // Exemple : getip serveur1.uvsq.fr
                        if (commande.getArgs().length == 1) {
                            GetIpCommande getIpCmd = new GetIpCommande(dns, commande.getArgs()[0], ui);
                            getIpCmd.execute();
                        } else {
                            ui.affiche("Usage: getip <nom_machine>");
                        }
                        break;

                    default:
                        ui.affiche("Commande inconnue: " + nomCommande);
                }
                System.out.print("> "); // invite pour la prochaine commande
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la base DNS : " + e.getMessage());
        }
    }
}
