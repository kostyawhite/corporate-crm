package ru.sberbank.crm.util;

import com.vaadin.flow.shared.Registration;
import ru.sberbank.crm.entity.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static List<Consumer<Task>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<Task> listener) {
        listeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(Task task) {
        for (Consumer<Task> listener : listeners) {
            executor.execute(() -> listener.accept(task));
        }
    }
}

