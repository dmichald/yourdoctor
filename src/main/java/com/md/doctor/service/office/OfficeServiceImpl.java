package com.md.doctor.service.office;

import com.md.doctor.dto.OfficeDto;
import com.md.doctor.models.Doctor;
import com.md.doctor.repository.ContactRepo;
import com.md.doctor.repository.DoctorRepo;
import com.md.doctor.repository.OfficeRepo;
import com.md.doctor.service.doctor.DoctorService;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImpl implements OfficeService {
    private OfficeRepo officeRepository;
    private DoctorService doctorService;
    private ContactRepo contactRepository;

    public OfficeServiceImpl(OfficeRepo officeRepository, DoctorService doctorService, ContactRepo contactRepository) {
        this.officeRepository = officeRepository;
        this.doctorService = doctorService;
        this.contactRepository = contactRepository;
    }

    @Override
    public void saveOffice(OfficeDto officeDto) {




    }
}
