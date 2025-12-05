package com.whiteboard.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long whiteboardId;
    private String userId;
    private LocalDateTime lastActive;

    public Session() {}
    public Session(Long whiteboardId, String userId) {
        this.whiteboardId = whiteboardId;
        this.userId = userId;
        this.lastActive = LocalDateTime.now();
    }

    
}

