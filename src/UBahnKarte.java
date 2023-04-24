import java.util.*;
public class UBahnKarte {
    //Erklärung der Variablen
    private List<Bahnhof> bahnhofs;

    // Erstellen eines Konstruktors
    public UBahnKarte() {
        this.bahnhofs = new ArrayList<>();
    }

    //Getters und setters
    public void BahnhofHinzufügen(Bahnhof bahnhof) {
        bahnhofs.add(bahnhof);
    }

    //Methode des kürzesten Weges
    public List<Bahnhof> denkürzestenWegfinden(Bahnhof Startbahnhof, Bahnhof Endbahnhof) {
        Map<Bahnhof, Integer> Entfernungen = new HashMap<>();
        Map<Bahnhof, Bahnhof> frühereBahnhöfe = new HashMap<>();
        PriorityQueue<Bahnhof> PrioritätWarteschlange = new PriorityQueue<>(Comparator.comparing(Entfernungen::get));
        Set<Bahnhof> besucht = new HashSet<>();

        // Entfernung der Startbahnhof auf 0 setzen und zur Warteschlange hinzufügen
        Entfernungen.put(Startbahnhof, 0);
        PrioritätWarteschlange.add(Startbahnhof);

        while (!PrioritätWarteschlange.isEmpty()) {
            Bahnhof aktuelleBahnhof = PrioritätWarteschlange.poll();
            besucht.add(aktuelleBahnhof);

            for (Bahnhof Nachbar : aktuelleBahnhof.NachbarHolen()) {
                if (besucht.contains(Nachbar)) {
                    continue;
                }

                int Entfernung = Entfernungen.get(aktuelleBahnhof) + 1;

                if (!Entfernungen.containsKey(Nachbar) || Entfernung < Entfernungen.get(Nachbar)) {
                    Entfernungen.put(Nachbar, Entfernung);
                    frühereBahnhöfe.put(Nachbar, aktuelleBahnhof);
                    PrioritätWarteschlange.add(Nachbar);
                }
            }
        }

        // Weg von der Endbahnhof zur Startbahnhof bauen
        List<Bahnhof> kürzesterWeg = new ArrayList<>();
        Bahnhof aktuelleBahnhof = Endbahnhof;
        while (frühereBahnhöfe.containsKey(aktuelleBahnhof)) {
            kürzesterWeg.add(0, aktuelleBahnhof);
            aktuelleBahnhof = frühereBahnhöfe.get(aktuelleBahnhof);
        }

        if (!kürzesterWeg.isEmpty()) {
            kürzesterWeg.add(0, Startbahnhof);
        }

        return kürzesterWeg;
    }

}
