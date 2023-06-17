package entities;

import models.MessageHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Storage handled messages
 */
public class StorageMessageHandler implements MessageHandler {
    private ArrayList<String> messages;

    public StorageMessageHandler(){
        messages = new ArrayList<>();
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Handle message
     * @param message
     */
    @Override
    public void handle(@NotNull String message) {
        messages.add(message);
    }
}
