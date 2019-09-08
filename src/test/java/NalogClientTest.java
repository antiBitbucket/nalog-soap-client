import com.domodedovo.NalogClient;
import com.domodedovo.unisoft.ws.fnsndscaws2.request.NdsRequest2.NP;
import com.domodedovo.unisoft.ws.fnsndscaws2.response.NdsResponse2;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NalogClientTest {

    private NalogClient nalogClient = new NalogClient();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void getNPFromConsoleTestIP() {
        NP actual = nalogClient.getNPFromConsole(new String[]{"1"});
        NP expected = new NP();
        expected.setINN("1");
        expected.setDT(simpleDateFormat.format(new Date()));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getNPFromConsoleTestUL() {
        NP actual = nalogClient.getNPFromConsole(new String[]{"1", "2"});
        NP expected = new NP();
        expected.setINN("1");
        expected.setKPP("2");
        expected.setDT(simpleDateFormat.format(new Date()));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getIndicator() {
        String expected = "Налогоплательщик зарегистрирован в ЕГРН и имел статус действующего в указанную дату";
        Assert.assertEquals(expected, nalogClient.getIndicator(0));
        expected = "Налогоплательщик зарегистрирован в ЕГРН, но не имел статус действующего в указанную дату";
        Assert.assertEquals(expected, nalogClient.getIndicator(1));
        expected = "Налогоплательщик зарегистрирован в ЕГРН";
        Assert.assertEquals(expected, nalogClient.getIndicator(2));
        expected = "Налогоплательщик с указанным ИНН зарегистрирован в ЕГРН, КПП не соответствует ИНН или не указан*";
        Assert.assertEquals(expected, nalogClient.getIndicator(3));
        expected = "Налогоплательщик с указанным ИНН не зарегистрирован в ЕГРН";
        Assert.assertEquals(expected, nalogClient.getIndicator(4));
        expected = "Некорректный ИНН";
        Assert.assertEquals(expected, nalogClient.getIndicator(5));
        expected = "Недопустимое количество символов ИНН";
        Assert.assertEquals(expected, nalogClient.getIndicator(6));
        expected = "Недопустимое количество символов КПП";
        Assert.assertEquals(expected, nalogClient.getIndicator(7));
        expected = "Недопустимые символы в ИНН";
        Assert.assertEquals(expected, nalogClient.getIndicator(8));
        expected = "Недопустимые символы в КПП";
        Assert.assertEquals(expected, nalogClient.getIndicator(9));
        expected = "КПП не должен использоваться при проверке ИП";
        Assert.assertEquals(expected, nalogClient.getIndicator(10));
        expected = "некорректный формат даты";
        Assert.assertEquals(expected, nalogClient.getIndicator(11));
        expected = "некорректная дата (ранее 01.01.1991 или позднее текущей даты)";
        Assert.assertEquals(expected, nalogClient.getIndicator(12));
        expected = "ошибка на сервере";
        Assert.assertEquals(expected, nalogClient.getIndicator(100));
    }

    @Test
    public void printResponseTestUL() {
        NdsResponse2.NP np = new NdsResponse2.NP();
        np.setINN("1");
        np.setKPP("2");
        np.setDT(simpleDateFormat.format(new Date()));
        np.setState("6");
        String expected = "Ответ получен: \n ИНН: 1 \n КПП: 2 \n Состояние: " + nalogClient.getIndicator(6);
        Assert.assertEquals(expected, nalogClient.printResponse(np));
    }

    @Test
    public void printResponseTestIP() {
        NdsResponse2.NP np = new NdsResponse2.NP();
        np.setINN("1");
        np.setDT(simpleDateFormat.format(new Date()));
        np.setState("6");
        String expected = "Ответ получен: \n ИНН: 1 \n Состояние: " + nalogClient.getIndicator(6);
        Assert.assertEquals(expected, nalogClient.printResponse(np));
    }

}
