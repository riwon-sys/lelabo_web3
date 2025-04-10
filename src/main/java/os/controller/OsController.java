// OsController 구성 | rw 25-04-10 생성

package os.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import os.model.dto.OsDto;
import os.service.OsService;

import java.util.List;

@RestController
@RequestMapping("/os")
@RequiredArgsConstructor

public class OsController {

    private final OsService osService;

    // [1]. C
    /*{
        "oname" : "헬스머신" ,
            "odescription" : "건강 해질 수 있음",
            "oqt" : "3"
    }*/
    @PostMapping("/ospost") // http://localhost:8080/os/ospost
    public OsDto osPost( @RequestBody OsDto osDto){
        return osService.osPost(osDto);
    }

    // [2]. R
    @GetMapping("/osfindall") // http://localhost:8080/os/osfindall
    public List<OsDto>osFindAll(){
        return osService.osFindAll();
    }

    // [2]-2. R
    @GetMapping("/osfindview") // http://localhost:8080/os/osfindview
    public OsDto osFindById( @RequestParam int oid){
        return osService.osFindById(oid);
    }
    // [3]. U + @Transactional
     /*{
        "oname" : "울트라 헬스머신" ,
            "odescription" : "매우 건강 해질 수 있음",
            "oqt" : "6"
    }*/

    // [3]. U + @Transactional
    @PutMapping("/osupdate") // http://localhost:8080/os/osupdate
    public OsDto osUpdate( @RequestBody OsDto osDto){
        return osService.osUpdate(osDto);
    }

    // [4]. D
    @DeleteMapping // http://localhost:8080/os/?oid=11
    public boolean osDelete( @RequestParam int oid){
        return  osService.osDelete(oid);
    }

    // [5]. R3+PAGING
    @GetMapping("/page") // http://localhost:8080/os/page?page=1&size=3
    public List<OsDto> osFindByPage(@RequestParam(defaultValue = "1")int page,
                                    @RequestParam(defaultValue = "3")int size)
    {return osService.osFindByPage(page,size);
    }

    // [6]. R3 (입력한 값이 일치한 제목조회) TITLE | 전체조회 (PAGING)
    @GetMapping("/allserch") // http://localhost:8080/os/allserch?name=울트라헬스머신
    public List<OsDto>osAllserch( @RequestParam String oname ){
        return osService.osAllserch(oname);
    }

    // [6-2]. R3 (입력한 값이 일치한 제목 , 설명조회) KEYWORD | 전체조회 (PAGING)
    @GetMapping("/serch") // http://localhost:8080/os/serch?keyword=매우 건강 해질 수 있음
    public List<OsDto>osSerch(@RequestParam String keyword){
        return osService.osSerch(keyword);
    }


} // END
