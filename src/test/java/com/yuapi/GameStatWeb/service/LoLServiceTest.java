package com.yuapi.GameStatWeb.service;


import com.yuapi.GameStatWeb.domain.enums.QueueType;
import com.yuapi.GameStatWeb.web.dto.*;
import com.yuapi.GameStatWeb.web.dto.lol.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoLServiceTest {

    @Autowired
    private LoLService lolService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${api.key.lol}")
    private String lolApiKey;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    private UriComponentsBuilder defaultUriBuilder(String region, String endpoint) {
        return UriComponentsBuilder.fromUriString("https://" + region + ".api.riotgames.com" + endpoint)
                .queryParam("api_key", lolApiKey);
    }

    @Test
    public void testGetCurrentVersion() {
        String jsonResponse = """
                ["15.6.1","15.5.1","15.4.1","15.3.1","15.2.1","15.1.1","14.24.1","14.23.1","14.22.1","14.21.1"]
                """;

        String url = "https://ddragon.leagueoflegends.com/api/versions.json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        String currentVersion = lolService.getCurrentVersion();

        assertThat(currentVersion).isEqualTo("15.6.1");

        mockServer.verify();
    }

    @Test
    public void testGetRealmsByRegion() {
        String region = "kr";
        String jsonResponse = "{\"n\":{\"item\":\"15.6.1\",\"rune\":\"7.23.1\",\"mastery\":\"7.23.1\",\"summoner\":\"15.6.1\",\"champion\":\"15.6.1\",\"profileicon\":\"15.6.1\",\"map\":\"15.6.1\",\"language\":\"15.6.1\",\"sticker\":\"15.6.1\"},\"v\":\"15.6.1\",\"l\":\"ko_KR\",\"cdn\":\"https://ddragon.leagueoflegends.com/cdn\",\"dd\":\"15.6.1\",\"lg\":\"15.6.1\",\"css\":\"15.6.1\",\"profileiconmax\":28,\"store\":null}";

        String url = "https://ddragon.leagueoflegends.com/realms/" + region + ".json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLRealmsDto realms = lolService.getRealmsByRegion(region);

        assertThat(realms.getN().getItem()).isEqualTo("15.6.1");
        assertThat(realms.getN().getRune()).isEqualTo("7.23.1");
        assertThat(realms.getN().getMastery()).isEqualTo("7.23.1");
        assertThat(realms.getN().getSummoner()).isEqualTo("15.6.1");
        assertThat(realms.getN().getChampion()).isEqualTo("15.6.1");
        assertThat(realms.getN().getProfileicon()).isEqualTo("15.6.1");
        assertThat(realms.getN().getMap()).isEqualTo("15.6.1");
        assertThat(realms.getN().getLanguage()).isEqualTo("15.6.1");
        assertThat(realms.getN().getSticker()).isEqualTo("15.6.1");
        assertThat(realms.getV()).isEqualTo("15.6.1");
        assertThat(realms.getL()).isEqualTo("ko_KR");
        assertThat(realms.getCdn()).isEqualTo("https://ddragon.leagueoflegends.com/cdn");

        mockServer.verify();
    }

    @Test
    public void testGetChampionList() {
        String version = "15.6.1";
        String language = "ko_KR";
        String jsonResponse = """
                {"type":"champion","format":"standAloneComplex","version":"15.6.1","data":{"Aatrox":{"version":"15.6.1","id":"Aatrox","key":"266","name":"아트록스","title":"다르킨의 검","blurb":"한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를...","info":{"attack":8,"defense":4,"magic":3,"difficulty":4},"image":{"full":"Aatrox.png","sprite":"champion0.png","group":"champion","x":0,"y":0,"w":48,"h":48},"tags":["Fighter"],"partype":"피의 샘","stats":{"hp":650,"hpperlevel":114,"mp":0,"mpperlevel":0,"movespeed":345,"armor":38,"armorperlevel":4.8,"spellblock":32,"spellblockperlevel":2.05,"attackrange":175,"hpregen":3,"hpregenperlevel":0.5,"mpregen":0,"mpregenperlevel":0,"crit":0,"critperlevel":0,"attackdamage":60,"attackdamageperlevel":5,"attackspeedperlevel":2.5,"attackspeed":0.651}},"Ahri":{"version":"15.6.1","id":"Ahri","key":"103","name":"아리","title":"구미호","blurb":"영혼 세계의 마법과 선천적으로 연결된 아리는 먹잇감의 감정을 조종하고 정수를 집어삼킬 수 있는 여우 모습의 바스타야로, 영혼을 먹어 치울 때마다 그 안에 담긴 지혜와 기억의 편린을 받아들인다. 강력하면서도 불안정한 포식자였던 아리는 이제 조상들의 흔적을 찾아 세상을 여행하며 지금껏 훔친 기억을 버리고 스스로 자신만의 추억을 쌓으려 한다.","info":{"attack":3,"defense":4,"magic":8,"difficulty":5},"image":{"full":"Ahri.png","sprite":"champion0.png","group":"champion","x":48,"y":0,"w":48,"h":48},"tags":["Mage","Assassin"],"partype":"마나","stats":{"hp":590,"hpperlevel":104,"mp":418,"mpperlevel":25,"movespeed":330,"armor":21,"armorperlevel":4.7,"spellblock":30,"spellblockperlevel":1.3,"attackrange":550,"hpregen":2.5,"hpregenperlevel":0.6,"mpregen":8,"mpregenperlevel":0.8,"crit":0,"critperlevel":0,"attackdamage":53,"attackdamageperlevel":3,"attackspeedperlevel":2.2,"attackspeed":0.668}},"Akali":{"version":"15.6.1","id":"Akali","key":"84","name":"아칼리","title":"섬기는 이 없는 암살자","blurb":"킨코우 결사단과 그림자의 권이라는 지위를 버린 아칼리는 아이오니아인들에게 필요한 강력한 무기가 되어 홀로 싸우고 있다. 스승 쉔의 가르침을 잊지 않은 채 아이오니아의 적을 하나씩 암살하기로 맹세했다. 아칼리의 살행은 은밀할지 모르나 그녀의 메시지는 대륙 전체를 뒤흔든다. ''경외하라. 나는 섬기는 이 없는 암살자다.''","info":{"attack":5,"defense":3,"magic":8,"difficulty":7},"image":{"full":"Akali.png","sprite":"champion0.png","group":"champion","x":96,"y":0,"w":48,"h":48},"tags":["Assassin"],"partype":"기력","stats":{"hp":600,"hpperlevel":119,"mp":200,"mpperlevel":0,"movespeed":345,"armor":23,"armorperlevel":4.7,"spellblock":37,"spellblockperlevel":2.05,"attackrange":125,"hpregen":9,"hpregenperlevel":0.9,"mpregen":50,"mpregenperlevel":0,"crit":0,"critperlevel":0,"attackdamage":62,"attackdamageperlevel":3.3,"attackspeedperlevel":3.2,"attackspeed":0.625}},"Akshan":{"version":"15.6.1","id":"Akshan","key":"166","name":"아크샨","title":"떠도는 감시자","blurb":"상반신을 드러낸 아크샨은 위험에 직면하면 눈썹을 치켜올리며 당당한 카리스마, 정의로운 복수심으로 악과 맞서 싸운다. 전투에서 뛰어난 은신술을 발휘하며 적의 눈을 피한 후 제일 예기치 못한 순간 다시 모습을 드러낸다. 불타는 정의감과 죽음을 되돌리는 전설적인 무기로 룬테라의 수많은 악한들이 저지른 잘못을 바로잡으며 '멍청이가 되지 말자'라는 자신만의 도덕적 기준에 따라 살아간다.","info":{"attack":0,"defense":0,"magic":0,"difficulty":0},"image":{"full":"Akshan.png","sprite":"champion0.png","group":"champion","x":144,"y":0,"w":48,"h":48},"tags":["Marksman","Assassin"],"partype":"마나","stats":{"hp":630,"hpperlevel":107,"mp":350,"mpperlevel":40,"movespeed":330,"armor":26,"armorperlevel":4.7,"spellblock":30,"spellblockperlevel":1.3,"attackrange":500,"hpregen":3.75,"hpregenperlevel":0.65,"mpregen":8.2,"mpregenperlevel":0.7,"crit":0,"critperlevel":0,"attackdamage":52,"attackdamageperlevel":3,"attackspeedperlevel":4,"attackspeed":0.638}}}}
                """;

        String url = "https://ddragon.leagueoflegends.com/cdn/" + version + "/data/" + language + "/champion.json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLChampionListDto champions = lolService.getChampionList(version, language);

        assertThat(champions).isNotNull();
        assertThat(champions.getVersion()).isEqualTo(version);
        assertThat(champions.getData()).isNotNull();
        assertThat(champions.getData().size()).isEqualTo(4);
        assertThat(champions.getData().get("Aatrox").getName()).isEqualTo("아트록스");

        mockServer.verify();
    }

    @Test
    public void testGetChampionDetail() {
        String version = "15.6.1";
        String language = "ko_KR";
        String name = "Aatrox";
        String jsonResponse = """
                {"type":"champion","format":"standAloneComplex","version":"15.6.1","data":{"Aatrox":{"id":"Aatrox","key":"266","name":"아트록스","title":"다르킨의 검","image":{"full":"Aatrox.png","sprite":"champion0.png","group":"champion","x":0,"y":0,"w":48,"h":48},"skins":[{"id":"266000","num":0,"name":"default","chromas":false},{"id":"266001","num":1,"name":"정의의 아트록스","chromas":false},{"id":"266002","num":2,"name":"메카 아트록스","chromas":true},{"id":"266003","num":3,"name":"바다 사냥꾼 아트록스","chromas":false},{"id":"266007","num":7,"name":"핏빛달 아트록스","chromas":false},{"id":"266008","num":8,"name":"프레스티지 핏빛달 아트록스","chromas":false},{"id":"266009","num":9,"name":"승리의 아트록스","chromas":true},{"id":"266011","num":11,"name":"오디세이 아트록스","chromas":true},{"id":"266020","num":20,"name":"프레스티지 핏빛달 아트록스 (2022)","chromas":false},{"id":"266021","num":21,"name":"달을 삼킨 아트록스","chromas":true},{"id":"266030","num":30,"name":"DRX 아트록스","chromas":true},{"id":"266031","num":31,"name":"프레스티지 DRX 아트록스","chromas":false},{"id":"266033","num":33,"name":"태고족 아트록스","chromas":true}],"lore":"한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를 열망한다.","blurb":"한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를...","allytips":["공격 성공률을 높이려면 다르킨의 검을 사용하는 동안 파멸의 돌진을 사용하세요.","지옥사슬 같은 군중 제어 스킬이나 아군의 이동 불가 효과를 통해 다르킨의 검을 쉽게 적중시킬 수 있습니다.","전투를 시작할 준비가 되면 세계의 종결자를 사용하세요."],"enemytips":["아트록스의 공격은 미리 표시되므로 공격이 예상되는 지역에서 벗어나세요.","아트록스를 향해 이동하거나 양옆으로 이동하면 지옥사슬에서 더 쉽게 벗어날 수 있습니다.","아트록스가 궁극기를 사용하면 부활하지 못하도록 거리를 유지하세요."],"tags":["Fighter"],"partype":"피의 샘","info":{"attack":8,"defense":4,"magic":3,"difficulty":4},"stats":{"hp":650,"hpperlevel":114,"mp":0,"mpperlevel":0,"movespeed":345,"armor":38,"armorperlevel":4.8,"spellblock":32,"spellblockperlevel":2.05,"attackrange":175,"hpregen":3,"hpregenperlevel":0.5,"mpregen":0,"mpregenperlevel":0,"crit":0,"critperlevel":0,"attackdamage":60,"attackdamageperlevel":5,"attackspeedperlevel":2.5,"attackspeed":0.651},"spells":[{"id":"AatroxQ","name":"다르킨의 검","description":"아트록스가 대검을 내리쳐 물리 피해를 줍니다. 세 번까지 휘두를 수 있으며 각 공격은 피해 범위가 다릅니다.","tooltip":"아트록스가 대검을 내리쳐 <physicalDamage>{{ qdamage }}의 물리 피해</physicalDamage>를 입힙니다. 끝에 적중한 적을 잠깐 <status>공중으로 띄워 올리고</status> <physicalDamage>{{ qedgedamage }}</physicalDamage>의 피해를 입힙니다. 이 스킬은 두 번 <recast>재사용</recast>할 수 있으며 다시 사용할 때마다 범위 모양이 변하고 이전보다 25% 더 많은 피해를 입힙니다.{{ spellmodifierdescriptionappend }}","leveltip":{"label":["재사용 대기시간","피해량","총 공격력 %"],"effect":["{{ cooldown }} -> {{ cooldownNL }}","{{ qbasedamage }} -> {{ qbasedamageNL }}","{{ qtotaladratio*100.000000 }}% -> {{ qtotaladrationl*100.000000 }}%"]},"maxrank":5,"cooldown":[14,12,10,8,6],"cooldownBurn":"14/12/10/8/6","cost":[0,0,0,0,0],"costBurn":"0","datavalues":{},"effect":[null,[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]],"effectBurn":[null,"0","0","0","0","0","0","0","0","0","0"],"vars":[],"costType":"소모값 없음","maxammo":"-1","range":[25000,25000,25000,25000,25000],"rangeBurn":"25000","image":{"full":"AatroxQ.png","sprite":"spell0.png","group":"spell","x":384,"y":48,"w":48,"h":48},"resource":"소모값 없음"},{"id":"AatroxW","name":"지옥사슬","description":"아트록스가 지면을 내리쳐 처음 맞힌 적에게 피해를 줍니다. 대상이 챔피언 또는 대형 몬스터인 경우 일정 시간 안에 해당 지역을 벗어나지 않으면 중앙으로 끌려가 다시 피해를 받습니다.","tooltip":"아트록스가 사슬을 발사하여 처음 적중한 적을 {{ wslowduration }}초 동안 {{ wslowpercentage*-100 }}%만큼 <status>둔화</status>시키고 <magicDamage>{{ wdamage }}의 마법 피해</magicDamage>를 입힙니다. 챔피언과 대형 정글 몬스터는 {{ wslowduration }}초 안에 해당 지역을 벗어나지 않으면 중심으로 <status>끌려가</status> 다시 같은 양의 피해를 입습니다.{{ spellmodifierdescriptionappend }}","leveltip":{"label":["재사용 대기시간","피해량","둔화"],"effect":["{{ cooldown }} -> {{ cooldownNL }}","{{ wbasedamage }} -> {{ wbasedamageNL }}","{{ wslowpercentage*-100.000000 }}% -> {{ wslowpercentagenl*-100.000000 }}%"]},"maxrank":5,"cooldown":[20,18,16,14,12],"cooldownBurn":"20/18/16/14/12","cost":[0,0,0,0,0],"costBurn":"0","datavalues":{},"effect":[null,[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]],"effectBurn":[null,"0","0","0","0","0","0","0","0","0","0"],"vars":[],"costType":"소모값 없음","maxammo":"-1","range":[825,825,825,825,825],"rangeBurn":"825","image":{"full":"AatroxW.png","sprite":"spell0.png","group":"spell","x":432,"y":48,"w":48,"h":48},"resource":"소모값 없음"},{"id":"AatroxE","name":"파멸의 돌진","description":"기본 지속 효과로 아트록스가 적 챔피언에게 피해를 입히면 체력을 회복합니다. 사용 시, 아트록스가 지정한 방향으로 돌진합니다.","tooltip":"<spellPassive>기본 지속 효과:</spellPassive> 아트록스가 챔피언에게 가한 피해의 <lifeSteal>{{ totalevamp }}</lifeSteal>만큼 체력을 회복합니다.<br /><br /><spellActive>사용 시:</spellActive> 아트록스가 돌진합니다. 이 스킬은 다른 스킬이 진행되는 동안 사용할 수 있습니다.{{ spellmodifierdescriptionappend }}","leveltip":{"label":["재사용 대기시간"],"effect":["{{ cooldown }} -> {{ cooldownNL }}"]},"maxrank":5,"cooldown":[9,8,7,6,5],"cooldownBurn":"9/8/7/6/5","cost":[0,0,0,0,0],"costBurn":"0","datavalues":{},"effect":[null,[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]],"effectBurn":[null,"0","0","0","0","0","0","0","0","0","0"],"vars":[],"costType":"소모값 없음","maxammo":"-1","range":[25000,25000,25000,25000,25000],"rangeBurn":"25000","image":{"full":"AatroxE.png","sprite":"spell0.png","group":"spell","x":0,"y":96,"w":48,"h":48},"resource":"소모값 없음"},{"id":"AatroxR","name":"세계의 종결자","description":"아트록스가 악마의 힘을 해방하여 근처 적 미니언에게 공포를 주고 자신의 공격력과 체력 회복량, 이동 속도가 증가합니다. 아트록스가 챔피언 처치에 관여하면 이 효과의 지속시간이 연장됩니다.","tooltip":"아트록스가 진정한 악마의 모습을 드러내 근처 미니언이 {{ rminionfearduration }}초 동안 <status>공포</status>에 떨게 하고 <speed>이동 속도가 {{ rmovementspeedbonus*100 }}%</speed> 증가했다가 {{ rduration }}초에 걸쳐 원래대로 돌아옵니다. 지속시간 동안 <scaleAD>공격력이 {{ rtotaladamp*100 }}%</scaleAD>, <healing>자신에 대한 체력 회복 효과가 {{ rhealingamp*100 }}%</healing> 증가합니다.<br /><br />챔피언 처치 관여 시 이 효과의 지속시간이 {{ rextension }}초 늘어나고 <speed>이동 속도</speed> 효과가 초기화됩니다.{{ spellmodifierdescriptionappend }}","leveltip":{"label":["총 공격력 증가","회복량 증가","이동 속도","재사용 대기시간"],"effect":["{{ rtotaladamp*100.000000 }}% -> {{ rtotaladampnl*100.000000 }}%","{{ rhealingamp*100.000000 }}% -> {{ rhealingampnl*100.000000 }}%","{{ rmovementspeedbonus*100.000000 }}% -> {{ rmovementspeedbonusnl*100.000000 }}%","{{ cooldown }} -> {{ cooldownNL }}"]},"maxrank":3,"cooldown":[120,100,80],"cooldownBurn":"120/100/80","cost":[0,0,0],"costBurn":"0","datavalues":{},"effect":[null,[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]],"effectBurn":[null,"0","0","0","0","0","0","0","0","0","0"],"vars":[],"costType":"소모값 없음","maxammo":"-1","range":[25000,25000,25000],"rangeBurn":"25000","image":{"full":"AatroxR.png","sprite":"spell0.png","group":"spell","x":48,"y":96,"w":48,"h":48},"resource":"소모값 없음"}],"passive":{"name":"사신 태세","description":"주기적으로 아트록스의 기본 공격이 대상 최대 체력에 비례하여 추가 <physicalDamage>물리 피해</physicalDamage>를 입히고 자신의 체력을 회복합니다. ","image":{"full":"Aatrox_Passive.png","sprite":"passive0.png","group":"passive","x":0,"y":0,"w":48,"h":48}},"recommended":[]}}}
                """;

        String url = "https://ddragon.leagueoflegends.com/cdn/" + version + "/data/" + language + "/champion/" + name + ".json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLChampionDetail detail = lolService.getChampionDetail(version, language, name);

        assertThat(detail).isNotNull();
        assertThat(detail.getVersion()).isEqualTo(version);
        assertThat(detail.getData()).isNotNull();
        assertThat(detail.getData().get(name).getId()).isEqualTo(name);
    }

    @Test
    public void testGetAccountByRiotId() {
        String gameName = "테스트";
        String tagLine = "KR1";

        String jsonResponse = """
            {
                "puuid": "testPuuid",
                "gameName": "테스트",
                "tagLine": "KR1"
            }
            """;
        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        RiotAccountDto account = lolService.getAccountByRiotId(gameName, tagLine);

        assertThat(account).isNotNull();
        assertThat(account.getPuuid()).isEqualTo("testPuuid");

        mockServer.verify();
    }

    @Test
    public void testGetSummonerByPuuid() {
        String region = "kr";
        String puuid = "testPuuid";
        String jsonResponse = """
            {
                "accountId": "testAccountId",
                "profileIconId": 1,
                "revisionDate": 1630000000000,
                "id": "testId",
                "puuid": "testPuuid",
                "summonerLevel": 30
            }
            """;
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/summoner/v4/summoners/by-puuid/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLSummonerDto summoner = lolService.getSummonerByPuuid(region, puuid);

        assertThat(summoner).isNotNull();
        assertThat(summoner.getPuuid()).isEqualTo("testPuuid");
        assertThat(summoner.getAccountId()).isEqualTo("testAccountId");
        assertThat(summoner.getProfileIconId()).isEqualTo(1);
        assertThat(summoner.getRevisionDate()).isEqualTo(1630000000000L);
        assertThat(summoner.getId()).isEqualTo("testId");
        assertThat(summoner.getSummonerLevel()).isEqualTo(30L);

        mockServer.verify();
    }

    @Test
    public void testGetMatchIdsByPuuid() {
        String region = "kr";
        String puuid = "testPuuid";
        LoLMatchIdsQueryDto query = LoLMatchIdsQueryDto.builder().build();
        String jsonResponse = """
            [
                "testMatchId1",
                "testMatchId2",
                "testMatchId3",
                "testMatchId4"
            ]
            """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/lol/match/v5/matches/by-puuid/" + puuid + "/ids");
        uriBuilder.queryParam("start", query.getStart());
        uriBuilder.queryParam("count", query.getCount());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        String[] matchIds = lolService.getMatchIdsByPuuid(region, puuid, query);

        assertThat(matchIds).isNotNull();
        assertThat(matchIds.length).isEqualTo(4);
        assertThat(matchIds[0]).isEqualTo("testMatchId1");
        assertThat(matchIds[1]).isEqualTo("testMatchId2");
        assertThat(matchIds[2]).isEqualTo("testMatchId3");
        assertThat(matchIds[3]).isEqualTo("testMatchId4");

        mockServer.verify();
    }

    @Test
    public void testGetMatchById() {
        String region = "kr";
        String matchId = "testMatchId";
        String jsonResponse = """
                {
                    "metadata": {
                        "dataVersion": "testVersion",
                        "matchId": "testMatchId",
                        "participants": [
                            "testParticipantId1",
                            "testParticipantId2",
                            "testParticipantId3"
                        ]
                    },
                    "info": {
                        "endOfGameResult": "win",
                        "gameCreation": 100,
                        "gameDuration": 50,
                        "participants": [],
                        "gameMode": "testMode"
                    }
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/lol/match/v5/matches/" + matchId);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLMatchDto match = lolService.getMatchById(region, matchId);

        assertThat(match).isNotNull();
        assertThat(match.getMetadata().getDataVersion()).isEqualTo("testVersion");
        assertThat(match.getMetadata().getMatchId()).isEqualTo("testMatchId");
        assertThat(match.getMetadata().getParticipants().get(0)).isEqualTo("testParticipantId1");
        assertThat(match.getMetadata().getParticipants().get(1)).isEqualTo("testParticipantId2");
        assertThat(match.getMetadata().getParticipants().get(2)).isEqualTo("testParticipantId3");
        assertThat(match.getInfo().getEndOfGameResult()).isEqualTo("win");
        assertThat(match.getInfo().getGameCreation()).isEqualTo(100);
        assertThat(match.getInfo().getGameDuration()).isEqualTo(50);
        assertThat(match.getInfo().getParticipants().size()).isEqualTo(0);
        assertThat(match.getInfo().getGameMode()).isEqualTo("testMode");

        mockServer.verify();
    }

    @Test
    public void testGetChallengerLeague() {
        String region = "kr";
        QueueType queueType = QueueType.RANKED_SOLO_5x5;
        String jsonResponse = """
                {
                    "leagueId": "testLeagueId",
                    "entries": [{
                        "freshBlood": false,
                        "wins": 5,
                        "puuid": "testPuuid"
                    }],
                    "tier": "challenger",
                    "name": "testChallenger",
                    "queue": "RANKED_SOLO_5x5"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/challengerleagues/by-queue/" + queueType.getValue());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueListDto challengerLeague = lolService.getChallengerLeague(region, queueType);

        assertThat(challengerLeague).isNotNull();
        assertThat(challengerLeague.getLeagueId()).isEqualTo("testLeagueId");
        assertThat(challengerLeague.getEntries().get(0).isFreshBlood()).isEqualTo(false);
        assertThat(challengerLeague.getEntries().get(0).getWins()).isEqualTo(5);
        assertThat(challengerLeague.getEntries().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(challengerLeague.getTier()).isEqualTo("challenger");
        assertThat(challengerLeague.getName()).isEqualTo("testChallenger");
        assertThat(challengerLeague.getQueue()).isEqualTo("RANKED_SOLO_5x5");

        mockServer.verify();
    }

    @Test
    public void testGetGrandmasterLeague() {
        String region = "kr";
        QueueType queueType = QueueType.RANKED_FLEX_SR;
        String jsonResponse = """
                {
                    "leagueId": "testLeagueId",
                    "entries": [{
                        "freshBlood": true,
                        "wins": 3,
                        "puuid": "testPuuid"
                    }],
                    "tier": "grandmaster",
                    "name": "testGrandmaster",
                    "queue": "RANKED_FLEX_SR"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/grandmasterleagues/by-queue/" + queueType.getValue());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueListDto grandmasterLeague = lolService.getGrandmasterLeague(region, queueType);

        assertThat(grandmasterLeague).isNotNull();
        assertThat(grandmasterLeague.getLeagueId()).isEqualTo("testLeagueId");
        assertThat(grandmasterLeague.getEntries().get(0).isFreshBlood()).isEqualTo(true);
        assertThat(grandmasterLeague.getEntries().get(0).getWins()).isEqualTo(3);
        assertThat(grandmasterLeague.getEntries().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(grandmasterLeague.getTier()).isEqualTo("grandmaster");
        assertThat(grandmasterLeague.getName()).isEqualTo("testGrandmaster");
        assertThat(grandmasterLeague.getQueue()).isEqualTo("RANKED_FLEX_SR");

        mockServer.verify();
    }

    @Test
    public void testGetMasterLeague() {
        String region = "kr";
        QueueType queueType = QueueType.RANKED_FLEX_TT;
        String jsonResponse = """
                {
                    "leagueId": "testLeagueId",
                    "entries": [{
                        "freshBlood": false,
                        "wins": 1,
                        "puuid": "testPuuid"
                    }],
                    "tier": "master",
                    "name": "testMaster",
                    "queue": "RANKED_FLEX_TT"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/masterleagues/by-queue/" + queueType.getValue());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueListDto masterLeague = lolService.getMasterLeague(region, queueType);

        assertThat(masterLeague).isNotNull();
        assertThat(masterLeague.getLeagueId()).isEqualTo("testLeagueId");
        assertThat(masterLeague.getEntries().get(0).isFreshBlood()).isEqualTo(false);
        assertThat(masterLeague.getEntries().get(0).getWins()).isEqualTo(1);
        assertThat(masterLeague.getEntries().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(masterLeague.getTier()).isEqualTo("master");
        assertThat(masterLeague.getName()).isEqualTo("testMaster");
        assertThat(masterLeague.getQueue()).isEqualTo("RANKED_FLEX_TT");

        mockServer.verify();
    }

    @Test
    public void testGetLeagueEntriesByPuuid() {
        String region = "kr";
        String puuid = "testPuuid";
        String jsonResponse = """
                [
                    {
                        "leagueId": "testLeagueId1",
                        "queueType": "RANKED_SOLO_5x5",
                        "tier": "PLATINUM",
                        "rank": "III",
                        "summonerId": "testSummonerId",
                        "puuid": "testPuuid",
                        "leaguePoints": 12,
                        "wins": 11,
                        "losses": 10,
                        "veteran": false,
                        "inactive": false,
                        "freshBlood": false,
                        "hotStreak": false
                    },
                    {
                        "leagueId": "testLeagueId2",
                        "queueType": "RANKED_FLEX_SR",
                        "tier": "EMERALD",
                        "rank": "I",
                        "summonerId": "testSummonerId",
                        "puuid": "testPuuid",
                        "leaguePoints": 64,
                        "wins": 20,
                        "losses": 13,
                        "veteran": false,
                        "inactive": false,
                        "freshBlood": false,
                        "hotStreak": false
                    }
                ]
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/entries/by-puuid/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueEntryDto[] entries = lolService.getLeagueEntriesByPuuid(region, puuid);

        assertThat(entries).isNotNull();
        assertThat(entries.length).isEqualTo(2);
        assertThat(entries[0].getLeagueId()).isEqualTo("testLeagueId1");
        assertThat(entries[0].getQueueType()).isEqualTo("RANKED_SOLO_5x5");
        assertThat(entries[0].getTier()).isEqualTo("PLATINUM");
        assertThat(entries[0].getRank()).isEqualTo("III");
        assertThat(entries[0].getSummonerId()).isEqualTo("testSummonerId");
        assertThat(entries[0].getPuuid()).isEqualTo("testPuuid");
        assertThat(entries[1].getLeagueId()).isEqualTo("testLeagueId2");
        assertThat(entries[1].getQueueType()).isEqualTo("RANKED_FLEX_SR");
        assertThat(entries[1].getTier()).isEqualTo("EMERALD");
        assertThat(entries[1].getRank()).isEqualTo("I");
        assertThat(entries[1].getSummonerId()).isEqualTo("testSummonerId");
        assertThat(entries[1].getPuuid()).isEqualTo("testPuuid");

        mockServer.verify();
    }

    @Test
    public void testGetChampionRotations() {
        String region = "kr";
        String responseJson = """
                {
                    "freeChampionIds": [
                        12,
                        24,
                        25,
                        29,
                        30,
                        38,
                        50,
                        68,
                        74,
                        75,
                        81,
                        103,
                        115,
                        119,
                        127,
                        136,
                        234,
                        240,
                        876,
                        902
                    ],
                    "freeChampionIdsForNewPlayers": [
                        222,
                        254,
                        33,
                        82,
                        131,
                        350,
                        54,
                        17,
                        18,
                        37,
                        51,
                        145,
                        134,
                        89,
                        875,
                        80,
                        115,
                        91,
                        113,
                        112
                    ],
                    "maxNewPlayerLevel": 10
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/platform/v3/champion-rotations");

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLChampionRotationDto championRotations = lolService.getChampionRotations(region);

        assertThat(championRotations).isNotNull();
        assertThat(championRotations.getFreeChampionIdsForNewPlayers().size()).isEqualTo(20);
        assertThat(championRotations.getFreeChampionIdsForNewPlayers().size()).isEqualTo(20);
        assertThat(championRotations.getMaxNewPlayerLevel()).isEqualTo(10);
    }

    @Test
    public void testGetCurrentGame() {
        String region = "kr";
        String puuid = "testPuuid";
        String responseJson = """
                {
                    "gameId": 12,
                    "gameType": "RANK",
                    "gameStartTime": 1,
                    "mapId": 2,
                    "gameMode": "testMode"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/spectator/v5/active-games/by-summoner/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLCurrentGameInfoDto currentGame = lolService.getCurrentGame(region, puuid);

        assertThat(currentGame).isNotNull();
        assertThat(currentGame.getGameId()).isEqualTo(12);
        assertThat(currentGame.getGameType()).isEqualTo("RANK");
        assertThat(currentGame.getGameStartTime()).isEqualTo(1);
        assertThat(currentGame.getMapId()).isEqualTo(2);
        assertThat(currentGame.getGameMode()).isEqualTo("testMode");

        mockServer.verify();
    }

    @Test
    public void testGetClashPlayer() {
        String region = "kr";
        String puuid = "testPuuid";
        String responseJson = """
                [
                    {
                        "summonerId": "testSummonerId",
                        "puuid": "testPuuid",
                        "teamId": "testTeamId",
                        "position": "TOP",
                        "role": "MEMBER"
                    }
                ]
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/clash/v1/players/by-puuid/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLPlayerDto[] players = lolService.getClashPlayer(region, puuid);

        assertThat(players).isNotNull();
        assertThat(players[0].getSummonerId()).isEqualTo("testSummonerId");
        assertThat(players[0].getPuuid()).isEqualTo("testPuuid");
        assertThat(players[0].getTeamId()).isEqualTo("testTeamId");
        assertThat(players[0].getPosition()).isEqualTo("TOP");
        assertThat(players[0].getRole()).isEqualTo("MEMBER");

        mockServer.verify();
    }

    @Test
    public void testGetClashTeam() {
        String region = "kr";
        String teamId = "testTeamId";
        String responseJson = """
                {
                    "id": "testTeamId",
                    "tournamentId": 1,
                    "name": "testTeam",
                    "iconId": 1,
                    "tier": 3,
                    "captain": "captainId",
                    "abbreviation": "abbreviation",
                    "players": [
                        {
                            "summonerId": "testSummonerId",
                            "puuid": "testPuuid",
                            "teamId": "testTeamId",
                            "position": "BOTTOM",
                            "role": "CAPTAIN"
                        }
                    ]
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/clash/v1/teams/" + teamId);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLTeamDto team = lolService.getClashTeam(region, teamId);

        assertThat(team).isNotNull();
        assertThat(team.getId()).isEqualTo("testTeamId");
        assertThat(team.getTournamentId()).isEqualTo(1);
        assertThat(team.getName()).isEqualTo("testTeam");
        assertThat(team.getIconId()).isEqualTo(1);
        assertThat(team.getTier()).isEqualTo(3);
        assertThat(team.getCaptain()).isEqualTo("captainId");
        assertThat(team.getAbbreviation()).isEqualTo("abbreviation");
        assertThat(team.getPlayers().get(0).getSummonerId()).isEqualTo("testSummonerId");
        assertThat(team.getPlayers().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(team.getPlayers().get(0).getTeamId()).isEqualTo("testTeamId");
        assertThat(team.getPlayers().get(0).getPosition()).isEqualTo("BOTTOM");
        assertThat(team.getPlayers().get(0).getRole()).isEqualTo("CAPTAIN");

        mockServer.verify();
    }
}
