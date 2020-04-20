package com.ssm.service.impl;

import com.ssm.dao.QuestionMapper;
import com.ssm.entity.Field;
import com.ssm.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void addField(Field field) {
        questionMapper.addField(field);
    }

    @Override
    public List<Field> getFields(int pageNum, int pageSize) {
        return questionMapper.getFields(pageNum,pageSize);
    }

    @Override
    public List<Field> getAll() {
        return questionMapper.getFieldsAll();
    }

    @Override
    public int getFieldCount() {
        return questionMapper.getFieldCount();
    }
}
