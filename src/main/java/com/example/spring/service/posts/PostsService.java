package com.example.spring.service.posts;

import com.example.spring.domain.posts.Posts;
import com.example.spring.domain.posts.PostsRepository;
import com.example.spring.web.dto.PostsListResponseDto;
import com.example.spring.web.dto.PostsResponseDto;
import com.example.spring.web.dto.PostsSaveRequestDto;
import com.example.spring.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
// 트랜잭션과 도메인 간의 순서만 보장
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다, id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    // readOnly : 트랜잭션 범위는 유지, 조회 기능만 가능 >> 조회 속도 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .stream()
                .map(PostsListResponseDto::new)
                // = .map(posts -> new PostsListResponseDto(posts))
                // postsRepository 결과로 넘어온 Posts 의 Stream 을
               // map 을 통해 PostsListResponseDto 변환 -> List 로 반환하는 메소드
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
        // JpaRepository 에서 이미 delete 메소드를 지원하고 있음
        // 엔티티를 파라미터로 삭제할 수도 있고, deleteById 메소드를 이용하면 id 로 삭제할 수 있음
        // 존재하는 Posts 인지 확인을 위해 엔티티 조회 후 그대로 삭제
    }

}
