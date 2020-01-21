import java.time.LocalDate;
import java.util.Arrays;

public class Organization {
    int id;
    String code;
    String name_full;
    String name_short;
    String inn;
    CompanyType company_type;
    String ogrn;
    LocalDate egrul_date;
    Country country;
    String fio_head;
    String address;
    String phone;
    String e_mail;
    String www;
    boolean is_resident;
    Security[] securities;


    public class CompanyType{
        int id;
        String name_short;
        String name_full;
    }

    public class Country{
        int id;
        String code;
        String name;
    }

    public class Security{
        int id;
        String code;
        String name_full;
        String cfi;
        LocalDate date_to;
        LocalDate state_reg_date;
        State state;
        Currency currency;

        public class State{
            int id;
            String name;
        }

        public class Currency {
            int id;
            String code;
            String name_short;
            String name_full;
        }

    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name_full='" + name_full + '\'' +
                ", name_short='" + name_short + '\'' +
                ", inn='" + inn + '\'' +
                ", company_type=" + company_type +
                ", ogrn='" + ogrn + '\'' +
                ", egrul_date=" + egrul_date +
                ", country=" + country +
                ", fio_head='" + fio_head + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", www='" + www + '\'' +
                ", is_resident='" + is_resident + '\'' +
                ", securities=" + Arrays.toString(securities) +
                '}';
    }
}
