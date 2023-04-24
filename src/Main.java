//Importieren der benötigten Bibliotheken
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Listen von Stationen, Linien und Dateien deklarieren
        List<Bahnhof> bahnhofs = new ArrayList<>();
        List<String> UBahnLinien = new ArrayList<>();
        List<File> files;

        //Stationen anhand von Textdateien im Verzeichnis Lines erstellen
        File directory = new File("src/Lines");
        files = List.of(Objects.requireNonNull(directory.listFiles()));
        for (File file : files) {
            try {
                String name = file.getName().replace(".txt", "");
                UBahnLinien.add(name);
                Scanner stationScanner = new Scanner(file);
                while (stationScanner.hasNextLine()) {

                    Bahnhof bahnhof = new Bahnhof(stationScanner.nextLine(), name);
                    bahnhofs.add(bahnhof);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        //Hinzufügen der Nachbarn der einzelnen Bahnhöfe
        for (int i = 0; i < bahnhofs.size(); i++) {
            for (String line : UBahnLinien) {
                if (bahnhofs.get(i).UBahnLinieHolen().equals(line)) {
                    if (i == 0) {
                        bahnhofs.get(i).NachbarHinzufügen(bahnhofs.get(i + 1));
                    } else if ((i+1) == bahnhofs.size()) {
                        bahnhofs.get(i).NachbarHinzufügen(bahnhofs.get(i - 1));
                    } else if (!bahnhofs.get(i - 1).UBahnLinieHolen().equals(line)) {
                        bahnhofs.get(i).NachbarHinzufügen(bahnhofs.get(i + 1));
                    } else if (!bahnhofs.get(i + 1).UBahnLinieHolen().equals(line)) {
                        bahnhofs.get(i).NachbarHinzufügen(bahnhofs.get(i - 1));
                    } else if (i < bahnhofs.size()) {
                        bahnhofs.get(i).NachbarHinzufügen(bahnhofs.get(i - 1));
                        bahnhofs.get(i).NachbarHinzufügen(bahnhofs.get(i + 1));
                    }
                }
            }
        }

        //Hinzufügen der Nachbarn jeder benachbarten Bahnhöfe
        for (int i = 0; i < bahnhofs.size(); i++) {
            for (Bahnhof value : bahnhofs) {
                if (bahnhofs.get(i).NameHolen().equals(value.NameHolen()) && !bahnhofs.get(i).UBahnLinieHolen().equals(value.UBahnLinieHolen())) {
                    for (Bahnhof bahnhof : bahnhofs.get(i).NachbarHolen()) {
                        value.NachbarHinzufügen(bahnhof);
                    }
                    for (Bahnhof bahnhof : value.NachbarHolen()) {
                        bahnhofs.get(i).NachbarHinzufügen(bahnhof);
                    }
                }
            }
        }

        //U-Bahn-Karte erstellen
        UBahnKarte UBahnKarte = new UBahnKarte();


       //Hinzufügen von Bahnhöfen zur Bahnhofkarte
        for (Bahnhof bahnhof : bahnhofs) {
            UBahnKarte.BahnhofHinzufügen(bahnhof);
        }


        //Benutzeroberfläche
        boolean status = true;
        while (status) {
            System.out.println("");
            System.out.println("Herzlich willkommen bei Tashkent U-Bahn!");
            System.out.println("Wählen Sie eine Option");
            System.out.println("1. Alle Bahnhöfe anzeigen;");
            System.out.println("2. Kürzester Weg zwischen den Bahnhöfen;");
            System.out.println("3. Formularprogramm beenden");
            Scanner AuswahlScanner = new Scanner(System.in);
            String Auswahl = AuswahlScanner.nextLine();
            switch (Auswahl) {
                case "1":
                    for (Bahnhof bahnhof : bahnhofs) {
                        System.out.println(bahnhof.NameHolen() + "(" + bahnhof.UBahnLinieHolen() + ")");
                    }
                    break;
                case "2":
                    Scanner startScanner = new Scanner(System.in);
                    Scanner endScanner = new Scanner(System.in);
                    System.out.println("Startbahnhof bitte schreiben: ");
                    String Startbahnhof = startScanner.nextLine();
                    System.out.println("Endbahnhof bitte schreiben: ");
                    String Endbahnhof = endScanner.nextLine();
                    Bahnhof BahnhofEin = null;
                    Bahnhof BahnhofZwei = null;
                    for (Bahnhof bahnhof : bahnhofs) {
                        if (bahnhof.NameHolen().equals(Startbahnhof)) {
                            BahnhofEin = bahnhof;
                        } else if (bahnhof.NameHolen().equals(Endbahnhof)) {
                            BahnhofZwei = bahnhof;
                        }
                    }

                    //Suche nach der kürzesten Route
                    List<Bahnhof> kürzesterWeg = UBahnKarte.denkürzestenWegfinden(BahnhofEin, BahnhofZwei);

                    //Schlussfolgerung
                    for (int i = 0; i < kürzesterWeg.size(); i++) {
                        System.out.println(kürzesterWeg.get(i).NameHolen() + "(" + kürzesterWeg.get(i).UBahnLinieHolen() + ")");
                        if (i + 1 < kürzesterWeg.size()) {
                            if (!kürzesterWeg.get(i).UBahnLinieHolen().equals(kürzesterWeg.get(i + 1).UBahnLinieHolen())) {
                                System.out.println("Wechsel zur " + kürzesterWeg.get(i + 1).UBahnLinieHolen());
                            }
                        }
                    }
                    break;
                case "3":
                    System.out.println("Vielen Dank, dass Sie sich für unser Programm entschieden haben und einen schönen Tag!");
                    status = false;
                    break;
                default:
                    break;
            }
        }
    }
}