package com.yuapi.GameStatWeb.web;

import com.yuapi.GameStatWeb.service.RiotApiService;
import com.yuapi.GameStatWeb.web.dto.LoLSummonerDto;
import com.yuapi.GameStatWeb.web.dto.RiotAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
public class LoLController {

    private final RiotApiService riotApiService;

    @GetMapping("/lol")
    public String lol(Model model) {
        model.addAttribute("title", "GG LoL");
        model.addAttribute("isLoLPage", true);

        return "lol/main";
    }

    @GetMapping("/lol/summoner/{region}/{encodedName}")
    public String lolSummoner(@PathVariable("region") String region, @PathVariable("encodedName") String encodedName, Model model) {
        model.addAttribute("title", "GG LoL");
        model.addAttribute("isLoLPage", true);

        String[] names = URLDecoder.decode(encodedName, StandardCharsets.UTF_8).split("#");
        RiotAccountDto account = riotApiService.getAccountByRiotId(region, names[0], names[1]);
        model.addAttribute("account", account);

        LoLSummonerDto summoner = riotApiService.getLoLSummonerByPuuid(region, account.getPuuid());
        model.addAttribute("summoner", summoner);

        return "lol/summoner";
    }
}
