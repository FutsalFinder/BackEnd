package futsal.futsalMatch.config.platform;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class IamConfig implements PlatformConfig {
    private final String platform = "IAM";
    private final String fullNameInKorean = "아이엠그라운드";
    private final String date = "start_date";
    private final String time = "start_time";
    private final String region = "fAddress";
    private final String matchTitle = "fName";
    private final String mainStadium = "fName";
    private final String subStadium = "fName";
    private final String matchType = "type";
    private final String sex = "gender_type";
    private final String level = "lev_type";
    private final String matchVS = "rule";
    private final String curPlayer = "nowcapacity";
    private final String maxPlayer = "max_cap";
    private final String price = "unit_price";
    private final String link = "s_match_num";
    private final String requestBaseURL = "https://m.iamground.kr/api/v1/s_match/getsmatchlist";
    private final String matchLinkBaseURL = "https://m.iamground.kr/futsal/s_match/detail/";

    private final int priority = 5;
}
