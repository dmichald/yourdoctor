package doctor.repository;

import doctor.models.Office;
import doctor.models.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfficeRepo extends CrudRepository<Office, Long> {

    Page<Office> findAllByDoctor_Specializations(Specialization specialization, Pageable pageable);

    @Query(value = "SELECT DISTINCT a.city FROM Office o  JOIN o.address a ON o.address.id = a.id")
    List<String> getCities();

    @Query(value = "SELECT  office.id as id, doctor.name as name, doctor.surname as surname, specialization.name as specialization FROM office " +
            "INNER JOIN doctor ON office.doctor_id = doctor.id " +
            "   JOIN doctor_specialization ON doctor.id = doctor_specialization.doctor_id " +
            "   JOIN specialization ON specialization.id = doctor_specialization.specialization_id " +
            "INNER JOIN address ON office.address_id = address.id " +
            "WHERE (?1 IS NULL OR address.city = ?1) " +
            "AND ( ?2 IS NULL OR doctor.name LIKE ?2% OR doctor.surname LIKE ?2%) " +
            "AND (specialization.id = ?3 OR ?3 IS NULL )  ",
            countQuery = "SELECT COUNT  (office.id) FROM office " +
                    "INNER JOIN doctor ON office.doctor_id = doctor.id " +
                    "   JOIN doctor_specialization ON doctor.id = doctor_specialization.doctor_id " +
                    "   JOIN specialization ON specialization.id = doctor_specialization.specialization_id " +
                    "INNER JOIN address ON office.address_id = address.id " +
                    "WHERE (?1 IS NULL OR address.city = ?1) " +
                    "AND (doctor.name LIKE ?2% OR doctor.surname LIKE ?2%) " +
                    "AND (specialization.id = ?3 OR ?3 IS NULL )  ",
            nativeQuery = true)
    Page<OfficeSummary> getOffices(String city, String doctorName, Long specializationId, Pageable pageable);
}


