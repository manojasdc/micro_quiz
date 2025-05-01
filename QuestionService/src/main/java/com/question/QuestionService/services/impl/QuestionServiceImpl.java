package com.question.QuestionService.services.impl;

import com.question.QuestionService.entities.Question;
import com.question.QuestionService.exceptions.ResourceNotFoundException;
import com.question.QuestionService.repositories.QuestionRepository;
import com.question.QuestionService.services.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Creates a new question
     * @param question the question to create
     * @return the created question
     * @throws IllegalArgumentException if question is null
     */
    @Override
    public Question create(final Question question) {
        Assert.notNull(question, "Question cannot be null");
        return questionRepository.save(question);
    }

    /**
     * Retrieves all questions
     * @return list of all questions
     */
    @Override
    public List<Question> get() {
        return questionRepository.findAll();
    }

    /**
     * Retrieves a specific question by ID
     * @param id the question ID
     * @return the found question
     * @throws ResourceNotFoundException if question is not found
     */
    @Override
    public Question getOne(final Long id) {
        Assert.notNull(id, "Question ID cannot be null");
        return questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
    }

    /**
     * Retrieves all questions for a specific quiz
     * @param quizId the quiz ID
     * @return list of questions belonging to the quiz
     */
    @Override
    public List<Question> getQuestionsOfQuiz(final Long quizId) {
        Assert.notNull(quizId, "Quiz ID cannot be null");
        return questionRepository.findByQuizId(quizId);
    }

    /**
     * Deletes a question by ID
     * @param id the question ID to delete
     * @throws ResourceNotFoundException if question is not found
     */
    @Override
    public void delete(final Long id) {
        Assert.notNull(id, "Question ID cannot be null");
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with ID: " + id);
        }
        questionRepository.deleteById(id);
    }
}
