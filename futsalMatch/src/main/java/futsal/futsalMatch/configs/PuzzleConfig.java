package futsal.futsalMatch.configs;

public class PuzzleConfig {
    public static final String date = "match_date";

    public static final String time = "match_time";

    public static final String region = "ground_region";

    public static final String match_title = "groundName"; //ground_info {_id:.., groundName : "용산 더베이스.."}
    //public static final String main_stadium = "ground_name";
    //public static final String sub_stadium = "ground_name";
    public static final String match_type = "isRank";
    public static final String sex = "sex";

    //public static final String level = "level";
    public static final String match_vs = "match_vs";
    public static final String cur_player = "apply_member";
    public static final String max_player = "max"; //personnel {min: 10, max: 18}
    public static final String price = "match_price";
    public static final String link = "_id";
    public static final String baseUrl = "https://puzzleplay.kr/filter";
    public static final String matchLinkBaseUrl = "https://puzzleplay.kr/social/";

    public static class PuzzleRegion{
        public static final String SEOUL = "5e8190a8bb3b302ce2e03279";
        public static final String GYEONGGI_NAMBU = "65126e1929b8b579c68f372e";
        public static final String GYEONGGI_DONGBU = "65126e3929b8b579c68f372f";
    }
}
