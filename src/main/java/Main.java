import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    static LocalDate dateConvertion(String date){
        LocalDate inputDate = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("dd/MM/yy");
        if(date.contains("/")) {
            if(date.length() == 10) {
                inputDate = LocalDate.parse(date, formatter3);
            } else if(date.length() == 8) {
                inputDate = LocalDate.parse(date,formatter4);
            }
        } else if(date.contains(".")) {
            if(date.length() == 10) {
                inputDate = LocalDate.parse(date, formatter1);
            } else if(date.length() == 8) {
                inputDate = LocalDate.parse(date,formatter2);
            }
        }
        return inputDate;
    }
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("./src/main/resources/organizations");

        JsonReader reader = new JsonReader(new FileReader(file));
        Type collectionType = new TypeToken<Collection<Organization>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        List<Organization> orgsList = gson.fromJson(reader, collectionType);

        //задание 1
        orgsList.forEach((organization) -> {
            LocalDate date = organization.egrul_date;
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yy");
            String dateLine = date.format(pattern);
            System.out.println(organization.name_short + " - \"Дата основания " + dateLine + "\"");
        });

        //задание 2
       int count;
        List<Organization.Security> list = orgsList.stream().flatMap(organization -> {
               Organization.Security[] sec = organization.securities;
               List<Organization.Security> filteredSec = Stream.of(sec)
                       .filter(security -> security.date_to.isBefore(LocalDate.now()))
                       .collect(Collectors.toList());
               filteredSec.forEach(security -> System.out.println(security.code + " " + security.date_to + " "
               + organization.name_full));
               return filteredSec.stream();
        }).collect(Collectors.toList());
        System.out.println(list.size());

        //задание 3

        String date1 = "01.01.1990";
        String date2 = "01.01.90";
        String date3 = "01/01/1990";
        String date4 = "01/01/90";

        orgsList.stream()
                .filter(organization -> organization.egrul_date.isAfter(dateConvertion(date1)))
                .forEach(organization -> {
                    System.out.println(organization.name_full + " " + organization.egrul_date);
                });

        //задание 4

        String currency1 = "USD";
        String currency2 = "RUB";
        String currency3 = "EU";
        orgsList.stream()
            .flatMap(organization -> Stream.of(organization.securities))
            .filter(security -> security.currency.code.equals(currency1))
            .forEach(security -> System.out.println(security.id + " " + security.code));
    }
}
