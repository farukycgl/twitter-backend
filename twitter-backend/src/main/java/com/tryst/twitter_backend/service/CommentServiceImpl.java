package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.Comment;
import com.tryst.twitter_backend.entity.Tweet;
import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.exceptions.CommentDeletionAuthorizationException;
import com.tryst.twitter_backend.exceptions.CommentNotFoundException;
import com.tryst.twitter_backend.exceptions.TweetNotFoundException;
import com.tryst.twitter_backend.exceptions.UserNotFoundException;
import com.tryst.twitter_backend.repository.CommentRepository;
import com.tryst.twitter_backend.repository.TweetRepository;
import com.tryst.twitter_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final TweetRepository tweetRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public Comment create(Long tweetId, Comment comment, Long userId) {

        Tweet tweet = tweetRepository.findById(userId)
                .orElseThrow(()-> new TweetNotFoundException(userId + " id'li tweet bulunamadı."));

        User user = userRepository.findById(tweetId)
                .orElseThrow(()-> new UserNotFoundException(userId + " id'li kullanıcı bulunamadı."));

        comment.setTweet(tweet);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setIsDeleted(false);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, Comment comment) {

        Comment commentToUpdate = commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotFoundException(id + " id'li yorum bulunamadı."));

        if(comment.getContent() != null)
            commentToUpdate.setContent(comment.getContent());

        return commentRepository.save(commentToUpdate);
    }

    @Override
    public void delete(Long id, Long userId) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotFoundException(id + " id'li yorum bulunamadı"));

        Long tweetOwnerId = comment.getTweet().getUser().getId();
        Long commentOwnerId = comment.getUser().getId();

        //kullanıcı id si bu iki kişiden birimi?
        if(!userId.equals(tweetOwnerId) && !userId.equals(commentOwnerId)){
            throw new CommentDeletionAuthorizationException("Yorumu silmek için yetkiniz yok!");
        }

        comment.setIsDeleted(true);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
