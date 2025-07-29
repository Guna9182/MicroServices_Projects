package com.telusko.quizapp.controller;

import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuestionDto;
import com.telusko.quizapp.model.Quiz;
import com.telusko.quizapp.model.Response;
import com.telusko.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizServ;

    @GetMapping("create")
    public ResponseEntity<String> createQuizQuestions(@RequestParam String category, @RequestParam int numOfQues, @RequestParam String title){
        return quizServ.createQuizQuestions(category, numOfQues, title);
    }

    @GetMapping("attendQuiz/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizById(@PathVariable int id){
        return quizServ.getQuizById(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizServ.correctAnswersCount(id, responses);
    }

}
