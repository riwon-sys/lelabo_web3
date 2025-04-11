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
    @PostMapping("/abpost") // http://localhost:8080/ab/abpost
    public AbrformDto abPost(@RequestBody AbrformDto abrformDto){
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
     /*
    {
      "aid" : "9",
      "atitle" : "주저하는 연인들을 위하여" ,
      "awriter" : "잔나비",
      "acontent" : "위로와 공감 그 이상의 무언가",
      "apwd" : "5678"
}
 */
    @PutMapping("/abupdate") // https://localhost:8080/ab/abupdate
    public AbrformDto abUpdate(@RequestBody AbrformDto abrformDto){
        return abrformService.abUpdate(abrformDto);
    }

    // [4]. D
    @DeleteMapping("/abdelete") // http://localhost:8080/ab/abdelete?aid=9
    public boolean abDelete(@RequestParam int aid){
        return abrformService.abDelete(aid);
    }



}
