package com.example.imports;

import com.example.imports.service.impl.ImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/imports")
@RequiredArgsConstructor
@Slf4j
public class ImportController {

     private final ImportService importService;

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        return ResponseEntity.ok(importService.findById(id));
    }

}
