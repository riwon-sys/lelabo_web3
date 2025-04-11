package example.class02._toDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/class02/todto")
public class ExamController {

    private final ExamService examService;

    @PostMapping()
    public boolean post( @RequestBody ExamDto examDto ){
        return examService.post( examDto );
    }


}
