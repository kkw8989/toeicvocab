//package com.toeicvocab.service;
//
//import com.toeicvocab.domain.Word;
//import com.toeicvocab.domain.Wordbook;
//import com.toeicvocab.dto.request.WordRequest;
//import com.toeicvocab.dto.response.WordResponse;
//import com.toeicvocab.repository.WordRepository;
//import com.toeicvocab.repository.WordbookRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class WordServiceTest {
//
//    @Mock
//    private WordRepository wordRepository;
//
//    @Mock
//    private WordbookRepository wordbookRepository;
//
//    @InjectMocks
//    private WordService wordService;
//
//    private WordRequest wordRequest;
//    private Word word;
//    private Wordbook wordbook;
//
//    @BeforeEach
//    void setUp() {
//        // 단어장 설정
//        wordbook = Wordbook.builder()
//                .id(1L)
//                .title("TOEIC 초급 단어장")
//                .description("TOEIC 시험 대비 초급 단어장")
//                .build();
//
//        // 단어 요청 데이터 설정
//        wordRequest = new WordRequest();
//        wordRequest.setWord("apple");
//        wordRequest.setMeaning("사과");
//        wordRequest.setExample("I eat an apple every day.");
//        wordRequest.setDifficultyLevel(Word.DifficultyLevel.EASY);
//        wordRequest.setWordbookId(1L);
//
//        // 단어 엔티티 설정
//        word = Word.builder()
//                .id(1L)
//                .word("apple")
//                .meaning("사과")
//                .example("I eat an apple every day.")
//                .difficultyLevel(Word.DifficultyLevel.EASY)
//                .wordbook(wordbook)
//                .build();
//    }
//
//    @Test
//    void createWord_Success() {
//        // Given
//        when(wordbookRepository.findById(anyLong())).thenReturn(Optional.of(wordbook));
//        when(wordRepository.save(any(Word.class))).thenReturn(word);
//
//        // When
//        WordResponse result = wordService.createWord(wordRequest);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("apple", result.getWord());
//        assertEquals("사과", result.getMeaning());
//        assertEquals("I eat an apple every day.", result.getExample());
//        assertEquals(Word.DifficultyLevel.EASY, result.getDifficultyLevel());
//        assertEquals(1L, result.getWordbookId());
//
//        verify(wordbookRepository).findById(1L);
//        verify(wordRepository).save(any(Word.class));
//    }
//
//    @Test
//    void createWord_WordbookNotFound() {
//        // Given
//        when(wordbookRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // When & Then
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            wordService.createWord(wordRequest);
//        });
//
//        assertEquals("단어장을 찾을 수 없습니다: 1", exception.getMessage());
//        verify(wordbookRepository).findById(1L);
//        verify(wordRepository, never()).save(any(Word.class));
//    }
//
//    @Test
//    void updateWord_Success() {
//        // Given
//        when(wordRepository.findById(anyLong())).thenReturn(Optional.of(word));
//        when(wordbookRepository.findById(anyLong())).thenReturn(Optional.of(wordbook));
//        when(wordRepository.save(any(Word.class))).thenReturn(word);
//
//        // 수정할 단어 데이터 설정
//        WordRequest updateRequest = new WordRequest();
//        updateRequest.setWord("banana");
//        updateRequest.setMeaning("바나나");
//        updateRequest.setExample("Monkeys like to eat bananas.");
//        updateRequest.setDifficultyLevel(Word.DifficultyLevel.MEDIUM);
//        updateRequest.setWordbookId(1L);
//
//        // When
//        WordResponse result = wordService.updateWord(1L, updateRequest);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("banana", result.getWord());
//        assertEquals("바나나", result.getMeaning());
//        assertEquals("Monkeys like to eat bananas.", result.getExample());
//        assertEquals(Word.DifficultyLevel.MEDIUM, result.getDifficultyLevel());
//        assertEquals(1L, result.getWordbookId());
//
//        verify(wordRepository).findById(1L);
//        verify(wordbookRepository).findById(1L);
//        verify(wordRepository).save(any(Word.class));
//    }
//
//    @Test
//    void getWordsByWordbook_Success() {
//        // Given
//        when(wordbookRepository.findById(anyLong())).thenReturn(Optional.of(wordbook));
//        when(wordRepository.findByWordbook(any(Wordbook.class))).thenReturn(Arrays.asList(word));
//
//        // When
//        List<WordResponse> result = wordService.getWordsByWordbook(1L);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("apple", result.get(0).getWord());
//        assertEquals("사과", result.get(0).getMeaning());
//
//        verify(wordbookRepository).findById(1L);
//        verify(wordRepository).findByWordbook(wordbook);
//    }
//
//    @Test
//    void deleteWord_Success() {
//        // Given
//        when(wordRepository.findById(anyLong())).thenReturn(Optional.of(word));
//
//        // When
//        wordService.deleteWord(1L);
//
//        // Then
//        verify(wordRepository).findById(1L);
//        verify(wordRepository).delete(word);
//    }
//}
