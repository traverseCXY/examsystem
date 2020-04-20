package com.exam.scoremarker;

import java.util.HashMap;
import com.exam.scoremarker.entity.AnswerSheet;
import com.exam.scoremarker.entity.AnswerSheetItem;
import com.exam.scoremarker.entity.ExamPaper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

/**
 * 消费者实体类和发送数据到数据库
 */
@Service
@Scope("prototype")
public class ScoreCalcuService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("answerSheetPostUri")
    private String answerSheetPostUri;

    @Autowired
    @Qualifier("examPaperGetUri")
    private String examPaperGetUri;

    @Autowired
    HashMap<String, ExamPaper> Examapapers;

    private static final Logger LOGGER = Logger.getLogger(ScoreCalcuService.class);

    /**/
    public void calcuScore(AnswerSheet as) {
        System.out.println("answerSheet is calculating..." + as);

        ExamPaper examPaper = Examapapers.get(as.getExamPaperId());

        // TODO 计算得分 结果等数据..
        if (examPaper == null) {
            examPaper = this.getExamPaper(as.getExamPaperId());
            Examapapers.put(examPaper.getId() + "", examPaper);
        }
        Gson gson = new Gson();
        AnswerSheet target = gson.fromJson(examPaper.getAnswerSheet(),AnswerSheet.class);
        HashMap<Integer, AnswerSheetItem> answerMap = new HashMap<Integer, AnswerSheetItem>();
        for(AnswerSheetItem item : target.getAnswerSheetItems()){
            answerMap.put(item.getQuestionId(), item);
        }
        as.setPointMax(target.getPointMax());
        for(AnswerSheetItem item : as.getAnswerSheetItems()){
            if(item.getAnswer().equals(answerMap.get(item.getQuestionId()).getAnswer())){
                as.setPointRaw(as.getPointRaw() + answerMap.get(item.getQuestionId()).getPoint());
                item.setPoint(answerMap.get(item.getQuestionId()).getPoint());
                item.setRight(true);
            }
        }
        this.postAnswerSheet(answerSheetPostUri, as);
        System.out.println("answerSheet has been post to" + answerSheetPostUri);
    }

    private void postAnswerSheet(String uri, Object body) {
        try {
            restTemplate.postForLocation(uri, body);
        } catch (RestClientException e) {
            LOGGER.error("Received exception:", e);
        }
    }

    private ExamPaper getExamPaper(int examaperId) {
        ExamPaper examPaper = null;
        System.out.println("try to fetch exampaper");
        String uri = examPaperGetUri + "/" + examaperId;
        examPaper = restTemplate.getForObject(uri,ExamPaper.class);
        if(examPaper == null){
            LOGGER.error("Get Exampaper exception: The specical Examaper is not existed.");
            System.out.println("Get Exampaper exception: The specical Examaper is not existed.");
        }
        return examPaper;
    }

}
