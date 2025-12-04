package com.whiteboard.Controller;

import com.whiteboard.Entity.WhiteboardObject;
import com.whiteboard.Service.WhiteboardObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objects")
public class WhiteboardObjectController {
    
    @Autowired
    private WhiteboardObjectService objectService;
    
    @GetMapping
    public List<WhiteboardObject> getAllObjects() {
        return objectService.getAllObjects();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<WhiteboardObject> getObjectById(@PathVariable Integer id) {
        Optional<WhiteboardObject> object = objectService.getObjectById(id);
        return object.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public List<WhiteboardObject> getObjectsByUser(@PathVariable Long userId) {
        return objectService.getObjectsByUser(userId);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<WhiteboardObject> updateObject(
            @PathVariable Integer id,
            @RequestBody WhiteboardObject objectDetails) {
        try {
            WhiteboardObject updatedObject = objectService.updateObject(id, objectDetails);
            return ResponseEntity.ok(updatedObject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObject(@PathVariable Integer id) {
        try {
            objectService.deleteObject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
