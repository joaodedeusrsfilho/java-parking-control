package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service //indicando que é uma camada de serviço
public class ParkingSpotService {
    /*vamos criar um ponto de injeção de dependencia, entre essa camada service e o
    * repository, pois a classe model vai acessar esta classe service e essa classe
    * service vai acessar a interface repository*/

    @Autowired  /*injetando dependencia da classe ParkingSpotService, pois
    esta classe vai se comunicar com a class ParkingSpotRepository*/
    ParkingSpotRepository parkingSpotRepository;

    /*Só que quando é metodo construtivo ou destrutivo é interessante anotar ele com
    * @Transactional principalmente quando a gente tem relacionamento que vai ter
    * deleção em cascata ou salvamento em cascata, pois se caso de errado ele garante
    * o goback e garante que não tenha dados quebrados*/
    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block){
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }
}
