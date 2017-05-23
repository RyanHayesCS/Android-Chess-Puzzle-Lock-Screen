package com.hayes;  

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;  
@Path("/PuzzleService") 

public class PuzzleService {  
   PuzzleDao puzzleDao = new PuzzleDao();
   @Context
   private ServletContext context; 
   @GET 
   @Path("/puzzles") 
   @Produces(MediaType.APPLICATION_XML) 
   public List<Puzzle> getPuzzles(){ 
	  /*String initialLayout = "bwr00bwb02bbq03bwk04bbb05bwn06bbr07bbp08bbp10bwp11bbp12bwp13bbp14bbp17bwn18bbp23wwb34wbp44wwq45wwp48wbp49wwp50wbp51wbp53wwp54wbp55wbr56wwn57wbb58wbk60wbn62wwr63";
	  String finalLayout = "bwr00bwb02bbq03bwk04bbb05bwn06bbr07bbp08bbp10bwp11bbp12wwq13bbp14bbp17bwn18bbp23wwb34wbp44wwp48wbp49wwp50wbp51wbp53wwp54wbp55wbr56wwn57wbb58wbk60wbn62wwr63";
	  return new Puzzle("foolsmate", initialLayout, finalLayout);*/
	   return puzzleDao.getAllPuzzles(context);
   }
}