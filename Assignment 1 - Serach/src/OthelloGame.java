
public class OthelloGame {
	public int currentPlayer = OthelloGame.BLACK;
	public int[][] board;
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 3;

	public OthelloGame(){
		board = new int[8][8];
		board[4][3] = BLACK;
		board[3][4] = BLACK;
		board[3][3] = WHITE;
		board[4][4] = WHITE;
	}

    protected void setPlaceForTest(int x, int y, int color){
        board[x][y] = color;
    }

    public OthelloGame(OthelloGame other){
        board = new int[8][8];
        for(int i = 0; i < 8; i++) {
            for( int j = 0; j < 8; j++){
                board[i][j] = other.board[i][j];
            }
        }
        this.currentPlayer = other.currentPlayer;
    }

    public int countPoints() {
		int whitePoints = 0;
		int blackPoints = 0;

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++){
				switch(board[i][j]) {
				case EMPTY :
					break;
				case BLACK :
					blackPoints++;
					break;
				case WHITE :
					whitePoints++;
					break;
				}
			}
		}
		switch (currentPlayer) {
		case OthelloGame.BLACK :
			return blackPoints - whitePoints;
		case OthelloGame.WHITE :
			return whitePoints - blackPoints;
		default:
			return 0;
		}
	}

    public int countPoints(int color){
        int points = 0;

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == color)
                    points++;
            }
        }
        return points;
    }

    public int winnerIs(){
        int whitePoints = countPoints(WHITE);
        int blackPoints = countPoints(BLACK);

        if(whitePoints > blackPoints)
            return WHITE;
        else if(whitePoints == blackPoints)
            return 0;
        else
            return BLACK;
    }
	
	public boolean makeMove(GameMove move){
        if (isLegalMove(move) == Direction.None)
            return false;

        board[move.x][move.y] = currentPlayer;

        GameMove currentMove = new GameMove();
        for (Direction lookDir : Direction.values()) {
            if (lookDir != Direction.None && (findOneViableDirection(move, getDirection(lookDir, currentMove)))) {
                flipInDirection(move, getDirection(lookDir, currentMove));
            }
        }

        changeCurrentPlayerTo(currentPlayer ^= 0x2);

        return true;

	}

    public void changeCurrentPlayerTo(int color) {
        currentPlayer = color;
    }

    private void flipInDirection(GameMove startPosition, GameMove direction) {
		GameMove currentPosition = new GameMove(startPosition);
        currentPosition.add(direction);

		while(currentPosition.isInBounds() && isOppositeColor(board[currentPosition.x][currentPosition.y],currentPlayer)){

			board[currentPosition.x][currentPosition.y] = currentPlayer;
            currentPosition.add(direction);
		}
	}

	public Direction isLegalMove(final GameMove move) {
		if(!(move.isInBounds()) || board[move.x][move.y] != 0)
			return Direction.None;

		GameMove m = new GameMove();
        for (Direction dir: Direction.values()) {
			if(dir != Direction.None && (findOneViableDirection(move, OthelloGame.getDirection(dir,m)))) {
				return dir;
			}
		}
		return Direction.None;
	}

	private boolean findOneViableDirection(final GameMove move, GameMove direction){
		GameMove currentPosition = new GameMove(move);
		currentPosition.add(direction);

		if(!(currentPosition.isInBounds() && OthelloGame.isOppositeColor(board[currentPosition.x][currentPosition.y], currentPlayer))){
			return false;
		}
		while(currentPosition.isInBounds() && isOppositeColor(board[currentPosition.x][currentPosition.y], currentPlayer)){
			currentPosition.add(direction);
		}
		if(currentPosition.isInBounds() && board[currentPosition.x][currentPosition.y] == currentPlayer){
			return true;
		}
		return false;
		
	}

	private static boolean isOppositeColor(int boardValue, int color){

        return  ((boardValue ^ 0x2))  == color;
	}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   a  b  c  d  e  f  g  h \n");
        for(int i = 0; i < 8; i++) {
            sb.append(i+1);
            sb.append('|');
            for(int j = 0; j < 8; j++){
                switch(board[j][i]) {
                    case EMPTY :
                        sb.append(" - ");
                        break;
                    case BLACK :
                        sb.append(" B ");
                        break;
                    case WHITE :
                        sb.append(" W ");
                        break;
                }
                if(j ==  7) {
                    sb.append("|\n");
                }
            }
        }
        return sb.toString();
    }

    public static GameMove getDirection(Direction dir, GameMove d){
		switch(dir) {
            case Up:
                d.x = 0;
                d.y = -1;
                break;
            case RightUp:
                d.x = 1;
                d.y = -1;
                break;
            case Right:
                d.x = 1;
                d.y = 0;
                break;
            case RightDown:
                d.x = 1;
                d.y = 1;
                break;
            case Down:
                d.x = 0;
                d.y = 1;
                break;
            case LeftDown:
                d.x = -1;
                d.y = 1;
                break;
            case Left:
                d.x = -1;
                d.y = 0;
                break;
            case LeftUp:
                d.x = -1;
                d.y = -1;
                break;
        }
		return d;
	}

    public boolean isFull() {
        for(int i = 0; i < 8; i++) {
            for( int j = 0; j < 8; j++){
                if(board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    static public class GameMove {

		public int x;
		public int y;

		public GameMove(GameMove other){
			this.x = other.x;
			this.y = other.y;
		}
		
		public GameMove(){
			this.y = -1;
		}
		
		public void changeTo(GameMove other){
			this.x=other.x;
			this.y = other.y;
		}
		
		public boolean add(GameMove other){
			this.x += other.x;
			this.y += other.y;
			return isInBounds();
		}
		
		public boolean isInBounds(){
			return isInBounds(x) && isInBounds(y);
		}
		
		static boolean isInBounds(int c){
			return c < 8 && c >= 0;
		}
		
		public String toString(){
			return String.format("%c%d",'a' + x, 1 +y);
		}
	}
}
