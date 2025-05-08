package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.Tweet;
import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.exceptions.TweetNotFoundException;
import com.tryst.twitter_backend.exceptions.TweetOwnerException;
import com.tryst.twitter_backend.repository.TweetRepository;
import com.tryst.twitter_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Service
public class TweetServiceImpl implements TweetService{

    @Autowired
    private final TweetRepository tweetRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public Tweet create(Tweet tweet) {

        if(tweet.getUser() == null){
            throw new TweetOwnerException("Tweet'in bir kullanıcısı olmalıdır");
        }

        User user = userRepository.findById(tweet.getUser().getId())
                .orElseThrow(()-> new TweetOwnerException("Geçersiz kullanıcı!"));

        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }

    @GetMapping
    public List<Tweet> findAllTweets(){
        return tweetRepository.findAll();
    }

    @Override
    public List<Tweet> findByUserId(Long userId) {

        List<Tweet> tweets = tweetRepository.findByUserId(userId);

        if(tweets.isEmpty()){
            throw new TweetNotFoundException(userId + " id'li kullanıcı bulunamadı.");
        }

        return tweets;
    }

    @Override
    public Tweet findById(Long id) {
        return tweetRepository
                .findById(id)
                .orElseThrow(()-> new TweetNotFoundException(id + " id'li tweet bulunamadı."));
    }

    @Override
    public Tweet update(Long id, Tweet tweet) {

        Tweet tweetToUpdate = tweetRepository.findById(id)
                .orElseThrow(()-> new TweetNotFoundException(id + " id'li tweet bulunamadı."));

        if(tweet.getContent() != null)
            tweetToUpdate.setContent(tweet.getContent());

        return tweetRepository.save(tweetToUpdate);
    }

    @Override
    public void delete(Long id, Long userId) {

        Tweet existingTweet = tweetRepository
                .findById(id)
                        .orElseThrow(()-> new TweetNotFoundException(id + " id'li tweet bulunamadı."));

        if(!existingTweet.getUser().getId().equals(userId)){
            throw new TweetOwnerException("Bu tweeti silme yetkiniz yok.");
        }

        tweetRepository.deleteById(id);
    }
}
