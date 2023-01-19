package org.checkers.database.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "save_time", nullable = false)
    private Instant saveTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Instant saveTime) {
        this.saveTime = saveTime;
    }

}