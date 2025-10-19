package fr.uvsq.dns;

public class GetIpCommande {

    private final Dns dns;
    private final String nomMachine;
    private final DnsTUI ui;

    public GetIpCommande(Dns dns, String nomMachine, DnsTUI ui) {
        this.dns = dns;
        this.nomMachine = nomMachine;
        this.ui = ui;
    }

    public void execute() {
        try {
            NomMachine nom = new NomMachine(nomMachine);
            DnsItem item = dns.getItem(nom);
            if (item != null) {
                ui.affiche(item.getAdresseIP().toString());
            } else {
                ui.affiche("Aucune adresse IP trouvée pour " + nomMachine);
            }
        } catch (Exception e) {
            ui.affiche("Erreur : " + e.getMessage());
        }
    }
}
