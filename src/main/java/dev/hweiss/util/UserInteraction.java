package dev.hweiss.util;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Eine Hilfsklasse für Interaktion mit dem Nutzer.
 */
public abstract class UserInteraction implements AutoCloseable {

    /**
     * Gibt eine Nachricht als Zeichenkette aus.
     *
     * @param message Die Nachricht - wird in einen String umgewandelt.
     */
    public abstract void output(Object message);

    /**
     * Liest einen Input als einzelne Zeile ein.
     *
     * @return Die Nutzereingabe.
     */
    public abstract String input();

    /**
     * Fordert den Nutzer zu einer Eingabe auf und gibt diese Zurück.
     *
     * @param nachricht Die Aufforderung.
     * @return Der eingegebene Wert.
     */
    public String prompt(Object nachricht) {
        output(nachricht);
        return input();
    }

    /**
     * Fordert den Nutzer zu einer Eingabe auf und überprüft diese auf das angegebene Kriterium. Die Eingabe
     * wird solange wiederholt, bis eine gültige Eingabe getätigt wurde.
     *
     * @param nachricht Die Aufforderung.
     * @param kriterium Das Kriterium.
     * @return Der erste gültige eingegebene Wert.
     */
    public String prompt(Object nachricht, Predicate<? super String> kriterium) {
        return prompt(nachricht, kriterium, null);
    }

    /**
     * Fordert den Nutzer zu einer Eingabe auf und überprüft diese auf das angegebene Kriterium.
     * Die Aufforderung wird solange wiederholt, bis eine gültige Eingabe getätigt wurde.
     *
     * @param nachricht    Die Aufforderung.
     * @param kriterium    Das Kriterium.
     * @param errormessage Die Fehlermeldung, die dem Nutzer angezeigt wird.
     * @return Der erste gültige eingegebene Wert.
     */
    public String prompt(Object nachricht, Predicate<? super String> kriterium, String errormessage) {
        String input = prompt(nachricht);
        while (!kriterium.test(input)) {
            if (errormessage != null) {
                output(errormessage);
            }
            input = prompt(nachricht);
        }
        return input;
    }

    /**
     * Fordert den Nutzer zu einer Eingabe auf, konvertiert diese und überprüft sie auf das angegebene Kriterium.
     * Die Aufforderung wird solange wiederholt, bis eine gültige Eingabe getätigt wurde.
     *
     * @param nachricht    Die Aufforderung.
     * @param converter    Die konvertierungsfunktion
     * @param kriterium    Das Kriterium.
     * @param errorMessage Die Fehlermeldung, die dem Nutzer angezeigt wird.
     * @return Der erste gültige eingegebene Wert.
     * @param <T> Der Typ der gewünschten Eingabe
     */
    public <T> T prompt(Object nachricht, Function<String, T> converter, Predicate<? super T> kriterium, String errorMessage) {
        while (true) {
            String input = prompt(nachricht);
            try {
                T result = converter.apply(input);
                if (kriterium.test(result)) return result;
            } catch (Exception e) {
                // TODO log
            }
            if (errorMessage != null) output(errorMessage);
        }
    }

    @Override
    public void close() {
        // Leere implementierung
    }
}
