package futsal.futsalMatch.controller.dto;

import java.time.LocalDate;

public record DayInfo(LocalDate date, Integer dayOfMonth, String dayWeek, Boolean isHoliday) {
}

