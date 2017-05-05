

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Data {
	
	//public static ArrayList<String> scoreList = new ArrayList<String>();
	//public static ArrayList<Integer> scoreLength = new ArrayList<Integer>();
	//public static ArrayList<Integer> scorePoint = new ArrayList<Integer>();
	static int[][]array=new int[10000][2];
	static int counter = 0;
	
	public static ArrayList<String> temp = new ArrayList<String>();
	
	public static void store(int length, int score){
		try{
	    	File file = new File(System.getenv("APPDATA")+"\\udrewbird.txt");
			 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(length+" "+score);
			bw.newLine();


			
			bw.close();
			
	    	} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	
	
	public static void read(){
		 FileReader fileReader;
		 
		try {
			fileReader = new FileReader(System.getenv("APPDATA")+"\\udrewbird.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            String linearray[]=line.split(" ");
				array[counter][0]=Integer.parseInt(linearray[0]);
				array[counter][1]=Integer.parseInt(linearray[1]);
				counter++;
	        }
	        bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	}
	
	public static void sortArray(){
		for(int i=0; i<counter; i++)
		{
			int tempLength=(array[i][0]);
			int tempScore=(array[i][1]);
			int minIndex=i;

			for (int j=i+1; j<counter; j++)
			{
				if (tempLength<(array[j][0]))
				{
					tempLength=(array[j][0]);
					tempScore=(array[j][1]);
					minIndex=j;
				}
	                if (tempLength==(array[j][0]))
	                {
	                	if (tempScore<(array[j][1])){
	                		tempLength=(array[j][0]);
		                    tempScore=(array[j][1]);
		                    minIndex=j;
	                	}
	                }
				 
			}
			if(minIndex !=i)
			{
				array[minIndex][0]=array[i][0];
				array[minIndex][1]=array[i][1];
				array[i][0]=tempLength;
				array[i][1]=tempScore;
			}
		} 
	}
	public static int totalPoint(){
		int totalScore=0;
		for(int i=0;i<array.length;i++){
			if(array[i][0]!=0){
			totalScore=totalScore+(array[i][1]);
			}
		}
		return totalScore;
		
	}
	public static int totalLength(){
		int totalScore=0;
		int totalLength=0;
		for(int i=0;i<array.length;i++){
			if(array[i][0]!=0){
			totalLength=totalLength+(array[i][0]);
			}
		}
		return totalLength;
		
	}
	
	public static ArrayList<Integer> top10(){
		ArrayList<Integer> top10 = new ArrayList<Integer>();
		sortArray();
		for(int i=0;i<10;i++){
		int x = array[i][0];
		top10.add(x);
		}
		
		return top10;
	}
	public static void main(String[] args){
		read();

//		System.out.println(totalPoint());
//		System.out.println(totalLength());
//		for(int i=0;i<array.length;i++){
//			System.out.println(array[i][0]+" "+array[i][1]);
//		}
		
		System.out.print(top10());
	}
	
	
}
