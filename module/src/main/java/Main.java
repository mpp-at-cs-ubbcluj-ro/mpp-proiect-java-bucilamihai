import domain.Child;
import domain.Enrollment;
import domain.OfficeResponsable;
import repository.database.OfficeResponsableDBRepository;
import repository.interfaces.OfficeResponsableRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        }
        catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        OfficeResponsable officeResponsable = new OfficeResponsable("test", "test");
        OfficeResponsableRepository officeResponsableRepository = new OfficeResponsableDBRepository(properties);
        officeResponsableRepository.add(officeResponsable);
        officeResponsableRepository.findAll().forEach(System.out::println);
        System.out.println();
        System.out.println(officeResponsableRepository.findById(1L));
        officeResponsableRepository.update(new OfficeResponsable("cristiii", "password"), 2L);
        System.out.println();
        officeResponsableRepository.delete(officeResponsableRepository.findById(11L));
        System.out.println();
        officeResponsableRepository.findAll().forEach(System.out::println);
    }
}