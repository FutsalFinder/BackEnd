package futsal.futsalMatch.config.platform;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UrbanConfig implements PlatformConfig {
    private final String platform = "URBAN";
    private final String fullNameInKorean = "어반풋볼";
    private final String date = "span.date";
    private final String time = "li.time > span";
    private final String region = "";
    private final String matchTitle = "li.name > div > div";
    private final String mainStadium = "";
    private final String subStadium = "";
    private final String matchType = "";
    private final String sex = "";
    private final String level = "";
    private final String matchVS = "";
    private final String curPlayer = "";
    private final String maxPlayer = "";
    private final String price = "li.apply .button";
    private final String link = "data_id";
    private final String requestBaseURL = "https://urbanfootball.co.kr/result/result_get_data.php";
    private final String matchLinkBaseURL = "https://urbanfootball.co.kr/goods/goods_view.html?goods_no=";

    private final int priority = 4;
}
