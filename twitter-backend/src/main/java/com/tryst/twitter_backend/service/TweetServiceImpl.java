package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.dto.TweetRequestDto;
import com.tryst.twitter_backend.dto.TweetResponseDto;
import com.tryst.twitter_backend.dto.UserResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Service
public class TweetServiceImpl implements TweetService{

    @Autowired
    private final TweetRepository tweetRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public TweetResponseDto create(TweetRequestDto tweetRequestDto, User authUser) {

        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new TweetOwnerException("Geçersiz kullanıcı!"));

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequestDto.getContent());
        tweet.setUser(user);

        Tweet savedTweet = tweetRepository.save(tweet);

        UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getFullName(), user.getEmail());
        return new TweetResponseDto(savedTweet.getId(), savedTweet.getContent(), userResponseDto);
    }

    @Override
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
    public TweetResponseDto update(Long id, TweetRequestDto tweetRequestDto, User authUser) {

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(()-> new TweetNotFoundException(id + " id'li tweet bulunamadı."));

        if (!tweet.getUser().getId().equals(authUser.getId())) {
            throw new TweetOwnerException("Bu tweeti güncelleme yetkiniz yok!");
        }

        tweet.setContent(tweetRequestDto.getContent());
        Tweet updatedTweet = tweetRepository.save(tweet);

        return new TweetResponseDto(updatedTweet.getId(), updatedTweet.getContent(),
                new UserResponseDto(tweet.getUser().getId(), tweet.getUser().getFullName(), tweet.getUser().getEmail()));
    }

    @Override
    public void delete(Long id, User authUser) {

        Tweet existingTweet = tweetRepository
                .findById(id)
                .orElseThrow(()-> new TweetNotFoundException(id + " id'li tweet bulunamadı."));

        if (!existingTweet.getUser().getId().equals(authUser.getId())) {
            throw new TweetOwnerException("Bu tweeti silme yetkiniz yok!");
        }

        tweetRepository.deleteById(id);
    }
}
