package application;

import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Product> list = new ArrayList<>();

        System.out.print("Enter the file path: ");
        String sourceFilePathStr = sc.nextLine();
        File sourceFile = new File(sourceFilePathStr);
        String sourceFolderStr = sourceFile.getParent();
        boolean success = new File(sourceFolderStr + "\\out").mkdir();
        String targetFileStr = sourceFolderStr + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFilePathStr))){
            String itemCsv = br.readLine();
            while(itemCsv != null){

                String[] fields = itemCsv.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                list.add(new Product(name, price, quantity));
                itemCsv = br.readLine();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){

                for (Product item : list){
                    bw.write(item.getName() + ", " + String.format("%.2f", item.total()));
                    bw.newLine();
                }

                System.out.println("FILE CREATED! (Path: " + targetFileStr + ")");
                BufferedReader newBr = new BufferedReader(new FileReader(targetFileStr));
                String summaryStr = newBr.readLine();
                while (summaryStr != null){
                    System.out.print(summaryStr);
                    summaryStr = newBr.readLine();
                }

            }
            catch (IOException e){
                System.out.print("Error writing file: " + e.getMessage());
            }

        }
        catch (IOException e) {
            System.out.print("Error reading file: " + e.getMessage());
        }
    }
}