package com.ssm.service.impl;

import com.ssm.dao.NewsMapper;
import com.ssm.entity.News;
import com.ssm.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getNewsList(int pageNum, int pageSize) {
        return newsMapper.getNewsList(pageNum, pageSize);
    }
}
