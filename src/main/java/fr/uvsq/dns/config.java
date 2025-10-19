package fr.uvsq.dns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();

    public Config(String nomFichier) throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(nomFichier)) {
            if (input == null) {
                throw new IOException("Fichier de configuration introuvable : " + nomFichier);
            }
            properties.load(input);
        }
    }

    public String get(String cle) {
        return properties.getProperty(cle);
    }
}
