package org.checkers.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Turn", schema = "Checkers")
public class TurnEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "game_id")
    private int gameId;
    @Basic
    @Column(name = "checker_color")
    private String checkerColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Object getCheckerColor() {
        return checkerColor;
    }

    public void setCheckerColor(String checkerColor) {
        this.checkerColor = checkerColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnEntity that = (TurnEntity) o;
        return id == that.id && gameId == that.gameId && Objects.equals(checkerColor, that.checkerColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameId, checkerColor);
    }
}
