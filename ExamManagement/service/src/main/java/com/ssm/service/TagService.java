package com.ssm.service;

import com.ssm.entity.Tag;

import java.util.List;

public interface TagService {
    public List<Tag> getTags(Integer pageNum, Integer pageSize);

    public void addTag(Tag tag);

    public void deleteTagById(int id);

    List<Tag> getAll();
}
