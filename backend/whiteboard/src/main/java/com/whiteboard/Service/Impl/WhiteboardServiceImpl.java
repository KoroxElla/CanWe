package com.whiteboard.Service.Impl;

import com.whiteboard.Entity.*;
import com.whiteboard.Repository.*;
import com.whiteboard.Service.WhiteboardService;
import com.whiteboard.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WhiteboardServiceImpl implements WhiteboardService {
    
    @Autowired
    private WhiteboardRepository whiteboardRepository;
    
    @Autowired
    private WhiteboardObjectRepository objectRepository;
    
    @Autowired
    private WhiteboardUserRepository whiteboardUserRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Whiteboard> getAllWhiteboards() {
        return whiteboardRepository.findAll();
    }
    
    @Override
    public Optional<Whiteboard> getWhiteboardById(Integer id) {
        return whiteboardRepository.findById(id);
    }
    
    @Override
    public Whiteboard createWhiteboard(Whiteboard whiteboard) {
        // Business logic validation
        if (whiteboard.getName() == null || whiteboard.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Whiteboard name cannot be empty");
        }
        return whiteboardRepository.save(whiteboard);
    }
    
    @Override
    public void deleteWhiteboard(Integer id) {
        // Delete all associated objects first
        List<WhiteboardObject> objects = objectRepository.findByWhiteboard_BoardId(id);
        objectRepository.deleteAll(objects);
        
        // Delete all user associations
        // You'll need to add this method to the repository
        List<WhiteboardUser> whiteboardUsers = whiteboardUserRepository.findByBoardId(id);
        whiteboardUserRepository.deleteAll(whiteboardUsers);
        
        // Delete the whiteboard
        whiteboardRepository.deleteById(id);
    }
    
    @Override
    public void addUserToWhiteboard(Integer boardId, WhiteboardUserDTO whiteboardUserDTO) {
        Whiteboard whiteboard = whiteboardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Whiteboard not found with id: " + boardId));
        
        User user = userRepository.findById(whiteboardUserDTO.getUser_id())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + whiteboardUserDTO.getUser_id()));
        
        // Check if user is already added to this whiteboard
        WhiteboardUserId compositeId = new WhiteboardUserId(boardId, user.getId().intValue());
        
        if (whiteboardUserRepository.existsById(compositeId)) {
            throw new RuntimeException("User is already added to this whiteboard");
        }
        
        WhiteboardUser whiteboardUser = new WhiteboardUser();
        whiteboardUser.setId(compositeId);
        whiteboardUser.setWhiteboard(whiteboard);
        whiteboardUser.setUser(user);
        
        whiteboardUserRepository.save(whiteboardUser);
    }

    @Override
    public void removeUserFromWhiteboard(Integer boardId, Long userId) {
        WhiteboardUserId id = new WhiteboardUserId(boardId, userId.intValue());

        if (!whiteboardUserRepository.existsById(id)) {
            throw new RuntimeException("User not found in this whiteboard");
        }

        whiteboardUserRepository.deleteById(id);
    }

    @Override
    public List<WhiteboardUser> getWhiteboardUsers(Integer boardId) {
        return whiteboardUserRepository.findByBoardId(boardId);
    }

    @Override
    public List<WhiteboardObject> getWhiteboardObjects(Integer boardId) {
        if (!whiteboardRepository.existsById(boardId)) {
            throw new RuntimeException("Whiteboard not found with id: " + boardId);
        }
        return objectRepository.findByWhiteboard_BoardId(boardId);
    }

    @Override
    public WhiteboardObject addObjectToWhiteboard(Integer boardId, WhiteboardObjectDTO objectDTO) {
        Whiteboard whiteboard = whiteboardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Whiteboard not found with id: " + boardId));

        User user = userRepository.findById(objectDTO.getUser_id())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + objectDTO.getUser_id()));

        WhiteboardUserId compositeId = new WhiteboardUserId(boardId, user.getId().intValue());
        if (!whiteboardUserRepository.existsById(compositeId)) {
            throw new RuntimeException("User does not have access to this whiteboard");
        }

        List<String> validTypes = List.of("line", "circle", "text", "rectangle", "ellipse");
        if (!validTypes.contains(objectDTO.getType().toLowerCase())) {
            throw new IllegalArgumentException("Invalid object type: " + objectDTO.getType());
        }

        WhiteboardObject object = new WhiteboardObject();
        object.setWhiteboard(whiteboard);
        object.setUser(user);
        object.setType(objectDTO.getType());
        object.setData(objectDTO.getData());
        object.setCreated_at(LocalDateTime.now());

        return objectRepository.save(object);
    }

    
    @Override
    public void deleteObjectFromWhiteboard(Integer objectId) {
        if (!objectRepository.existsById(objectId)) {
            throw new RuntimeException("Object not found with id: " + objectId);
        }
        objectRepository.deleteById(objectId);
    }
}
