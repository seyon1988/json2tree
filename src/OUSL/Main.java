package OUSL;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("\n-------------------------------------------------------\n\n");
        String filePath = "C:\\Users\\baums2\\Desktop\\js3.txt";
        String content = "";

        Main obj = new Main();
        content = obj.readFileAsString(filePath);

        //System.out.println(content);

        List<Character> braces = new ArrayList<Character>();
        List<Integer> braceIndices = new ArrayList<Integer>();

        int [][] braceIndicesDetails = new int[4][1000];

        for(int i=0;i<4;i++){
            for(int j=0;j<1000;j++){
                braceIndicesDetails[i][j] = -1;
            }
        }

        int p = 0,q=0,r=0;
        for(int i=0;i<content.length();i++){
            char ch = content.charAt(i);
            if( ch=='{' || ch=='}' || ch=='[' || ch==']' ){
                braces.add(ch);
                braces.add(' ');
                braceIndices.add(i);
            }

            if( ch=='{' ){
                braceIndicesDetails[0][p] = i;
                braceIndicesDetails[2][p] = 1;
                p++;
            }else if( ch=='[' ){
                braceIndicesDetails[0][p] = i;
                braceIndicesDetails[2][p] = 2;
                p++;
            }else if( ch=='}' ){
                q = p-1;
                while(braceIndicesDetails[1][q] != -1) q--;
                braceIndicesDetails[1][q] = i;
                braceIndicesDetails[2][q] = 1;
            }else if( ch==']' ){
                q = p-1;
                while(braceIndicesDetails[1][q] != -1) q--;
                braceIndicesDetails[1][q] = i;
                braceIndicesDetails[2][q] = 2;
            }

        }

        StringBuilder sb = new StringBuilder();

        for(Character ch:braces){
            sb.append(ch);
        }
        System.out.println(sb.toString());

        for(Integer i:braceIndices){
            System.out.print(i + " ");
        }


        System.out.println("\n\n-------------------------------------------------------\n\n");

        System.out.println("\n\n");
        for(int i=0;i<3;i++){
            for(int j=0;j<p;j++){
                System.out.print(braceIndicesDetails[i][j]+"\t");
            }
            System.out.println("");
        }
        System.out.println("\n\n-------------------------------------------------------\n\n");

        for(int i=0;i<3;i++){
            for(int j=0;j<p;j++){
                if(braceIndicesDetails[2][j]==1)
                System.out.print(braceIndicesDetails[i][j]+"\t");
            }
            System.out.println("");
        }

        System.out.println("\n\n-------------------------------------------------------\n\n");

        int depth = 0,k=0;
        for(int j=0;j<p;j++){
            if(braceIndicesDetails[2][j]==1){
                if(j==0){
                    System.out.println("Depth "+depth+" = [ "+ braceIndicesDetails[0][j] + ", " + braceIndicesDetails[1][j] +" ]" );
                    braceIndicesDetails[3][j]=depth;
                }else if(braceIndicesDetails[0][j]>braceIndicesDetails[0][j-1] && braceIndicesDetails[1][j]<braceIndicesDetails[1][j-1] ){
                    depth++;
                    braceIndicesDetails[3][j]=depth;
                    System.out.println("Depth "+depth+" = [ "+ braceIndicesDetails[0][j] + ", " + braceIndicesDetails[1][j] +" ]" );
                }else if(braceIndicesDetails[0][j]>braceIndicesDetails[0][j-1] && braceIndicesDetails[1][j]>braceIndicesDetails[1][j-1] ){
                    k = j;
                    while(braceIndicesDetails[0][j]>braceIndicesDetails[0][k-1] && braceIndicesDetails[1][j]>braceIndicesDetails[1][k-1] ){
                        k--;
                    }
                    depth = braceIndicesDetails[3][k];
                    braceIndicesDetails[3][j] = depth;
                    System.out.println("Depth "+depth+" = [ "+ braceIndicesDetails[0][j] + ", " + braceIndicesDetails[1][j] +" ]" );
                }
            }
        }

    }

    private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }




}
