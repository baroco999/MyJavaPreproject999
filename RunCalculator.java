import java.util.*;

public class RunCalculator{
	public static void main (String[] args){
		Scanner scan = new Scanner (System.in);
		String s = scan.next();
		String st = s.trim();
		Calculator calcul = new Calculator (st);
		try {
            int result = calcul.calculate();
		} catch (NumberFormatException e) {
	        System.out.println(e.toString());
        }
		if (calcul.getRomanNumInput() == true) {
			System.out.println(calcul.conv.intToRoman(calcul.getResult()));
		} else {
			System.out.println(calcul.getResult());
		}
	}
}

class Calculator {
	private boolean romanNumInput = false;
	private char[] values;
	private int[] intValues;
	Converter conv;
	private int result;
	private int index;
	private char sign;

	public Calculator (String s){
		values = new char[s.length()];
		values = s.toCharArray();
		intValues = new int[2];
		conv = new Converter();
	}

	public boolean getRomanNumInput(){
		return romanNumInput;
	}
	
	public int getResult(){
	    return result;
	}
	public int calculate() throws NumberFormatException {
	    	for (index=0; index < values.length; index++) {
            if (!Character.isLetterOrDigit(values[index]) & !Character.isWhitespace(values[index])) { 
             sign = values[index];
             break;
             } 
        }
		if (Character.isDigit(values[0]) & Character.isDigit(values[values.length-1])){
			intValues[0]= Integer.parseInt(String.valueOf(values,0,index));
			intValues[1]= Integer.parseInt(String.valueOf(values,index+1,values.length-(index+1)));
		} else if (Character.isLetter(values[0]) & Character.isLetter(values[values.length-1])){
			this.romanNumInput = true;
			intValues[0]= conv.romanToInt(String.valueOf(values,0,index));
			intValues[1]= conv.romanToInt(String.valueOf(values,index+1,values.length-(index+1)));
		} else {
		    throw new NumberFormatException("Invalid number format");
		}
		
		for(int i=0; i<intValues.length; i++){  
            if((intValues[i]<1)|(intValues[i]>10)) {
                throw new NumberFormatException ("Invalid number format");
            }
        }
		result = this.getRes();
		if (result > 0){
            return result;
		} else {
            throw new NumberFormatException("Invalid number format");
		}
	}

	int getRes(){
		  switch (sign) {
		    case '+': return (intValues[0] + intValues[1]);
			case '-': return (intValues[0] - intValues[1]);
			case '*': return (intValues[0] * intValues[1]);
			case '/': return (intValues[0] / intValues[1]);
		    default:  return -1;
		  }
	}
	
	class Converter {
		int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		String[] letters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

		public String intToRoman (int num){
			String romanValue = "";
			int N = num;
			for (int i=0; i<numbers.length; i++){
				while (N >= numbers[i]){
					N -= numbers[i];
					romanValue += letters[i];
				}
			}
			return romanValue;
		}// method intToRoman()
		
		int romanToInt (String roman) throws NumberFormatException {
			int[] number = new int[roman.length()];
            int arabic =0;
            int i=0;
			while(i<roman.length()){
				number[i] = this.letterToNumber(roman.charAt(i));
				if(number[i] == -1) {
					throw new NumberFormatException("Invalid number format");
				}
				i++;
			}
			arabic += number[0]; 
			for (int ii=0; ii<(number.length-1); ii++) {
				if(number[ii]>=number[ii+1]){
					arabic += number[ii+1];
				} else {
					arabic += (number[ii+1]-(number[ii]*2));
				}
			}
			return arabic;
		}
		
		int letterToNumber(char letter) {
		    switch (letter) {
				 case 'I': return 1;
				 case 'V': return 5;	
				 case 'X': return 10;
				 case 'L': return 50;
				 case 'C': return 100;
				 case 'D': return 500;
				 case 'M': return 1000;
				 default: return -1;
		       }
		}
	}
}
