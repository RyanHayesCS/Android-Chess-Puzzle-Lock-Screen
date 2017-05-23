package com.hayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import com.hayes.Puzzle;

public class PuzzleDao {
	 @SuppressWarnings("unchecked")
	 /*@Context ServletContext context*/
	public List<Puzzle> getAllPuzzles(@Context ServletContext context)
	 { 
	      List<Puzzle> puzzleList = new ArrayList<Puzzle>(); 
	      try { 
	    	 //InputStream stream = this.getClass().getClassLoader().getResourceAsStream("WEB-INF/data/Puzzles.txt");
	    	 InputStream stream = context.getResourceAsStream("WEB-INF/data/Puzzles.txt");
	    	 BufferedReader br = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
	         /*File file = new File("Puzzles.txt"); 
	         if (br == null) { 
	            Puzzle puzzle = new Puzzle("foolsmate",
	            		"bwr00bwb02bbq03bwk04bbb05bwn06bbr07bbp08bbp10bwp11bbp12bwp13bbp14bbp17bwn18bbp23wwb34wbp44wwq45wwp48wbp49wwp50wbp51wbp53wwp54wbp55wbr56wwn57wbb58wbk60wbn62wwr63",
	            		"bwr00bwb02bbq03bwk04bbb05bwn06bbr07bbp08bbp10bwp11bbp12wwq13bbp14bbp17bwn18bbp23wwb34wbp44wwp48wbp49wwp50wbp51wbp53wwp54wbp55wbr56wwn57wbb58wbk60wbn62wwr63"); 
	            //puzzleList = new ArrayList<Puzzle>(); 
	            puzzleList.add(puzzle); 
	           // saveUserList(userList); 
	         } 
	         else{*/ 	
		        	 String strLine;

	        		 while ((strLine = br.readLine()) != null)
	        		 {
	        		    String name = strLine;
	        		    String initialLayout = br.readLine();
	        		    String finalLayout = br.readLine();   
	        		    puzzleList.add(new Puzzle(name, initialLayout, finalLayout));
	        		 }
	        		 br.close();
	        // } 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      }
	     /* Puzzle puzzle1 = new Puzzle("foolsmate",
          		"bwr00bwb02bbq03bwk04bbb05bwn06bbr07bbp08bbp10bwp11bbp12bwp13bbp14bbp17bwn18bbp23wwb34wbp44wwq45wwp48wbp49wwp50wbp51wbp53wwp54wbp55wbr56wwn57wbb58wbk60wbn62wwr63",
          		"bwr00bwb02bbq03bwk04bbb05bwn06bbr07bbp08bbp10bwp11bbp12wwq13bbp14bbp17bwn18bbp23wwb34wbp44wwp48wbp49wwp50wbp51wbp53wwp54wbp55wbr56wwn57wbb58wbk60wbn62wwr63");
	      Puzzle puzzle2 = new Puzzle("rookmate", 
	    		  "bwk06bbp14wwp22bwq34wbp53wwp54wbp55wbr60wbk62",
	    		  "wwr04bwk06bbp14wwp22bwq34wbp53wwp54wbp55wbk62");
	      Puzzle puzzle3 = new Puzzle("queenmate", 
	    		  "bwn02bbk05bb08bwp13bbp14wwp16bbp17bbr23wwb25wbq30bbq33bbp35wbp46bwp57wbp49wbp53wbp55wbk62",
	    		  "bwn02wbq03bbk05bb08bwp13bbp14wwp16bbp17bbr23wwb25bbq33bbp35wbp46bwp57wbp49wbp53wbp55wbk62");
	      Puzzle puzzle4 = new Puzzle("pawnfork",
	    		  "bbk05bwp13bbp14bbr24bbn26wbp46wwp47wbp49wwn50wwr52wbp53wwk54",
	    		  "bbk05bwp13bbp14bbr24bbn26wbp33wbp46wwp47wwn50wwr52wbp53wwk54");
	      Puzzle puzzle5 = new Puzzle("pawnmate",
	    		  "bbp23wwk29bwk31bbr39wwr47wwp54",
	    		  "bbp23wwk29bwk31wwp38bbr39wwr47");
	      Puzzle puzzle6 = new Puzzle("rooksac",
	    		  "bwr00bwb02bwk04bbb05bbr07bbp08bwp09bbp10bwp13bbp14bwp15bwn18bbn21wbb30wwq32wbq36wwp48wbp49wwp50wbp53wwp54wbp55wbk58wwr59wwb61wbn62wwr63",
	    		  "bwr00bwb02wbr03bwk04bbb05bbr07bbp08bwp09bbp10bwp13bbp14bwp15bwn18bbn21wbb30wwq32wbq36wwp48wbp49wwp50wbp53wwp54wbp55wbk58wwb61wbn62wwr63");
	      puzzleList.add(puzzle1);
	      puzzleList.add(puzzle2);
	      puzzleList.add(puzzle3);
	      puzzleList.add(puzzle4);
	      puzzleList.add(puzzle5);
	      puzzleList.add(puzzle6);*/
	      
	      return puzzleList; 
	 }
}
