package com.api.parkingcontrol.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/*essa classe vai precisar das anotation com relação ao JPA para a gente criar um
mapeamento para tornar essa classe uma entidade*/
@Entity
@Table(name = "TB_PARKING_SPOT")//definindo o nome na tabela do banco de dados.
/*agora antes de criar os atributos e metodos precisamos implementar o Serializable*/
public class ParkingSpotModel implements Serializable {
    /*o Serializable faz uma conversão por de baixo dos panos onde
    transforma objetos java para bytes
    que serão salvos no banco de dados e esse serialVersionUID é um controle que é
    feito pela JVM*/
    @Serial
    private static final long serialVersionUID = 1L;

    /*abaixo a definição dos campos do banco de dados*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//id gerado de forma automatica.
    private UUID id;/*tipo UUID identificador apropriado para estruturas de
    microservices, ou seja id que trabalha de forma distribuidas para arquiteturas
    distribuidas*/

    @Column(nullable = false)
    private LocalDateTime registrationDate;//data de registro do cadastro

    @Column(nullable = false, unique = true, length = 10)
    private String parkingSpotNumber;/*as propriedades desse campo estão definidas
    na linha superior em @Column*/

    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;

    @Column(nullable = false, length = 70)
    private String brandCar;//marca do carro

    @Column(nullable = false, length = 70)
    private String modelCar;

    @Column(nullable = false)
    private String colorCar;



    @Column(nullable = false, length = 130)
    private String responsibleName;

    @Column(nullable = false, length = 30)
    private String apartment;

    @Column(nullable = false, length = 30)
    private String block;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public String getLicensePlateCar() {
        return licensePlateCar;
    }

    public void setLicensePlateCar(String licensePlateCar) {
        this.licensePlateCar = licensePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
