package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.dto.VoteSubmission
import net.nikodem.model.exception.EmptyElectionIdException
import net.nikodem.service.VoteReceivingService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class VoteReceivingControllerSpec extends Specification {

    MockMvc mockMvc;

    @Mock
    VoteReceivingService voteReceivingServiceMock

    @InjectMocks
    VoteReceivingController controller

    VoteSubmission voteSubmission = new VoteSubmission("electionId", "chosenAnswer", "voterKey", "voteKey");

    def setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "Submitting vote with valid information returns HTTP code ACCEPTED"() {
        when:
        doNothing().when(voteReceivingServiceMock).receiveVote(voteSubmission)
        then:
        mockMvc.perform(post("/votes")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voteSubmission))
        ).andExpect(status().isAccepted())
    }

    def "Submitting vote with invalid information throws exception"() {
        when:
        doThrow(EmptyElectionIdException).when(voteReceivingServiceMock).receiveVote(voteSubmission)
        then:
        mockMvc.perform(post("/votes")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voteSubmission))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.errorMessage', is(notNullValue())));
    }
}
