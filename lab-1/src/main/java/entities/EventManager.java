package entities;

import models.MessageHandler;

import java.util.*;

/**
 * Class creating the {@link Bank}'s notifications
 */
public class EventManager {
    private final Map<String, List<MessageHandler>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    /**
     * Create {@link Client}'s subscribe to {@link Bank}'s event
     * @param eventType
     * @param listener
     */
    public void subscribe(String eventType, MessageHandler listener) {
        List<MessageHandler> users = listeners.get(eventType);
        users.add(listener);
    }

    /**
     * Create {@link Client}'s unsubscribe to {@link Bank}'s event
     *      * @param eventType
     *      * @param listener
     */
    public void unsubscribe(String eventType, MessageHandler listener) {
        List<MessageHandler> users = listeners.get(eventType);
        users.remove(listener);
    }

    /**
     * Notify all {@link Client}s
     * @param eventType
     * @param message
     */
    public void notify(String eventType, String message) {
        List<MessageHandler> users = listeners.get(eventType);
        for (MessageHandler listener : users) {
            listener.handle(message);
        }
    }
}