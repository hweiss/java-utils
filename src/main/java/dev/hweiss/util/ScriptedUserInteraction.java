package dev.hweiss.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Eine vorkonfigurierte UserInteraction für Testzwecke.
 */
public class ScriptedUserInteraction extends UserInteraction {

    private final Iterator<String> inputs;
    private final List<String> outputs;
    private final List<String> outputView;

    public ScriptedUserInteraction(List<String> inputs) {
        this.inputs = List.copyOf(inputs).iterator();
        this.outputs = new ArrayList<>();
        this.outputView = Collections.unmodifiableList(this.outputs);
    }

    public ScriptedUserInteraction(String... inputs) {
        this(List.of(inputs));
    }

    @Override
    public void output(Object message) {
        this.outputs.add(message.toString());
    }

    public List<String> getOutputs() {
        return this.outputView;
    }

    @Override
    public String input() {
        if (this.inputs.hasNext()) {
            return this.inputs.next();
        } else {
            throw new NoSuchElementException("Keine weiteren inputs");
        }
    }
}
