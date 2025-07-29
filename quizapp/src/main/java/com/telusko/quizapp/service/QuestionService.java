package com.telusko.quizapp.service;

import com.telusko.quizapp.exception.DataNotFound;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.repo.QuestionRepo;
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
}
