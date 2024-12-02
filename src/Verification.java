import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Verification {
    private String carteValide = "123456";
    private String codeCorrect = "password";
    private int tentatives = 0;

    public static void main(String[] args) {
        Verification systeme = new Verification();
        systeme.verifierAcces();
    }

    public void verifierAcces() {
        Scanner scanner = new Scanner(System.in);

        // Vérification de la carte
        System.out.print("Veuillez entrer votre carte : ");
        String carte = scanner.nextLine();

        if (!carte.equals(carteValide)) {
            System.out.println("Accès refusé : carte invalide.");
            return;
        }

        // Vérification du code
        while (tentatives < 3) {
            System.out.print("Veuillez entrer votre code : ");
            String code = scanner.nextLine();

            if (code.equals(codeCorrect)) {
                System.out.println("Accès accordé, La porte s'ouvre.");
                return;
            } else {
                tentatives++;
                System.out.println("Code incorrect. Tentatives restantes : " + (3 - tentatives));
            }
        }

        // Si le code est incorrect après 3 tentatives
        System.out.println("Accès refusé : code incorrect. Déclenchement de l'alarme.");
        jouerSonAlarme();
    }

    private void jouerSonAlarme() {
        try {
            File son = new File("bruitage sirene alarme.mp3"); // Chemin vers le fichier audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(son);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            // Attendre que le son se termine avant de continuer
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("Erreur lors de la lecture du son : " + e.getMessage());
        }
    }
}