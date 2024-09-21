package p02.game;

import java.util.EventListener;
import java.util.EventObject;

public interface CustomEventListener extends EventListener {
    void handleEvent(EventObject event);
}