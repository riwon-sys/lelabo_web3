/* ProductController 클래스 | rw 25-04-23 생성
  - 상품 관련 요청을 처리하는 REST API 컨트롤러입니다.
  - 상품 등록, 전체 조회, 개별 조회, 삭제 기능을 제공합니다.
  - JWT 토큰 기반 인증을 통해 로그인된 사용자가 상품을 등록/삭제할 수 있습니다.
  - 이미지 첨부는 multipart/form-data 방식으로 처리됩니다.
*/

package web.controller;

// [ * ] 필수 어노테이션 및 클래스 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.dto.CategoryDto;
import web.model.dto.ProductDto;
import web.model.entity.ProductEntity;
import web.service.MemberService;
import web.service.ProductService;

import java.util.List;

// [ * ] REST API 컨트롤러 선언 (JSON 반환)
@RestController

// [ * ] 해당 컨트롤러의 기본 URL prefix 설정: "/product"
@RequestMapping("/product")

// [ * ] final 필드 자동 생성자 주입 (생성자 통해 DI)
@RequiredArgsConstructor

// [ * ] CORS 허용 (모든 출처 허용)
@CrossOrigin("*")
public class ProductController { // CS

    // [ * ] DI(의존성 주입) - 제품 관련 서비스
    private final ProductService productService;

    // [ * ] DI(의존성 주입) - 회원 관련 서비스
    private final MemberService memberService;

    // [1] 제품 등록 기능
    /*
        매핑 방식: POST, 요청 URL: /product/register
        매개변수: 토큰(Authorization), 등록할 값들(pname, pcontent, pprice, files, cno)
        응답 데이터 타입: Boolean (성공 여부)
     */
    @PostMapping("/register")
    public ResponseEntity<Boolean> registerProduct(
            @RequestHeader("Authorization") String token ,         // (1) HTTP 헤더에서 토큰을 받음
            @ModelAttribute ProductDto productDto ){              // (2) multipart/form-data 형식으로 제품 정보 및 파일 수신

        System.out.println("token = " + token + ", productDto = " + productDto); // 디버깅용 로그

        int loginMno;
        try {
            loginMno = memberService.info( token ).getMno();       // (3) 토큰으로 회원번호 조회
        } catch (Exception e) {
            return ResponseEntity.status(401).body(false);         // (4) 실패 시 401 Unauthorized 응답
        }

        boolean result = productService.registerProduct(productDto, loginMno); // (5) 제품 등록 시도
        if (!result) return ResponseEntity.status(400).body(false);            // (6) 실패 시 400 Bad Request

        return ResponseEntity.status(201).body(true);              // (7) 성공 시 201 Created 반환
    }

    // [2] 제품 개별 조회 기능
    /*
        매핑 방식: GET, 요청 URL: /product/view?pno=1
        매개변수: 제품번호(pno)
        응답 데이터 타입: ProductDto
     */
    @GetMapping("/view")
    public ResponseEntity<ProductDto> viewProduct(@RequestParam long pno){
        ProductDto productDto = productService.viewProduct(pno);   // (1) 제품 조회 시도

        if (productDto == null) {
            return ResponseEntity.status(404).body(null);          // (2) 조회 실패 시 404 반환
        } else {
            return ResponseEntity.status(200).body(productDto);    // (3) 성공 시 200 OK 반환
        }
    }

    // [3] 제품 삭제 기능
    /*
        매핑 방식: DELETE, 요청 URL: /product/delete?pno=1
        매개변수: 토큰, 제품번호(pno)
        응답 데이터 타입: Boolean
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteProduct(
            @RequestHeader("Authorization") String token,
            @RequestParam int pno ){

        int loginMno;
        try {
            loginMno = memberService.info(token).getMno();         // (1) 토큰으로 회원 정보 확인
        } catch (Exception e) {
            return ResponseEntity.status(401).body(false);         // (2) 권한 없음
        }

        boolean result = productService.deleteProduct(pno, loginMno); // (3) 제품 삭제 시도
        if (!result) return ResponseEntity.status(400).body(false);   // (4) 실패 시 400 반환
        return ResponseEntity.status(200).body(true);                // (5) 성공 시 200 OK
    }

    // [4] 제품 수정 기능 (+ 이미지 추가)
    /*
        매핑 방식: PUT, 요청 URL: /product/update
        매개변수: 토큰, 수정할 값들(productDto)
        응답 데이터 타입: Boolean
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateProduct(
            @RequestHeader("Authorization") String token,
            @ModelAttribute ProductDto productDto ){

        int loginMno;
        try {
            loginMno = memberService.info(token).getMno();          // (1) 토큰에서 회원번호 추출
        } catch (Exception e) {
            return ResponseEntity.status(401).body(false);          // (2) 실패 시 401 반환
        }

        boolean result = productService.updateProduct(productDto, loginMno); // (3) 수정 시도
        if (!result) return ResponseEntity.status(400).body(false);         // (4) 실패 시 400 반환
        return ResponseEntity.status(200).body(true);                       // (5) 성공 시 200 반환
    }

    // [5] 이미지 개별 삭제 기능
    /*
        매핑 방식: DELETE, 요청 URL: /product/image
        매개변수: 이미지 번호(ino), 토큰
        응답 데이터 타입: Boolean
     */
    @DeleteMapping("/image")
    public ResponseEntity<Boolean> deleteImage(
            @RequestParam long ino,                           // (1) 삭제 대상 이미지 번호
            @RequestHeader("Authorization") String token ) {  // (2) 사용자 인증용 토큰

        int loginMno;
        try {
            loginMno = memberService.info(token).getMno();    // (3) 토큰 기반 로그인 사용자 확인
        } catch (Exception e) {
            return ResponseEntity.status(401).body(false);    // (4) 인증 실패 → 401 Unauthorized
        }

        boolean result = productService.deleteImage(ino, loginMno); // (5) 이미지 삭제 시도
        if (!result) {
            return ResponseEntity.status(400).body(false);          // (6) 실패 시 400 반환 (반환 누락 수정)
        }

        return ResponseEntity.status(200).body(true);         // (7) 삭제 성공 시 200 반환
    }

    // [6] 카테고리 전체 조회
    /*
        매핑 방식: GET, 요청 URL: /product/category
        매개변수: 없음
        응답 데이터 타입: List<CategoryDto>
     */
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> allCategory(){
        List<CategoryDto> categoryDtoList = productService.allCategory(); // (1) 전체 카테고리 목록 조회
        return ResponseEntity.status(200).body(categoryDtoList);         // (2) 200 OK 응답
    }

    // [7] 검색 + 페이징 + 카테고리 필터 전체 조회 기능
    /*
        매핑 방식: GET, 요청 URL: /product/all
        매개변수: 카테고리번호(cno), 페이지번호(page), 페이지당 수(size), 검색어(keyword)
        응답 데이터 타입: List<ProductDto>
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> allProducts(
            @RequestParam(required = false) Long cno,              // (1) 카테고리 번호 (없으면 전체)
            @RequestParam(defaultValue = "1") int page,            // (2) 페이지 번호 기본값 = 1
            @RequestParam(defaultValue = "5") int size,            // (3) 페이지당 항목 수 기본값 = 5
            @RequestParam(required = false) String keyword){       // (4) 검색 키워드 (optional)

        List<ProductDto> productDtoList = productService.allProducts(cno, page, size, keyword); // (5) 조건 기반 전체 조회
        return ResponseEntity.status(200).body(productDtoList);    // (6) 200 OK 응답
    }

} // CE