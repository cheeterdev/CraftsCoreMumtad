package de.craftsblock.craftscore.event;

import de.craftsblock.craftscore.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ListenerRegistry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Listener>> data = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Short, ConcurrentLinkedQueue<Event>> channelQueues = new ConcurrentHashMap<>();

    public void register(ListenerAdapter adapter) {
        for (Method method : Utils.getMethodsByAnnotation(adapter.getClass(), EventHandler.class))
            try {
                if (method.getParameterCount() <= 0)
                    throw new IllegalStateException("The methode " + method.getName() + " is provided with " + EventHandler.class.getName() + " but does not include " + Event.class.getName() + " as argument!");
                Class<?> parameter = method.getParameters()[0].getType();
                if (!Event.class.isAssignableFrom(parameter))
                    throw new IllegalStateException("The methode " + method.getName() + " is provided with " + EventHandler.class.getName() + " but does not include " + Event.class.getName() + " as argument!");
                EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                ConcurrentLinkedQueue<Listener> tmp = data.getOrDefault(parameter.getName(), new ConcurrentLinkedQueue<>());
                tmp.add(new Listener(method, adapter, eventHandler.priority()));
                data.put(parameter.getName(), tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void unregister(ListenerAdapter adapter) {
        for (Method method : Utils.getMethodsByAnnotation(adapter.getClass(), EventHandler.class))
            try {
                if (method.getParameterCount() <= 0)
                    throw new IllegalStateException("The methode " + method.getName() + " is provided with " + EventHandler.class.getName() + " but does not include " + Event.class.getName() + " as argument!");
                Class<?> parameter = method.getParameters()[0].getType();
                if (!Event.class.isAssignableFrom(parameter))
                    throw new IllegalStateException("The methode " + method.getName() + " is provided with " + EventHandler.class.getName() + " but does not include " + Event.class.getName() + " as argument!");
                EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                ConcurrentLinkedQueue<Listener> tmp = data.getOrDefault(parameter.getName(), new ConcurrentLinkedQueue<>());
                tmp.removeIf(listener -> (listener.method.equals(method) && listener.priority.equals(eventHandler.priority())));
                data.put(parameter.getName(), tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void call(Event event) throws InvocationTargetException, IllegalAccessException {
        if (!this.data.containsKey(event.getClass().getName()))
            return;

        ConcurrentLinkedQueue<Listener> data = new ConcurrentLinkedQueue<>(List.of(this.data.get(event.getClass().getName()).toArray(new Listener[0])));
        EventPriority next = EventPriority.LOWEST;
        while (next != null) {
            if (data.isEmpty()) break;

            for (Listener tile : data)
                if (tile.priority == next) {
                    tile.method.invoke(tile.self, event);
                    data.remove(tile);
                }

            next = EventPriority.next(next);
        }
    }

    public void queueCall(Event event) {
        queueCall((short) 0, event);
    }

    public void queueCall(Short channel, Event event) {
        channelQueues.computeIfAbsent(channel, i -> new ConcurrentLinkedQueue<>()).add(event);
    }

    public void callQueued() throws InvocationTargetException, IllegalAccessException {
        callQueued((short) 0);
    }

    public void callAllQueued() throws InvocationTargetException, IllegalAccessException {
        for (Short channel : channelQueues.keySet())
            callQueued(channel);
    }

    public void callQueued(Short channel) throws InvocationTargetException, IllegalAccessException {
        if (!channelQueues.containsKey(channel)) return;

        ConcurrentLinkedQueue<Event> queue = channelQueues.get(channel);
        for (Event event : queue) {
            call(event);
            queue.remove(event);
        }

        if (queue.isEmpty()) channelQueues.remove(channel);
    }

    private record Listener(Method method, Object self, EventPriority priority) {
    }

}
