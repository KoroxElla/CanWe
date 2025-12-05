package com.whiteboard.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "objects")
public class WhiteboardObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer object_id;


    @ManyToOne
    @JoinColumn(name = "board_id")
    private Whiteboard whiteboard;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String type; // 'line', 'circle', etc

    @Column(columnDefinition = "jsonb")
    private String data;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime created_at;
}

