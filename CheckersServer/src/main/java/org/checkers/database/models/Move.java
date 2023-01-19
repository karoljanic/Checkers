package org.checkers.database.models;

import javax.persistence.*;

@Entity
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "move_number_in_turn", nullable = false)
    private Integer moveNumberInTurn;

    @Lob
    @Column(name = "move_type")
    private String moveType;

    @Column(name = "start_x", nullable = false)
    private Integer startX;

    @Column(name = "start_y", nullable = false)
    private Integer startY;

    @Column(name = "end_x", nullable = false)
    private Integer endX;

    @Column(name = "end_y", nullable = false)
    private Integer endY;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoveNumberInTurn() {
        return moveNumberInTurn;
    }

    public void setMoveNumberInTurn(Integer moveNumberInTurn) {
        this.moveNumberInTurn = moveNumberInTurn;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer startX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer startY) {
        this.startY = startY;
    }

    public Integer getEndX() {
        return endX;
    }

    public void setEndX(Integer endX) {
        this.endX = endX;
    }

    public Integer getEndY() {
        return endY;
    }

    public void setEndY(Integer endY) {
        this.endY = endY;
    }

}