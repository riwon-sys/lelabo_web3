// AbrformController 구성 | rw 25-04-11 생성

package abrform.controller;

import abrform.model.dto.AbrformDto;
import abrform.service.AbrformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ab")
@RequiredArgsConstructor
public class AbrformController {

    private final AbrformService abrformService;

    // [1]. C
 /*
{
      "atitle" : "꿈과 책과 힘과 벽" ,
      "awriter" : "잔나비",
      "acontent" : "잔잔함을 느낄수 있다.",
      "apwd" : "1234"
}
 */
/*
    @PostMapping("/abpost") // http://localhost:8080/ab/abpost
    public AbrformDto abPost(@RequestBody AbrformDto abrformDto){
        return abrformService.abPost(abrformDto);
    }
*/
    // [1]. C | rw 25-04-13 수정
    @PostMapping("/abpost")
    public AbrformDto abPost(@ModelAttribute AbrformDto abrformDto) {
        return abrformService.abPost(abrformDto);
    }



    // [2]. R
    @GetMapping("/abfindall") // http://localhost:8080/ab/abfindall
    public List<AbrformDto>abFindAll(){ return abrformService.abFindAll();
    }

    // [2-2]. R2
    @GetMapping("/abfindbyid") // http://localhost:8080/ab/abfindbyid?aid=9
    public AbrformDto abFindById(@RequestParam int aid){
        return  abrformService.abFindById(aid);
    }

    // [3]. U + @Transactional
    @PutMapping("/abupdate") // http://localhost:8080/ab/abupdate
    public AbrformDto abUpdate(@RequestBody AbrformDto abrformDto){
        // fs

        // 사용자가 전달한 책 추천 정보(DTO)를 기반으로
        // → 데이터베이스에 저장된 기존 정보를 수정합니다.
        // 이 과정에서는 비밀번호가 일치하는지 먼저 확인한 뒤,
        // 일치할 경우에만 수정이 수행됩니다.
        return abrformService.abUpdate(abrformDto);

        // fe
    }

    /*
    // [3]. U + @Transactional | rw 25-04-11 생성 rw 25-04-12 소멸
{
  "aid": "9",
  "atitle": "주저하는 연인들을 위하여 (수정됨)",
  "awriter": "잔나비",
  "acontent": "비밀번호가 맞으면 수정 성공!",
  "apwd": "1234"  // "apwd": "4567" (x)
}
     @PutMapping("/abupdate") // http://localhost:8080/ab/abupdate
     public AbrformDto abUpdate(@RequestBody AbrformDto abrformDto){
         return abrformService.abUpdate(abrformDto);
     }
     */

    // [4]. D | rw 25-04-12 수정 // http://localhost:8080/ab/abdelete?aid=9
    @DeleteMapping("/abdelete") // http://localhost:8080/ab/abdelete?aid=9&apwd=1234
    public boolean abDelete(@RequestParam int aid, @RequestParam String apwd) {
        // fs

        // 사용자가 전달한 aid(게시글 번호)와 apwd(비밀번호)를
        // 서비스 계층에 전달하여 삭제 요청을 처리합니다.
        return abrformService.abDelete(aid, apwd);

        // fe
    }


}
