//package com.toeicvocab.dto.response;
//
//import com.toeicvocab.domain.Wordbook;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class WordbookResponse {
//    private Long id;
//    private String title;
//    private String description;
//    private int wordCount;
//    private List<WordResponse> words;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    public static WordbookResponse fromEntity(Wordbook wordbook, boolean includeWords) {
//        WordbookResponseBuilder builder = WordbookResponse.builder()
//                .id(wordbook.getId())
//                .title(wordbook.getTitle())
//                .description(wordbook.getDescription())
//                .wordCount(wordbook.getWords().size())
//                .createdAt(wordbook.getCreatedAt())
//                .updatedAt(wordbook.getUpdatedAt());
//
//        if (includeWords) {
//            builder.words(wordbook.getWords().stream()
//                    .map(WordResponse::fromEntity)
//                    .collect(Collectors.toList()));
//        }
//
//        return builder.build();
//    }
//}
