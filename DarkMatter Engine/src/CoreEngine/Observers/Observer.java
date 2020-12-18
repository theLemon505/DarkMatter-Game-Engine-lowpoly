package CoreEngine.Observers;

import CoreEngine.Objects.Node;
import CoreEngine.Observers.Events.Event;

public interface Observer {
    void onNotify(Node object, Event event);
}