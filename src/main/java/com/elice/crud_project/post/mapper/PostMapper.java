package com.elice.crud_project.post.mapper;

import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.Entity.PostPostDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public class PostMapper {
    public Post postPostDTOToPost(PostPostDto postPostDto) {
        if (postPostDto == null) {
            return null;
        } else {
            Post post = new Post();
            post.setPostTitle(postPostDto.getPostTitle());
            post.setPostContent(postPostDto.getPostContent());
            return post;
        }
    }
}
