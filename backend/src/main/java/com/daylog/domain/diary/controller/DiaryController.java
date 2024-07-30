package com.daylog.domain.diary.controller;

import com.daylog.domain.diary.dto.DiaryResponse;
import com.daylog.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {

    private final DiaryService diaryService;

    //텍스트로 일기 요청하기
    @PostMapping("/diaries")
    public DiaryResponse requestDiaryByText(@RequestBody String text) {
        return diaryService.generateDiaryImage(text);
    }



}
