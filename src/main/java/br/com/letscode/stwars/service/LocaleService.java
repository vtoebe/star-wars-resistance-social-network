package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.model.LocaleEntity;
import br.com.letscode.stwars.model.LocationHistoryEntity;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.repository.LocationRepository;
import br.com.letscode.stwars.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocaleService {

    private final LocationRepository locationRepository;
    private final BaseService baseService;

    public void saveLocale(PersonRequestDto request, PersonEntity person){
        LocaleEntity locale = EntityUtils.getLocale(person);
        locale.setPerson(person);
        locale.setBase(baseService.getBase(request.getBase()));

        saveToLocationHistory(person, locale);
    }

    public void updateLocale(LocaleRequestDto request, PersonEntity person){
        LocaleEntity locale = EntityUtils.getLocale(person);

        locale.setLatitude(request.getLatitude());
        locale.setLongitude(request.getLongitude());
        locale.setBase(baseService.getBase(request.getBase()));

        saveToLocationHistory(person, locale);
    }

    public void saveToLocationHistory(PersonEntity person
            , LocaleEntity locale) {
        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();
        locationHistoryEntity.setPerson(person);
        locationHistoryEntity.setLatitude(locale.getLatitude());
        locationHistoryEntity.setLongitude(locale.getLongitude());
        locationHistoryEntity.setBase(locale.getBase());

        locationRepository.save(locationHistoryEntity);
    }


}