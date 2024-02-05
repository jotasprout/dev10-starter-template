package learn.memories.domain;

import learn.memories.models.Memory;

import java.util.ArrayList;
import java.util.List;

public class MemoryResult {

    private ArrayList<String> messages = new ArrayList<>();
    private Memory memory;

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    public boolean isSuccess() {
        // If an error message exists, the operation failed.
        return messages.size() == 0;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
}
