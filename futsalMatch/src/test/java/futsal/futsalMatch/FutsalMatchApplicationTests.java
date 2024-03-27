package futsal.futsalMatch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class FutsalMatchApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void localDate(){

		LocalDate localDate = LocalDate.of(2024, 3, 19);
		System.out.println();
		System.out.println();
		System.out.println("FutsalMatchApplicationTests.localDate");
		System.out.println(localDate.toString());
		System.out.println();

	}

}
