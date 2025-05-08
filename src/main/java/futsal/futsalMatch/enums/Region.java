package futsal.futsalMatch.enums;

import futsal.futsalMatch.exception.InvalidRegionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
    ALL(0, "모든지역"),
    SEOUL(1, "서울"),
    GYEONGGI(2, "경기");

    private final int value;
    private final String name;

    public static Region fromValue(int value) {
        for (Region region : Region.values()) {
            if (region.value == value) return region;
        }
        throw new InvalidRegionException("Invalid Region value: " + value);
    }
}
