package com.example.buscabombas;

public class Cell {


    public boolean isChecked;


    public boolean isBomb;

    public boolean isChecked() {
        return isChecked;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getId() {
        return id;
    }

    public Cell(boolean isBomb, boolean isChecked, int coordX, int coordY, int id) {
        this.isChecked = isChecked;
        this.isBomb = isBomb;
        this.coordX = coordX;
        this.coordY = coordY;
        this.id = id;
    }

    public int coordX;

    public Cell(boolean isBomb) {
        this.isBomb = isBomb;
        this.coordX = coordX;
        this.coordY = coordY;
        this.id = id;
    }

    public int coordY;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBomb() {
        return isBomb;
    }


    public int id;







}
