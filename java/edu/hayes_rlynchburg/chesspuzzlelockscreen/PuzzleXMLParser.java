package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan Hayes on 4/11/2017.
 */

public class PuzzleXMLParser {
    public static List<Puzzle> parseFeed(String content){

        try{
            boolean inDataItemTag = false;
            String currentTagName = "";
            Puzzle puzzle = null;
            List<Puzzle> puzzleList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if(currentTagName.equals("puzzle")){
                            inDataItemTag = true;
                            puzzle = new Puzzle();
                            puzzleList.add(puzzle);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("product")){
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if(inDataItemTag && puzzle != null)
                        {
                            switch (currentTagName)
                            {
                                case "initialLayout":
                                    puzzle.setInitialLayout(parser.getText());
                                    break;
                                case "finalLayout":
                                    puzzle.setFinalLayout(parser.getText());
                                    break;
                                case "name":
                                    puzzle.setName(parser.getText());
                                default:
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            return puzzleList;

        }catch(Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

}