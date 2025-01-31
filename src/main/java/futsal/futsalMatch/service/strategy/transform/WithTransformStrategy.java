package futsal.futsalMatch.service.strategy.transform;

import futsal.futsalMatch.config.platform.PlatformConfig;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class WithTransformStrategy extends TransformStrategy {
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
        return jsonObject.optString(config.getDate()); //yyyy-mm-dd
    }

    @Override
    protected String resolveTime(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getTime()); //hh:mm
    }

    @Override
    protected String resolveRegion(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String region = jsonObject.optString(config.getRegion());

        return switch(region) {
            case "0" -> "서울";
            default -> null;
        };
    }

    @Override
    protected String resolveMatchTitle(PlatformConfig config, Object matchData) {
        return resolveMainStadium(config, matchData) + " " + resolveSubStadium(config, matchData);
    }

    @Override
    protected String resolveMainStadium(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMainStadium());
    }

    @Override
    protected String resolveSubStadium(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getSubStadium());
    }

    @Override
    protected String resolveMatchType(PlatformConfig config, Object matchData) {
        return "일반매치";
    }

    @Override
    protected String resolveSex(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String sex = jsonObject.optString(config.getSex());

        return switch(sex) {
            case "0" -> "남자";
            case "1" -> "여자";
            case "2" -> "남녀모두";
            default -> null;
        };
    }

    @Override
    protected String resolveLevel(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String level = jsonObject.optString(config.getLevel());

        return switch(level) {
            case "3" -> "모든레벨";
            default -> null;
        };
    }

    @Override
    protected String resolveMatchVS(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String matchVS = jsonObject.optString(config.getMatchVS()); // 매치 최대 인원 수

        try {
            //3파전 매치만 존재한다고 가정
            return String.valueOf(Integer.parseInt(matchVS) / 3);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected String resolveCurPlayer(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getCurPlayer());
    }

    @Override
    protected String resolveMaxPlayer(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMaxPlayer());
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
