package KKSC.page.domain.board.service.impl;

import KKSC.page.domain.board.dto.request.AddBoardRequest;
import KKSC.page.domain.board.dto.request.UpdateBoardRequest;
import KKSC.page.domain.board.entity.Board;
import KKSC.page.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@RequiredArgsConstructor
@Service
public class BoardServiceImpl {
    private final BoardRepository boardRepository;

    @PreAuthorize("hasRole('permission_level0')")
    public Board create(AddBoardRequest request) {
        return boardRepository.save(request.toEntity());
    }


    public List<Board> findAll(){
        return boardRepository.findAll(); //데이터베이스에 저장되어있는 글을 모두가져오는 메소드
    }


    public Board findById (long id){
        return boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ id));
    }
    // db에 저장되어있는 글의 id를 이용해 글을 조회
    // 조회하고 없으면 IllegalArgumentException 예외 발생

    @PreAuthorize("hasRole('permission_level0')")
    public void delete (long id){
        boardRepository.deleteById(id);
    }


    // 매칭한 메소드를 하나의 트랜잭션으로 묶는 역할을 한다.
    public Board update(long id, UpdateBoardRequest request){
        Board board= boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found:"+id));
        board.update(request.getTitle(),request.getContent());
        return board;
    }
}
