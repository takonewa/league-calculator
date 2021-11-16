package za.co.deposita;

import lombok.RequiredArgsConstructor;
import za.co.deposita.service.FileService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LeagueCalculatorApplication implements ApplicationRunner {
    private final FileService fileService;
    private final static String FILE_PATH = "input.csv";

    public static void main(String[] args) {
        SpringApplication.run(LeagueCalculatorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        fileService.processFile(FILE_PATH);
    }
}
