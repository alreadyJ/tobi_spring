import com.splitcorp.first.tamplateCallback.Calculator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;

    @Before
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = getClass().getResource("number.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(this.numFilePath), is(15));
    }
}
