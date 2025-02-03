package futsal.futsalMatch.service.strategy.transform;

import futsal.futsalMatch.config.platform.PlatformConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.IntStream;

@Component
public class PuzzleTransformStrategy extends TransformStrategy {
    @Override
    protected Object resolveObject(Object matchData) {
        return new JSONObject(matchData.toString());
    }

    @Override
    protected String resolvePlatform(PlatformConfig config, Object matchData) {
        return config.getPlatform();
    }

    @Override
    protected String resolveDate(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        try {
            String date = jsonObject.optString(config.getDate()); //yyyy-mm-dd
            String time = resolveTime(config, matchData);

            if(time != null && time.equals("23:59")) {
                // 자정은 전날 23:59로 변환
                return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).minusDays(1).toString();
            } else {
                return date;
            }
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    protected String resolveTime(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;

        try {
            String time = jsonObject.optString(config.getTime()); //hh:mm

            // 자정은 전날 23:59로 변환
            if(LocalTime.parse(time).equals(LocalTime.of(0, 0))) {
                return "23:59";
            } else {
                return time;
            }
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    protected String resolveRegion(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String region = jsonObject.optString(config.getRegion());

        final String SEOUL_CODE = config.getCustomFields().get("SEOUL_CODE");
        final String GYEONGGI_NAMBU_CODE = config.getCustomFields().get("GYEONGGI_NAMBU_CODE");
        final String GYEONGGI_DONGBU_CODE = config.getCustomFields().get("GYEONGGI_DONGBU_CODE");

        if(region.equals(SEOUL_CODE)) {
            return "서울";
        } else if(region.equals(GYEONGGI_NAMBU_CODE)) {
            return "경기";
        } else if(region.equals(GYEONGGI_DONGBU_CODE)) {
            return "경기";
        }

        return null;
    }

    @Override
    protected String resolveMatchTitle(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.getJSONObject("ground_info").optString(config.getMatchTitle());
    }

    @Override
    protected String resolveMainStadium(PlatformConfig config, Object matchData) {
        return null; //퍼즐플레이는 Main Stadium 정보가 존재하지 않음
    }

    @Override
    protected String resolveSubStadium(PlatformConfig config, Object matchData) {
        return null; //퍼즐플레이는 Sub Stadium 정보가 존재하지 않음
    }

    @Override
    protected String resolveMatchType(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String matchType = jsonObject.optString(config.getMatchType());

        return switch(matchType) {
            case "1" -> "랭크";
            case "0" -> "일반매치";
            default -> null;
        };
    }

    @Override
    protected String resolveSex(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String sex = jsonObject.optString(config.getSex());

        return switch(sex) {
            case "1" -> "남자";
            case "2" -> "여자";
            case "3" -> "남녀모두";
            default -> null;
        };
    }

    @Override
    protected String resolveLevel(PlatformConfig config, Object matchData) {
        return "모든레벨";
    }

    @Override
    protected String resolveMatchVS(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMatchVS());
    }

    @Override
    protected String resolveCurPlayer(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        JSONArray applyMembers = jsonObject.getJSONArray(config.getCurPlayer());

        long curPlayer = IntStream.range(0, applyMembers.length())
                .mapToObj(applyMembers::get)
                .map(m -> new JSONObject(m.toString()))
                .filter(m -> m.optString("isFake").isEmpty())
                .count();

        String guestCnt = jsonObject.optString("guest_cnt");
        if (!guestCnt.isEmpty()) {
            curPlayer += Integer.parseInt(guestCnt);
        }

        return String.valueOf(curPlayer);
    }

    @Override
    protected String resolveMaxPlayer(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.getJSONObject("personnel").optString(config.getMaxPlayer()); //personnel {min: 10, max: 18}
    }

    @Override
    protected String resolvePrice(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getPrice());
    }

    @Override
    protected String resolveLink(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return config.getMatchLinkBaseURL() + jsonObject.optString(config.getLink());
    }
}
