package com.ssm.service;

import com.ssm.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentByTypeAndReferId(int referType, int referId, int indexId);

    void addComment(Comment comment);

}
