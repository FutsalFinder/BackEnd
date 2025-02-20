package futsal.futsalMatch.domain;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchInfo {
    private String platform;
    private String date;
    private String time;
    private String region;
    private String match_title;
    private String main_stadium;
    private String sub_stadium;
    private String match_type;
    private String sex;
    private String level;
    private String match_vs;
    private String cur_player;
    private String max_player;
    private String price;
    private String link;
}
