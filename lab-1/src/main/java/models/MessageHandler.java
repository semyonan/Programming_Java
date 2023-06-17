package models;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for message notifications
 */
public interface MessageHandler {
    /**
     * Handle some message
     * @param message
     */
    public void handle(@NotNull String message);
}
