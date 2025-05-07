package futsal.futsalMatch.config.platform;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PlabConfig implements PlatformConfig {
    private final String platform = "PLAB";
    private final String fullNameInKorean = "플랩풋볼";
    private final String date = "schedule";
    private final String time = "schedule";
    private final String region = "area_group_name";
    private final String matchTitle = "label_title";
    private final String mainStadium = "stadium_group_name";
    private final String subStadium = "label_stadium2";
    private final String matchType = "type";
    private final String sex = "sex";
    private final String level = "level";
    private final String matchVS = "player_cnt";
    private final String curPlayer = "confirm_cnt";
    private final String maxPlayer = "max_player_cnt";
    private final String price = "fee";
    private final String link = "id";
    private final String requestBaseURL = "https://www.plabfootball.com/api/v2/integrated-matches/";
    private final String matchLinkBaseURL = "https://www.plabfootball.com/match/";

    private final int priority = 1;
}
