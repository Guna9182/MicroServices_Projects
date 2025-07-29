package com.telusko.quizapp.service;

import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuestionDto;
import com.telusko.quizapp.model.Quiz;
import com.telusko.quizapp.model.Response;
import com.telusko.quizapp.repo.QuestionRepo;
import com.telusko.quizapp.repo.QuizRepo;
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
    private QuestionRepo questionRepo;

    public ResponseEntity<String> createQuizQuestions(String category, int numOfQues, String title) {

        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numOfQues);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);

        quizRepo.save(quiz);

        return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDto>> getQuizById(int id) {

              Optional<Quiz> quiz =  quizRepo.findById(id);

              List<Question> questionsFromDB = quiz.get().getQuestion();

              List<QuestionDto> questionsForUsers = new ArrayList<>();

              for(Question q : questionsFromDB) {
                  QuestionDto questionDto = new QuestionDto(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());

                  questionsForUsers.add(questionDto);
              }

              return new ResponseEntity<>(questionsForUsers, HttpStatus.FOUND);
    }

    public ResponseEntity<Integer> correctAnswersCount(int id, List<Response> responses) {

        Quiz quiz = quizRepo.findById(id).get();

        List<Question> questions = quiz.getQuestion();

        int correctAnswerCount = 0;

        int i = 0;
        for (Response response : responses){
            if(response.getSubmittedAns().equals(questions.get(i).getRightAnswer()))
                correctAnswerCount++;
            i++;
//            System.out.println(correctAnswerCount);
        }
        return new ResponseEntity<>(correctAnswerCount, HttpStatus.ACCEPTED);
    }
}
