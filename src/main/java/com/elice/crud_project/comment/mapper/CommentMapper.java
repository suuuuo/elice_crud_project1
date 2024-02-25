package com.elice.crud_project.comment.mapper;

import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.entity.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class CommentMapper {
    public Comment commentDtoToComment(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        } else {
            Comment comment = new Comment();
            comment.setCommemtContent(commentDto.getCommentContent());
            return comment;
        }
    }
}
