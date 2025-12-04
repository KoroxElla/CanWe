package com.whiteboard.Controller;

import com.whiteboard.Entity.*;
import com.whiteboard.Service.WhiteboardService;
import com.whiteboard.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/whiteboards")
public class WhiteboardController {
    
    @Autowired
    private WhiteboardService whiteboardService;
    
    @GetMapping
    public ResponseEntity<List<Whiteboard>> getAllWhiteboards() {
        return ResponseEntity.ok(whiteboardService.getAllWhiteboards());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Whiteboard> getWhiteboardById(@PathVariable Integer id) {
        return whiteboardService.getWhiteboardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Whiteboard> createWhiteboard(@RequestBody Whiteboard whiteboard) {
        try {
            Whiteboard createdWhiteboard = whiteboardService.createWhiteboard(whiteboard);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWhiteboard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWhiteboard(@PathVariable Integer id) {
        try {
            whiteboardService.deleteWhiteboard(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{boardId}/users")
    public ResponseEntity<Void> addUserToWhiteboard(
            @PathVariable Integer boardId,
            @RequestBody WhiteboardUserDTO whiteboardUserDTO) {
        try {
            whiteboardService.addUserToWhiteboard(boardId, whiteboardUserDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{boardId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromWhiteboard(
            @PathVariable Integer boardId,
            @PathVariable Long userId) {
        try {
            whiteboardService.removeUserFromWhiteboard(boardId, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{boardId}/objects")
    public ResponseEntity<List<WhiteboardObject>> getWhiteboardObjects(@PathVariable Integer boardId) {
        try {
            List<WhiteboardObject> objects = whiteboardService.getWhiteboardObjects(boardId);
            return ResponseEntity.ok(objects);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{boardId}/objects")
    public ResponseEntity<WhiteboardObject> addObjectToWhiteboard(
            @PathVariable Integer boardId,
            @RequestBody WhiteboardObjectDTO objectDTO) {
        try {
            WhiteboardObject object = whiteboardService.addObjectToWhiteboard(boardId, objectDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(object);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
