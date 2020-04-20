package com.ssm.service;

import com.ssm.entity.Field;

import java.util.List;

public interface FieldService {

    void addField(Field field);

    List<Field> getFields(int pageNum,int pageSize);

    List<Field> getAll();

    int getFieldCount();


}
