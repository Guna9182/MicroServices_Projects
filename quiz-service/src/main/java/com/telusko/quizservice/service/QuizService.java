package com.telusko.quizservice.service;

import com.telusko.quizservice.exception.DataNotFound;
import com.telusko.quizservice.fegin.QuizInterface;
import com.telusko.quizservice.model.QuestionDto;
import com.telusko.quizservice.model.Quiz;
import com.telusko.quizservice.model.Response;
import com.telusko.quizservice.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuizQuestions(String category, int numOfQues, String title) {
         List<Integer> questionIds = quizInterface.generateQuestionsForQuiz(category, numOfQues).getBody();
         Quiz quiz = new Quiz();
         quiz.setTitle(title);
         quiz.setQuestion(questionIds);

         quizRepo.save(quiz);

        return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDto>> getQuizById(int id) {

             List<Integer> quesIds = quizRepo.findById(id).get().getQuestionIds();

             ResponseEntity<List<QuestionDto>> questionsForUsers = quizInterface.getQuestionsOfGeneratedQuiz(quesIds);

              return questionsForUsers;
    }

    public ResponseEntity<Integer> correctAnswersCount(Integer id, List<Response> responses) {

        quizRepo.findById(id).orElseThrow(() -> new DataNotFound("Quiz not found with id : " + id));

        if(quizRepo.findById(id).get().getId().equals(id)) {
            ResponseEntity<Integer> correctAnswerCount = quizInterface.getCorrectAnsCount(responses);
            return correctAnswerCount;
        }
        return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
    }
}
