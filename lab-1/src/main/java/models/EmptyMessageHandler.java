package models;

import org.jetbrains.annotations.NotNull;

/**
 * Empty implementation of {@link MessageHandler}
 */
public class EmptyMessageHandler implements MessageHandler {
    /**
     * Empty message handle
     * @param message
     */
    @Override
    public void handle(@NotNull String message) {
    }
}
