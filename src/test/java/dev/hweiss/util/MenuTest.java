package dev.hweiss.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuTest {

    private Menu menu;
    private TestAktion aktion;

    private static class TestAktion extends Aktion {
        private int count = 0;

        protected TestAktion() {
            super("Testaktion");
        }

        @Override
        public boolean execute(UserInteraction userInteraction) {
            count++;
            return true;
        }
    }

    @BeforeEach
    public void setUp() {
        aktion = new TestAktion();
        menu = new Menu()
                .mitUnterMenue("test", "Testmenü 1", new Menu()
                        .mitAktion("aktion", aktion)
                );
    }

    @Test
    public void testMenue() {
        try (UserInteraction ui = new ScriptedUserInteraction(
                "test", "aktion", "falscheinabe", "aktion", "zurück", "aktion", "test", "aktion", "exit")) {
            menu.enter(ui);
            assertEquals(3, aktion.count);
        }
    }


    @Test
    public void testMenueOhneExit() {
        try (UserInteraction ui = new ScriptedUserInteraction(
                "test", "aktion", "falscheinabe", "aktion", "zurück", "aktion", "test", "aktion", "zurück", "zurück")) {
            menu.enter(ui);
            assertEquals(3, aktion.count);
        }
    }


}
