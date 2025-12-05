package com.whiteboard.Service;

import com.whiteboard.Entity.*;
import com.whiteboard.dto.*;
import java.util.List;
import java.util.Optional;

public interface WhiteboardService {
    List<Whiteboard> getAllWhiteboards();
    Optional<Whiteboard> getWhiteboardById(Integer id);
    Whiteboard createWhiteboard(Whiteboard whiteboard);
    void deleteWhiteboard(Integer id);
    
    // Whiteboard Users
    void addUserToWhiteboard(Integer boardId, WhiteboardUserDTO whiteboardUserDTO);
    void removeUserFromWhiteboard(Integer boardId, Long userId);
    List<WhiteboardUser> getWhiteboardUsers(Integer boardId);
    
    // Whiteboard Objects
    List<WhiteboardObject> getWhiteboardObjects(Integer boardId);
    WhiteboardObject addObjectToWhiteboard(Integer boardId, WhiteboardObjectDTO objectDTO);
    void deleteObjectFromWhiteboard(Integer objectId);
}
