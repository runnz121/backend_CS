package malangcute.bellytime.bellytimeCustomer.cooltime.controller;

import lombok.AllArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.MyListResponse;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cooltime")
@AllArgsConstructor
public class CoolTimeController {

    private final CoolTimeService coolTimeService;


    @GetMapping("/mylist")
    public ResponseEntity<?> myList(HttpServletRequest request){

        List<MyListResponse> myList = coolTimeService.getMyList(request);

        return ResponseEntity.status(HttpStatus.OK).body(myList);
    }

    @GetMapping("/check")
    public String check() {
        return "check cooltime";
    }
}
