package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.dto.TweetRequestDto;
import com.tryst.twitter_backend.dto.TweetResponseDto;
import com.tryst.twitter_backend.entity.Tweet;
import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.exceptions.TweetNotFoundException;
import com.tryst.twitter_backend.exceptions.TweetOwnerException;
import com.tryst.twitter_backend.repository.TweetRepository;
import com.tryst.twitter_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    private TweetService tweetService;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        tweetService = new TweetServiceImpl(tweetRepository, userRepository);
    }

    @Test
    void create() {

        User authUser = new User();
        authUser.setId(1L);
        authUser.setFullName("Test user");
        authUser.setEmail("user@test.com");

        TweetRequestDto requestDto = new TweetRequestDto("test tweetidir.");
        requestDto.setContent("test tweetidir.");

        Tweet savedTweet = new Tweet();
        savedTweet.setId(2L);
        savedTweet.setContent(requestDto.getContent());
        savedTweet.setUser(authUser);

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(authUser));
        given(tweetRepository.save(any(Tweet.class))).willReturn(savedTweet);

        TweetResponseDto responseDto = tweetService.create(requestDto, authUser);

        assertNotNull(responseDto);
        assertEquals(2L, responseDto.id());
        assertEquals("test tweetidir.", responseDto.content());
        assertNotNull(responseDto.tweetUser());
        assertEquals(authUser.getId(), responseDto.tweetUser().id());
        assertEquals("Test user", responseDto.tweetUser().fullName());
        assertEquals("user@test.com", responseDto.tweetUser().email());

        verify(userRepository).findById(authUser.getId());
        verify(tweetRepository).save(any(Tweet.class));
    }

    @Test
    void canNotCreate(){
        User authUser = new User();
        authUser.setId(1L);

        TweetRequestDto requestDto = new TweetRequestDto("test tweetidir.");

        given(userRepository.findById(authUser.getId())).willReturn(Optional.empty());

        assertThrows(TweetOwnerException.class, ()-> {
            tweetService.create(requestDto, authUser);
        });

        verify(userRepository, times(1)).findById(authUser.getId());
        verify(tweetRepository, never()).save(any());
    }

    @Test
    void findAllTweets() {
        tweetRepository.findAll();
        verify(tweetRepository).findAll();
    }

    @Test
    void findByUserId() {
        User authUser = new User();
        authUser.setId(1L);

        Tweet onceTweet = new Tweet();
        onceTweet.setId(1L);
        onceTweet.setContent("Birinci tweet");

        Tweet secondTweet = new Tweet();
        secondTweet.setId(2L);
        secondTweet.setContent("İkinci tweet");

        List<Tweet> tweetList = List.of(onceTweet, secondTweet);

        given(tweetRepository.findByUserId(authUser.getId())).willReturn(tweetList);

        List<Tweet> result = tweetService.findByUserId(authUser.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Birinci tweet", result.get(0).getContent());
        assertEquals("İkinci tweet", result.get(1).getContent());

        verify(tweetRepository).findByUserId(authUser.getId());
    }

    @Test
    void canNotFindByUserId(){
        User authUser = new User();
        authUser.setId(1L);

        given(tweetRepository.findByUserId(authUser.getId())).willReturn(List.of());

        TweetNotFoundException exception = assertThrows(TweetNotFoundException.class, () -> {
            tweetService.findByUserId(authUser.getId());
        });

        assertEquals(authUser.getId() + " id'li kullanıcı bulunamadı.", exception.getMessage());

        verify(tweetRepository).findByUserId(authUser.getId());

    }

    @Test
    void findById() {
        Long tweetId = 1L;

        User user = new User();
        user.setId(2L);
        user.setFullName("Test user");

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setContent("Test deneme tweeti");
        tweet.setUser(user);

        given(tweetRepository.findById(tweetId)).willReturn(Optional.of(tweet));

        Tweet result = tweetService.findById(tweetId);

        assertNotNull(result);
        assertEquals(tweetId, result.getId());
        assertEquals("Test deneme tweeti", result.getContent());
        assertEquals(user, result.getUser());

        verify(tweetRepository).findById(tweetId);
    }

    @Test
    void canNotFindById(){
        Long tweetId = 1L;

        given(tweetRepository.findById(tweetId)).willReturn(Optional.empty());

        TweetNotFoundException exception = assertThrows(TweetNotFoundException.class, () -> {
            tweetService.findById(tweetId);
        });

        assertEquals(tweetId + " id'li tweet bulunamadı.", exception.getMessage());

        verify(tweetRepository).findById(tweetId);
    }

    @Test
    void update() {
        User authUser = new User();
        authUser.setId(1L);
        authUser.setFullName("Test user");
        authUser.setEmail("user@test.com");

        Tweet existingTweet = new Tweet();
        existingTweet.setId(10L);
        existingTweet.setContent("eski tweet");
        existingTweet.setUser(authUser);

        TweetRequestDto updateRequest = new TweetRequestDto("yeni tweet");

        Tweet updatedTweet = new Tweet();
        updatedTweet.setId(10L);
        updatedTweet.setContent(updateRequest.getContent());
        updatedTweet.setUser(authUser);

        given(tweetRepository.findById(10L)).willReturn(Optional.of(existingTweet));
        given(tweetRepository.save(any(Tweet.class))).willReturn(updatedTweet);

        TweetResponseDto responseDto = tweetService.update(10L, updateRequest, authUser);

        assertNotNull(responseDto);
        assertEquals(10L, responseDto.id());
        assertEquals("yeni tweet", responseDto.content());
        assertEquals(authUser.getId(), responseDto.tweetUser().id());
        assertEquals(authUser.getFullName(), responseDto.tweetUser().fullName());
        assertEquals(authUser.getEmail(), responseDto.tweetUser().email());

        verify(tweetRepository).findById(10L);
        verify(tweetRepository).save(any(Tweet.class));
    }

    @Test
    void whenNotTweetOwner(){
        User authUser = new User();
        authUser.setId(2L);

        User tweetOwner = new User();
        tweetOwner.setId(1L);

        Tweet existingTweet = new Tweet();
        existingTweet.setId(10L);
        existingTweet.setContent("eski tweet");
        existingTweet.setUser(tweetOwner);

        TweetRequestDto updateRequest = new TweetRequestDto("yeni tweet");

        given(tweetRepository.findById(10L)).willReturn(Optional.of(existingTweet));

        assertThrows(TweetOwnerException.class, () -> {
            tweetService.update(10L, updateRequest, authUser);
        });

        verify(tweetRepository).findById(10L);
        verify(tweetRepository, never()).save(any());
    }

    @Test
    void delete() {
        User authUser = new User();
        authUser.setId(1L);

        Tweet existingTweet = new Tweet();
        existingTweet.setId(10L);
        existingTweet.setContent("silinecek tweet");
        existingTweet.setUser(authUser);

        given(tweetRepository.findById(10L)).willReturn(Optional.of(existingTweet));

        tweetService.delete(10L, authUser);

        verify(tweetRepository).findById(10L);
        verify(tweetRepository).deleteById(10L);
    }
}