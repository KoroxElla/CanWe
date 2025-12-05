package com.whiteboard.Service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.HashSet;

@Service
public class SessionService {
    
    // Store active sessions: boardId -> Set of userIds
    private final Map<Integer, Set<Long>> activeSessions = new ConcurrentHashMap<>();
    
    // Store WebSocket session IDs: sessionId -> boardId
    private final Map<String, Integer> sessionToBoard = new ConcurrentHashMap<>();
    
    // Store user sessions: userId -> Set of sessionIds
    private final Map<Long, Set<String>> userSessions = new ConcurrentHashMap<>();
    
    public void joinSession(Integer boardId, Long userId, String sessionId) {
        activeSessions.computeIfAbsent(boardId, k -> new HashSet<>()).add(userId);
        sessionToBoard.put(sessionId, boardId);
        userSessions.computeIfAbsent(userId, k -> new HashSet<>()).add(sessionId);
    }
    
    public void leaveSession(String sessionId) {
        Integer boardId = sessionToBoard.remove(sessionId);
        if (boardId != null) {
            // Find which user this session belongs to
            userSessions.entrySet().stream()
                .filter(entry -> entry.getValue().contains(sessionId))
                .findFirst()
                .ifPresent(entry -> {
                    Long userId = entry.getKey();
                    entry.getValue().remove(sessionId);
                    
                    // If user has no more sessions, remove from active sessions
                    if (entry.getValue().isEmpty()) {
                        userSessions.remove(userId);
                        Set<Long> users = activeSessions.get(boardId);
                        if (users != null) {
                            users.remove(userId);
                            if (users.isEmpty()) {
                                activeSessions.remove(boardId);
                            }
                        }
                    }
                });
        }
    }
    
    public Set<Long> getActiveUsers(Integer boardId) {
        return activeSessions.getOrDefault(boardId, new HashSet<>());
    }
    
    public boolean isUserActive(Integer boardId, Long userId) {
        return activeSessions.getOrDefault(boardId, new HashSet<>()).contains(userId);
    }
    
    public int getActiveUserCount(Integer boardId) {
        return activeSessions.getOrDefault(boardId, new HashSet<>()).size();
    }
}
