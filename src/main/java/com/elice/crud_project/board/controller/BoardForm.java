package com.elice.crud_project.board.controller;

import com.elice.crud_project.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    private String boardName;
    private String boardIntro;
}
