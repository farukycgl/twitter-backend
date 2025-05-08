package com.tryst.twitter_backend.controller;


import com.tryst.twitter_backend.entity.Comment;
import com.tryst.twitter_backend.service.CommentService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/tryst/twitter/api/tweet/{tweetId}/comment")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@PathVariable Long tweetId,
                              @Validated @RequestBody Comment comment,
                              @RequestParam Long userId){

        return commentService.create(tweetId, comment, userId);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@Positive @PathVariable Long id,
                                 @Validated @RequestBody Comment comment){

        return commentService.update(id, comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@Positive @PathVariable Long id,
                              @RequestParam Long userId){

        commentService.delete(id, userId);
    }



}
