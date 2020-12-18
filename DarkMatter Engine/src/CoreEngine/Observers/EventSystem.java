package CoreEngine.Observers;

import CoreEngine.Objects.Node;
import CoreEngine.Observers.Events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {
    private static List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void notify(Node obj, Event event) {
        for (Observer observer : observers) {
            observer.onNotify(obj, event);
        }
    }
}
