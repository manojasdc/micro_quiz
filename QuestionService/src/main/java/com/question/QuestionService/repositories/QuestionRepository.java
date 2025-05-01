package com.question.QuestionService.repositories;

import com.question.QuestionService.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByQuizId(Long quizId);
}
// This is a repository interface for the Question entity.
// It extends JpaRepository to provide CRUD operations and a custom method to find questions by quizId.
// The JpaRepository interface takes two parameters: the entity type (Question) and the primary key type (Long).
// The custom method findByQuizId(Long quizId) retrieves a list of questions associated with a specific quiz ID.