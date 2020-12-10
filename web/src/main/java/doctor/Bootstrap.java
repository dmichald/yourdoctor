package doctor;


import doctor.models.*;
import doctor.models.security.Role;
import doctor.models.security.User;
import doctor.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OfficeRepo officeRepo;
    private final ReservationRepo reservationRepo;
    private final DoctorRepo doctorRepo;
    private final AddressRepo addressRepo;
    private final ContactRepo contactRepo;
    private final SpecializationRepo specializationRepo;
    private final PatientRepo patientRepo;


    @Transactional
    @Override
    public void run(String... args) {
        Role role = new Role();
        role.setName("ROLE_DOCTOR");

        User user = new User();
        user.setEnabled(true);
        user.setEmail("test@Test.pl");
        user.setPassword(passwordEncoder.encode("test"));
        user.setUsername("test");
        user.getRoles().add(role);
        user.addRole(role);
        userRepository.save(user);
        for (int i = 0; i < 50; i++) {
            addToDb(i);
        }
    }

    private void addToDb(int i) {

        Doctor doctor = new Doctor();
        doctor.setName("Roman");
        doctor.setSurname("Testowy" + i);
        Specialization s = specializationRepo.findById(1L).orElseThrow();
        Doctor savedDoc = doctorRepo.save(doctor);
        savedDoc.addSpecialization(s);


        Address docAddres = new Address();
        docAddres.setCity("Kraków");
        docAddres.setStreet("ul. Nowa 32");
        docAddres.setCode("33-302");
        Address savedDocAddr = addressRepo.save(docAddres);

        Address patientAddr = new Address();
        patientAddr.setCity("PATIENT CITY");
        patientAddr.setStreet("ul. Nowa 32");
        patientAddr.setCode("33-302");
        Address savedPatientAddr = addressRepo.save(patientAddr);

        Contact contact = new Contact();
        contact.setEmail("contact@contact.pl");
        contact.setTelephoneNumber("783682926");
        Contact savedContact = contactRepo.save(contact);

        Patient patient = new Patient();
        patient.setEmail("email@email.pl");
        patient.setTelephoneNumber("782732721");
        Patient savedPatient = patientRepo.save(patient);


        Office office = new Office();
        office.setDoctor(savedDoc);
        office.setContact(savedContact);
        office.setAddress(savedDocAddr);
        office.setPrice(100);
        office.setStartWorkAt(Time.valueOf(LocalTime.of(8, 0)));
        office.setFinishWorkAt(Time.valueOf(LocalTime.of(16, 0)));
        office.setOneVisitDuration(30);
        Office savedOffice = officeRepo.save(office);
        savedOffice.setOwner(userRepository.findByUsername("test").orElseThrow());


        Reservation reservation = new Reservation();
        reservation.setId(89L);
        reservation.setCanceled(false);
        reservation.setDate(Date.valueOf(LocalDate.of(2021, 3, 21)));
        reservation.setStartTime(Time.valueOf(LocalTime.of(10, 0)));
        Reservation savedRes = reservationRepo.save(reservation);
        savedRes.addPatient(savedPatient);
        savedRes.addOffice(savedOffice);
    }
}
