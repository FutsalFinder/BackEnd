package futsal.futsalMatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(Model model) {
        LocalDate today = LocalDate.now();
        List<Map<String, String>> days = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            LocalDate date = today.plusDays(i);
            String dayOfTheWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN).substring(0, 1);

            days.add(Map.of(
                    "date", date.toString(),  // yyyy-MM-dd
                    "day-date", String.valueOf(date.getDayOfMonth()),
                    "day-week", dayOfTheWeek
            ));
        }

        model.addAttribute("days", days);
        return "index";
    }
}
