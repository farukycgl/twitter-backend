package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.dto.CommentResponseDto;
import com.tryst.twitter_backend.dto.JustCommentResponseDto;
import com.tryst.twitter_backend.dto.TweetResponseDto;
import com.tryst.twitter_backend.dto.UserResponseDto;
import com.tryst.twitter_backend.entity.Comment;
import com.tryst.twitter_backend.service.CommentService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/tweets/{tweetId}/comments")
public class CommentController {

    @Autowired
    private final CommentService commentService;


    @GetMapping
    public List<JustCommentResponseDto> getAllCommentByTweetId() {

        return commentService.findAllComment()
                .stream()
                .map(comment -> new JustCommentResponseDto(comment.getContent()))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(@PathVariable Long tweetId,
                                         @Validated @RequestBody Comment comment) {

        Comment createdComment = commentService.create(tweetId, comment);

        return new CommentResponseDto(comment.getContent(),
                new TweetResponseDto(tweetId, createdComment.getContent(),
                        new UserResponseDto(createdComment.getTweet().getUser().getId(), createdComment.getTweet().getUser().getFullName(), createdComment.getTweet().getUser().getEmail())),
                new UserResponseDto(createdComment.getUser().getId(), createdComment.getUser().getFullName(), createdComment.getUser().getEmail()));
    }

    @PutMapping("/{id}")
    public JustCommentResponseDto updateComment(@Positive @PathVariable Long id,
                                                @Validated @RequestBody Comment comment) {

        Comment updatedComment = commentService.update(id, comment);

        return new JustCommentResponseDto(updatedComment.getContent());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@Positive @PathVariable Long id) {
        commentService.delete(id);
    }


}
