package com.whiteboard.Service.Impl;

import com.whiteboard.Entity.WhiteboardObject;
import com.whiteboard.Repository.WhiteboardObjectRepository;
import com.whiteboard.Service.WhiteboardObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WhiteboardObjectServiceImpl implements WhiteboardObjectService {
    
    @Autowired
    private WhiteboardObjectRepository objectRepository;
    
    @Override
    public List<WhiteboardObject> getAllObjects() {
        return objectRepository.findAll();
    }
    
    @Override
    public Optional<WhiteboardObject> getObjectById(Integer id) {
        return objectRepository.findById(id);
    }
    
    @Override
    public List<WhiteboardObject> getObjectsByUser(Long userId) {
        return objectRepository.findByUser_Id(userId);
    }
    
    @Override
    public WhiteboardObject updateObject(Integer id, WhiteboardObject objectDetails) {
        return objectRepository.findById(id).map(object -> {
            object.setType(objectDetails.getType());
            object.setData(objectDetails.getData());
            return objectRepository.save(object);
        }).orElseThrow(() -> new RuntimeException("Object not found with id: " + id));
    }
    
    @Override
    public void deleteObject(Integer id) {
        if (!objectRepository.existsById(id)) {
            throw new RuntimeException("Object not found with id: " + id);
        }
        objectRepository.deleteById(id);
    }
}
