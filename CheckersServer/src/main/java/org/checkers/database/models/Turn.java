package org.checkers.database.models;

import org.checkers.database.models.Game;

import javax.persistence.*;

@Entity
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Lob
    @Column(name = "checker_color")
    private String checkerColor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getCheckerColor() {
        return checkerColor;
    }

    public void setCheckerColor(String checkerColor) {
        this.checkerColor = checkerColor;
    }

}