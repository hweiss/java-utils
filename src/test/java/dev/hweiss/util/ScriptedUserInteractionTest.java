package dev.hweiss.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScriptedUserInteractionTest {

    @Test
    public void testOutputView() {
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction(List.of())) {
            // Outputs sollen zu beginn leer sein
            assertTrue(ui.getOutputs().isEmpty());
            // Aufrufe von .output() werden an die Liste angehängt
            ui.output("test");
            assertIterableEquals(List.of("test"), ui.getOutputs());
            // Die Liste ist eine unmodifizierbare View
            assertThrows(UnsupportedOperationException.class, ui.getOutputs()::clear);
            // Die view wird wiederverwendet
            assertSame(ui.getOutputs(), ui.getOutputs());
        }

    }

    @Test
    public void testInput() {
        List<String> inputs = List.of("dies", "ist", "ein", "Test");
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction(inputs)) {
            var actual = List.of(ui.input(), ui.input(), ui.input(), ui.input());
            assertIterableEquals(inputs, actual);
            assertThrows(NoSuchElementException.class, ui::input);
        }
    }

    @Test
    public void testPrompt() {
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction(List.of("asdf"))) {
            assertEquals("asdf", ui.prompt("Input Prompt"));
            assertIterableEquals(List.of("Input Prompt"), ui.getOutputs());
        }
    }

    @Test
    public void testPromptRepeats() {
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction(List.of("asdf", "wsda", "fgsa", "fwer"))) {
            assertEquals("fgsa", ui.prompt("prompt", s -> s.startsWith("f")));
            assertEquals(3, ui.getOutputs().size());
            assertTrue(ui.getOutputs().stream().allMatch("prompt"::equals));
        }
    }

    @Test
    public void testPromptRepeatsWithErrorMessage() {
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction(List.of("asdf", "wsda", "fgsa", "fwer"))) {
            assertEquals("fgsa", ui.prompt("prompt", s -> s.startsWith("f"),"error"));
            assertIterableEquals(List.of("prompt", "error", "prompt", "error", "prompt"), ui.getOutputs());
        }
    }

    @Test
    public void testConvertingPrompt() {
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction("asdf", "54", "42", "22")) {
            int input = ui.prompt("prompt", Integer::parseInt, i -> i < 50, null);
            assertEquals(42, input);
            assertEquals(3, ui.getOutputs().size());
            assertTrue(ui.getOutputs().stream().allMatch("prompt"::equals));
        }
    }

    @Test
    public void testConvertingPromptWithErrorMessage() {
        try (ScriptedUserInteraction ui = new ScriptedUserInteraction("asdf", "54", "42", "22")) {
            int input = ui.prompt("prompt", Integer::parseInt, i -> i < 50, "error");
            assertEquals(42, input);
            assertIterableEquals(List.of("prompt", "error", "prompt", "error", "prompt"), ui.getOutputs());
        }
    }
}


