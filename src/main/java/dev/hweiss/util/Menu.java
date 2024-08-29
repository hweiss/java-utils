package dev.hweiss.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Menu {

    private final Map<String, Aktion> aktionen = new LinkedHashMap<>();

    public Menu mitAktion(String befehl, Aktion aktion) {
        this.aktionen.put(befehl, aktion);
        return this;
    }

    public Menu mitUnterMenue(String befehl, String beschreibung, Menu menu) {
        this.aktionen.put(befehl, Aktion.mit(beschreibung, menu::enter));
        return this;
    }


    private String liste() {
        return aktionen.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().beschreibung())
                .collect(Collectors.joining("\n"));
    }

    public boolean enter(UserInteraction userInteraction) {
        this.aktionen.put("zurück", Aktion.BACK);
        this.aktionen.put("exit", Aktion.EXIT);
        boolean weiter;
        do {
            userInteraction.output(liste());
            String input = userInteraction.prompt("Gib einen Befehl ein:", String::toLowerCase, aktionen.keySet()::contains, "Ungültige Eingabe");
            if ("zurück".equals(input)) return true;
            if ("exit".equals(input)) return false;
            weiter = aktionen.get(input).execute(userInteraction);
        } while (weiter);
        return false;
    }
}
