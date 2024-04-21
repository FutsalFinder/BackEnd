package futsal.futsalMatch.converter;

import futsal.futsalMatch.configs.IamConfig;
import futsal.futsalMatch.domain.MatchInfos.*;
import futsal.futsalMatch.configs.PlabConfig;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.configs.WithConfig;
import futsal.futsalMatch.domain.MatchInfos.record.IamMatchInfo;
import futsal.futsalMatch.domain.MatchInfos.record.PlabMatchInfo;
import futsal.futsalMatch.domain.MatchInfos.record.PuzzleMatchInfo;
import futsal.futsalMatch.domain.MatchInfos.record.WithMatchInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatchInfoConverter {
    private static final String defaultValue = "null";

    private static String getValue(JSONObject matchInfoJson, String key) {
        try {
            return matchInfoJson.get(key).toString();
        } catch (JSONException je) {
            return defaultValue;
        }
    }

    public static MatchInfo convert(PlabMatchInfo plabMatchInfo) {
        MatchInfo matchInfo = new MatchInfo();
        JSONObject matchInfoJson = plabMatchInfo.matchInfoJson();

        String platform = "Plab";
        matchInfo.setPlatform(platform);

        String date = getValue(matchInfoJson, PlabConfig.date); //yyyy-mm-ddThh:mm
        if (!date.equals(defaultValue)) matchInfo.setDate(date.substring(0, 10));

        String time = getValue(matchInfoJson, PlabConfig.time); //yyyy-mm-ddThh:mm
        if (!time.equals(defaultValue)) matchInfo.setTime(time.substring(11, 16));

        String region = getValue(matchInfoJson, PlabConfig.region); //"서울", "경기"
        matchInfo.setRegion(region);

        String matchTitle = getValue(matchInfoJson, PlabConfig.match_title);
        matchInfo.setMatch_title(matchTitle);

        String mainStadium = getValue(matchInfoJson, PlabConfig.main_stadium);
        matchInfo.setMain_stadium(mainStadium);

        String subStadium = getValue(matchInfoJson, PlabConfig.sub_stadium);
        matchInfo.setSub_stadium(subStadium);

        String matchType = getValue(matchInfoJson, PlabConfig.match_type);
        if (matchType.equals("league")) matchType = "플랩팀리그";
        if (matchType.equals("starter")) matchType = "트레이닝 매치";
        if (matchType.equals("tshirt")) matchType = "티셔츠 매치";
        if (matchType.equals("3teams")) matchType = "일반매치";
        matchInfo.setMatch_type(matchType);

        String sex = getValue(matchInfoJson, PlabConfig.sex);
        if (sex.equals("1")) sex = "남자";
        if (sex.equals("0")) sex = "남녀모두";
        if (sex.equals("-1")) sex = "여자";
        matchInfo.setSex(sex);

        String level = getValue(matchInfoJson, PlabConfig.level);
        if (level.equals("0")) level = "모든레벨";
        if (level.equals("3")) level = "아마추어1 이하";
        if (level.equals("5")) level = "아마추어2 이상";
        matchInfo.setLevel(level);

        String match_vs = getValue(matchInfoJson, PlabConfig.match_vs);
        matchInfo.setMatch_vs(match_vs);

        String cur_player = getValue(matchInfoJson, PlabConfig.cur_player);
        matchInfo.setCur_player(cur_player);

        String max_player = getValue(matchInfoJson, PlabConfig.max_player);
        matchInfo.setMax_player(max_player);

        String price = getValue(matchInfoJson, PlabConfig.price);
        matchInfo.setPrice(price);

        String link = getValue(matchInfoJson, PlabConfig.link);
        matchInfo.setLink(PlabConfig.matchLinkBaseUrl + link);

        return matchInfo;
    }

    public static MatchInfo convert(PuzzleMatchInfo puzzleMatchInfo) {
        MatchInfo matchInfo = new MatchInfo();
        JSONObject matchInfoJson = puzzleMatchInfo.matchInfoJson();

        String platform = "Puzzle";
        matchInfo.setPlatform(platform);

        String date = getValue(matchInfoJson, PuzzleConfig.date); //yyyy-mm-ddThh:mm
        matchInfo.setDate(date);

        String time = getValue(matchInfoJson, PuzzleConfig.time); //yyyy-mm-ddThh:mm
        matchInfo.setTime(time);

        String region = getValue(matchInfoJson, PuzzleConfig.region);
        if (region.equals(PuzzleConfig.PuzzleRegion.SEOUL)) region = "서울";
        if (region.equals(PuzzleConfig.PuzzleRegion.GYEONGGI_DONGBU)) region = "경기";
        if (region.equals(PuzzleConfig.PuzzleRegion.GYEONGGI_NAMBU)) region = "경기";
        matchInfo.setRegion(region);

        /********************puzzle ground info**************/
        JSONObject groundInfo = matchInfoJson.getJSONObject("ground_info");
        String matchTitle = getValue(groundInfo, PuzzleConfig.match_title);
        matchInfo.setMatch_title(matchTitle);

        matchInfo.setMain_stadium(defaultValue);
        matchInfo.setSub_stadium(defaultValue);
        /***************************************************/

        String matchType = getValue(matchInfoJson, PuzzleConfig.match_type);
        if (matchType.equals("1")) matchType = "랭크";
        if (matchType.equals("0")) matchType = "일반매치";
        matchInfo.setMatch_type(matchType);

        String sex = getValue(matchInfoJson, PuzzleConfig.sex);
        if (sex.equals("1")) sex = "남자";
        if (sex.equals("3")) sex = "남녀모두";
        if (sex.equals("2")) sex = "여자";
        matchInfo.setSex(sex);

        String level = "모든레벨";
        matchInfo.setLevel(level);

        String match_vs = getValue(matchInfoJson, PuzzleConfig.match_vs);
        matchInfo.setMatch_vs(match_vs);

        /********************puzzle cur_player**************/
        int cur_player = 0;
        JSONArray applyMembers = matchInfoJson.getJSONArray(PuzzleConfig.cur_player);
        for (Object applyMember : applyMembers) {
            JSONObject member = new JSONObject(applyMember.toString());
            if (getValue(member, "isFake").equals(defaultValue)) cur_player++;
        }
        int guest_cnt = 0;
        String guestCnt = getValue(matchInfoJson, "guest_cnt");
        if (!guestCnt.equals(defaultValue)) guest_cnt += Integer.parseInt(guestCnt);
        matchInfo.setCur_player(Integer.toString(cur_player + guest_cnt));
        /***************************************************/

        String max_player = getValue(matchInfoJson.getJSONObject("personnel"), PuzzleConfig.max_player);
        matchInfo.setMax_player(max_player);

        String price = getValue(matchInfoJson, PuzzleConfig.price);
        matchInfo.setPrice(price);

        String link = getValue(matchInfoJson, PuzzleConfig.link);
        matchInfo.setLink(PuzzleConfig.matchLinkBaseUrl + link);

        return matchInfo;
    }

    public static MatchInfo convert(WithMatchInfo withMatchInfo) {
        MatchInfo matchInfo = new MatchInfo();
        JSONObject matchInfoJson = withMatchInfo.matchInfoJson();

        String platform = "With";
        matchInfo.setPlatform(platform);

        String date = getValue(matchInfoJson, WithConfig.date); //yyyy-mm-dd
        matchInfo.setDate(date);

        String time = getValue(matchInfoJson, WithConfig.time); //hh:mm
        matchInfo.setTime(time);

        String region = getValue(matchInfoJson, WithConfig.region); //"0"
        if (region.equals("0")) region = "서울";
        matchInfo.setRegion(region);

        String mainStadium = getValue(matchInfoJson, WithConfig.main_stadium);
        matchInfo.setMain_stadium(mainStadium);

        String subStadium = getValue(matchInfoJson, WithConfig.sub_stadium);
        matchInfo.setSub_stadium(subStadium);

        matchInfo.setMatch_title(mainStadium + " " + subStadium);

        matchInfo.setMatch_type("일반매치");

        String sex = getValue(matchInfoJson, WithConfig.sex);
        if (sex.equals("0")) sex = "남자";
        if (sex.equals("2")) sex = "남녀모두";
        if (sex.equals("1")) sex = "여자";
        matchInfo.setSex(sex);

        String level = getValue(matchInfoJson, WithConfig.level);
        if (level.equals("3")) level = "모든레벨";
        matchInfo.setLevel(level);

        String match_vs = getValue(matchInfoJson, WithConfig.match_vs);
        if (!match_vs.equals(defaultValue)) match_vs = Integer.toString(Integer.parseInt(match_vs) / 3);
        matchInfo.setMatch_vs(match_vs);

        String cur_player = getValue(matchInfoJson, WithConfig.cur_player);
        matchInfo.setCur_player(cur_player);

        String max_player = getValue(matchInfoJson, WithConfig.max_player);
        matchInfo.setMax_player(max_player);

        String price = getValue(matchInfoJson, WithConfig.price);
        matchInfo.setPrice(price);

        String link = getValue(matchInfoJson, WithConfig.link);
        matchInfo.setLink(WithConfig.matchLinkBaseUrl + link);

        return matchInfo;
    }

    public static MatchInfo convert(IamMatchInfo iamMatchInfo) {
        MatchInfo matchInfo = new MatchInfo();
        JSONObject matchInfoJson = iamMatchInfo.matchInfoJson();

        String platform = "Iam";
        matchInfo.setPlatform(platform);

        String date = getValue(matchInfoJson, IamConfig.date); //yyyy-mm-dd
        matchInfo.setDate(date);

        String time = getValue(matchInfoJson, IamConfig.time); //hh:mm:ss
        if(!time.equals(defaultValue)) matchInfo.setTime(time.substring(0, 5));

        String region = getValue(matchInfoJson, IamConfig.region); //"서울시 용산구..., 경기도 고양시..."
        if(!region.equals(defaultValue)) matchInfo.setRegion(region.substring(0, 2));

        String match_title = getValue(matchInfoJson, IamConfig.match_title);
        matchInfo.setMatch_title(match_title);

        matchInfo.setMain_stadium(defaultValue);
        matchInfo.setSub_stadium(defaultValue);

        matchInfo.setMatch_type("일반매치");

        String sex = getValue(matchInfoJson, IamConfig.sex);
        if (sex.equals("1")) sex = "남자";
        else if (sex.equals("2")) sex = "여자";
        else sex = "남녀모두"; //아이엠그라운드 소셜매치는 혼성매치가 없는듯
        matchInfo.setSex(sex);

        String level = getValue(matchInfoJson, IamConfig.level);
        if (level.equals("5")) level = "모든레벨";
        matchInfo.setLevel(level);

        String match_vs = getValue(matchInfoJson, IamConfig.match_vs);
        matchInfo.setMatch_vs(match_vs);

        /********************iam cur_player, max_player**************/
        String nowCapacity = getValue(matchInfoJson, IamConfig.cur_player);
        String max_player = getValue(matchInfoJson, IamConfig.max_player);
        if(!nowCapacity.equals(defaultValue) && !max_player.equals(defaultValue)){
            int cur_player = Integer.parseInt(max_player) - Integer.parseInt(nowCapacity);
            matchInfo.setCur_player(Integer.toString(cur_player));
        }
        matchInfo.setMax_player(max_player);
        /***************************************************/

        String price = getValue(matchInfoJson, IamConfig.price);
        matchInfo.setPrice(price);

        String link = getValue(matchInfoJson, IamConfig.link);
        matchInfo.setLink(IamConfig.matchLinkBaseUrl + link);

        return matchInfo;
    }

}
