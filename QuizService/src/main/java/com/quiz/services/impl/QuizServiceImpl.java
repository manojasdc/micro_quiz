package com.quiz.services.impl;

import com.quiz.entities.Quiz;
import com.quiz.exceptions.QuizNotFoundException;
import com.quiz.repositories.QuizRepository;
import com.quiz.services.QuestionClient;
import com.quiz.services.QuizService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionClient questionClient;

    public QuizServiceImpl(QuizRepository quizRepository, QuestionClient questionClient) {
        this.quizRepository = quizRepository;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz add(Quiz quiz) {
        Assert.notNull(quiz, "Quiz cannot be null");
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        return quizRepository.findAll().stream()
                .map(this::enrichQuizWithQuestions)
                .collect(Collectors.toList());
    }

    @Override
    public Quiz get(Long id) {
        Assert.notNull(id, "Quiz ID cannot be null");
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with ID: " + id));
        return enrichQuizWithQuestions(quiz);
    }

    @Override
    public void remove(Long id) {
        Assert.notNull(id, "Quiz ID cannot be null");
        if (!quizRepository.existsById(id)) {
            throw new QuizNotFoundException("Quiz not found with ID: " + id);
        }
        quizRepository.deleteById(id);
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        Assert.notNull(id, "Quiz ID cannot be null");
        Assert.notNull(quiz, "Quiz cannot be null");
        
        Quiz existingQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with ID: " + id));
        
        existingQuiz.setTitle(quiz.getTitle());
        existingQuiz.setQuestions(quiz.getQuestions());
        return quizRepository.save(existingQuiz);
    }

    private Quiz enrichQuizWithQuestions(Quiz quiz) {
        quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
        return quiz;
    }
}
