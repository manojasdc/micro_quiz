package com.quiz.controllers;

import com.quiz.entities.Quiz;
import com.quiz.exceptions.QuizNotFoundException;
import com.quiz.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(final QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<Quiz> create(@RequestBody final Quiz quiz) {
        return ResponseEntity.ok(quizService.add(quiz));
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAll() {
        return ResponseEntity.ok(quizService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getOne(@PathVariable final Long id) {
        try {
            return ResponseEntity.ok(quizService.get(id));
        } catch (QuizNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        try {
            quizService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (QuizNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> update(@PathVariable final Long id, @RequestBody final Quiz quiz) {
        try {
            return ResponseEntity.ok(quizService.update(id, quiz));
        } catch (QuizNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
