package futsal.futsalMatch.config.platform;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WithConfig implements PlatformConfig {
    private final String platform = "WITH";
    private final String date = "date";
    private final String time = "play_time";
    private final String region = "mem_area";
    private final String matchTitle = "";
    private final String mainStadium = "mem_name";
    private final String subStadium = "stadium_name";
    private final String matchType = "type";
    private final String sex = "gender";
    private final String level = "game_level";
    private final String matchVS = "personnel_max";
    private final String curPlayer = "buy_count";
    private final String maxPlayer = "personnel_max";
    private final String price = "dis_price";
    private final String link = "idx";
    private final String requestBaseURL = "https://withfutsal.com/ajaxProcSocialMatch.php";
    private final String matchLinkBaseURL = "https://withfutsal.com/Sub/ground.php?block_idx=";
}
