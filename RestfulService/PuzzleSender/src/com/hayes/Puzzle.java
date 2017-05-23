package com.hayes;  

import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "puzzle") 

public class Puzzle implements Serializable {  
   private static final long serialVersionUID = 1L; 
   private String finalLayout;
   private String initialLayout;  

   private String name; 

   public Puzzle(){} 
    
   public Puzzle(String name, String initialLayout, String finalLayout ){  
      this.finalLayout = finalLayout;
      this.initialLayout = initialLayout; 
      this.name = name; 
   }  

   public String getFinalLayout() { 
	      return finalLayout; 
   } 
   @XmlElement 
   public void setFinalLayout(String finalLayout) { 
      this.finalLayout = finalLayout; 
   } 
   public String getInitialLayout() { 
	      return initialLayout; 
   } 
   @XmlElement 
   public void setInitialLayout(String initialLayout) { 
      this.initialLayout = initialLayout; 
   }  
   public String getName() { 
	      return name; 
   } 
   @XmlElement
   public void setName(String name) { 
      this.name = name; 
   } 
} 