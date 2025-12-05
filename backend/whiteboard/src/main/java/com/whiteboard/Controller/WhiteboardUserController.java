package com.whiteboard.Controller;

import com.whiteboard.Entity.WhiteboardUser;
import com.whiteboard.Entity.WhiteboardUserId;
import com.whiteboard.Repository.WhiteboardUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/whiteboard-users")
public class WhiteboardUserController {
    
    @Autowired
    private WhiteboardUserRepository whiteboardUserRepository;
    
    @GetMapping
    public List<WhiteboardUser> getAllWhiteboardUsers() {
        return whiteboardUserRepository.findAll();
    }
    
    @DeleteMapping("/{boardId}/{userId}")
    public ResponseEntity<Void> removeUserFromWhiteboard(
            @PathVariable Integer boardId,
            @PathVariable Long userId) {
        
        WhiteboardUserId id = new WhiteboardUserId();
        id.setBoard_id(boardId);
        id.setUser_id(userId.intValue());
        
        if (whiteboardUserRepository.existsById(id)) {
            whiteboardUserRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
