package com.ssm.dao;

import com.ssm.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*公告dao层*/
public interface NewsMapper {

    List<News> getNewsList(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize);
}
