package com.example.buscabombas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity   implements View.OnClickListener{


    Cell[][] myBoard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout frame = (TableLayout) findViewById(R.id.frame);;


    generateButtons(8,8,130,130, frame);

    generateBoard(8,10);



    System.out.println("QUEEEE:" + getSurroundingCells(myBoard[0][0]).size());


    System.out.println("GET SURROUNDING BOMBSSSS:");
    getSurroundingBombs(myBoard[2][2]);

/* ArrayList<Integer[]> randomCoordinates  = new ArrayList<>();

        Integer [] array = new Integer [2];

        array[0] = 22;

        array[1] = 3;

        randomCoordinates.add(array);

        System.out.println("EEEEEEY" + randomCoordinates.get(0)[0]);*/



    }



    public void generateButtons(int buttonsPerRow, int numberOfRows, int buttonWidth, int buttonHeight, TableLayout frame) {

        int amountOfButtons = buttonsPerRow * numberOfRows;

        int counter = 0;



        TableRow[] rows = new TableRow[numberOfRows];

        Button[] buttons = new Button[amountOfButtons];

        for (int i = 0; i < numberOfRows; i++) {

            rows[i] = new TableRow(this);

            rows[i].setLayoutParams(new TableRow.LayoutParams(buttonWidth*buttonsPerRow,buttonHeight));

            frame.addView(rows[i], new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));



            for (int j = 0; j < buttonsPerRow ; j++) {

                buttons[counter] = new Button(this);

                buttons[counter].setLayoutParams(new TableRow.LayoutParams(buttonWidth,buttonHeight));

                buttons[counter].setId(counter);

                buttons[counter].setOnClickListener(this);

                rows[i].addView(buttons[counter]);
                counter++;

            }


        }
        System.out.println("BOTONASO_____" + buttons[63].getId());

    }

    public void generateBombs(int numberOfBombs, int sizeOfGrid) {


       // board = new Cell[sizeOfGrid][sizeOfGrid];

        for (int i = 0; i < sizeOfGrid; i++) {


            for (int j = 0; j < sizeOfGrid; j++) {

            }
        }

    }


    public void randomBombs(int rowsNumber, int numberOfBombs) {


        ArrayList<Integer> randomCoordinates  = new ArrayList<>();


        for (int i = 0; i < rowsNumber; i++) {

            int x = (int)(Math.random()*rowsNumber);

            for (int j = 0; j < rowsNumber ; i++) {

                Integer [] array = new Integer [1];

                array[0] = 2;

                array[1] = 3;

                randomCoordinates.add(array[0],array[1]);

            }
        }

    }


    private boolean listContains (ArrayList <int[]> list, int i, int j) {
        for ( int[] pair:list) {
            if(pair[0]==i && pair[1]==j){
                return true;
            }
        }
        return false;
    }

    private void generateBoard(int rowsNumber, int numberOfBombs) {

        int id = 0;
        myBoard = new Cell[rowsNumber][rowsNumber];

        ArrayList<int[]> bombs = new ArrayList<>();



        for (int i = 0; i < numberOfBombs; i++) {
            int[] randomArray;

            int random1;
            int random2;
            do {
                System.out.println("numeros aleatorios");
                random1 = (int) (Math.random() * rowsNumber); //3
                random2 = (int) (Math.random() * rowsNumber); //2

                randomArray = new int[]{random1, random2};
            }
            while (listContains(bombs,random1,random2));

            bombs.add(randomArray);
            System.out.println(randomArray[0] + " " + randomArray[1]);
        }


        for (int i = 0; i < myBoard.length; i++) {
            for (int j = 0; j < myBoard[0].length; j++) {

                myBoard[i][j] = new Cell(listContains(bombs,i,j), false, i,j,id);


                System.out.print(myBoard[i][j].isBomb() + " ");
                id++;

                System.out.print(myBoard[i][j].getCoordX() + " " + myBoard[i][j].getCoordY());
            }
            System.out.println();
        }


    }

    @Override
    public void onClick(View view) {

        Button btn = (Button) view;


        int intID = view.getId();

        System.out.println(intID);

        Cell clickedCell = getCell(intID);


        if(clickedCell.isBomb()
        ) {
            btn.setText("B");
        }
        else{
            btn.setText(Integer.toString(getSurroundingBombs(clickedCell)));
        }



    }

    public Cell getCell(int id) {


        for (int i = 0; i < myBoard.length ; i++) {

            for (int j = 0; j < myBoard[0].length; j++) {

                if(myBoard[i][j].getId()==id){
                    return myBoard[i][j];
                }

            }
        }
        return null;

    }

    public int getSurroundingBombs(Cell cell) {

        int numberOfBombs = 0;

        for (  Cell cells : (getSurroundingCells(cell)) )  {

            if(cells.isBomb()) numberOfBombs++;
        }
        return numberOfBombs;
    }

    public ArrayList<Cell> getSurroundingCells(Cell cell) {


        ArrayList<Cell> surroundingCells  = new ArrayList<>();

        int x = cell.getCoordX();
        int y = cell.getCoordY();

    //FILA SUPERIOR
        if (x-1 >= 0 && y-1 >= 0 ) {

            surroundingCells.add(myBoard[x-1][y-1]);

        }
        if (x-1 >= 0 ) {

            surroundingCells.add(myBoard[x-1][y]);

        }
        if (x-1 >= 0 && y+1 < myBoard[0].length ) {

            surroundingCells.add(myBoard[x-1][y+1]);

        }

    //MISMA FILA
        if ( y-1 >= 0 ) {

            surroundingCells.add(myBoard[x][y-1]);

        }
        if (y+1 < myBoard[0].length ) {

            surroundingCells.add(myBoard[x][y+1]);

        }

    //FILA INFERIOR
        if (x+1 < myBoard[0].length && y-1 >= 0 ) {

            surroundingCells.add(myBoard[x+1][y-1]);

        }
        if (x+1 < myBoard[0].length ) {

            surroundingCells.add(myBoard[x+1][y]);

        }
        if (x+1 < myBoard[0].length && y+1 < myBoard[0].length ) {

            surroundingCells.add(myBoard[x+1][y+1]);

        }

        return surroundingCells;

    }


}