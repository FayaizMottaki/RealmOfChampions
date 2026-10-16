package com.realmofchampions.util;

import java.util.ArrayList;
import java.util.List;

public class BattleLog {
    private final List<String> logs;

    public BattleLog() {
        this.logs = new ArrayList<>();
    }

    public void log(String message) {
        logs.add(message);
        System.out.println("[LOG]: " + message);
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    public String getLatestLog() {
        return logs.isEmpty() ? "" : logs.get(logs.size() - 1);
    }

    public void clear() {
        logs.clear();
    }
}
