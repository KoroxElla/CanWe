package com.whiteboard.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "whiteboard_users")
public class WhiteboardUser {
    @EmbeddedId
    private WhiteboardUserId id;

    @ManyToOne
    @MapsId("board_id")
    @JoinColumn(name = "board_id")
    private Whiteboard whiteboard;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;
}

