import java.io.*;
import java.util.*;

public class LexicalAnalyzer
{
	
	 static final String LETTER="LETTER";
	 static final String DIGIT="DIGIT";
	 static final String UNKNOWN="UNKNOWN";
	 static final String INT_LIT="INT_LIT";
	 static final String IDENT="IDENT";
	 static final String ASSIGN_OP="ASSIGN_OP";
	 static final String ADD_OP="ADD_OP";
	 static final String SUB_OP="SUB_OP";
	 static final String MULT_OP="MULT_OP";
	 static final String DIV_OP="DIV_OP";
	 static final String LEFT_PAREN = "LEFT_PAREN";
	 static final String RIGHT_PAREN="RIGHT_PAREN";
	 static final String END_OF_FILE="END_OF_FILE";
	 private static String charClass;
	 private static char lexeme[];
	 private static char nextChar;
	 private static int lexLength;
	 private static int token;
	 private static String nextToken;
	 private static File file;
	 private static FileInputStream inputFS;
   
   //All specific characters, non-digit, non-letter
   public static String lookup(char ch)
   {
       switch (ch)
       {
        case '(':
            addChar();
            nextToken = LEFT_PAREN;
            break;
        case ')':
            addChar();
            nextToken = RIGHT_PAREN;
            break;
        case '+':
            addChar();
            nextToken = ADD_OP;
            break;
        case '-':
            addChar();
            nextToken = SUB_OP;
            break;
        case '*':
            addChar();
            nextToken = MULT_OP;
            break;
        case '/':
            addChar();
            nextToken = DIV_OP;
            break;
        case '=':
            addChar();
            nextToken = ASSIGN_OP;
            break;
        default:
            addChar();
            nextToken = END_OF_FILE;
            break;
       }
       return nextToken;
   }
   
   
   public static void addChar()
   {
        if (lexLength <= 98)
        {
            lexeme[lexLength++] = nextChar;
           lexeme[lexLength] = 0;
        }
        else
           System.out.println("Error -lexeme is too long\n");
   }
   
   
   public static void getChar()
   {
       try
       {
       if(inputFS.available()>0)
       {
           nextChar=(char)inputFS.read();
           if(Character.isLetter(nextChar))
               charClass=LETTER;
           else if(Character.isDigit(nextChar))
               charClass=DIGIT;
           else
               charClass=UNKNOWN;
       }
       else
           charClass=END_OF_FILE;
       	//System.out.println();
       	//System.out.println("********************************************************************************");
          
       }
       catch(IOException e)
       {
           e.printStackTrace();
       }
   }
   
   
   public static void getRidOfSpace()
   {
       while(Character.isSpaceChar(nextChar))
           getChar();
      
   }
   
   
   public static String doTheLex()
   {
       lexLength = 0;
        getRidOfSpace();
        switch (charClass)
        {
        //Letters
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT)
                {
                   addChar();
                   getChar();
               }
                nextToken = IDENT;
                break;
                //Integers
            case DIGIT:
                addChar();
                getChar();
               while(charClass == DIGIT)
               {
                    addChar();
                    getChar();
               }
               nextToken = INT_LIT;
               break;
            //non digit/letters
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
           // End of the file
            case END_OF_FILE:
            	System.out.println();
            	System.out.println("********************************************************************************");
                nextToken = END_OF_FILE;
               lexeme[0] = 'E';
               lexeme[1] = 'O';
               lexeme[2] = 'F';
               lexeme[3] = 0;
               break;

       }
        
       System.out.print("Next token is: "+nextToken+" Next lexeme is ");
       for(int i=0;i<lexLength;i++)
           System.out.print(lexeme[i]);
       System.out.println();
       return nextToken;
   
   }
   
   //Injects and reads the file then loops for each character in the file and goes through identifying and printing each token andd lexeme
   public static void main(String args[]) throws FileNotFoundException
   {
       lexLength=0;
       lexeme=new char[100];
       for(int i=0;i<=99;i++){
           lexeme[i]='0';
       }
       file = new File("src/lexInput.txt");
       System.out.println("Yaser Iftikhar, CSCI42000DB, FALL 2018, Lexical Analyzer");
       System.out.println("********************************************************************************");
       System.out.println();
       
       if (!file.exists())
       {
             System.out.println( "Professor Salimi, try putting a direct path for the input file.");
             return;
       }
       try
       {
             inputFS = new FileInputStream(file);
             char current;
             while (inputFS.available() > 0)
             {
                 getChar();
               doTheLex();
           }
       }
       catch (IOException e)
       {
             e.printStackTrace();
       }
   }
}
