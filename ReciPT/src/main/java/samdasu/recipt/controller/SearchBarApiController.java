package samdasu.recipt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 최근 검색어 - 플러터
 * 연령별 추천 - 10대, 20대, 30대, 40대... 전체 띄우기 vs 사용자가 10, 20, 30, 40 입력하면 입력한 연령대만 보여줄건지
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchBarApiController {
}
