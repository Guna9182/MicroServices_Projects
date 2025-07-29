package com.telusko.question_service.controller;

import com.telusko.question_service.model.Question;
import com.telusko.question_service.model.QuestionDto;
import com.telusko.question_service.model.Response;
import com.telusko.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionSer;

    @Autowired
    private Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){

        return questionSer.getAllQuestions();
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
       return questionSer.addQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionSer.deleteQuestion(id);
    }

    @GetMapping("category/{categoryName}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable String categoryName){
        return questionSer.getByCategory(categoryName);
    }

    //createQuiz
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer noOfQues){
        return questionSer.createQuestionsForQuiz(category, noOfQues);
    }

    //getQuestionsOfCreatedQuiz
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsOfGeneratedQuiz(@RequestBody List<Integer> questionsId){
        System.out.println(environment.getProperty("local.server.port"));
       return questionSer.getQuestionsFromDB(questionsId);
    }

    //getCorrectAnsCount
    @PostMapping("getScore")
    public ResponseEntity<Integer> getCorrectAnsCount(@RequestBody List<Response> responses){

        return questionSer.getCorrectAnsCountFromDB(responses);
    }

}
