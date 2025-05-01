package com.question.QuestionService.controllers;

import com.question.QuestionService.entities.Question;
import com.question.QuestionService.services.QuestionService;
import com.question.QuestionService.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Creates a new question
     */
    @PostMapping
    public Question createQuestion(@RequestBody final Question question) {
        return questionService.create(question);
    }

    /**
     * Retrieves all questions
     */
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.get();
    }

    /**
     * Retrieves a specific question by ID
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable final Long questionId) {
        try {
            Question question = questionService.getOne(questionId);
            return ResponseEntity.ok(question);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all questions for a specific quiz
     */
    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsOfQuiz(@PathVariable final Long quizId) {
        return questionService.getQuestionsOfQuiz(quizId);
    }

    /**
     * Deletes a question by ID
     */
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable final Long questionId) {
        try {
            questionService.delete(questionId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
