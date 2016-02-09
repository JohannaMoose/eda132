import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Program {
	public static long timeLimitForSearch = 5;
	
	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Välkommen till Othello, du kommer spela som svart (B).");
		System.out.println();
		String timeInput;
		do {
			System.out.print("Hur lång tid vill du att datorn ska ha, skrivet i sekunder: ");
			timeInput = in.readLine();
		} while (!setTime(timeInput));
		System.out.println(String.format("Betänketiden för datorn är nu satt till %s", timeInput));
		System.out.println();
		System.out.println("Nu är det dags att börja spela, du som svart börjar.\nAnge ditt drag genom att skriva in först vilken bokstav och sedan vilken siffra som representerar var du vill lägga din bricka");
		System.out.println();

		OthelloGame board = new OthelloGame();
		System.out.println(board);
		String input = "";
		do {
			System.out.print("Vilket är ditt drag? ");
			input = in.readLine();
			if(processUserInput(input, board)){
				System.out.println();
				System.out.println(board);
				computerMove(timeInput, board);
				if (!gameContinues(timeInput, board)) return;
			}else {
				System.out.println("Det var inte ett gilltigt drag, välj något annat");
			}
		}
		while(!board.isFull());

		handleEndGame(board);
	}

	private static boolean setTime(String value) {
		try {
			timeLimitForSearch = Integer.parseInt(value);
			return true;
		} catch (NumberFormatException nfe) {
			System.err.println(String.format("Kunde inte sätta tiden till \"%s\", det var inte rätt formaterat", value));
			return false;
		}
	}

	private static boolean computerMove(String timeInput, OthelloGame currentBoard) {
		System.out.println();
		System.out.println(String.format("Nu är det datorns tur... den kommer tänka i %s sekunder", timeInput));
		OthelloGame.GameMove aiMove =Algorithm.alphaBetaSearch(currentBoard);
		if(aiMove != null){
			System.out.println("Datorn lägger sin bricka på " + aiMove.toString());
			currentBoard.makeMove(aiMove);
			System.out.println();
			System.out.println(currentBoard);
			System.out.println();
			return true;
		}else {
			System.out.println("Datorn kunde inte hitta ett gilltigt drag att göra");
			return false;
		}
	}

	private static boolean processUserInput(String input, OthelloGame currentBoard){
		if(input.length() == 2){
			OthelloGame.GameMove move = createMove(input);
			return (move.isInBounds()) ? currentBoard.makeMove(move):false;
		}
		return false;
	}

	private static OthelloGame.GameMove createMove(String input) {
		input = input.toLowerCase();
		OthelloGame.GameMove move = new OthelloGame.GameMove();
		move.x = input.charAt(0) - 'a';
		move.y = input.charAt(1) - '1';
		return move;
	}

	private static boolean gameContinues(String timeInput, OthelloGame board) {
		System.out.println("Vänta medan spelet analyseras");
		OthelloGame.GameMove possibleMove = Algorithm.alphaBetaSearch(board);
		if(possibleMove == null)
		{
			System.out.println("Det finns inga gilltiga drag du kan göra, därför är det datorns tur igen");
			board.changeCurrentPlayerTo(OthelloGame.WHITE);
			if(!computerMove(timeInput, board))
			{
				System.out.println("Spelet kan inte hitta några gilltiga drag för någon av spelarna, spelet avslutas");
				handleEndGame(board);
				return false;
			}
			else{
				System.out.println("Det är din tur igen");
				board.changeCurrentPlayerTo(OthelloGame.BLACK);
			}
		}else {
			System.out.println("Det är din tur igen");
		}
		return true;
	}

	private static void handleEndGame(OthelloGame board) {
		System.out.println();
		System.out.println("Spelet är nu avslutat");
		System.out.println("Du som spelade svart fick: " + board.countPoints(OthelloGame.BLACK));
		System.out.println("Datorn som spelade vitt fick: " + board.countPoints(OthelloGame.WHITE));
		System.out.print("Den som vann var ");
		switch (board.winnerIs()){
			case OthelloGame.WHITE: System.out.println("datorn");break;
			case OthelloGame.BLACK: System.out.println("du");break;
			default: System.out.println("ingen, det var oavgjort");break;
		}
		System.out.println();
		System.out.println("Programmet kommer nu att stänga ner, vill du spela igen måste du starta om programmet");
		System.out.println("Tack för att du spelade");
	}
}