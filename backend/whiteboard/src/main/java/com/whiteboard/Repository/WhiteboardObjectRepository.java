package com.whiteboard.Repository;

import com.whiteboard.Entity.WhiteboardObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WhiteboardObjectRepository extends JpaRepository<WhiteboardObject, Integer> {
    List<WhiteboardObject> findByWhiteboard_BoardId(Integer boardId);
    List<WhiteboardObject> findByUser_Id(Long userId);
}
