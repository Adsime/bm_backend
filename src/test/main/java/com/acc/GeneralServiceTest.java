package main.java.com.acc;

import com.acc.service.GeneralService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;



public class GeneralServiceTest {

    @Test
    public void shouldReturnFalseWhenCredentialsDosentVerify() {
        boolean excpectedResult = false;

        GeneralService generalService = new GeneralService();
        boolean result = generalService.verify("lol2k");

        assertEquals(excpectedResult, result);
    }

    @Test
    public void shouldReturnTrueWhenCredentialsVerifys() {
        boolean excpectedResult = true;

        GeneralService generalService = new GeneralService();
        boolean result = generalService.verify("m2Gdd8W3qWSnulyhonkuu_1X");

        assertEquals(excpectedResult, result);
    }
}
