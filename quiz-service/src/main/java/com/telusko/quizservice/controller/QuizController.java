package com.telusko.quizservice.controller;

import com.telusko.quizservice.model.QuestionDto;
import com.telusko.quizservice.model.Response;
import com.telusko.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizServ;

//    @Autowired
//    private RestTemplate restTemplate;

    //to check server port of an instance service
//    @Autowired
//    Environment environment;

    @GetMapping("create")
    public ResponseEntity<String> createQuizQuestions(@RequestParam String category, @RequestParam Integer noOfQues, @RequestParam String title){
//        System.out.println(environment.getProperty("local.server.port"));
        return quizServ.createQuizQuestions(category, noOfQues, title);
    }

    @GetMapping("createdQuiz/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizById(@PathVariable int id){
        // this is doing by rest template, Alternative of Feign Client
//        QuestionDto dto = restTemplate.getForObject("http://localhost:8080/question/get/{id}", QuestionDto.class);

        return quizServ.getQuizById(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizServ.correctAnswersCount(id, responses);
    }

}
