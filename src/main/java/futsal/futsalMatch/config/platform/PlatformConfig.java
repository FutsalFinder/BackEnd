package futsal.futsalMatch.config.platform;

import java.util.Map;

public interface PlatformConfig {
    String getPlatform();
    String getFullNameInKorean();
    String getDate();
    String getTime();
    String getRegion();
    String getMatchTitle();
    String getMainStadium();
    String getSubStadium();
    String getMatchType();
    String getSex();
    String getLevel();
    String getMatchVS();
    String getCurPlayer();
    String getMaxPlayer();
    String getPrice();
    String getLink();
    String getRequestBaseURL();
    String getMatchLinkBaseURL();

    default Map<String, String> getCustomFields() {
        return Map.of();
    }
    default int getPriority() {
        return 999;
    }
}

