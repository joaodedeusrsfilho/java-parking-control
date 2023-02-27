package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController/*RestController será a camada que vai receber as solicitações
 pois vamos criar uma API REST*/

@CrossOrigin(origins = "*", maxAge = 3600)/*para permitir que seja acessado de qualquer
fonte*/

@RequestMapping("/parking-spot")//URI a nivel de classe para acessar esse recurso

public class ParkingSpotController {
    @Autowired //injeção de dependencia para acessar a interface parkingSpotService
    ParkingSpotService parkingSpotService;

    @PostMapping("")/*aqui foi anotado como POSTMapping pq se trata de um metodo
    Post. e aqui não foi definido nenhuma URI pq já foi definida a nivel de
    class com a URI /parking-spot, ou seja quando ela foi requisitada este metodo será
    chamado.*/

    /*o metodo abaixo saveParkingSpot é do tipo ResponseEntity, pois vai retornar
    * o Status e o corpo da resposta do metodo*/
    /*depois foi utilizado <Object> pq vamos receber diferente tipos de retorno
    * a depender de algumas verificações que vamos fazer inicialmente*/
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        /*este metodo recebe um parkingSpotDto que tem as informações que serão passada pelo
        * o cliente, que será passado pro metodo via JSON, pois estamos contruindo uma
        * API REST, foi preciso definir dentro do parentese do metodo saveParkingSpot o @RequestBody
        * para ele receber os dados via JSON. e foi declado @Valid também para que
        * os dados cheguem validados conforme estão validados dentro da classe
        * ParkingSpotDto*/

        /*abaixo verificações se existem os dados abaixo no banco de dados*/
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Placa já em uso!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Vaga já em uso!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Apartamento e bloco já em uso!");
        }

        var parkingSpotModel = new ParkingSpotModel();/*aqui uma novidade
        declando uma variavel com var e apos instanciada com new, já fica declado
        que a variavel é do tipo que está sendo instanciado pelo o new*/

        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);/*aqui é uma forma
        de converter o DTO para MODEL, utilizando este metodo*/

        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        /*aqui estamos setando a data de registro, passando para UTC*/

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        /*em ResponseEntity.status(HttpStatus.CREATED) estou passando o status HttpStatus
        * e em body(parkingSpotService.save(parkingSpotModel)) estou passando o retorno do metodo save
        * que vai ser a vaga de estacionamento salva com a data e com id já no banco*/
    }
    /*abaixo o metodo Get, primeiro vamos fazer o metodo getAll, solicitando
    * toda a listagem de vagas de estacionamento que estão listadas no banco de dados*/

    /*O metodo abaixo vai retornar uma listagem dos parkingSpotsModel existente la no
    * banco de dados*/
    @GetMapping
    /*Vamos inserir paginação na nossa API no metodo getAllParkingSports*/
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page=0, size=10, sort="id", direction= Sort.Direction.ASC) Pageable pageable){
    /*page =0, size o numero de elementos, sort pelo o que ele vai ordenar e vamos passsar o pageable como parametro do metodo abaixo o findAll e ao inves
    * de retornar uma List vamos retonar um Page e vamos no service passar o Page*/
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    /* agora vamos programar mais um endpoint ou seja mais um serviço que será obter
    o id */
    @GetMapping("/{id}")/*aqui o URI será = localhost/parking-spot/{id} sempre que essa URI foi digitada no navegador
    este metodo será acionado*/
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value="id") UUID id){/*@PathVariable(value="id") para
    capturar o id da URL do browser, e qual o tipo desse id? é o UUID*/
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        /*um Optional com o valor pedido pelo método. O valor pode ser nulo ou não. Esse método é o mais usado.*/
        /*abaixo vamos fazer uma verificação com o if*/

        if(!parkingSpotModelOptional.isPresent()){//se não for presente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Essa vaga de estacionamento não existe.");
        }
        //caso exista
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    //Metodo para deletar ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value="id") UUID id){/*@PathVariable(value="id") para
    capturar o id da URL do browser, e qual o tipo desse id? é o UUID*/
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        /*um Optional com o valor pedido pelo método. O valor pode ser nulo ou não. Esse método é o mais usado.*/
        /*abaixo vamos fazer uma verificação com o if*/

        if(!parkingSpotModelOptional.isPresent()){//se não for presente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Essa vaga de estacionamento não existe.");
        }
        //caso exista
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Id deletado com sucesso!");
    }

    //Metodo PUT = atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){/*aqui estou
                                                    passando dois parametros ID e parkingSpotDto com os campos
                                                    para serem validados utilizando o @Valid*/
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        /*um Optional com o valor pedido pelo método. O valor pode ser nulo ou não. Esse método é o mais usado.*/
        /*abaixo vamos fazer uma verificação com o if*/

        if(!parkingSpotModelOptional.isPresent()){//se não for presente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Essa vaga de estacionamento não existe.");
        }
        //caso exista
        /*existem duas maneiras de realizar o PUT vamos a primeira*/
        /*
        var parkingSpotModel = parkingSpotModelOptional.get();/*quando a gente cria essa variavel
        a gente pode obter os registros do banco de dados que foram obtidos em parkingSpotModelOptional = parkingSpotService.findById(id);
        agora vamos setar para atualizar os registros do banco de dados mas, não iremos setar o ID nem a DATA DE REGISTRO
        pois essas campos já foram criados no momento da criação dos dados.*/
        /*
        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
        parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
        parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
        parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
        parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
        parkingSpotModel.setBlock(parkingSpotDto.getBlock());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));*/

        //a segunda forma agora
        var parkingSpotModel = new ParkingSpotModel();//criando uma nova instalancia de parkingSpotModel
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);//fazer a conversão de Dto para Model
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}
