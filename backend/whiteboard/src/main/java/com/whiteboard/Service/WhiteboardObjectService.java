package com.whiteboard.Service;

import com.whiteboard.Entity.WhiteboardObject;
import java.util.List;
import java.util.Optional;

public interface WhiteboardObjectService {
    List<WhiteboardObject> getAllObjects();
    Optional<WhiteboardObject> getObjectById(Integer id);
    List<WhiteboardObject> getObjectsByUser(Long userId);
    WhiteboardObject updateObject(Integer id, WhiteboardObject objectDetails);
    void deleteObject(Integer id);
}
