package com.example.buscabombas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity   implements View.OnClickListener, View.OnLongClickListener {


    Cell[][] myBoard;

    Button[] buttons;
    TableRow[] rows;

    int gameState = 1;

    boolean gameOn = true;
    //TEST TEST TEST

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TableLayout frame = (TableLayout) findViewById(R.id.frame);;

        Button activity = (Button) findViewById(R.id.changeActivity);

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                gameState = 3;
                System.out.println(gameState);

                frame.removeAllViews();

                if(gameState==2) {
                    generateButtons(12,12,86,100, frame);

                    generateBoard(12,30);



                }
                if(gameState==3) {
                    generateButtons(16,16,65,100, frame);

                    generateBoard(16,60);



                }
            }
        });



        if(gameState==1){


    generateButtons(8,8,130,130, frame);

    generateBoard(8,10);



        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int integerID = item.getItemId();

        switch (integerID){


            case R.id.instrucciones:

                System.out.println("Instrucciones");

                message();

                return true;



            case R.id.nuevojuego:

                System.out.println("Nuevo Juego");

                return true;

            case R.id.configuracion:

                System.out.println("Configuración");
                return true;

            case R.id.personaje:
                System.out.println("Personaje");
                return true;


            default:  return false;



        }
    }

    public  void generateButtons(int buttonsPerRow, int numberOfRows, int buttonWidth, int buttonHeight, TableLayout frame) {

        int amountOfButtons = buttonsPerRow * numberOfRows;

        int counter = 0;

        rows = new TableRow[numberOfRows];

        buttons = new Button[amountOfButtons];

        for (int i = 0; i < numberOfRows; i++) {

            rows[i] = new TableRow(this);

            rows[i].setLayoutParams(new TableRow.LayoutParams(buttonWidth*buttonsPerRow,buttonHeight));

            frame.addView(rows[i], new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));



            for (int j = 0; j < buttonsPerRow ; j++) {

                buttons[counter] = new Button(this);

                buttons[counter].setLayoutParams(new TableRow.LayoutParams(buttonWidth,buttonHeight));

                buttons[counter].setId(counter);

                buttons[counter].setOnClickListener(this);

                buttons[counter].setOnLongClickListener(this);

                buttons[counter].setBackgroundColor(Color.GRAY);


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
            btn.setText("X");
            btn.setEnabled(false);
        }
        else{
           // btn.setText(Integer.toString(getSurroundingBombs(clickedCell)));


            discoverCells(   getCell(btn.getId())    );
        }



    }

    public boolean onLongClick (View view) {

        Button btn = (Button) view;

        Cell clickedCell = getCell( btn.getId() );

        if(clickedCell.isBomb) {

            btn.setText("B!");

            btn.setEnabled(false);
            return false;
        }
        if(!clickedCell.isBomb){

            btn.setText("LST");
            btn.setEnabled(false);
            return false;
        }


        return false;
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

    public Button getButton(int id){

        for (int i = 0; i < buttons.length ; i++) {



                if(buttons[i].getId()==id){
                    return buttons[i];
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

    public void discoverCells (Cell cell) {
        
        
        if(getSurroundingBombs(cell)==0 && !cell.isChecked) {

            cell.setChecked(true);
            //getButton(cell.getId()).setEnabled(false);
            for (Cell nextCells: getSurroundingCells(cell)
                 ) {

                discoverCells(nextCells);
            }

        }
        else {

            if(getSurroundingBombs(cell) == 0)
            {
                getButton(  cell.getId()).setBackgroundColor(Color.DKGRAY)   ;
                getButton(cell.getId()).setEnabled(false);
            }

            if(getSurroundingBombs(cell) > 0)
            {
                getButton(cell.getId()).setText(    Integer.toString(getSurroundingBombs(cell))     );
                getButton(cell.getId()).setEnabled(false);
            }



        }
    }

    public void message(){

        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);

        dialogueBuilder.setMessage("Se trata de una copia del buscaminas. \n" +
                " Cuando pulsas en una casilla, sale un número que identifica cuantas" +
                "bombas hay alrrededor" +
                ". Si pulsas encima de una bomba pierdes. Si crees que hay una bomba haz click largo para desactivarla." +
                "Si haces click largo en una casilla donde no hay una bomba pierdes" +
                "\n Ganas después de encontrar todas las bombas")
                .setTitle("Instrucciones")
                .setCancelable(true)
                .setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        dialogueBuilder.show();

    }

}