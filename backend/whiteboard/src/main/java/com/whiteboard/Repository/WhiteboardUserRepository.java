package com.whiteboard.Repository;

import com.whiteboard.Entity.WhiteboardUser;
import com.whiteboard.Entity.WhiteboardUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WhiteboardUserRepository extends JpaRepository<WhiteboardUser, WhiteboardUserId> {
	@Query("SELECT wu FROM WhiteboardUser wu WHERE wu.id.board_id = :boardId")
    	List<WhiteboardUser> findByBoardId(@Param("boardId") Integer boardId);

    	@Query("SELECT wu FROM WhiteboardUser wu WHERE wu.id.user_id = :userId")
    	List<WhiteboardUser> findByUserId(@Param("userId") Integer userId);
}
