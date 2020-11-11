package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static int matching_end_to_start_for_first_level(Orders [] Array_Orders, boolean [] array_of_truth,int index, int index_of_start){
        for(int i = index - 1 ; i >= index_of_start ; --i)
            if(Array_Orders[i].getEnd() <= Array_Orders[index].getStart() && array_of_truth[i]) {
                array_of_truth[index] = true;
                return i;
            }
        return -1;
    }
    public static int max_value_for_first_level(Orders [] Array_Orders, boolean[] array_of_truth, int size_of_array, int index_of_start, boolean [] array_temp)
    {
        //trzymamy tutaj wartosci z danych zamowien
        int [] array_storing_values = new int [size_of_array];
        LinkedList<Integer> [] list_of_indexes = new LinkedList[size_of_array];
        //pierwszy zapelniamy pierwsza wartoscia
        array_storing_values[index_of_start] = Array_Orders[index_of_start].getValue();
        list_of_indexes[index_of_start] = new LinkedList<>();
//        list_of_indexes[index_of].add(index_of);

        // Fill entries in M[] using recursive property
        for (int i = index_of_start + 1 ; i < size_of_array ; ++i)
        {
            int index_of_matching = matching_end_to_start_for_first_level(Array_Orders, array_of_truth, i, index_of_start);
            if (index_of_matching != -1) {
                int whole_profit = Array_Orders[i].getValue();
                whole_profit += array_storing_values[index_of_matching];
                if(whole_profit >= array_storing_values[i-1]) {
                    array_storing_values[i] = whole_profit;
//                    System.out.println(whole_profit);
                    list_of_indexes[i] = (LinkedList)list_of_indexes[index_of_matching].clone();
                    list_of_indexes[i].add(index_of_matching);
                    list_of_indexes[i].add(i);
                }
                else{
                    array_storing_values[i] = array_storing_values[i - 1];
                    list_of_indexes[i] = (LinkedList)list_of_indexes[i - 1].clone();
                }
//              array_storing_values[i] = Math.max(whole_profit, array_storing_values[i-1]);
            }
            else {
                array_storing_values[i] = array_storing_values[i - 1];
                list_of_indexes[i] = (LinkedList)list_of_indexes[i - 1].clone();
            }
        }
//        System.out.print("=========00");
//        System.out.println(list_of_indexes[size_of_array-1].size());
//        System.out.println("---------");
        for(int i = 0 ; i < list_of_indexes[size_of_array - 1].size() ; ++i)
            array_temp[list_of_indexes[size_of_array - 1].get(i)] = true;

//        temp = (LinkedList)list_of_indexes[size_of_array - 1].clone();
        return array_storing_values[size_of_array - 1];
    }

    //funkcja znajdujaca max w drugim poziomie poszukiwan
    public static int matching_end_to_start_for_second_level(Orders [] Array_Orders, int index){
        for(int i = index - 1 ; i >= 0 ; --i)
            if(Array_Orders[i].getEnd() <= Array_Orders[index].getStart())
                return i;
        return -1;
    }

    public static int max_value_for_second_level(Orders [] Array_Orders, int size_of_array)
    {
        //trzymamy tutaj wartosci z danych zamowien
        int [] array_storing_values = new int [size_of_array];
        //pierwszy zapelniamy pierwsza wartoscia
        array_storing_values[0] = Array_Orders[0].getValue();

        // Fill entries in M[] using recursive property
        for (int i = 1 ; i < size_of_array ; i++)
        {
            // Find profit including the current job
            int whole_profit = Array_Orders[i].getValue();
            int index_of_matching = matching_end_to_start_for_second_level(Array_Orders, i);
            if (index_of_matching != -1)
                whole_profit += array_storing_values[index_of_matching];

            // Store maximum of including and excluding
            array_storing_values[i] = Math.max(whole_profit, array_storing_values[i-1]);
        }
        return array_storing_values[size_of_array - 1];
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\matt3\\Desktop\\testy_ASD\\maly69.txt");
        Scanner in = new Scanner(file);
        int amount_of_data = in.nextInt();
        Orders [] Array_Orders = new Orders[amount_of_data];
        int[] temp = new int[3];
        int temp_for_checking_end = 1000;
        for (int i = 0; i < amount_of_data; ++i) {
            for (int j = 0; j < 3; ++j)
                temp[j] = in.nextInt();
            Array_Orders[i] = new Orders(temp[0], temp[1], temp[2]);
            if(temp[1] < temp_for_checking_end)
                temp_for_checking_end = temp[1];
        }
        //sortowanie n * log(n)
        Arrays.sort(Array_Orders, new Lil_Help());
        int how_many_beginnings = 0;
        boolean check_beginnings = true;
        for(int i = 0 ; i < amount_of_data ; ++i) {
            if(check_beginnings)
                if(temp_for_checking_end <= Array_Orders[i].getStart())
                    check_beginnings = false;
                else
                    ++how_many_beginnings;
            Array_Orders[i].giveID(i);
        }
        //debug
//        System.out.println(how_many_beginnings);
//        for(int i = 0 ; i < how_many_beginnings ; ++i)
//            System.out.println(Array_Orders[i]);
        int max = 0;
//        System.out.println(how_many_beginnings);
        for(int i = 0 ; i < how_many_beginnings ; ++i){
            boolean [] helping_array = new boolean[amount_of_data];
            int size = amount_of_data;
            boolean [] boolean_Array = new boolean[amount_of_data];
            boolean_Array[i] = true;
//            for(int x = 0 ; x <amount_of_data;++x)
//                System.out.println(boolean_Array[x] +" --->"+Array_Orders[x]);
//            System.out.println("----------------------");
            int first_value = max_value_for_first_level(Array_Orders, boolean_Array, amount_of_data, i, helping_array);
//            System.out.println("---+"+first_value+"+---");
//            System.out.println(size);
//            for(int x = 0 ; x <amount_of_data;++x)
//                System.out.println(boolean_Array[x] +" --->"+Array_Orders[x]);
//            System.out.println("======================");
            for(int j = 0 ; j<amount_of_data ;++j )
                if(helping_array[j])
                    --size;
//            System.out.println(size);
            Orders [] copy = new Orders[size];
            int t = 0;
            for(int j = 0 ; j < amount_of_data ; ++j)
                if (!helping_array[j]) {
                        copy[t] = new Orders(Array_Orders[j].getStart(),Array_Orders[j].getEnd(), Array_Orders[j].getValue(), Array_Orders[j].getID());
                        ++t;
                    }
//            for(int j = 0 ; j < size ; ++j)
//                System.out.println(copy[j]);
//            System.out.println("-======-");
//            System.out.println(copy[0]);
            first_value += max_value_for_second_level(copy, size);
            if(max < first_value)
                max = first_value;
            System.out.println(max);
            }
        System.out.println(max);
    }
}
