package com.hotel.pethotel.controller;

import com.hotel.pethotel.dto.AnimalDto;
import com.hotel.pethotel.model.AnimalModel;
import com.hotel.pethotel.model.ClientModel;
import com.hotel.pethotel.model.UserModel;
import com.hotel.pethotel.service.AnimalService;
import com.hotel.pethotel.service.ClientService;
import com.hotel.pethotel.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.hotel.pethotel.mapper.AnimalMapper;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clientpanel")
public class ClientPanelController {
    private final AnimalService animalService;
    private final ClientService clientService;
    private final UserService userService;

//reservations//
    @GetMapping("/clientReservations")
    public String showClientReservation() {
        return "clientReservations";
    }

    //animals//
    @GetMapping("/clientAnimals")
    public String listAnimals(Model model) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        List<AnimalModel> animalList = clientService.getAnimalsByClientEmail(email);
        model.addAttribute("clientAnimals", animalList);
        return "Animals/clientAnimals";
    }

    @GetMapping("/clientAnimals/addAnimal")
    public String getAddClientAnimal(Model model) {
        AnimalModel animal = new AnimalModel();
        model.addAttribute("newAnimal", animal);
        return "Animals/addAnimal";
    }

    @PostMapping("/clientAnimals/addAnimal")
    public RedirectView postAddAnimal(@ModelAttribute("newAnimal") AnimalModel animal) {
        System.out.println("Received animal: " + animal);
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        ClientModel client = clientService.getClientByEmail(email);
        animal.setClient(client);
        animalService.addAnimal(animal);
        return new RedirectView("/clientpanel/clientAnimals");
    }


    //    @PostMapping ("/clientAnimals/addAnimal")
//    public RedirectView postAddAnimal(AnimalModel animal){
//        animalService.addAnimal(animal);
//        return new RedirectView("/clientpanel/clientAnimals");
//    }
    @GetMapping("/clientAnimals/editanimal/{id}")
    public String getEditAnimal(@PathVariable("id") String id, Model model) {
        Long animalId = Long.parseLong(id);
        AnimalModel animalModel = animalService.getAnimalById(animalId);
        model.addAttribute("editedAnimal", animalModel);
        return "Animals/editAnimal";
    }



    @PostMapping("/clientAnimals/editanimal")
    public RedirectView postEditAnimal(@ModelAttribute("editedAnimal") AnimalModel editedAnimal) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        ClientModel client = clientService.getClientByEmail(email);
        editedAnimal.setClient(client);
        animalService.saveEditAnimal(editedAnimal);
        return new RedirectView("/clientpanel/clientAnimals");
    }

    ///SEARCHER//

//@GetMapping ("/searcher")
//public String listAnimalsToChoose(Model model) {
//    String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//    List<AnimalModel> animalList = clientService.getAnimalsByClientEmail(email);
//    model.addAttribute("clientAnimals", animalList);
//    return "Searcher/clientAnimalList";
//
//}

//    @GetMapping ("/searcher")
//    public String listAnimalsToChoose(Model model) {
//        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        List<AnimalModel> animalList = clientService.getAnimalsByClientEmail(email);
//        // Convert AnimalModel to AnimalDto using AnimalMapper
//        List<AnimalDto> animalDtoList = animalList.stream()//TWORZE STREAM Z LISTY ANIMALlIST
//                .map(AnimalMapper::toAnimalDto)//TUTAJ ITERUJE I PRZEKSZTALCAM OBIEKT TYPU ANIMALMODEL NA ANIMALDTO
//                .collect(Collectors.toList());//ZBIERAM WYNIK PRZETWOREZENIA NA ANIMALDTO I ZBIERAMY ELEMENTY STRUMIENIA I ZAPISUJE JE DO LISTY
//
//        model.addAttribute("clientAnimals", animalDtoList);
//        return "Searcher/clientAnimalList";
//
//    }

//    @GetMapping("/personalData")
//    public String showPersonalData(Model model, Principal principal) {
//        String userEmail = principal.getName();
//        UserModel user = userService.getUserByEmail(userEmail);
//
//        if (user != null) {
//            model.addAttribute("user", user);
//        }
//
//        return "personalData";
//    }

//    @PostMapping("/clientAnimals")
//    public String showClientAnimals() {
//        return "Animals/clientAnimals";
//    }


//    @GetMapping("/logout")
//    public String logout() {
//        return "redirect:/login";
//    }


}