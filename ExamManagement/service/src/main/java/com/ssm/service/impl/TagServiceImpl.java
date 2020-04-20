package com.ssm.service.impl;

import com.ssm.dao.QuestionMapper;
import com.ssm.entity.Tag;
import com.ssm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public List<Tag> getTags(Integer pageNum, Integer pageSize) {
        return questionMapper.getTags(pageNum, pageSize);
    }

    @Override
    public void addTag(Tag tag) {
        questionMapper.addTag(tag);
    }

    @Override
    public void deleteTagById(int id) {
        questionMapper.deleteTagById(id);
    }

    @Override
    public List<Tag> getAll() {
        return questionMapper.getTagsAll();
    }
}
