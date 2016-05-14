package net.nikodem.controller;

import net.nikodem.model.exception.NikodemocracyException;
import net.nikodem.model.json.ErrorMessage;
import net.nikodem.model.json.VoteSubmission;
import net.nikodem.service.VoteReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VoteReceivingController {

    @Autowired
    private VoteReceivingService voteReceivingService;

    @RequestMapping(value = "/votes", method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> receiveVote(@RequestBody VoteSubmission voteSubmission){
        try {
            voteReceivingService.receiveVote(voteSubmission);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NikodemocracyException ex){
            return new ResponseEntity<>(ex.getErrorMessageJson(), HttpStatus.BAD_REQUEST);
        }
    }

}
