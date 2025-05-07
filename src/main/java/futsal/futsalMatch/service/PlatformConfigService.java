package futsal.futsalMatch.service;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.controller.dto.PlatformInfo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformConfigService {

    private final List<PlatformConfig> configs;
    private final List<PlatformInfo> platformInfos = new ArrayList<>();

    @PostConstruct
    public void init() {
        configs.sort(Comparator.comparing(PlatformConfig::getPriority));
        for(PlatformConfig config : configs) {
            platformInfos.add(new PlatformInfo(config.getPlatform(), config.getFullNameInKorean()));
        }
    }

    public List<PlatformInfo> getPlatformInfos() {
        return platformInfos;
    }
}
