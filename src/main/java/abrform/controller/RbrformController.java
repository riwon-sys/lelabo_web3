package abrform.controller;

import abrform.model.dto.AbrformDto;
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

    // [1].C
 /*
    {
      "rtitle" : "꿈과 책과 힘과 벽을 읽고나서" ,
      "rwriter" : "잔나비 팬",
      "rcontent" : "잔잔함과 감동을 느꼇다.",
      "rpwd" : "1234"
}
 */
    @PostMapping("/rbpost") // http://localhost:8080/rb/rbpost
    RbrformDto rbPost(@RequestBody RbrformDto rbrformDto){
        return rbrformService.rbPost(rbrformDto);
    }

    // [2]. R
    @GetMapping("/rbfindall") // http://localhost:8080/rb/rbfindall
    public List<RbrformDto>rbFindAll(){
        return rbrformService.rbFindAll();
    }

    // [2-2]. R2
    @GetMapping("/rbfindbyid") // http://localhost:8080/rb/rbfindbyid?rno=9
    public RbrformDto rbFindById(@RequestParam int rno){
        return  rbrformService.rbFindById(rno);
    }

    // [3]. U + @Transactional
     /*
    {
      "rno" : "9",
      "rtitle" : "주저하는 연인들을 위하여..." ,
      "rwriter" : "잔나비 팬",
      "rcontent" : "위로를 느낄수 있었다.",
      "rpwd" : "5678"
}
 */
    @PutMapping("/rbupdate") // http://localhost:8080/rb/rbupdate
    public RbrformDto rbUpdate(@RequestBody RbrformDto rbrformDto){
        return  rbrformService.rbUpdate(rbrformDto);
    }

    // [4]. D
    @DeleteMapping("/rbdelete") // http://localhost:8080/rb/rbdelete?rno=9
    public boolean rbDelete(@RequestParam int rno){
        return  rbrformService.rbDelete(rno);
    }


}
