package com.telusko.question_service.service;

import com.telusko.question_service.exception.DataNotFound;
import com.telusko.question_service.model.Question;
import com.telusko.question_service.model.QuestionDto;
import com.telusko.question_service.model.Response;
import com.telusko.question_service.repo.QuestionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo repo;


    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.FOUND);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            repo.save(question);
            return new ResponseEntity<>("saved..", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Can't save it.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int id) {

//        try {
            Question q = repo.findById(id).orElseThrow(() -> new DataNotFound("No Data found with this id : " + id));
            repo.deleteById(q.getId());
            return new ResponseEntity<>("deleted..", HttpStatus.FOUND);
//        } catch (DataNotFound e){
//            System.out.println(e.getMessage());
//        }
//        catch (Exception ex) {
//            e.printStackTrace();
//            System.out.println(ex.getMessage());
//        }
//        return new ResponseEntity<>("No Data to delete.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Question>> getByCategory(String categoryName) {
        List<Question> foundByCategory = repo.findByCategory(categoryName);

        return new ResponseEntity<>(foundByCategory, HttpStatus.FOUND);
    }

    public ResponseEntity<List<Integer>> createQuestionsForQuiz(String category, Integer noOfQues) {
          List<Integer> numOfQues = repo.findByCategoryAndNoOfQues(category, noOfQues);
        return new ResponseEntity<>(numOfQues, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDto>> getQuestionsFromDB(List<Integer> questionsId) {

        List<Question> questionsList = new ArrayList<>();

        List<QuestionDto> dtoList = new ArrayList<>();

        for(Integer id : questionsId) {
            Question question = repo.findById(id).get();
            questionsList.add(question);
        }

        for(Question q : questionsList){

            QuestionDto dto = new QuestionDto();
            dto.setId(q.getId());
            dto.setQuestionTitle(q.getQuestionTitle());
            dto.setOption1(q.getOption1());
            dto.setOption2(q.getOption2());
            dto.setOption3(q.getOption3());
            dto.setOption4(q.getOption4());

            dtoList.add(dto);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getCorrectAnsCountFromDB(List<Response> responses) {

        int count=0;

        for(Response response : responses) {
            Question question = repo.findById(response.getId()).get();

            if(question.getRightAnswer().equals(response.getSubmittedAns())){
                count++;
            }
        }

        return new ResponseEntity<>(count, HttpStatus.ACCEPTED);
    }
}
