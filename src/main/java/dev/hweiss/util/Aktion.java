package dev.hweiss.util;

import java.util.function.Function;

public abstract class Aktion {

    public static final Aktion BACK = Aktion.mit("Eine Ebene zurück", ui -> true);
    public static final Aktion EXIT = Aktion.mit("Beenden", ui -> false);

    public static Aktion mit(String beschreibung, Function<UserInteraction, Boolean> funktion) {
        return new Aktion(beschreibung) {

            @Override
            public boolean execute(UserInteraction userInteraction) {
                return funktion.apply(userInteraction);
            }
        };
    }

    private final String beschreibung;

    protected Aktion(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String beschreibung() {
        return beschreibung;
    }

    public abstract boolean execute(UserInteraction userInteraction);
}
