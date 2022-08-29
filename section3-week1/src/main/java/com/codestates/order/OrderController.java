package com.codestates.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/order") // Map 객체를 리턴하면 JSON 형식으로 자동변환해주기 때문에 produces 생략 가능
public class OrderController {
    @PostMapping
    public ResponseEntity postOrder(@RequestParam("memberId") long memberId,
                            @RequestParam("coffeeId") long coffeeId) {
        System.out.println("# memberId: " + memberId);
        System.out.println("# coffeeId: " + coffeeId);

//        String response =
//                "{\"" +
//                        "memberId\":\""+memberId+"\"," +
//                        "\"coffeeId\":\""+coffeeId+"\"" +
//                        "}";
//        return response;
        // JSON 문자열 수작업을 Map 객체로 대체
        Map<String, Object> map = new HashMap<>();
        map.put("engName", memberId);
        map.put("korName", coffeeId);

        // 리턴 값을 ResponseEntity 객체로 변경
        return new ResponseEntity<>(map, HttpStatus.CREATED); // 생성자 파라미터로 응답 데이터(map)와 HTTP 응답 상태를 함께 전달
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") long orderId) {
        System.out.println("# orderId: " + orderId);

        // not implementation
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders() {
        System.out.println("# get Orders");

        // not implementation
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
