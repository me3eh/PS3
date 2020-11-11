package com.company;

import java.util.Comparator;

public class Lil_Help implements Comparator<Orders> {

    public int compare(Orders a, Orders b){
        if(a.getStart() - b.getStart() == 0)
            return Integer.compare(a.getEnd(), b.getEnd());
        return Integer.compare(a.getStart(), b.getStart());
    }
}
