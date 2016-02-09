public class Algorithm {
	private static long startTime;
	public static int numMovesTried;
	
	
	public static OthelloGame.GameMove alphaBetaSearch(OthelloGame currentBoard){
		startTime = System.currentTimeMillis();

		return findBestMove(currentBoard, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private static OthelloGame.GameMove findBestMove(OthelloGame state, int alpha, int beta) {
		OthelloGame.GameMove action = new OthelloGame.GameMove();

		if(terminalTest(state, action))
			return null;

		int v = Integer.MIN_VALUE;

		OthelloGame.GameMove bestMove = new OthelloGame.GameMove(action);

		do {
			int minTest = minValue(result(state, action), alpha, beta);

			if(minTest > v){
				v = minTest;
				bestMove.changeTo(action);
			}

			alpha = Math.max(alpha, v);

		}while ( getNextMove(state, action) != null);

		return bestMove;
	}

	private static int maxValue(OthelloGame state, int alpha, int beta) {
		OthelloGame.GameMove action = new OthelloGame.GameMove();

		if(terminalTest(state, action))
			return state.countPoints();

		int v = Integer.MIN_VALUE;

		do {

			v = Math.max(v, minValue(result(state, action), alpha, beta));

			if(v >= beta)
				return v;

			alpha = Math.max(alpha, v);

		}while ( getNextMove(state, action) != null);

		return v;
	}

	private static int minValue(OthelloGame state, int alpha, int beta) {
		OthelloGame.GameMove action = new OthelloGame.GameMove();

		if(terminalTest(state, action))
			return state.countPoints();

		int v = Integer.MAX_VALUE;

		do {

			v = Math.min(v, maxValue(result(state, action), alpha, beta));

			if(v <= alpha)
				return v;

			beta = Math.min(beta, v);
			
		} while (getNextMove(state, action) != null);

		return v;
	}
	
	private static boolean terminalTest(OthelloGame currentBoard, OthelloGame.GameMove action){
		return getNextMove(currentBoard, action) == null;
	}

	private static OthelloGame result(OthelloGame currentBoard, OthelloGame.GameMove move) {
		OthelloGame newBoard = new OthelloGame(currentBoard);
		newBoard.makeMove(move);
		return newBoard;
	}

	private static OthelloGame.GameMove getNextMove(OthelloGame state, OthelloGame.GameMove move) {

		if (timeSpent() > Program.timeLimitForSearch)
			return null;
		else
			return nextLegalMove(state, move);
	}

	private static long timeSpent() {
		return (System.currentTimeMillis() - startTime) / 1000;
	}

	private static OthelloGame.GameMove nextLegalMove(OthelloGame state, OthelloGame.GameMove move) {
		while (move.x < 8) {
			while (move.y < 7) {
				if (state.isLegalMove(move) != Direction.None) {
					numMovesTried++;
					return move;
				}
				move.y++;
			}
			move.x++;
			move.y = 0;
		}

		return null;
	}
}
