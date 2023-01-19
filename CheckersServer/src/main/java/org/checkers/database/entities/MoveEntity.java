package org.checkers.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Move", schema = "Checkers")
public class MoveEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "turn_id")
    private int turnId;
    @Basic
    @Column(name = "move_number_in_turn")
    private int moveNumberInTurn;
    @Basic
    @Column(name = "move_type")
    private String moveType;
    @Basic
    @Column(name = "start_x")
    private int startX;
    @Basic
    @Column(name = "start_y")
    private int startY;
    @Basic
    @Column(name = "end_x")
    private int endX;
    @Basic
    @Column(name = "end_y")
    private int endY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public int getMoveNumberInTurn() {
        return moveNumberInTurn;
    }

    public void setMoveNumberInTurn(int moveNumberInTurn) {
        this.moveNumberInTurn = moveNumberInTurn;
    }

    public Object getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveEntity that = (MoveEntity) o;
        return id == that.id && turnId == that.turnId && moveNumberInTurn == that.moveNumberInTurn && startX == that.startX && startY == that.startY && endX == that.endX && endY == that.endY && Objects.equals(moveType, that.moveType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, turnId, moveNumberInTurn, moveType, startX, startY, endX, endY);
    }
}
