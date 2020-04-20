package com.ssm.service.impl;

import com.ssm.dao.CommentMapper;
import com.ssm.entity.Comment;
import com.ssm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentByTypeAndReferId(int referType, int referId, int indexId) {
        return commentMapper.getCommentByTypeAndReferId(referType, referId, indexId);
    }

    @Override
    public void addComment(Comment comment) {
        try{
            Object index = commentMapper.getMaxCommentIndexByTypeAndReferId(comment.getCommentType(), comment.getReferId());
            int i = 0;
            if(index == null)
                i = 0;
            else
                i = (Integer) index;
            comment.setIndexId(i + 1);
            commentMapper.addComment(comment);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
