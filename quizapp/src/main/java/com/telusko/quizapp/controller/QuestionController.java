package com.telusko.quizapp.controller;

import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.repo.QuestionRepo;
import com.telusko.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionSer;


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
}
