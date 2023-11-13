package com.example.exports;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exports")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @SneakyThrows
    @GetMapping("/{entityName}")
    public void exportEntities(@PathVariable String entityName, HttpServletResponse response){
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + entityName + ".csv\"");
        exportService.exportFromDatabase(response.getWriter(), entityName);
    }

}
