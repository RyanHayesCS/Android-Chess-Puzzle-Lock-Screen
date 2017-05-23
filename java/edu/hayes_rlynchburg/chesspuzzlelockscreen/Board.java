package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static edu.hayes_rlynchburg.chesspuzzlelockscreen.Tile.Color.BLACK;
import static edu.hayes_rlynchburg.chesspuzzlelockscreen.Tile.Color.WHITE;
import static java.lang.Integer.parseInt;

/**
 * Created by Ryan Hayes on 2/6/2017.
 */

public class Board extends BaseAdapter {

    /*public class pawnJump{
        public int oldTile;
        public int newTile;
    }*/

    public static int blackKingLoc;
    public static int whiteKingLoc;

    public static boolean[] promotionCandidates;

    public static Context mContext;

    public Board(Context c){
        mContext = c;
        promotionCandidates = new boolean[64];
        board_ = new Tile[64];
        initializeBoard();
    }

    public int getCount(){
        return board_.length;
    }

    public Object getItem(int position){
        return board_[position];
    }

    public long getItemId(int position){
        return 0;
    }

    public void swap(int a, int b, Context cxt){
        int prevWKing = whiteKingLoc;
        int prevBKing = blackKingLoc;
        if(board_[a].isOccupied() && board_[b].getColor() != board_[a].getColor() && board_[a].isValid(b)) {
            mContext =cxt;
            //Log.d("State", "A and B: " + a + " " + b );
            if(board_[a].getName() == "king")
            {
                if(b == a+2){
                    Board.board_[a+3].format(Board.board_[a+1], Board.mContext);
                }else if(b == a-2){
                    Board.board_[a-4].format(Board.board_[a-1], Board.mContext);
                }
            }
            board_[a].format(board_[b], cxt);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

       // imageView.setImageResource(board_[position]);
        imageView.setImageResource(board_[position].getId());

        return imageView;
    }

    public static Tile[] board_;

    private void initializeBoard(){
        /*for(int i = 0; i < 64;++i){
            if((i % 2 != 0) && (i % 7 != 0)){
                board_[i] = new Tile("empty", Tile.Color.WHITE);
            }else{
                board_[i] = new Tile("empty", Tile.Color.BLACK);
            }

        }*/
        board_[0] = new Tile("empty", WHITE, 0);
        board_[1] = new Tile("empty", BLACK, 1);
        board_[2] = new Tile("empty", WHITE, 2);
        board_[3] = new Tile("empty", BLACK, 3);
        board_[4] = new Tile("empty", WHITE, 4);
        board_[5] = new Tile("empty", BLACK, 5);
        board_[6] = new Tile("empty", WHITE, 6);
        board_[7] = new Tile("empty", BLACK, 7);

        board_[8] = new Tile("empty", BLACK, 8);
        board_[9] = new Tile("empty", WHITE, 9);
        board_[10] = new Tile("empty", BLACK, 10);
        board_[11] = new Tile("empty", WHITE, 11);
        board_[12] = new Tile("empty", BLACK, 12);
        board_[13] = new Tile("empty", WHITE, 13);
        board_[14] = new Tile("empty", BLACK, 14);
        board_[15] = new Tile("empty", WHITE, 15);

        board_[16] = new Tile("empty", WHITE, 16);
        board_[17] = new Tile("empty", BLACK, 17);
        board_[18] = new Tile("empty", WHITE, 18);
        board_[19] = new Tile("empty", BLACK, 19);
        board_[20] = new Tile("empty", WHITE, 20);
        board_[21] = new Tile("empty", BLACK, 21);
        board_[22] = new Tile("empty", WHITE, 22);
        board_[23] = new Tile("empty", BLACK, 23);

        board_[24] = new Tile("empty", BLACK, 24);
        board_[25] = new Tile("empty", WHITE, 25);
        board_[26] = new Tile("empty", BLACK, 26);
        board_[27] = new Tile("empty", WHITE, 27);
        board_[28] = new Tile("empty", BLACK, 28);
        board_[29] = new Tile("empty", WHITE, 29);
        board_[30] = new Tile("empty", BLACK, 30);
        board_[31] = new Tile("empty", WHITE, 31);

        board_[32] = new Tile("empty", WHITE, 32);
        board_[33] = new Tile("empty", BLACK, 33);
        board_[34] = new Tile("empty", WHITE, 34);
        board_[35] = new Tile("empty", BLACK, 35);
        board_[36] = new Tile("empty", WHITE, 36);
        board_[37] = new Tile("empty", BLACK, 37);
        board_[38] = new Tile("empty", WHITE, 38);
        board_[39] = new Tile("empty", BLACK, 39);

        board_[40] = new Tile("empty", BLACK, 40);
        board_[41] = new Tile("empty", WHITE, 41);
        board_[42] = new Tile("empty", BLACK, 42);
        board_[43] = new Tile("empty", WHITE, 43);
        board_[44] = new Tile("empty", BLACK, 44);
        board_[45] = new Tile("empty", WHITE, 45);
        board_[46] = new Tile("empty", BLACK, 46);
        board_[47] = new Tile("empty", WHITE, 47);

        board_[48] = new Tile("empty", WHITE, 48);
        board_[49] = new Tile("empty", BLACK, 49);
        board_[50] = new Tile("empty", WHITE, 50);
        board_[51] = new Tile("empty", BLACK, 51);
        board_[52] = new Tile("empty", WHITE, 52);
        board_[53] = new Tile("empty", BLACK, 53);
        board_[54] = new Tile("empty", WHITE, 54);
        board_[55] = new Tile("empty", BLACK, 55);

        board_[56] = new Tile("empty", BLACK, 56);
        board_[57] = new Tile("empty", WHITE, 57);
        board_[58] = new Tile("empty", BLACK, 58);
        board_[59] = new Tile("empty", WHITE, 59);
        board_[60] = new Tile("empty", BLACK, 60);
        board_[61] = new Tile("empty", WHITE, 61);
        board_[62] = new Tile("empty", BLACK, 62);
        board_[63] = new Tile("empty", WHITE, 63);
    }

    public void placePieces(String layout){

        char[] layoutArray = layout.toCharArray();

        for(int i = 0;i < layout.length() - 1;) {
            String pieceName = "pawn";
            Tile.Color pieceColor = WHITE;
            Tile.Color tileColor = WHITE;
            int boardIndex = 0;
            if(layoutArray[i] == 'b'){
                pieceColor = BLACK;
            }
            i++;
            if(layoutArray[i] == 'b'){
                tileColor = BLACK;
            }
            i++;
            if(layoutArray[i] == 'p'){
                pieceName = "pawn";
            }
            else if(layoutArray[i] == 'r'){
                pieceName = "rook";
            }
            else if(layoutArray[i] == 'n'){
                pieceName = "knight";
            }
            else if(layoutArray[i] == 'b'){
                pieceName = "bishop";
            }
            else if(layoutArray[i] == 'q'){
                pieceName = "queen";
            }
            else if(layoutArray[i] == 'k'){
                pieceName = "king";
            }
            i++;
            boardIndex = getIndex(layoutArray, i);
            i+=2;

            if(pieceName == "king"){
                if(pieceColor.equals(BLACK)){
                    blackKingLoc = boardIndex;
                }else{
                    whiteKingLoc = boardIndex;
                }
            }

            board_[boardIndex] = new Tile(pieceName, tileColor, pieceColor, boardIndex);
        }
    }

    public static boolean evaluate(String answer)
    {
        String currentLayout = "";
        for(Tile piece: board_){
            if(piece.getName() != "empty"){
                String pieceColor = "b";
                String tileColor = "b";
                String pieceName = "p";
                String pieceLoc = "0";

                if(piece.getColor() == WHITE){
                    pieceColor = "w";
                }
                if(piece.getTileColor() == WHITE){
                    tileColor = "w";
                }

                if(piece.getName() == "rook"){
                    pieceName = "r";
                }
                else if(piece.getName() == "bishop"){
                    pieceName = "b";
                }
                else if(piece.getName() == "knight"){
                    pieceName = "n";
                }
                else if(piece.getName() == "queen"){
                    pieceName = "q";
                }
                else if(piece.getName() == "king"){
                    pieceName = "k";
                }

                if(piece.getLoc() < 10){
                    pieceLoc += Integer.toString(piece.getLoc());
                }
                else{
                    pieceLoc = Integer.toString(piece.getLoc());
                }

                currentLayout += (pieceColor + tileColor + pieceName + pieceLoc);
            }
        }
        Log.d("STATE", "The generated answer is: " + currentLayout);
        return (answer.equals(currentLayout));
    }

    private int getIndex(char[] layout, int index){
        String boardIndex = String.valueOf(layout[index]) + String.valueOf(layout[index+1]);
        return parseInt(boardIndex, 10);
    }

    static Collection<Integer> calculateAttacksOnTile(final int tile,
                                                   final Collection<Integer> moves) {
        final List<Integer> attackMoves = new ArrayList<>();
        //Log.d("STATE", "The Kings Tile is: " + Integer.toString(Tile));
        for (final int move : moves) {
            if (tile == move) {
                attackMoves.add(move);
            }
        }
       // Log.d("STATE", "The amount of attack moves on the king is: " + Integer.toString(attackMoves.size()));
        return attackMoves;
    }

    public boolean isInCheck(Tile.Color color){
        boolean isCheck = false;
        if(color.equals(BLACK)){
            isCheck = !calculateAttacksOnTile(blackKingLoc, calculateAllMovesWhite()).isEmpty();
        }
        else{
            isCheck = !calculateAttacksOnTile(whiteKingLoc, calculateAllMovesBlack()).isEmpty();
        }
        return isCheck;
    }

    public boolean hasEscapeMoves(Tile.Color color) {
        if(color.equals(BLACK)){
            for(int move : calculateAllMovesBlack()) {
                if(!board_[move].isOccupied()){
                    board_[move].setName("pawn");
                    if (!isInCheck(BLACK)) {
                        Log.d("STATE", "Made it out of check: " + move);
                        board_[move].setName("empty");
                        return true;
                    }
                    board_[move].setName("empty");
                }
                else{
                    String previousName =  board_[move].getName();
                    Tile.Color previousColor = board_[move].getColor();
                    Tile.Color previousKingTile = board_[blackKingLoc].getTileColor();
                    int prevLoc = blackKingLoc;
                    board_[prevLoc].format(board_[move], mContext);
                    blackKingLoc = move;
                   if(!isInCheck(BLACK)) {
                       board_[move] = new Tile(previousName, board_[move].getTileColor(), previousColor, move);
                       board_[prevLoc] = new Tile("king", previousKingTile, BLACK, prevLoc);
                       blackKingLoc = prevLoc;
                       return true;
                   }
                    board_[prevLoc] = new Tile("king", previousKingTile, BLACK, prevLoc);
                    board_[move] = new Tile(previousName, board_[move].getTileColor(), previousColor, move);
                    blackKingLoc = prevLoc;
                }
            }
        }else{
            for(final int move : calculateAllMovesWhite()) {
                if(!board_[move].isOccupied()){
                    board_[move].setName("pawn"); //can placing a piece where a check is happening stop the check?
                    if (!isInCheck(WHITE)) {
                        board_[move].setName("empty");
                        return true;
                    }
                    board_[move].setName("empty");
                }else{
                    String previousName =  board_[move].getName();
                    Tile.Color previousColor = board_[move].getColor();
                    Tile.Color previousKingTile = board_[whiteKingLoc].getTileColor();
                    int prevLoc = whiteKingLoc;
                    board_[prevLoc].format(board_[move], mContext);
                    whiteKingLoc = move;
                    if(!isInCheck(WHITE)) {
                        board_[move] = new Tile(previousName, board_[move].getTileColor(), previousColor, move);
                        board_[prevLoc] = new Tile("king", previousKingTile, WHITE, prevLoc);
                        whiteKingLoc = prevLoc;

                        return true;
                    }
                    board_[prevLoc] = new Tile("king", previousKingTile, WHITE, prevLoc);
                    board_[move] = new Tile(previousName, board_[move].getTileColor(), previousColor, move);
                    whiteKingLoc = prevLoc;
                }
            }
        }
        return false;
    }

    static Collection<Integer> calculateAllMovesBlack(){
        final List<Integer> attackMoves = new ArrayList<>();
        for(int i = 0; i < 64; i++){
            if(board_[i].getColor().equals(Tile.Color.BLACK)){
                if (board_[i].getName() == "pawn") {
                    attackMoves.addAll(board_[i].calculatePawnLegalMoves());
                }
                else if(board_[i].getName() == "rook") {
                    attackMoves.addAll(board_[i].calculateRookLegalMoves());
                }
                else if (board_[i].getName() =="bishop") {
                    attackMoves.addAll(board_[i].calculateBishopLegalMoves());
                }
                else if (board_[i].getName() =="king"){
                    attackMoves.addAll(board_[i].calculateKingLegalMoves());
                }
                else if (board_[i].getName() == "queen"){
                    attackMoves.addAll(board_[i].calculateQueenLegalMoves());
                }
                else if (board_[i].getName() == "knight"){
                    attackMoves.addAll(board_[i].calculateKnightLegalMoves());
                }
            }
        }
        return attackMoves;
    }

    static Collection<Integer> calculateAllMovesWhite(){
        final List<Integer> attackMoves = new ArrayList<>();
        for(int i = 0; i < 64; i++){
            if(board_[i].getColor().equals(WHITE)){
                if (board_[i].getName() == "pawn") {
                    attackMoves.addAll(board_[i].calculatePawnLegalMoves());
                }
                else if(board_[i].getName() == "rook") {
                    attackMoves.addAll(board_[i].calculateRookLegalMoves());
                }
                else if (board_[i].getName() =="bishop") {
                    attackMoves.addAll(board_[i].calculateBishopLegalMoves());
                }
                else if (board_[i].getName() =="king"){
                    attackMoves.addAll(board_[i].calculateKingLegalMoves());
                }
                else if (board_[i].getName() == "queen"){
                    attackMoves.addAll(board_[i].calculateQueenLegalMoves());
                }
                else if (board_[i].getName() == "knight"){
                    attackMoves.addAll(board_[i].calculateKnightLegalMoves());
                }
            }
        }
        return attackMoves;
    }

    public void clear(){
           initializeBoard();
    }
}
