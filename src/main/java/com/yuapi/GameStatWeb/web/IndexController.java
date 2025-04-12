package com.yuapi.GameStatWeb.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "GG Stats");

        return "index";
    }

//    @GetMapping("/example")
//    public String example(Model model) {
//        model.addAttribute("title", "Example Page");
//        model.addAttribute("posts", postsService.findAllDesc());
//
//        return "example";
//    }
//
//    @GetMapping("/posts/save")
//    public String postsSave(Model model) {
//        model.addAttribute("title", "Posts Save");
//
//        return "posts-save";
//    }
//
//    @GetMapping("/posts/update/{id}")
//    public String postsUpdate(@PathVariable Long id, Model model) {
//        model.addAttribute("title", "Posts Update");
//
//        PostsResponseDto dto = postsService.findById(id);
//        model.addAttribute("post", dto);
//
//        return "posts-update";
//    }
}
