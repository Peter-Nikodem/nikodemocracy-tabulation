package net.nikodem.controller;

import net.nikodem.model.exception.*;
import net.nikodem.model.dto.*;
import net.nikodem.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResultsController {

    private ResultsService resultsService;

    @RequestMapping(value = "/results/{electionId}", method = RequestMethod.GET)
    public ResponseEntity<AbstractNikodemocracyResponse> getResults(@PathVariable(value = "electionId") String electionId) {
        try {
            ElectionResults results = resultsService.getElectionResults(electionId);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (NikodemocracyRequestException ex) {
            return new ResponseEntity<>(ex.getErrorMessageJson(),HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    public void setResultsService(ResultsService resultsService) {
        this.resultsService = resultsService;
    }
}
