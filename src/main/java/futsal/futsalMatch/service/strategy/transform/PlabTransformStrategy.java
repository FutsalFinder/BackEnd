package futsal.futsalMatch.service.strategy.transform;

import futsal.futsalMatch.config.platform.PlabConfig;
import futsal.futsalMatch.config.platform.PlatformConfig;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@Component
public class PlabTransformStrategy extends TransformStrategy {
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
            String dateTime = jsonObject.optString(config.getDate()); //yyyy-mm-ddThh:mm:ss+hh:mm
            return OffsetDateTime.parse(dateTime).toLocalDate().toString();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    protected String resolveTime(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;

        try {
            String dateTime = jsonObject.optString(config.getTime()); //yyyy-mm-ddThh:mm:ss+hh:mm
            return OffsetDateTime.parse(dateTime).toLocalTime().toString();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    protected String resolveRegion(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getRegion(), null); //"서울", "경기"
    }

    @Override
    protected String resolveMatchTitle(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMatchTitle(), null);
    }

    @Override
    protected String resolveMainStadium(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMainStadium(), null);
    }

    @Override
    protected String resolveSubStadium(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getSubStadium(), null);
    }

    @Override
    protected String resolveMatchType(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;

        // 플랩풋볼 게스트 매치
        if(jsonObject.optString(PlabConfig.productType).equals("guest")) {
            return "게스트 모집";
        }

        String matchType = jsonObject.optString(config.getMatchType());

        return switch(matchType) {
            case "league" -> "플랩팀리그";
            case "starter" -> "트레이닝 매치";
            case "tshirt" -> "티셔츠 매치";
            case "3teams" -> "일반매치";
            default -> null;
        };
    }

    @Override
    protected String resolveSex(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String sex = jsonObject.optString(config.getSex());

        return switch(sex) {
            case "1" -> "남자";
            case "0" -> "남녀모두";
            case "-1" -> "여자";
            default -> null;
        };
    }

    @Override
    protected String resolveLevel(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String level = jsonObject.optString(config.getLevel());

        return switch(level) {
            case "0" -> "모든레벨";
            case "3" -> "아마추어2 이하";
            case "5" -> "아마추어4 이상";
            default -> null;
        };
    }

    @Override
    protected String resolveMatchVS(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMatchVS(), null);
    }

    @Override
    protected String resolveCurPlayer(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getCurPlayer(), null);
    }

    @Override
    protected String resolveMaxPlayer(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getMaxPlayer(), null);
    }

    @Override
    protected String resolvePrice(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        return jsonObject.optString(config.getPrice(), null);
    }

    @Override
    protected String resolveLink(PlatformConfig config, Object matchData) {
        JSONObject jsonObject = (JSONObject) matchData;
        String id = jsonObject.optString(config.getLink());

        // 플랩풋볼 게스트 매치
        if(jsonObject.optString(PlabConfig.productType).equals("guest")) {
            return PlabConfig.guestMatchLinkBaseURL + id;
        }

        return config.getMatchLinkBaseURL() + id;
    }
}
