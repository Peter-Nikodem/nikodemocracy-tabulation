package net.nikodem.controller;

import net.nikodem.model.dto.*;
import net.nikodem.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class ElectionReceivingController {

    private ElectionReceivingService electionReceivingService;

    @RequestMapping(value = "/elections", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receiveElectionInformation(@RequestBody ElectionInformation electionInformation) {
        electionReceivingService.receiveElection(electionInformation);
    }

    @Autowired
    public void setElectionReceivingService(ElectionReceivingService electionReceivingService) {
        this.electionReceivingService = electionReceivingService;
    }

}
