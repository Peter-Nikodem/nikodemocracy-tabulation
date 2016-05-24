RESTful registration authority for a simple voting system protocol.

## Description

My second attempt for a proof of concept for an implementation of a secure election protocol with 2 central authorities mentioned in Bruce Schneier's Applied Cryptography.

According to Schenier, ideal voting protocol has, at the very least, these six requirements:
 1. Only authorized voters can vote.
 2. No one can vote more than once.
 3. No one can determine for whom anyone else voted.
 4. No one can change or duplicate anyone else's vote.
 5. Every voter can make sure that his vote has been taken into account in the final tabulation.
 6. (Optional) Everyone knows who voted and who didn't.

One of the proposed ways to achieve this is to use two central authorities.

### Protocol

#### Registration authority (RA) - https://github.com/Peter-Nikodem/nikodemocracy-registration
  - responsible for creating election and registering voters.

#### Tabulation authority (TA)
  - responsible for tabulating votes and publishing results.

#### Steps:
 1. All voters create an account at RA by choosing an username and a password.
 2. Election can be registered by choosing electionId, question, possible answers and lists of usernames of invited voters.
 3. RA sends list of all voter keys to TA (without mappings to voters).
 4. Every invited voter can randomly choose a vote key and submits vote to TA that consists of electionId, vote key, chosen answer and his voterKey that he can request from RA by authentification with electionId and his username and password. 
 5. When TA receives a vote, the authority verifies that vote contains a valid and not yet used voter key and stores pair vote key - chosen option.
 6. Anyone who knows electionId can request current results of the election. Results contain electionId, question, pairs of chosen answers and number of votes they have received sorted by the greatest amount of votes and list of voteKeys and their chosen answer. Any voter can verify his vote was correctly counted by identifying it through vote key.

Protocol is potentially vulnerable to collusion between RA and TA. If they cooperated, they could correlate databases and figure out who voted for whom.
However, since registration and tabulation are split into separate projects, they can be each hosted by independent entity and monitored by third party in order to prevent any undesired cooperation.



