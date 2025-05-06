//package com.toeicvocab.service;
//
//import com.toeicvocab.domain.Post;
//import com.toeicvocab.domain.User;
//import com.toeicvocab.dto.request.PostRequest;
//import com.toeicvocab.dto.response.PostResponse;
//import com.toeicvocab.repository.PostRepository;
//import com.toeicvocab.repository.UserRepository;
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
//public class PostServiceTest {
//
//    @Mock
//    private PostRepository postRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private PostService postService;
//
//    private User user;
//    private PostRequest postRequest;
//    private Post post;
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
//        // 게시글 요청 데이터 설정
//        postRequest = new PostRequest();
//        postRequest.setTitle("테스트 게시글");
//        postRequest.setContent("이것은 테스트 게시글입니다.");
//
//        // 게시글 엔티티 설정
//        post = Post.builder()
//                .id(1L)
//                .title("테스트 게시글")
//                .content("이것은 테스트 게시글입니다.")
//                .user(user)
//                .build();
//    }
//
//    @Test
//    void createPost_Success() {
//        // Given
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
//        when(postRepository.save(any(Post.class))).thenReturn(post);
//
//        // When
//        PostResponse result = postService.createPost("testuser", postRequest);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("테스트 게시글", result.getTitle());
//        assertEquals("이것은 테스트 게시글입니다.", result.getContent());
//        assertEquals("testuser", result.getUsername());
//
//        verify(userRepository).findByUsername("testuser");
//        verify(postRepository).save(any(Post.class));
//    }
//
//    @Test
//    void updatePost_Success() {
//        // Given
//        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
//        when(postRepository.save(any(Post.class))).thenReturn(post);
//
//        // 수정할 게시글 데이터 설정
//        PostRequest updateRequest = new PostRequest();
//        updateRequest.setTitle("수정된 게시글");
//        updateRequest.setContent("내용이 수정되었습니다.");
//
//        // When
//        PostResponse result = postService.updatePost(1L, "testuser", updateRequest);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("수정된 게시글", result.getTitle());
//        assertEquals("내용이 수정되었습니다.", result.getContent());
//        assertEquals("testuser", result.getUsername());
//
//        verify(postRepository).findById(1L);
//        verify(postRepository).save(any(Post.class));
//    }
//
//    @Test
//    void updatePost_NotAuthor() {
//        // Given
//        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
//
//        // When & Then
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            postService.updatePost(1L, "otheruser", postRequest);
//        });
//
//        assertEquals("게시글 수정 권한이 없습니다.", exception.getMessage());
//        verify(postRepository).findById(1L);
//        verify(postRepository, never()).save(any(Post.class));
//    }
//
//    @Test
//    void getAllPosts_Success() {
//        // Given
//        when(postRepository.findAllByOrderByCreatedAtDesc()).thenReturn(Arrays.asList(post));
//
//        // When
//        List<PostResponse> results = postService.getAllPosts();
//
//        // Then
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals("테스트 게시글", results.get(0).getTitle());
//        assertEquals("이것은 테스트 게시글입니다.", results.get(0).getContent());
//        assertEquals("testuser", results.get(0).getUsername());
//
//        verify(postRepository).findAllByOrderByCreatedAtDesc();
//    }
//
//    @Test
//    void deletePost_Success() {
//        // Given
//        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
//
//        // When
//        postService.deletePost(1L, "testuser");
//
//        // Then
//        verify(postRepository).findById(1L);
//        verify(postRepository).delete(post);
//    }
//
//    @Test
//    void deletePost_NotAuthor() {
//        // Given
//        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
//
//        // When & Then
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            postService.deletePost(1L, "otheruser");
//        });
//
//        assertEquals("게시글 삭제 권한이 없습니다.", exception.getMessage());
//        verify(postRepository).findById(1L);
//        verify(postRepository, never()).delete(any(Post.class));
//    }
//}