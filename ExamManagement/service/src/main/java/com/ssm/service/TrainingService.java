package com.ssm.service;

import com.ssm.entity.Training;
import com.ssm.entity.TrainingSection;
import com.ssm.entity.TrainingSectionProcess;
import com.ssm.entity.UserTraining;

import java.util.List;
import java.util.Map;

public interface TrainingService {

    /**
     * 获取培训列表
     * @param userId 为-1时，获取所有培训，否则获取userId发布的培训
     * @return
     */
    public List<Training> getTrainingList(int pageNum,int pageSize,int userId);

    /**
     * 添加培训
     * @param training
     */
    public void addTraining(Training training);

    /**
     * 获取培训id下用户培训清单
     * @param trainingId
     * @param userId
     * @param searchStr
     * @return
     */
    public List<UserTraining> getUserTrainingList(int trainingId, int userId, String searchStr);

    /**
     * 获取培训id下每个用户的章节培训进度
     * @param trainingId
     * @param searchStr
     * @return
     */
    public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByTrainingId(int trainingId, List<Integer> idList, String searchStr);

    /**
     * 获取培训章节
     * @param trainingId
     * @param userId 不等于-1则为该用户发布的培训
     * @return
     */
    public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, int userId);

}
