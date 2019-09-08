package com.domodedovo;

import com.domodedovo.unisoft.ws.fnsndscaws2.request.NdsRequest2.NP;
import com.domodedovo.unisoft.ws.fnsndscaws2.response.NdsResponse2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NalogClient {

    public NP getNPFromConsole(String[] args) {
        NP np = new NP();
        int countForms = args.length;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if (countForms > 0) {
            np.setINN(args[0]);
            if (countForms >= 2) {
                np.setKPP(args[1]);
            }
            np.setDT(countForms == 3 ? args[2] : simpleDateFormat.format(new Date()));
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Введите ИНН:");
                np.setINN(br.readLine().trim());
                System.out.println("Введите КПП (для ЮЛ)");
                String kpp = br.readLine().trim();
                if (!kpp.isEmpty()) {
                    np.setKPP(kpp);
                }
                np.setDT(simpleDateFormat.format(new Date()));
            } catch (IOException e) {
                System.out.println("Данные введены некорректно ");
                e.printStackTrace();
            }
        }
        return np;
    }

    public String printResponse(NdsResponse2.NP responseNP) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ответ получен: \n ИНН: ").append(responseNP.getINN()).append(" \n");
        if (responseNP.getKPP() != null) {
            sb.append(" КПП: ").append(responseNP.getKPP()).append(" \n");
        }
        sb.append(" Состояние: ").append(getIndicator(Integer.parseInt(responseNP.getState())));
        return sb.toString();
    }

    public String getIndicator(int state) {
        switch (state) {
            case (0):
                return "Налогоплательщик зарегистрирован в ЕГРН и имел статус действующего в указанную дату";
            case (1):
                return "Налогоплательщик зарегистрирован в ЕГРН, но не имел статус действующего в указанную дату";
            case (2):
                return "Налогоплательщик зарегистрирован в ЕГРН";
            case (3):
                return "Налогоплательщик с указанным ИНН зарегистрирован в ЕГРН, КПП не соответствует ИНН или не " +
                        "указан*";
            case (4):
                return "Налогоплательщик с указанным ИНН не зарегистрирован в ЕГРН";
            case (5):
                return "Некорректный ИНН";
            case (6):
                return "Недопустимое количество символов ИНН";
            case (7):
                return "Недопустимое количество символов КПП";
            case (8):
                return "Недопустимые символы в ИНН";
            case (9):
                return "Недопустимые символы в КПП";
            case (10):
                return "КПП не должен использоваться при проверке ИП";
            case (11):
                return "некорректный формат даты";
            case (12):
                return "некорректная дата (ранее 01.01.1991 или позднее текущей даты)";
            default:
                return "ошибка на сервере";
        }
    }

}
