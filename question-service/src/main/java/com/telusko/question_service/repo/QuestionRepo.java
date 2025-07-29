package com.telusko.question_service.repo;

import com.telusko.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String categoryName);

    //this is for quizService
    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER By RANDOM() LIMIT :numOfQues", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQues);


    @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :noOfQues", nativeQuery = true)
    List<Integer> findByCategoryAndNoOfQues(String category, Integer noOfQues);
}
