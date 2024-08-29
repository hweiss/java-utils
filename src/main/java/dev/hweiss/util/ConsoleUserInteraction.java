package dev.hweiss.util;

import java.util.Scanner;

/**
 * Ein Hilfsmittel zur Nutzerinteraktion basierend auf STDIN und STDOUT
 */
public class ConsoleUserInteraction extends UserInteraction {

    private final Scanner input;

    public ConsoleUserInteraction() {
        this.input = new Scanner(System.in);
    }

    @Override
    public void output(Object message) {
        System.out.println(message);
    }

    @Override
    public String input() {
        return input.nextLine();
    }

    @Override
    public void close() {
        input.close();
    }
}
