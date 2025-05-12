package com.toeicvocab.service;

import com.toeicvocab.domain.Word;
import com.toeicvocab.domain.Wordbook;
import com.toeicvocab.dto.request.WordRequest;
import com.toeicvocab.dto.response.WordResponse;
import com.toeicvocab.repository.WordRepository;
import com.toeicvocab.repository.WordbookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WordServiceTest {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordbookRepository wordbookRepository;

    @Autowired
    private WordService wordService;

    private WordRequest wordRequest;
    private Wordbook wordbook;
    private Long wordbookId;

    @BeforeEach
    void setUp() {
        // 실제 데이터베이스에 단어장 저장
        wordbook = Wordbook.builder()
                .title("TOEIC 초급 단어장")
                .description("TOEIC 시험 대비 초급 단어장")
                .build();

        // 저장 후 자동 생성된 ID 활용
        wordbook = wordbookRepository.save(wordbook);
        wordbookId = wordbook.getId();

        // 단어 요청 데이터 설정
        wordRequest = new WordRequest();
        wordRequest.setWord("apple");
        wordRequest.setMeaning("사과");
        wordRequest.setExample("I eat an apple every day.");
        wordRequest.setDifficultyLevel(Word.DifficultyLevel.EASY);
        wordRequest.setWordbookId(wordbookId);
    }

    @Test
    void createWord_Success() {
        // When
        WordResponse result = wordService.createWord(wordRequest);

        // Then
        assertNotNull(result);
        assertEquals("apple", result.getWord());
        assertEquals("사과", result.getMeaning());
        assertEquals("I eat an apple every day.", result.getExample());
        assertEquals(Word.DifficultyLevel.EASY, result.getDifficultyLevel());
        assertEquals(wordbookId, result.getWordbookId());

        // 실제 데이터베이스에서 조회하여 검증
        List<Word> savedWords = wordRepository.findByWordbook(wordbook);
        assertEquals(1, savedWords.size());
        assertEquals("apple", savedWords.get(0).getWord());
    }

    @Test
    void updateWord_Success() {
        // Given: 먼저 단어 생성
        WordResponse createdWord = wordService.createWord(wordRequest);

        // 수정할 단어 데이터 설정
        WordRequest updateRequest = new WordRequest();
        updateRequest.setWord("banana");
        updateRequest.setMeaning("바나나");
        updateRequest.setExample("Monkeys like to eat bananas.");
        updateRequest.setDifficultyLevel(Word.DifficultyLevel.MEDIUM);
        updateRequest.setWordbookId(wordbookId);

        // When
        WordResponse result = wordService.updateWord(createdWord.getId(), updateRequest);

        // Then
        assertNotNull(result);
        assertEquals("banana", result.getWord());
        assertEquals("바나나", result.getMeaning());
        assertEquals("Monkeys like to eat bananas.", result.getExample());
        assertEquals(Word.DifficultyLevel.MEDIUM, result.getDifficultyLevel());
        assertEquals(wordbookId, result.getWordbookId());

        // 실제 데이터베이스에서 조회하여 검증
        Word updatedWord = wordRepository.findById(createdWord.getId()).orElse(null);
        assertNotNull(updatedWord);
        assertEquals("banana", updatedWord.getWord());
        assertEquals("바나나", updatedWord.getMeaning());
    }

    @Test
    void getWordsByWordbook_Success() {
        // Given: 여러 단어 생성
        wordService.createWord(wordRequest);

        WordRequest word2Request = new WordRequest();
        word2Request.setWord("banana");
        word2Request.setMeaning("바나나");
        word2Request.setExample("I like bananas.");
        word2Request.setDifficultyLevel(Word.DifficultyLevel.EASY);
        word2Request.setWordbookId(wordbookId);
        wordService.createWord(word2Request);

        // When
        List<WordResponse> result = wordService.getWordsByWordbook(wordbookId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        // 데이터베이스 조회 결과 검증
        List<Word> savedWords = wordRepository.findByWordbook(wordbook);
        assertEquals(2, savedWords.size());
    }

//    @Test
//    @Transactional
//    void deleteWord_Success() {
//        // Given: 단어 생성
//        WordResponse createdWord = wordService.createWord(wordRequest);
//        Long wordId = createdWord.getId();
//
//        // 생성 확인
//        assertTrue(wordRepository.existsById(wordId));
//
//        // When
//        wordService.deleteWord(wordId);
//
//        // Then
//        assertFalse(wordRepository.existsById(wordId));
//    }
}