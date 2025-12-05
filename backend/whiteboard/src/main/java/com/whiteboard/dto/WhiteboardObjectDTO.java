package com.whiteboard.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WhiteboardObjectDTO {
    private Long user_id;
    private String type;
    private String data;
    
    // Constructor for easy conversion
    public WhiteboardObjectDTO(Long user_id, String type, String data) {
        this.user_id = user_id;
        this.type = type;
        this.data = data;
    }
}
