//package com.toeicvocab.service;
//
//import com.toeicvocab.domain.TestResult;
//import com.toeicvocab.domain.User;
//import com.toeicvocab.domain.Word;
//import com.toeicvocab.domain.Wordbook;
//import com.toeicvocab.dto.response.TestResultResponse;
//import com.toeicvocab.repository.TestResultRepository;
//import com.toeicvocab.repository.UserRepository;
//import com.toeicvocab.repository.WordRepository;
//import com.toeicvocab.repository.WordbookRepository;
//import com.toeicvocab.util.TestScoreCalculator;
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
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class TestServiceTest {
//
//    @Mock
//    private TestResultRepository testResultRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private WordbookRepository wordbookRepository;
//
//    @Mock
//    private WordRepository wordRepository;
//
//    @Mock
//    private TestScoreCalculator testScoreCalculator;
//
//    @InjectMocks
//    private TestService testService;
//
//    private User user;
//    private Wordbook wordbook;
//    private TestResult testResult;
//    private List<Word> wordList;
//
//    @BeforeEach
//    void setUp() {
//        // 사용자 설정
//        user = User.builder()
//                .id(1L)
//                .username("testuser")
//                .email("test@example.com")
//                .password("encodedPassword")
//                .build();
//
//        // 단어장 설정
//        wordbook = Wordbook.builder()
//                .id(1L)
//                .title("TOEIC 초급 단어장")
//                .description("TOEIC 시험 대비 초급 단어장")
//                .build();
//
//        // 테스트 결과 설정
//        testResult = TestResult.builder()
//                .id(1L)
//                .user(user)
//                .wordbook(wordbook)
//                .correctCount(25)
//                .totalCount(30)
//                .score(800)
//                .build();
//
//        // 테스트할 단어 목록 설정
//        Word word1 = Word.builder()
//                .id(1L)
//                .word("apple")
//                .meaning("사과")
//                .difficultyLevel(Word.DifficultyLevel.EASY)
//                .wordbook(wordbook)
//                .build();
//
//        Word word2 = Word.builder()
//                .id(2L)
//                .word("banana")
//                .meaning("바나나")
//                .difficultyLevel(Word.DifficultyLevel.EASY)
//                .wordbook(wordbook)
//                .build();
//
//        wordList = Arrays.asList(word1, word2);
//    }
//
//    @Test
//    void saveTestResult_Success() {
//        // Given
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
//        when(wordbookRepository.findById(anyLong())).thenReturn(Optional.of(wordbook));
//        when(testScoreCalculator.calculateScore(anyInt())).thenReturn(800);
//        when(testResultRepository.save(any(TestResult.class))).thenReturn(testResult);
//
//        // When
//        TestResultResponse result = testService.saveTestResult("testuser", 1L, 25, 30);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals(25, result.getCorrectCount());
//        assertEquals(30, result.getTotalCount());
//        assertEquals(800, result.getScore());
//        assertEquals("testuser", result.getUsername());
//        assertEquals("TOEIC 초급 단어장", result.getWordbookTitle());
//
//        verify(userRepository).findByUsername("testuser");
//        verify(wordbookRepository).findById(1L);
//        verify(testScoreCalculator).calculateScore(25);
//        verify(testResultRepository).save(any(TestResult.class));
//    }
//
//    @Test
//    void getUserTestResults_Success() {
//        // Given
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
//        when(testResultRepository.findByUserOrderByTestDateDesc(any(User.class)))
//                .thenReturn(Arrays.asList(testResult));
//
//        // When
//        List<TestResultResponse> results = testService.getUserTestResults("testuser");
//
//        // Then
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals(25, results.get(0).getCorrectCount());
//        assertEquals(30, results.get(0).getTotalCount());
//        assertEquals(800, results.get(0).getScore());
//
//        verify(userRepository).findByUsername("testuser");
//        verify(testResultRepository).findByUserOrderByTestDateDesc(user);
//    }
//
//    @Test
//    void getWordsForTest_Success() {
//        // Given
//        when(wordbookRepository.findById(anyLong())).thenReturn(Optional.of(wordbook));
//        when(wordRepository.findByWordbook(any(Wordbook.class))).thenReturn(wordList);
//
//        // When
//        List<Word> result = testService.getWordsForTest(1L);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("apple", result.get(0).getWord());
//        assertEquals("banana", result.get(1).getWord());
//
//        verify(wordbookRepository).findById(1L);
//        verify(wordRepository).findByWordbook(wordbook);
//    }
//}