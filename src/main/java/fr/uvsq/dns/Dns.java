package fr.uvsq.dns;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Dns {

    private final Map<AdresseIP, DnsItem> mapIP = new HashMap<>();
    private final Map<NomMachine, DnsItem> mapNom = new HashMap<>();
    private final Path fichierBase;

    public Dns(String cheminFichier) throws IOException {
        this.fichierBase = Paths.get(cheminFichier);
        loadBase();
    }

    private void loadBase() throws IOException {
        List<String> lignes = Files.readAllLines(fichierBase);
        for (String ligne : lignes) {
            if (ligne.trim().isEmpty()) continue;
            System.out.println("Chargement ligne : " + ligne);  // Pour debug
            String[] parts = ligne.split("\\s+");
            if (parts.length != 2) continue; // ignorer ligne mal formée

            NomMachine nom = new NomMachine(parts[0]);
            AdresseIP ip = new AdresseIP(parts[1]);

            DnsItem item = new DnsItem(ip, nom);
            mapIP.put(ip, item);
            mapNom.put(nom, item);
        }
    }

    public DnsItem getItem(AdresseIP ip) {
        return mapIP.get(ip);
    }

    public DnsItem getItem(NomMachine nom) {
        return mapNom.get(nom);
    }

    public NomMachine getNomMachine(AdresseIP ip) {
        DnsItem item = mapIP.get(ip);
        if (item != null) {
            return item.getNomMachine();
        }
        return null;
    }

    public List<DnsItem> getItems(String domaine) {
        return mapNom.keySet().stream()
            .filter(n -> n.getDomaine().equals(domaine))
            .map(mapNom::get)
            .sorted(Comparator.comparing(d -> d.getNomMachine().getNom()))
            .collect(Collectors.toList());
    }

    public void addItem(AdresseIP ip, NomMachine nom) throws Exception {
        if (mapIP.containsKey(ip)) {
            throw new Exception("ERREUR : L'adresse IP existe déjà !");
        }
        if (mapNom.containsKey(nom)) {
            throw new Exception("ERREUR : Le nom de machine existe déjà !");
        }
        DnsItem item = new DnsItem(ip, nom);
        mapIP.put(ip, item);
        mapNom.put(nom, item);
        saveBase();
    }

    private void saveBase() throws IOException {
        List<String> lignes = mapNom.keySet().stream()
            .map(n -> n.toString() + " " + mapNom.get(n).getAdresseIP().toString())
            .collect(Collectors.toList());
        Files.write(fichierBase, lignes, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
