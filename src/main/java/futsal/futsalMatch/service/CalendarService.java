package futsal.futsalMatch.service;

import futsal.futsalMatch.controller.dto.DayInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CalendarService {

    //TODO Cache 사용
    /*private final Map<LocalDate, List<DayInfo>> cache = new ConcurrentHashMap<>();

    public List<DayInfo> getNextTwoWeeksDays(LocalDate from) {
        cache.keySet().removeIf(date -> date.isBefore(from));
        return cache.computeIfAbsent(from, this::generateNextTwoWeeksDays);
    }*/

    public List<DayInfo> getNextTwoWeeksDays(LocalDate from) {
        return generateNextTwoWeeksDays(from);
    }

    public List<DayInfo> generateNextTwoWeeksDays(LocalDate from) {
        List<DayInfo> days = new ArrayList<>();
        for(int i = 0; i < 14; i++) {
            LocalDate date = from.plusDays(i);
            String dayOfTheWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN).substring(0, 1);
            Boolean isHoliday = false; //TODO 추후 구현 - 외부 API 연동

            days.add(new DayInfo(date, date.getDayOfMonth(), dayOfTheWeek, isHoliday));
        }
        return days;
    }
}
