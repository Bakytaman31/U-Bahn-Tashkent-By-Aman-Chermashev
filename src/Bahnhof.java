//Importieren der benötigten Bibliotheken
import java.util.*;

public class Bahnhof {
    //Erklärung der Variablen
    private String name;
    private String UBahnLinie;
    private List<Bahnhof> Nachbarn;

    // Erstellen eines Konstruktors
    public Bahnhof(String name, String UBahnLinie) {
        this.name = name;
        this.UBahnLinie = UBahnLinie;
        this.Nachbarn = new ArrayList<>();
    }

    //Getters und setters
    public String NameHolen() {
        return name;
    }
    public String UBahnLinieHolen() {
        return UBahnLinie;
    }
    public void NachbarHinzufügen(Bahnhof neighbor) {
        Nachbarn.add(neighbor);
    }
    public List<Bahnhof> NachbarHolen() {
        return Nachbarn;
    }
}
