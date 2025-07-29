package com.telusko.quizapp.repo;

import com.telusko.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String categoryName);

    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER By RANDOM() LIMIT :numOfQues", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQues);
}
