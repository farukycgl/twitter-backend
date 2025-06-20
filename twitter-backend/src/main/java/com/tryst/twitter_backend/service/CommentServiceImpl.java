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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<Comment> findById(Long id){
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment create(Long tweetId, Comment comment) {

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetNotFoundException(tweetId + " id'li tweet bulunamadı."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findUserByEmail(email)
                        .orElseThrow(()-> new UserNotFoundException("Kullanıcı bulunamadı!"));

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
    public void delete(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotFoundException(id + " id'li yorum bulunamadı"));

        Long tweetOwnerId = comment.getTweet().getUser().getId();
        Long commentOwnerId = comment.getUser().getId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("Kullanıcı bulunamadı!"));

        Long currentUserId = user.getId();

        //kullanıcı id si bu iki kişiden birimi?
        if(!currentUserId.equals(tweetOwnerId) && !currentUserId.equals(commentOwnerId)){
            throw new CommentDeletionAuthorizationException("Yorumu silmek için yetkiniz yok!");
        }

        comment.setIsDeleted(true);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
