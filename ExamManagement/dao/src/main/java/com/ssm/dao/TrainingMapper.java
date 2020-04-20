package com.ssm.dao;

import com.ssm.entity.Training;
import com.ssm.entity.TrainingSection;
import com.ssm.entity.TrainingSectionProcess;
import com.ssm.entity.UserTraining;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 培训dao层
 */
public interface TrainingMapper {

    /**
     * 获取培训列表
     * @param userId 为-1时，获取所有培训，否则获取userId发布的培训
     * @return
     */
    public List<Training> getTrainingList(@Param("pageNum") int pageNum,@Param("pageSize") int pageSize, int userId);

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
    public List<UserTraining> getUserTrainingList(@Param("trainingId") int trainingId, @Param("userId") int userId, @Param("searchStr") String searchStr);

    /**
     * 获取培训id下每个用户的章节培训进度
     * @param trainingId
     * @param idList 学员id
     * @param searchStr
     * @return
     */
    public List<TrainingSectionProcess> getTrainingSectionProcessListByTrainingId(@Param("trainingId") int trainingId, @Param("array") List<Integer> idList, @Param("searchStr") String searchStr);

    /**
     * 获取培训章节
     * @param trainingId
     * @param userId 不等于-1则为该用户发布的培训
     * @return
     */
    public List<TrainingSection> getTrainingSectionByTrainingId(@Param("trainingId") int trainingId, @Param("userId") int userId);

}
