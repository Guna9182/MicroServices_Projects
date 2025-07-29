package com.telusko.quizservice.fegin;

import com.telusko.quizservice.model.QuestionDto;
import com.telusko.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    //createQuiz
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer noOfQues);

    //getQuestionsOfCreatedQuiz
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsOfGeneratedQuiz(@RequestBody List<Integer> questionsId);


    //getCorrectAnsCount
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getCorrectAnsCount(@RequestBody List<Response> responses);

}
