package com.codestates.coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/coffees")
public class CoffeeController {
    @PostMapping
    public ResponseEntity postCoffe(@RequestBody CoffeePostDto coffeePostDto) {
        return new ResponseEntity<>(coffeePostDto, HttpStatus.CREATED);
    }
//    public ResponseEntity postCoffee(@RequestParam("engName") String engName,
//                            @RequestParam("korName") String korName,
//                             @RequestParam("price") long price) {

//        String response =
//                "{\"" +
//                        "engName\":\""+engName+"\"," +
//                        "\"korName\":\""+korName+"\"" +
//                        "\"price\":\""+price+"}";
//        return response;
//        // JSON 문자열 수작업을 Map 객체로 대체
//        Map<String, Object> map = new HashMap<>();
//        map.put("engName", engName);
//        map.put("korName", korName);
//        map.put("price", price);
//
//        // 리턴 값을 ResponseEntity 객체로 변경
//        return new ResponseEntity<>(map, HttpStatus.CREATED); // 생성자 파라미터로 응답 데이터(map)와 HTTP 응답 상태를 함께 전달
//    }
    @PatchMapping("/{coffee-id}")
    public ResponseEntity postCoffe(@PathVariable("coffee-id") long coffeeId,
                                    @RequestBody CoffeePatchDto coffeePatchDto) {
        coffeePatchDto.setCoffeeId(coffeeId);
        coffeePatchDto.setPrice(coffeePatchDto.getPrice());

        return new ResponseEntity<>(coffeePatchDto, HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {
        System.out.println("# coffeeId: " + coffeeId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees() {
        System.out.println("# get Coffees");

        return new ResponseEntity(HttpStatus.OK);
    }
}
