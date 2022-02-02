package com.example.buscabombas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.buscabombas.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity   implements View.OnClickListener, View.OnLongClickListener {


    private ActivityMainBinding binding;
    Cell[][] myBoard;

    Button[] buttons;
    TableRow[] rows;

    TableLayout frame;

    int gameLevel = 1;

    int numberOfBombs = 10;

    int discoveredBombs = 0;

    int activePlayer = 1;


    boolean gameOn = true;
    //TEST TEST TEST

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());

       // frame = (TableLayout) findViewById(R.id.frame);

        frame = binding.frame;

        Button confirm = binding.confirmLevel;

        confirm.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {

                RadioGroup rg = binding.difficultyRadio;
                confirmOptions();


                rg.setVisibility(View.INVISIBLE);

                frame.setClickable(true);

                System.out.println("Cerrar");

                clickableButtons();


            }
        });

        Button confirmPlayer = binding.confirmPlayer;

        confirmPlayer.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                RadioGroup rg = binding.playerRadio;
                playerOptions();
                clickableButtons();
                rg.setVisibility(View.INVISIBLE);
            }
        });

        generateLevel(1);

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

                message();

                return true;

            case R.id.nuevojuego:

                generateLevel(gameLevel);

                return true;

            case R.id.configuracion:

                difficultyOptions();

                return true;

            case R.id.personaje:

                RadioGroup rg = (RadioGroup) findViewById(R.id.playerRadio);

                rg.setVisibility(View.VISIBLE);

                unclickableButtons();
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

                ShapeDrawable shapedrawable = new ShapeDrawable();
                shapedrawable.setShape(new RectShape());
                shapedrawable.getPaint().setColor(Color.BLACK);
                shapedrawable.getPaint().setStrokeWidth(7f);
                shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
                buttons[counter].setBackground(shapedrawable);




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
            if(activePlayer==1){


                btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bomb, 0, 0, 0);
            }
            else if(activePlayer==2){
                btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key, 0, 0, 0);
            }
            else if(activePlayer==3){
                btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pill, 0, 0, 0);
            }
            btn.setEnabled(false);

            loseGame();
        }
        else{

            discoverCells(   getCell(btn.getId())    );
        }



    }

    public boolean onLongClick (View view) {

        Button btn = (Button) view;

        Cell clickedCell = getCell( btn.getId() );

        if(clickedCell.isBomb) {

            if(activePlayer==1){


            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bomb, 0, 0, 0);
            }
            else if(activePlayer==2){
                btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key, 0, 0, 0);
            }
            else if(activePlayer==3){
                btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pill, 0, 0, 0);
            }
            btn.setEnabled(false);

            discoveredBombs++;

            if(discoveredBombs==numberOfBombs) winGame();

            return false;
        }
        if(!clickedCell.isBomb){

            loseGame();
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

    public Button getButton(int id)
    {

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
            getButton(  cell.getId()).setBackgroundColor(Color.LTGRAY)   ;
            getButton(cell.getId()).setEnabled(false);
            //getButton(cell.getId()).setEnabled(false);
            for (Cell nextCells: getSurroundingCells(cell)
                 ) {

                discoverCells(nextCells);
            }

        }
        else {

            if(getSurroundingBombs(cell) == 0)
            {
                getButton(  cell.getId()).setBackgroundColor(Color.LTGRAY)   ;
                getButton(cell.getId()).setEnabled(false);
            }

            if(getSurroundingBombs(cell) > 0)
            {
                getButton(cell.getId()).setText(    Integer.toString(getSurroundingBombs(cell))  );


                switch (getSurroundingBombs(cell)) {

                    case 1:
                        getButton(cell.getId()).setTextColor(Color.BLUE);
                        break;
                    case 2:
                        getButton(cell.getId()).setTextColor(Color.GREEN);
                        break;
                    case 3:
                        getButton(cell.getId()).setTextColor(Color.YELLOW);
                        break;
                    case 4:
                        getButton(cell.getId()).setTextColor(Color.RED);
                        break;
                    case 5:
                        getButton(cell.getId()).setTextColor(Color.RED);
                        break;
                    case 6:
                        getButton(cell.getId()).setTextColor(Color.RED);
                        break;
                    case 7:
                        getButton(cell.getId()).setTextColor(Color.RED);
                        break;
                    case 8:
                        getButton(cell.getId()).setTextColor(Color.RED);
                        break;


                }



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


    public void generateLevel(int level){

        switch (level) {

            case 1:

                System.out.println();

                frame.removeAllViews();

                generateButtons(8,8,130,130, frame);

                generateBoard(8,10);

                numberOfBombs = 10;

                discoveredBombs = 0;

                break;

            case 2:
                frame.removeAllViews();

                generateButtons(12,12,86,100, frame);

                generateBoard(12,30);

                numberOfBombs = 30;
                discoveredBombs = 0;

                break;

            case 3:
                frame.removeAllViews();

                generateButtons(16,16,67,95, frame);

                generateBoard(16,60);

                numberOfBombs = 60;
                discoveredBombs = 0;


                break;
            default:
                frame.removeAllViews();

                generateButtons(8,8,130,130, frame);

                generateBoard(8,10);

                numberOfBombs = 10;
                discoveredBombs = 0;


                break;



        }

    }


    public void difficultyOptions() {

        RadioGroup rg = (RadioGroup) findViewById(R.id.difficultyRadio);

        rg.setVisibility(View.VISIBLE);

        unclickableButtons();
        System.out.println("Abrir");
    }

    public void confirmOptions() {


        RadioButton principiante = findViewById(R.id.principiante);
        RadioButton avanzado = findViewById(R.id.avanzado);
        RadioButton experto = findViewById(R.id.experto);


        if(principiante.isChecked()) {

            gameLevel = 1;

            generateLevel(1);

        }
        else if(avanzado.isChecked()){
            gameLevel = 2;

            generateLevel(2);
        }
        else if( experto.isChecked()){
            gameLevel = 3;

            generateLevel(3);
        }

    }


    public void loseGame() {

        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);

        dialogueBuilder.setMessage("¡Has perdido! No pasa nada, puedes volver a intentarlo")
                .setTitle("Derrota")
                .setCancelable(true)
                .setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                generateLevel(gameLevel);
                            }
                        });
        dialogueBuilder.setCancelable(false);
        dialogueBuilder.show();




    }


    public void winGame(){
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);

        dialogueBuilder.setMessage("¡HAS GANADO! ENHORABUENA")
                .setTitle("Victoria")
                .setCancelable(true)
                .setPositiveButton(
                        "¡Gracias!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                generateLevel(gameLevel);
                            }
                        });
        dialogueBuilder.setCancelable(false);
        dialogueBuilder.show();



    }


    public void playerOptions() {


        RadioButton bomb = findViewById(R.id.bomb);
        RadioButton key = findViewById(R.id.key);
        RadioButton pill = findViewById(R.id.pill);


        if(bomb.isChecked()) {

            activePlayer = 1;

            generateLevel(gameLevel);

        }
        else if(key.isChecked()){
            activePlayer = 2;

            generateLevel(gameLevel);
        }
        else if( pill.isChecked()){
            activePlayer = 3;

            generateLevel(gameLevel);
        }

    }


    public void unclickableButtons() {

        for (Button b: buttons
             ) {

            b.setEnabled(false);
        }

    }

    public void clickableButtons() {

        for (Button b: buttons
        ) {

            b.setEnabled(true);
        }

    }
}