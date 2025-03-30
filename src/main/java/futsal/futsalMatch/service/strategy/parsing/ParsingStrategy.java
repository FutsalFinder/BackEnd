package futsal.futsalMatch.service.strategy.parsing;

import java.time.LocalDate;
import java.util.List;

public interface ParsingStrategy {
    List<Object> parse(String fetchData, LocalDate date);
}
