/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/8/15
*/


package com.game.resources;


/*
 * HelperResource Utility class for GameStateResource
 */
public class HelperResource {

	public static boolean validMove(String nextState, String currentState, 
			int player) {
		
		boolean toReturn = true;
		
		/*
		 *  Check to find if the currentState and nextState differ by only one
		 *  slot. PlayerOne is always moves X and PlayerTwo always moves O.
		 */
		
		int numberOfChanges = 0;
		for(int x = 0; x < 9; x++) {
			if(currentState.charAt(x) != nextState.charAt(x)) {
				numberOfChanges++; 
				/*
				 * numberOfChanges must not be greater than 1
				 * Changes if player 1 has nextMove must be X
				 * Changes if player 2 has nextMove must be O
				 * Changes must not overwrite a previous turns move
				 */
				if(numberOfChanges > 1) {
					return false; 
				} else if((player == 1) && (nextState.charAt(x) != 'X')) {
					return false;
				} else if((player == 2) && (nextState.charAt(x) != 'O')) {
					return false;
				} else if((nextState.charAt(x) == 'X') && 
						(currentState.charAt(x) == 'O')) {
					return false;
				} else if((nextState.charAt(x) == 'O') && 
						(currentState.charAt(x) == 'X')) {
					return false;
				}
			}
		}
		
		// return false if no change has occurred
		if(numberOfChanges == 0) {
			return false;
		}
		
		return toReturn;
	}
	
	public static char winnerCondition(String currentState) {
		char toReturn = 'E';
		
		// Check horizontal
		for(int y = 0; y < 3; y++) {
			if((currentState.charAt(y * 3) == currentState.charAt(y * 3 + 1))
					&& (currentState.charAt(y * 3) == currentState.charAt(y * 3 + 2))
					&& (currentState.charAt(y * 3) != 'E')) {
				return currentState.charAt(y * 3);
			}
		}
		
		// Check vertical
		for(int y = 0; y < 3; y++) {
			if((currentState.charAt(y) == currentState.charAt(y + 3))
					&& (currentState.charAt(y) == currentState.charAt(y + 6))
					&& (currentState.charAt(y) != 'E')) {
				return currentState.charAt(y);
			}
		}
		
		// Check across
		if((currentState.charAt(0) == currentState.charAt(4))
				&& (currentState.charAt(0) == currentState.charAt(8))
				&& (currentState.charAt(0) != 'E')) {
			return currentState.charAt(0);
		} else if((currentState.charAt(2) == currentState.charAt(4))
				&& (currentState.charAt(2) == currentState.charAt(6))
				&& (currentState.charAt(2) != 'E')) {
			return currentState.charAt(2);
		}
		
		return toReturn;
	}
}
