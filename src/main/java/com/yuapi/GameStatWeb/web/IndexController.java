package com.yuapi.GameStatWeb.web;

import java.util.List;
import java.util.Map;
import com.yuapi.GameStatWeb.service.PostsService;
import com.yuapi.GameStatWeb.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "GG Stats");

        // 최근 경기 결과
        List<Map<String, String>> recentMatches = List.of(
                Map.of("champion", "아리", "result", "승리", "kda", "5/1/8", "gameTime", "30분"),
                Map.of("champion", "야스오", "result", "패배", "kda", "3/7/2", "gameTime", "25분"),
                Map.of("champion", "리 신", "result", "승리", "kda", "10/3/9", "gameTime", "35분")
        );

        // 인기 챔피언
        List<Map<String, String>> popularChampions = List.of(
                Map.of("name", "제드", "image", "/images/zedd.png", "pickRate", "15.6", "winRate", "52.3"),
                Map.of("name", "럭스", "image", "/images/lux.png", "pickRate", "12.8", "winRate", "50.1")
        );

        // 승률 순위
        List<Map<String, Object>> winRateRankings = List.of(
                Map.of("rank", 1, "name", "말파이트", "winRate", 55.6, "pickRate", 8.9),
                Map.of("rank", 2, "name", "세나", "winRate", 54.8, "pickRate", 7.5)
        );

        model.addAttribute("recentMatches", recentMatches);
        model.addAttribute("popularChampions", popularChampions);
        model.addAttribute("winRateRankings", winRateRankings);

        return "index";
    }

    @GetMapping("/example")
    public String example(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "example";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
