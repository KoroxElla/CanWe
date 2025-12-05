package com.whiteboard.Controller;

import com.whiteboard.Entity.WhiteboardObject;
import com.whiteboard.Service.WhiteboardObjectService;
import com.whiteboard.dto.WhiteboardObjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private WhiteboardObjectService objectService;
    
    // When a client draws something
    @MessageMapping("/whiteboard/{boardId}/draw")
    @SendTo("/topic/whiteboard/{boardId}")
    public WhiteboardObject drawObject(
            @DestinationVariable Integer boardId,
            @RequestBody WhiteboardObjectDTO objectDTO) {
        
        // Convert DTO to entity and save
        // For now, return a mock object
        WhiteboardObject object = new WhiteboardObject();
        object.setObject_id((int) (Math.random() * 1000));
        object.setType(objectDTO.getType());
        object.setData(objectDTO.getData());
        object.setCreated_at(LocalDateTime.now());
        
        // Broadcast to all connected clients
        return object;
    }
    
    // When a client deletes something
    @MessageMapping("/whiteboard/{boardId}/delete")
    @SendTo("/topic/whiteboard/{boardId}")
    public String deleteObject(
            @DestinationVariable Integer boardId,
            @RequestBody Integer objectId) {
        
        // Delete logic here
        return "DELETE:" + objectId;
    }
    
    // When a client clears the whiteboard
    @MessageMapping("/whiteboard/{boardId}/clear")
    @SendTo("/topic/whiteboard/{boardId}")
    public String clearWhiteboard(@DestinationVariable Integer boardId) {
        return "CLEAR";
    }
    
    // Session management - user joined
    @MessageMapping("/whiteboard/{boardId}/join")
    @SendTo("/topic/whiteboard/{boardId}/users")
    public String userJoined(
            @DestinationVariable Integer boardId,
            @RequestBody String userId) {
        return "JOIN:" + userId;
    }
    
    // Session management - user left
    @MessageMapping("/whiteboard/{boardId}/leave")
    @SendTo("/topic/whiteboard/{boardId}/users")
    public String userLeft(
            @DestinationVariable Integer boardId,
            @RequestBody String userId) {
        return "LEAVE:" + userId;
    }
}
