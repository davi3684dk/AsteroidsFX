package dk.sdu.cbse.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoreService {
    private int totalScore = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoreService.class, args);
    }

    @PostMapping("/score/add/{score}")
    public int addScore(@PathVariable int score) {
        totalScore += score;
        return totalScore;
    }

    @GetMapping("/score")
    public int getScore() {
        return totalScore;
    }
}
