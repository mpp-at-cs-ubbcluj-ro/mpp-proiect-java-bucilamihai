package repository.interfaces;

import domain.OfficeResponsable;
import repository.Repository;

public interface OfficeResponsableRepository extends Repository<Long, OfficeResponsable> {
    OfficeResponsable findByUsername(String username);
}
