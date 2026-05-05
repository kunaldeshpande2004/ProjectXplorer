package com.Batch_1.projectXplorer.Controller;

import com.Batch_1.projectXplorer.Service.PropyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/propy")
public class PropyController {

    @Autowired
    private PropyService propyService;

    @PostMapping("/ask")
    public Map<String, Object> ask(@RequestBody Map<String, String> body) {
        return propyService.askPropy(body.get("query"));
    }
}