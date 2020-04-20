package com.ssm.dao;

import com.ssm.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    List<Comment> getCommentByTypeAndReferId(int referType,int referId,int indexId);

    /**
     * 添加评论
     * @param comment
     */
    public void addComment(Comment comment);

    public Integer getMaxCommentIndexByTypeAndReferId(@Param("commentType") int commentType, @Param("referId") int referId);

}
