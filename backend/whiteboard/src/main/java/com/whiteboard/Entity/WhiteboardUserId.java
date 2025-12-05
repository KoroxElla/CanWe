package com.whiteboard.Entity;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class WhiteboardUserId implements Serializable {
    private Integer board_id;
    private Integer user_id;
    
    // Need default constructor
    public WhiteboardUserId() {}
    
    public WhiteboardUserId(Integer board_id, Integer user_id) {
        this.board_id = board_id;
        this.user_id = user_id;
    }
}
