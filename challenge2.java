//This program makes extensive use of hashmaps
/*First HashMap consists of Character-->Integer hashes which
 * is used to map an Ordinal Character Array to the Ordinal values in hex*/
 /*Second HashMap consists of Integer-->Integer Array hashes which maps 
  * each hex digit with all possible xor hex combinations of itself till f.
  * The result is an array which can again be used to index the OrdinalCharArray
  * in constant time.*/

import java.util.*;

class challenge2 {
	Character[] hexOrdinalCharArray={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	Integer [] hexOrdinalIntArray={0x0,0x1 , 0x2 , 0x3 , 0x4 , 0x5 , 0x6 , 0x7 , 0x8 , 0x9 , 0xa , 0xb , 0xc , 0xd , 0xe , 0xf};
	Integer [][] xorIntMap = new Integer[0x10][0x10];//The Combination matrix of all hex xor permutations
	HashMap<Character,Integer> hm=new HashMap<Character,Integer>();
	HashMap<Integer,Integer[]> hex_xor= new HashMap<Integer,Integer[]>();
	private void createXorCharacterMap(){
		for(int i=0x0 ; i<=0xf ; i++){
			for(int j=0x0 ; j<=0xf ; j++){
				xorIntMap[i][j]=hexOrdinalIntArray[i^j];//Bitwise xor for each(i,j) hex value
			}
		}
	}				
	private void createHashMaps(){
		for(int i=0; i<=0xf ; i++){
			hex_xor.put(hexOrdinalIntArray[i],xorIntMap[i]);
			hm.put(hexOrdinalCharArray[i],hexOrdinalIntArray[i]);//Maps each hex character to its xor_list
		}
	}
	private String xor(String first, String second){
		String xor_result="";
		int diff = first.length() - second.length();//Add appropriate padding to shorter hex strings if the string lengths are unequal
		if(diff>0){
			while(diff-- >0){
				second="0"+second;
			}
		}
		else{
			while(diff++ <0){
				first="0"+first;
			}
		}
		for(int i=0 ; i<first.length(); i++){
			Integer first_operand = hm.get(first.charAt(i));//Gets the dereferenced Integer corresponding to the hex character as key from the first string
			Integer [] first_operand_xor_list=hex_xor.get(first_operand);//Gets the xor_list for that operand.
			Integer second_operand = hm.get(second.charAt(i));//Gets the second operand in the same fashion
			xor_result+= hexOrdinalCharArray[first_operand_xor_list[second_operand]];//Constant time indexing of the OrdinalCharArray 
		}
		return xor_result;
	}
	public static void main(String args[]){
		challenge2 c2 = new challenge2();
		String first = "1c0111001f010100061a024b53535009181c";
		String second = "686974207468652062756c6c277320657965";
		//Result="746865206b696420646f6e277420706c6179"
		c2.createXorCharacterMap();
		c2.createHashMaps();
		String result = c2.xor(first,second);
		System.out.println(result);
	}
}
	
