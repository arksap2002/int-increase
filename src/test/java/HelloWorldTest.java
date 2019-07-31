import static org.junit.Assert.*;

public class HelloWorldTest {

    @org.junit.Test
    public void smartprint() {
        assertEquals("Hello, World", HelloWorld.smartprint("Hello, World"));
    }
}