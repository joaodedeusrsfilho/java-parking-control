package com.api.parkingcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/*DTO = data transfer object, é um padrão de projetos bastante usado em Java para o
transporte de dados entre diferentes componentes de um sistema, diferentes instâncias
ou processos de um sistema distribuído ou diferentes sistemas via serialização
UMA OBSERVAÇÃO ESTA CLASSE NÃO TERA O ID POIS ESTE, ESTA SENDO GERADO AUTOMATICAMENTE
PELO O PARKINGSPORT MODEL, E TAMBÉM NÃO TERÁ O LOCALDATETIME, POIS, ESTE TEM UMA
REGRINHA EM SUA CLASSE MODEL*/
public class ParkingSpotDto {

    /*UMA OBSERVAÇÃO essas verificações abaixo são as anotações do spring validation
    * default mas, depois também se precisar de anotações personalizadas podemos
    * utilizar da criação de constrains costomizadas dependendo da regra de negocio
    * dependendo do que, tem que ser validado em determinado campo.*/
    @NotBlank/*a anotation @NotBlank, vai verificar se não existe campo nulo ou
    string vazia por exemplo*/

    private String parkingSpotNumber;//numero da vaga

    @NotBlank
    @Size(max = 7)/*usado para limitar o numero de cacteres, entre outras anotatios
    que podemos utilizar como @email, @cpf, @notnull, @notemplyt
    todas essas verificações isso é possivel graças a dependencia
    spring-boot-starter-validation que está lá no arquivo POM.XML do projeto*/
    private String licensePlateCar;

    @NotBlank
    private String brandCar;//marca do carro

    @NotBlank
    private String modelCar;

    @NotBlank
    private String colorCar;

    @NotBlank
    private String responsibleName;

    @NotBlank
    private String apartment;

    @NotBlank
    private String block;

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
