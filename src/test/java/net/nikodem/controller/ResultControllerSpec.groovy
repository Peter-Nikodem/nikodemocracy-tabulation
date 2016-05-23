package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.exception.ElectionDoesNotExistException
import net.nikodem.model.dto.ElectionResults
import net.nikodem.model.dto.ResultPlace
import net.nikodem.model.dto.VoteProof
import net.nikodem.service.ResultsService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ResultControllerSpec extends Specification {

    MockMvc mockMvc;

    @Mock
    ResultsService resultsServiceMock

    @InjectMocks
    ResultsController controller

    ResultPlace[] resultsExample = [new ResultPlace('a', 4), new ResultPlace('c', 2), new ResultPlace('b', 0)]
    VoteProof[] voteProofsExample = [new VoteProof('01', 'a'), new VoteProof('02', 'a'), new VoteProof('03', 'c'), new VoteProof('04', 'a'),new VoteProof('05','c'),new VoteProof('06','a')]
    ElectionResults ELECTION_RESULTS_EXAMPLE = new ElectionResults("electionId1", "Favorite alphabet letter?",resultsExample,voteProofsExample)


    def setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "Requesting results with valid electionId return results object"() {
        when:
        when(resultsServiceMock.getElectionResults('electionId1')).thenReturn(ELECTION_RESULTS_EXAMPLE)
        then:
        mockMvc.perform(get("/results/electionId1")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.electionId', is('electionId1')))
                .andExpect(jsonPath('$.question',is('Favorite alphabet letter?')))
                .andExpect(jsonPath('$.voteProofs[0].voteKey',is('01')))
                .andExpect(jsonPath('$.voteProofs[2].answer',is('c')))
                .andExpect(jsonPath('$.sortedResults[0].votes',is(4)))
    }

    def "Requesting results with electionId must exist"(){
        when:
        when(resultsServiceMock.getElectionResults('electionId2')).thenThrow(new ElectionDoesNotExistException('electionId2'))
        then:
        mockMvc.perform(get("/results/electionId2")
            .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )       .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$.errorMessage',is('Election (electionId2) does not exist.')))
    }
}
