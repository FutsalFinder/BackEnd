package futsal.futsalMatch.config.platform;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class PuzzleConfig implements PlatformConfig {
    private final String platform = "PUZZLE";
    private final String date = "match_date";
    private final String time = "match_time";
    private final String region = "ground_region";
    private final String matchTitle = "groundName"; //ground_info {_id:.., groundName : "용산 더베이스.."}
    private final String mainStadium = "ground_name";
    private final String subStadium = "ground_name";
    private final String matchType = "isRank";
    private final String sex = "sex";
    private final String level = "level";
    private final String matchVS = "match_vs";
    private final String curPlayer = "apply_member";
    private final String maxPlayer = "max"; //personnel {min: 10, max: 18}
    private final String price = "match_price";
    private final String link = "_id";
    private final String requestBaseURL = "https://puzzleplay.kr/filter";
    private final String matchLinkBaseURL = "https://puzzleplay.kr/social/";

    private final Map<String, String> customFields = Map.of(
            "SEOUL_CODE", "5e8190a8bb3b302ce2e03279",
            "GYEONGGI_NAMBU_CODE", "65126e1929b8b579c68f372e",
            "GYEONGGI_DONGBU_CODE", "65126e3929b8b579c68f372f"
    );

}
