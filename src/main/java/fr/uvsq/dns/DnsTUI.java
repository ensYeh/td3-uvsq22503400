package fr.uvsq.dns;

import java.util.Scanner;

public class DnsTUI {
    private final Scanner scanner = new Scanner(System.in);

    // Lit la prochaine commande utilisateur
    public String nextLine() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    // Affiche un message à l'écran
    public void affiche(String message) {
        System.out.println(message);
    }
}
