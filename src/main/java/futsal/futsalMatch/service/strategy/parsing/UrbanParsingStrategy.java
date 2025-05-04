package futsal.futsalMatch.service.strategy.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UrbanParsingStrategy implements ParsingStrategy {
    @Override
    public List<Object> parse(String fetchData, LocalDate date) {
        Document document = Jsoup.parse(fetchData);
        Elements matchElements = document.select("ul.goods_table_item");

        String dateElement = "<span class='date'>" + date + "</span>";

        return matchElements.stream()
                .map(matchElement -> (Object) (matchElement + dateElement))
                .toList();
    }
}
