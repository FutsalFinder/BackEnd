package futsal.futsalMatch.controller;

import futsal.futsalMatch.enums.Region;
import futsal.futsalMatch.service.CalendarService;
import futsal.futsalMatch.service.PlatformConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final CalendarService calendarService;
    private final PlatformConfigService platformConfigService;

    @GetMapping("/")
    public String index(Model model) {
        LocalDate today = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate();
        model.addAttribute("days", calendarService.getNextTwoWeeksDays(today));
        model.addAttribute("platforms", platformConfigService.getPlatformInfos());
        model.addAttribute("regions", Region.values());
        model.addAttribute("defaultRegion", Region.SEOUL);

        return "index";
    }
}
