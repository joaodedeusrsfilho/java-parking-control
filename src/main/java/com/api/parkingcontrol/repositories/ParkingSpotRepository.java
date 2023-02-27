package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository /*para indicar que a interface será utilizada para transações com o
bando de dados. e para fins didatico e de organização, não havia necessidade pois,
 quando se extends JpaRepository o anotation @Repository já é embotido na interface

 a classe JpaRepository esta sendo extends para facilidar o manuseio das informação
 que estão no banco de dados, com metodos de criar, atualizar, deletar e salvar
 dados entre outras operações disponiveis no JpaRepository,
 em JpaRepository< aqui dentro a gente passa qual o model e qual o identificador está
 sendo utilizado> */
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {
    /*os metodos abaixo serão apenas declarado pois sera usado de fato na camada
    * ParkingSpotService*/
    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNumber(String parkingSpotNumber);

    boolean existsByApartmentAndBlock(String apartment, String block);

}
