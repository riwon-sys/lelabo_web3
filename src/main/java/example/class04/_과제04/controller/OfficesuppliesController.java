package example.class04._과제04.controller;

import example.class04._과제04.model.dto.OfficesuppliesDto;
import example.class04._과제04.service.OfficesuppliesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class04/Officesupplies")
@RequiredArgsConstructor
public class OfficesuppliesController {
    private final OfficesuppliesService officesuppliesService;

    // [1]. C
    @PostMapping // http://localhost:8080/class04/Officesupplies
    // { "name" : "헬스머신" , "description" : "건강해질수 있음 , 회사8층 헬스장에 3대 비치" , "quantity" : "3" }
    public OfficesuppliesDto officesuppliesSave( @RequestBody OfficesuppliesDto officesuppliesDto ){
        return officesuppliesService.officesuppliesSave( officesuppliesDto );
    }

    // [2]. R
    @GetMapping
    public List< OfficesuppliesDto >officesuppliesFindAll(){
        return officesuppliesService.officesuppliesFindAll();
    }

    // [2]-2. R2
    @GetMapping("/view") // http://localhost:8080/class04/Officesupplies/view?id=1
    public OfficesuppliesDto officesuppliesFindById(@RequestParam int id){
        return officesuppliesService.officesuppliesFindById(id);
    }
    // [3]. U + @Transactional
    // { "id" : "13" , "name" : "헬스머신123" , "description" : "건강해질수 있음 , 회사8층 헬스장에 3대 비치" , "quantity" : "3" }
    @PutMapping
    public OfficesuppliesDto officesuppliesUpdate( @RequestBody OfficesuppliesDto officesuppliesDto){
        return officesuppliesService.officesuppliesUpdate( officesuppliesDto );
    }
    // [4]. D
    @DeleteMapping // http://localhost:8080/class04/Officesupplies?id=25
    public  boolean officesuppliesDelete( @RequestParam int id ){
        return officesuppliesService.officesuppliesDelete( id );
    }

    // [5]. R3+PAGING
    @GetMapping("/page")// http://localhost:8080/class04/Officesupplies/page?page=1&size=3
    public List< OfficesuppliesDto > officesuppliesFindByPage(
            // @RequestParam(defaultValue = " 초기값 ")
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size){
        return officesuppliesService.officesuppliesFindByPage( page , size );
    }

    // [6]. R3 ( 입력한 값이 일치한 제목조회) title | 전체 조회 (PAGING)
    @GetMapping("/search1") // http://localhost:8080/class04/Officesupplies/search1?name=헬스머신
    public List<OfficesuppliesDto>search1( @RequestParam String name ){
        return officesuppliesService.search1( name );
    }

    // [6]-2. R3 (입력한 값이 일치하는 제목, 설명 조회) keyword | 전체 조회 (PAGING)
    @GetMapping("/search2") // http://localhost:8080//class04/Officesupplies/search2?keyword=건강해질수
    public List<OfficesuppliesDto> search2( @RequestParam String keyword ){
        return officesuppliesService.search2( keyword );
    }

}
