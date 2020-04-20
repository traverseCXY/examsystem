package com.ssm.service;

import com.ssm.entity.News;

import java.util.List;

public interface NewService {

    List<News> getNewsList(int pageNum,int pageSize);
}
