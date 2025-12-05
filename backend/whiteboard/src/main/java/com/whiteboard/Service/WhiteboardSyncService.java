package com.whiteboard.Service;

import com.whiteboard.Entity.WhiteboardObject;
import com.whiteboard.Repository.WhiteboardObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class WhiteboardSyncService {
    
    @Autowired
    private WhiteboardObjectRepository objectRepository;
    
    // Store pending operations for conflict resolution
    private final Map<Integer, List<WhiteboardObject>> pendingOperations = new ConcurrentHashMap<>();
    
    // Version vector for each whiteboard
    private final Map<Integer, Long> whiteboardVersions = new ConcurrentHashMap<>();
    
    /**
     * Merge incoming object with existing state
     * Using Operational Transformation (OT) for conflict resolution
     */
    public WhiteboardObject mergeObject(Integer boardId, WhiteboardObject incomingObject) {
        Long currentVersion = whiteboardVersions.getOrDefault(boardId, 0L);
        
        // Check for conflicts with pending operations
        if (hasConflict(boardId, incomingObject)) {
            // Apply Operational Transformation
            incomingObject = resolveConflict(boardId, incomingObject);
        }
        
        // Save the object
        WhiteboardObject savedObject = objectRepository.save(incomingObject);
        
        // Increment version
        whiteboardVersions.put(boardId, currentVersion + 1);
        
        return savedObject;
    }
    
    private boolean hasConflict(Integer boardId, WhiteboardObject newObject) {
        // Check if object with same ID already exists
        if (newObject.getObject_id() != null) {
            return objectRepository.existsById(newObject.getObject_id());
        }
        return false;
    }
    
    private WhiteboardObject resolveConflict(Integer boardId, WhiteboardObject newObject) {
        // Simple conflict resolution: assign new ID
        newObject.setObject_id(null); // Let DB generate new ID
        return newObject;
    }
    
    /**
     * Get current state of whiteboard with version
     */
    public SyncState getWhiteboardState(Integer boardId) {
        List<WhiteboardObject> objects = objectRepository.findByWhiteboard_BoardId(boardId);
        Long version = whiteboardVersions.getOrDefault(boardId, 0L);
        
        return new SyncState(objects, version);
    }
    
    /**
     * Sync state class
     */
    public static class SyncState {
        private List<WhiteboardObject> objects;
        private Long version;
        
        public SyncState(List<WhiteboardObject> objects, Long version) {
            this.objects = objects;
            this.version = version;
        }
        
        // Getters and setters
        public List<WhiteboardObject> getObjects() { return objects; }
        public void setObjects(List<WhiteboardObject> objects) { this.objects = objects; }
        public Long getVersion() { return version; }
        public void setVersion(Long version) { this.version = version; }
    }
}
