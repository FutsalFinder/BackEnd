package futsal.futsalMatch.service.strategy.transform;

import futsal.futsalMatch.config.platform.PlatformConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class UrbanTransformStrategy extends TransformStrategy {
    @Override
    protected Object resolveObject(Object matchData) {
        return Jsoup.parse(matchData.toString());
    }

    @Override
    protected String resolvePlatform(PlatformConfig config, Object matchData) {
        return config.getPlatform();
    }

    @Override
    protected String resolveDate(PlatformConfig config, Object matchData) {
        Document document = (Document) matchData;
        return document.select(config.getDate()).text();
    }

    @Override
    protected String resolveTime(PlatformConfig config, Object matchData) {
        Document document = (Document) matchData;
        return document.select(config.getTime()).text();
    }

    @Override
    protected String resolveRegion(PlatformConfig config, Object matchData) {
        return "서울";
    }

    @Override
    protected String resolveMatchTitle(PlatformConfig config, Object matchData) {
        Document document = (Document) matchData;
        Element element = document.selectFirst(config.getMatchTitle());

        if(element != null) {
            element.select("span.coupon").remove();
            return element.text().trim();
        } else {
            return null;
        }
    }

    @Override
    protected String resolveMainStadium(PlatformConfig config, Object matchData) {
        return null;
    }

    @Override
    protected String resolveSubStadium(PlatformConfig config, Object matchData) {
        return null;
    }

    @Override
    protected String resolveMatchType(PlatformConfig config, Object matchData) {
        return "일반매치";
    }

    @Override
    protected String resolveSex(PlatformConfig config, Object matchData) {
        Document document = (Document) matchData;
        if(document.selectFirst("span.sex.unisex") != null) {
            return "남녀모두";
        } else if(document.selectFirst("span.sex.male") != null) {
            return "남자";
        } else if(document.selectFirst("span.sex.female") != null) {
            return "여자";
        } else {
            return null;
        }
    }

    @Override
    protected String resolveLevel(PlatformConfig config, Object matchData) {
        return "모든레벨";
    }

    @Override
    protected String resolveMatchVS(PlatformConfig config, Object matchData) {
        return null;
    }

    @Override
    protected String resolveCurPlayer(PlatformConfig config, Object matchData) {
        return null;
    }

    @Override
    protected String resolveMaxPlayer(PlatformConfig config, Object matchData) {
        return null;
    }

    @Override
    protected String resolvePrice(PlatformConfig config, Object matchData) {
        Document document = (Document) matchData;
        return document.select(config.getPrice()).text().trim().replaceAll("[^0-9]", "");
    }

    @Override
    protected String resolveLink(PlatformConfig config, Object matchData) {
        Document document = (Document) matchData;
        return config.getMatchLinkBaseURL() + document.select("ul.goods_table_item").attr(config.getLink());
    }
}
