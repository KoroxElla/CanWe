package com.whiteboard.Repository;

import com.whiteboard.Entity.Whiteboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhiteboardRepository extends JpaRepository<Whiteboard, Integer> {
}
