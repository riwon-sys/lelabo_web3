package abrform.controller;

import abrform.model.dto.RbrformDto;
import abrform.service.RbrformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rb")
@RequiredArgsConstructor
public class RbrformController {
    private final RbrformService rbrformService;

    // [1]. C | rw 25-04-13 수정
/*
{
  "rtitle" : "꿈과 책과 힘과 벽을 읽고나서",
  "rwriter" : "잔나비 팬",
  "rcontent" : "잔잔함과 감동을 느꼈다.",
  "rpwd" : "1234"
}
*/
/*
    @PostMapping("/rbpost") // http://localhost:8080/rb/rbpost
    public RbrformDto rbPost(@RequestBody RbrformDto rbrformDto) {
        // fs

        // 사용자가 작성한 리뷰 데이터를 서비스로 전달하여 등록 처리합니다.
        // 등록 시 비밀번호는 암호화되어 DB에 저장됩니다.
        return rbrformService.rbPost(rbrformDto);

        // fe
    }
*/
    // [1]. C | rw 25-04-13 수정
    @PostMapping("/rbpost") // http://localhost:8080/rb/rbpost
    public RbrformDto rbPost(@ModelAttribute RbrformDto rbrformDto) {
        return rbrformService.rbPost(rbrformDto);
    }
    // [2]. R
    @GetMapping("/rbfindall") // http://localhost:8080/rb/rbfindall
    public List<RbrformDto> rbFindAll() {
        return rbrformService.rbFindAll();
    }

    // [2-2]. R2
    @GetMapping("/rbfindbyid") // http://localhost:8080/rb/rbfindbyid?rno=9
    public RbrformDto rbFindById(@RequestParam int rno) {
        return rbrformService.rbFindById(rno);
    }

    // [3]. U + @Transactional
     /*
{
      "rno" : "9",
      "rtitle" : "주저하는 연인들을 위하여..." ,
      "rwriter" : "잔나비 팬",
      "rcontent" : "내용변경 TEST123.",
      "rpwd" : "1234"
}
 */
    @PutMapping("/rbupdate") // http://localhost:8080/rb/rbupdate
    public RbrformDto rbUpdate(@RequestBody RbrformDto rbrformDto) {
        // 사용자가 전달한 리뷰 수정 요청을 처리합니다.
        // 이때, 기존에 등록된 리뷰와 비밀번호가 일치해야만 수정이 가능하도록 구성되어 있습니다.
        // 암호화된 비밀번호는 복호화할 수 없기 때문에,
        // 사용자가 입력한 평문 비밀번호를 기존 암호화된 비밀번호와 비교하여 검증합니다.

        return rbrformService.rbUpdate(rbrformDto);

    }
    // [4]. D | rw 25-04-13 수정
    @DeleteMapping("/rbdelete") // http://localhost:8080/rb/rbdelete?rno=9&rpwd=1234
    public boolean rbDelete(@RequestParam int rno, @RequestParam String rpwd) {
        // fs

        // 사용자가 전달한 rno(리뷰 번호)와 rpwd(비밀번호)를 바탕으로
        // 리뷰 삭제를 요청합니다. 암호화된 비밀번호와 비교하여
        // 일치할 경우에만 삭제가 수행됩니다.
        return rbrformService.rbDelete(rno, rpwd);

        // fe
    }
/*
    // [4]. D | rw 25-04-13 수정
    @DeleteMapping("/rbdelete") // http://localhost:8080/rb/rbdelete?rno=9&rpwd=1234
    public boolean rbDelete(@RequestParam int rno, @RequestParam String rpwd) {
        // fs

        // 사용자가 전달한 rno(리뷰 번호)와 rpwd(비밀번호)를 바탕으로
        // 리뷰 삭제를 요청합니다. 이때 암호화된 비밀번호와 비교하여
        // 일치할 경우에만 삭제를 진행합니다.
        return rbrformService.rbDelete(rno, rpwd);

        // fe
    }

 */
/*
   [4]. D 초기 구성 | rw 25-04-11 생성 rw 25-04-12 소멸
    @DeleteMapping("/rbdelete") // http://localhost:8080/rb/rbdelete?rno=9
    public boolean rbDelete(@RequestParam int rno) {
        return rbrformService.rbDelete(rno);
    }
*/

    // [5]. R3 특정 책(aid)에 대한 리뷰 전체 조회 | rw 25-04-13 생성
    @GetMapping("/rbfindbyaid") // http://localhost:8080/rb/rbfindbyaid?aid=9
    public List<RbrformDto> rbFindByAid(@RequestParam int aid) {
        // fs

        // 사용자가 요청한 책 등록 번호(aid)를 바탕으로
        // 해당 책에 작성된 모든 리뷰 목록을 반환합니다.
        return rbrformService.rbFindByAid(aid);

        // fe
    }
    // [6]. aid 기준 리뷰 전체 조회 | rw 25-04-13 생성
    @GetMapping("/rbreviewlist") // http://localhost:8080/rb/rbreviewlist?aid=3
    public List<RbrformDto> rbFindAllByAid(@RequestParam int aid) {
        // fs

        // 특정 책 번호(aid)에 연결된 리뷰 전체 조회 요청 처리
        return rbrformService.rbFindAllByAid(aid);

        // fe
    }


}
