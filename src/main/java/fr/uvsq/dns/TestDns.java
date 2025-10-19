package fr.uvsq.dns;

public class TestDns {
    public static void main(String[] args) {
        try {
            // On charge la base DNS depuis le fichier
            Dns dns = new Dns("src/main/resources/dns.txt");

            // Test 1 : recherche par nom de machine
            NomMachine nom = new NomMachine("serveur1.uvsq.fr");
            DnsItem item1 = dns.getItem(nom);
            System.out.println("Résultat pour " + nom + " : " + item1);

            // Test 2 : recherche par adresse IP
            AdresseIP ip = new AdresseIP("192.168.0.2");
            DnsItem item2 = dns.getItem(ip);
            System.out.println("Résultat pour " + ip + " : " + item2);

        } catch (java.io.IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de format dans les données : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
