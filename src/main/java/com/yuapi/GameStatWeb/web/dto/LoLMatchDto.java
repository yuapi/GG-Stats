package com.yuapi.GameStatWeb.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoLMatchDto {
    private MetadataDto metadata;
    private InfoDto info;

    @Getter
    @Builder
    public static class MetadataDto {
        private String dataVersion;
        private String matchId;
        private List<String> participants;
    }

    @Getter
    @Builder
    public static class InfoDto {
        private String endOfGameResult;
        private Long gameCreation;
        private Long gameDuration;
        private Long gameEndTimestamp;
        private Long gameId;
        private String gameMode;
        private String gameName;
        private Long gameStartTimestamp;
        private String gameType;
        private String gameVersion;
        private int mapId;
        private List<ParticipantDto> participants;
        private String platformId;
        private int queueId;
        private List<TeamDto> teams;
        private String tournamentCode;
    }

    @Getter
    @Builder
    public static class ParticipantDto {
        private int allInPings;
        private int assistMePings;
        private int assist;
        private int baronKills;
        private int bountyLevel;
        private int champExperience;
        private int champLevel;
        private int championId;
        private String championName;
        private int commandPings;
        private int championTransform;
        private int consumablesPurchased;
        private ChallengesDto challenges;
        private int damageDealtToBuildings;
        private int damageDealtToObjectives;
        private int damageDealtToTurrets;
        private int damageSelfMitigated;
        private int deaths;
        private int detectorWardsPlaced;
        private int doubleKills;
        private int dragonKills;
        private boolean eligibleForProgression;
        private int enemyMissingPings;
        private int enemyVisionPings;
        private boolean firstBloodAssist;
        private boolean firstBloodKill;
        private boolean firstTowerAssist;
        private boolean firstTowerKill;
        private boolean gameEndedInEarlySurrender;
        private boolean gameEndedInSurrender;
        private int holdPings;
        private int getBackPings;
        private int goldEarned;
        private int goldSpent;
        private String individualPosition;
        private int inhibitorKills;
        private int inhibitorTakedowns;
        private int inhibitorsLost;
        private int item0;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private int item6;
        private int itemsPurchased;
        private int killingSprees;
        private int kills;
        private String lane;
        private int largestCriticalStrike;
        private int largestKillingSpree;
        private int largestMultiKill;
        private int longestTimeSpentLiving;
        private int magicDamageDealt;
        private int magicDamageDealtToChampions;
        private int magicDamageTaken;
        private MissionsDto missions;
        private int neutralMissionsKilled;
        private int needVisionPings;
        private int nexusKills;
        private int nexusTakedowns;
        private int nexusLost;
        private int objectivesStolen;
        private int objectivesStolenAssists;
        private int onMyWayPings;
        private int participantId;
        private int playerScore0;
        private int playerScore1;
        private int playerScore2;
        private int playerScore3;
        private int playerScore4;
        private int playerScore5;
        private int playerScore6;
        private int playerScore7;
        private int playerScore8;
        private int playerScore9;
        private int playerScore10;
        private int playerScore11;
        private int pentaKills;
        private PerksDto perks;
        private int physicalDamageDealt;
        private int physicalDamageDealtToChampions;
        private int physicalDamageTaken;
        private int placement;
        private int playerAugment1;
        private int playerAugment2;
        private int playerAugment3;
        private int playerAugment4;
        private int playerSubteamId;
        private int pushPings;
        private int profileIcon;
        private String puuid;
        private int quadraKills;
        private String riotIdGameName;
        private String riotIdTagLine;
        private String role;
        private int sightWardsBoughtInGame;
        private int spell1Casts;
        private int spell2Casts;
        private int spell3Casts;
        private int spell4Casts;
        private int subteamPlacement;
        private int summoner1Casts;
        private int summoner1Id;
        private int summoner2Casts;
        private int summoner2Id;
        private String summonerId;
        private int summonerLevel;
        private String summonerName;
        private boolean teamEarlySurrendered;
        private int teamId;
        private String teamPosition;
        private int timeCCingOthers;
        private int timePlayed;
        private int totalAllyJungleMinionsKilled;
        private int totalDamageDealt;
        private int totalDamageDealtToChampions;
        private int totalDamageShieldedOnTeammates;
        private int totalDamageTaken;
        private int totalEnemyJungleMinionsKilled;
        private int totalHeal;
        private int totalHealsOnTeammates;
        private int totalMinionsKilled;
        private int totalTimeCCDealt;
        private int totalTimeSpentDead;
        private int totalUnitsHealed;
        private int tripleKills;
        private int trueDamageDealt;
        private int trueDamageDealtToChampions;
        private int trueDamageTaken;
        private int turretKills;
        private int turretTakedowns;
        private int turretsLost;
        private int unrealKills;
        private int visionScore;
        private int visionClearedPings;
        private int visionWardBoughtInGame;
        private int wardsKilled;
        private int wardsPlaced;
        private boolean win;
    }

    @Getter
    @Builder
    public static class ChallengesDto {
        private int _12AssistStreakCount;
        private int baronBuffGoldAdvantageOverThreshold;
        private float controlWardTimeCoverageInRiverOrEnemyHalf;
        private int earliestBaron;
        private int earliestDragonTakedown;
        private int earliestElderDragon;
        private int earlyLaningPhaseGoldExpAdvantage;
        private int fasterSupportQuestCompletion;
        private int fastestLegendary;
        private int hadAfkTeammate;
        private int highestChampionDamage;
        private int highestCrowedControlScore;
        private int highestWardKills;
        private int junglerKillsEarlyJungle;
        private int killsOnLanersEarlyJungleAsJungler;
        private int laningPhaseGoldExpAdvantage;
        private int legendaryCount;
        private float maxCsAdvantageOnLaneOpponent;
        private int maxLevelLeadLaneOpponent;
        private int mostWardsDestroyedOneSweeper;
        private int mythicItemUsed;
        private int playedChampSelectPosition;
        private int soloTurretsLategame;
        private int takedownsFirst25Minutes;
        private int teleportTakedowns;
        private int thirdInhibitorDestroyedTime;
        private int threeWardsOneSweeperCount;
        private float visionScoreAdvantageLaneOpponent;
        private int InfernalScalePickup;
        private int fistBumpParticipation;
        private int voidMonsterKill;
        private int abilityUses;
        private int acesBefore15Minutes;
        private float alliedJungleMonsterKills;
        private int baronTakedowns;
        private int blastConeOppositeOpponentCount;
        private int bountyGold;
        private int buffsStolen;
        private int completeSupportQuestInTime;
        private int controlWardsPlaced;
        private float damagePerMinute;
        private float damageTakenOnTeamPercentage;
        private int dancedWithRiftHerald;
        private int deathsByEnemyChamps;
        private int dodgeSkillShotsSmallWindow;
        private int doubleAces;
        private int dragonTakedowns;
        private List<Integer> legendaryItemUsed;
        private float effectiveHealAndShielding;
        private int elderDragonKillsWithOpposingSoul;
        private int elderDragonMultikills;
        private int enemyChampionImmobilizations;
        private float enemyJungleMonsterKills;
        private int epicMonsterKillsNearEnemyJungler;
        private int epicMonsterKillWithin30SecondsOfSpawn;
        private int epicMonsterSteals;
        private int epicMonsterStolenWithoutSmite;
        private int firstTurretKilled;
        private float firstTurretKilledTime;
        private int flawlessAces;
        private int fullTeamTakedown;
        private float gameLength;
        private int getTakedownsInAllLanesEarlyJungleAsLaner;
        private float goldPerMinute;
        private int hadOpenNexus;
        private int immobilizeAndKillWithAlly;
        private int initialBuffCount;
        private int initialCrabCount;
        private float jungleCsBefore10Minutes;
        private int junglerTakedownsNearDamagedEpicMonster;
        private float kda;
        private int killAfterHiddenWithAlly;
        private int killedChampTookFullTeamDamageSurvived;
        private int KillingSprees;
        private float killParticipation;
        private int killsNearEnemyTurret;
        private int killsOnOtherLanesEarlyJungleAsLaner;
        private int killsOnRecentlyHealedByAramPack;
        private int killsUnderOwnTurret;
        private int killsWithHelpFromEpicMonster;
        private int knockEnemyIntoTeamAndKill;
        private int kTurretsDestroyedBeforePlatesFall;
        private int landSkillShotsEarlyGame;
        private int laneMinionsFirst10Minutes;
        private int lostAnInhibitor;
        private int maxKillDeficit;
        private int mejaisFullStackInTime;
        private float moreEnemyJungleThanOpponent;
        private int multiKillOneSpell;
        private int multikills;
        private int multikillsAfterAggressiveFlash;
        private int multiTurretRiftHeraldCount;
        private int outerTurretExecutesBefore10Minutes;
        private int outnumberedKills;
        private int outnumberedNexusKill;
        private int perfectDragonSoulsTaken;
        private int perfectGame;
        private int pickKillWithAlly;
        private int poroExplosions;
        private int quickCleanse;
        private int quickFirstTurret;
        private int quickSoloKills;
        private int riftHeraldTakedowns;
        private int saveAllyFromDeath;
        private int scuttleCrabKills;
        private float shortestTimeToAceFromFirstTakedown;
        private int skillshotsDodged;
        private int skillshotsHit;
        private int snowballsHit;
        private int soloBaronKills;
        private int SWARM_DefeatAatrox;
        private int SWARM_DefeatBriar;
        private int SWARM_DefeatMiniBosses;
        private int SWARM_EvolveWeapon;
        private int SWARM_Have3Passives;
        private int SWARM_KillEnemy;
        private float SWARM_PickupGold;
        private int SWARM_ReachLevel50;
        private int SWARM_Survive15Min;
        private int SWARM_WinWith5EvolvedWeapons;
        private int soloKills;
        private int stealthWardsPlaced;
        private int survivedSingleDigitHpCount;
        private int survivedThreeImmobilizesInFight;
        private int takedownOnFirstTurret;
        private int takedowns;
        private int takedownsAfterGainingLevelAdvantage;
        private int takedownsBeforeJungleMinionSpawn;
        private int takedownsFirstXMinutes;
        private int takedownsInAlcove;
        private int takedownsInEnemyFountain;
        private int teamBaronKills;
        private float teamDamagePercentage;
        private int teamElderDragonKills;
        private int teamRiftHeraldKills;
        private int tookLargeDamageSurvived;
        private int turretPlatesTaken;
        private int turretsTakenWithRiftHerald;
        private int turretTakedowns;
        private int twentyMinionsIn3SecondsCount;
        private int twoWardsOneSweeperCount;
        private int unseenRecalls;
        private float visionScorePerMinute;
        private int wardsGuarded;
        private int wardTakedowns;
        private int wardTakedownsBefore20M;
    }

    @Getter
    @Builder
    public static class MissionsDto {
        private int playerScore0;
        private int playerScore1;
        private int playerScore2;
        private int playerScore3;
        private int playerScore4;
        private int playerScore5;
        private int playerScore6;
        private int playerScore7;
        private int playerScore8;
        private int playerScore9;
        private int playerScore10;
        private int playerScore11;
    }

    @Getter
    @Builder
    public static class PerksDto {
        private PerkStatsDto statPerks;
        private List<PerkStyleDto> styles;
    }

    @Getter
    @Builder
    public static class PerkStatsDto {
        private int defense;
        private int flex;
        private int offense;
    }

    @Getter
    @Builder
    public static class PerkStyleDto {
        private String description;
        private List<PerkStyleSelectionDto> selections;
        private int style;
    }

    @Getter
    @Builder
    public static class PerkStyleSelectionDto {
        private int perk;
        private int var1;
        private int var2;
        private int var3;
    }

    @Getter
    @Builder
    public static class TeamDto {
        private List<BanDto> bans;
        private ObjectivesDto objectives;
        private int teamId;
        private boolean win;
    }

    @Getter
    @Builder
    public static class BanDto {
        private int championId;
        private int pickTurn;
    }

    @Getter
    @Builder
    public static class ObjectivesDto {
        private ObjectiveDto baron;
        private ObjectiveDto champion;
        private ObjectiveDto dragon;
        private ObjectiveDto horde;
        private ObjectiveDto inhibitor;
        private ObjectiveDto riftHerald;
        private ObjectiveDto tower;
    }

    @Getter
    @Builder
    public static class ObjectiveDto {
        private boolean first;
        private int kills;
    }
}
