package net.nikodem.testdata

import net.nikodem.model.dto.ElectionInformation


class ExampleData {

    public static final ElectionInformation ELECTION_EXAMPLE = new ElectionInformation(
            '01',
            'Favorite alphabet character',
            ['a', 'b', 'c', 'd', 'e', 'h'],
            [FIRST_VOTER_KEY,SECOND_VOTER_KEY,THIRD_VOTER_KEY,FOURTH_VOTER_KEY,FIFTH_VOTER_KEY]
    )

    public static final String FIRST_VOTER_KEY = '0000111122223333'
    public static final String SECOND_VOTER_KEY = '0011223344556677'
    public static final String THIRD_VOTER_KEY = '1112223334445556'
    public static final String FOURTH_VOTER_KEY = '9999888877776666'
    public static final String FIFTH_VOTER_KEY = '0123456701234567'
}
