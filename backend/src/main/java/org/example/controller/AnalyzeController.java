package org.example.controller;

import org.example.service.logic.AnalyzeLogicService;
import org.example.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzeController {
    @Autowired
    AnalyzeLogicService analyzeLogicService;

    @GetMapping("/analyze/wordcount")
    public JsonResult<Integer> getRecentWordsInSeconds(@RequestParam(defaultValue = "3600") Long seconds) {
        return JsonResult.ok(analyzeLogicService.getRecentWordsInSeconds(seconds));
    }

    @GetMapping("/analyze/wordcount_alltime")
    public JsonResult<Integer>  getRecentWordsAllTime(){
        return JsonResult.ok(analyzeLogicService.getRecentWordsAllTime());
    }
}
