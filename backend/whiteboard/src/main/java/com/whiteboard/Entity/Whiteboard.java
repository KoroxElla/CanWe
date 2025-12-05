
package com.whiteboard.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "whiteboards")
@Getter
@Setter
public class Whiteboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer board_id;

    private String name;
}

