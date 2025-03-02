package com.yuapi.GameStatWeb.web;

import com.yuapi.GameStatWeb.service.RiotApiService;
import com.yuapi.GameStatWeb.web.dto.LoLSummonerDto;
import com.yuapi.GameStatWeb.web.dto.RiotAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoLController {

    private final RiotApiService riotApiService;

    @GetMapping("/lol")
    public String lol(Model model) {
        model.addAttribute("title", "GG LoL");

        return "lol/main";
    }

    @GetMapping("/lol/summoner/{region}/{name}")
    public String lolSummoner(@PathVariable("name") String name, @PathVariable("region") String region, Model model) {
        model.addAttribute("title", "GG LoL");

        String[] names = name.split("#");

        RiotAccountDto account = riotApiService.getAccountByRiotId(names[0], names[1], region);
        model.addAttribute("account", account);

        LoLSummonerDto summoner = riotApiService.getLoLSummonerByPuuid(account.getPuuid(), region);
        model.addAttribute("summoner", summoner);

        return "lol/summoner";
    }
}
