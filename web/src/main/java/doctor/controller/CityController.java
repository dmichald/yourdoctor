package doctor.controller;

import doctor.service.office.OfficeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/offices/cities")
@RequiredArgsConstructor
public class CityController {
    private final OfficeService officeService;

    @GetMapping
    CitiesListDto getCities() {
        return new CitiesListDto(officeService.getCities());
    }

}

@Getter
class CitiesListDto {
    private List<String> cities;

    CitiesListDto(List<String> cities) {
        this.cities = cities;
    }
}
