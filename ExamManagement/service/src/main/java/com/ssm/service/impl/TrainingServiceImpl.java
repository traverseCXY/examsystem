package com.ssm.service.impl;

import com.ssm.dao.TrainingMapper;
import com.ssm.entity.Training;
import com.ssm.entity.TrainingSection;
import com.ssm.entity.TrainingSectionProcess;
import com.ssm.entity.UserTraining;
import com.ssm.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingMapper trainingMapper;

    @Override
    public List<Training> getTrainingList(int pageNum, int pageSize, int userId) {
        return trainingMapper.getTrainingList(pageNum, pageSize, userId);
    }

    @Override
    public void addTraining(Training training) {
        trainingMapper.addTraining(training);
    }

    @Override
    public List<UserTraining> getUserTrainingList(int trainingId, int userId, String searchStr) {
        return trainingMapper.getUserTrainingList(trainingId, userId, searchStr);
    }

    @Override
    public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByTrainingId(int trainingId, List<Integer> idList, String searchStr) {
        if(idList.size() == 0)
            idList = null;
        List<TrainingSectionProcess> processList = trainingMapper.getTrainingSectionProcessListByTrainingId(trainingId, idList,searchStr);
        HashMap<Integer,List<TrainingSectionProcess>> map = new HashMap<Integer,List<TrainingSectionProcess>>();
        for(TrainingSectionProcess process : processList){
            List<TrainingSectionProcess> tmpList = new ArrayList<TrainingSectionProcess>();
            if(map.containsKey(process.getUserId()))
                tmpList = map.get(process.getUserId());
            tmpList.add(process);
            map.put(process.getUserId(), tmpList);
        }
        return map;
    }

    @Override
    public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, int userId) {
        return trainingMapper.getTrainingSectionByTrainingId(trainingId, userId);
    }
}
