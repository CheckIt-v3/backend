package com.techeer.checkIt.domain.readingVolume.controller;

import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.service.ReadingVolumeService;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultCode;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "독서현황 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/readingvolumes")
public class ReadingVolumeController {
    private final ReadingVolumeService readingVolumeService;
    private final UserService userService;

    @GetMapping("/{uid}")
    public ResponseEntity<ResultResponse>a(@PathVariable Long uid) {
        User user = userService.findUserById(uid);
        List<SearchReadingVolumesRes> readingVolumeList = readingVolumeService.findReadingVolumesByUserAndDateBetween(user);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_READING_VOLUMES_SUCCESS,readingVolumeList));
    }
}
