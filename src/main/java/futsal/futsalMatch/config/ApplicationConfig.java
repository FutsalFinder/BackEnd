package futsal.futsalMatch.config;

import futsal.futsalMatch.config.platform.PlabConfig;
import futsal.futsalMatch.config.platform.PuzzleConfig;
import futsal.futsalMatch.config.platform.UrbanConfig;
import futsal.futsalMatch.config.platform.WithConfig;
import futsal.futsalMatch.service.PlatformRequester;
import futsal.futsalMatch.service.strategy.parsing.PlabParsingStrategy;
import futsal.futsalMatch.service.strategy.parsing.PuzzleParsingStrategy;
import futsal.futsalMatch.service.strategy.parsing.UrbanParsingStrategy;
import futsal.futsalMatch.service.strategy.parsing.WithParsingStrategy;
import futsal.futsalMatch.service.strategy.request.PlabRequestStrategy;
import futsal.futsalMatch.service.strategy.request.PuzzleRequestStrategy;
import futsal.futsalMatch.service.strategy.request.UrbanRequestStrategy;
import futsal.futsalMatch.service.strategy.request.WithRequestStrategy;
import futsal.futsalMatch.service.strategy.transform.PlabTransformStrategy;
import futsal.futsalMatch.service.strategy.transform.PuzzleTransformStrategy;
import futsal.futsalMatch.service.strategy.transform.UrbanTransformStrategy;
import futsal.futsalMatch.service.strategy.transform.WithTransformStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final PlabRequestStrategy plabRequestStrategy;
    private final PlabParsingStrategy plabParsingStrategy;
    private final PlabTransformStrategy plabTransformStrategy;
    private final PlabConfig plabConfig;

    private final PuzzleRequestStrategy puzzleRequestStrategy;
    private final PuzzleParsingStrategy puzzleParsingStrategy;
    private final PuzzleTransformStrategy puzzleTransformStrategy;
    private final PuzzleConfig puzzleConfig;

    private final WithRequestStrategy withRequestStrategy;
    private final WithParsingStrategy withParsingStrategy;
    private final WithTransformStrategy withTransformStrategy;
    private final WithConfig withConfig;

    private final UrbanRequestStrategy urbanRequestStrategy;
    private final UrbanParsingStrategy urbanParsingStrategy;
    private final UrbanTransformStrategy urbanTransformStrategy;
    private final UrbanConfig urbanConfig;

    @Bean
    public PlatformRequester plabRequester() {
        return new PlatformRequester(plabRequestStrategy, plabParsingStrategy, plabTransformStrategy, plabConfig);
    }

    @Bean
    public PlatformRequester puzzleRequester() {
        return new PlatformRequester(puzzleRequestStrategy, puzzleParsingStrategy, puzzleTransformStrategy, puzzleConfig);
    }

    @Bean
    public PlatformRequester withRequester() {
        return new PlatformRequester(withRequestStrategy, withParsingStrategy, withTransformStrategy, withConfig);
    }

    @Bean
    public PlatformRequester urbanRequester() {
        return new PlatformRequester(urbanRequestStrategy, urbanParsingStrategy, urbanTransformStrategy, urbanConfig);
    }


}
