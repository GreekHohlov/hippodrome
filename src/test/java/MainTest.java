import org.junit.jupiter.api.*;

class MainTest {

    @Test
    @Timeout(22)
    @Disabled
    void mainClassTest() throws Exception {
        Main.main(null);
    }
}