package com.clara.rockpaperscissors;

/**
 * Created by admin on 11/5/16.
 */

public class Contract {

	interface ViewInterface {

		void displayPlayChoices(GameInterface callback);
		void displayWinner();


	}

	interface GameInterface {

		void userPlayed();
		void userReset();

	}


	interface OpponentCallbackInterface {

		void opponentFound(GameInterface callback);
		void opponentPlayed(GameInterface callback);
		void opponentReset(GameInterface callback);

	}


}
