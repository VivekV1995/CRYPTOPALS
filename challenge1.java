//One hex digit consists of 4 bits.
//One base-64 digit consists of 6 bits
//LCM of 4 and 6 is 12 bits.....
//3 hexadecimal digits can be expressed as 2 base64 digits.
/*Rough Character Map:
492H = SS Base-64
492H= 2 + 144 + 1024 = 1170
Base-64 :{ A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z 0-25
* a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z  26-51
* 0,1,2,3,4,5,6,7,8,9  51-61
* +,/  62-63}
* SS in base 64 = 1170 which means that S-position x 1 +S-position x 64 = 1170
* From the lookup table S=18.
* 18 x 1 + 64 x 18 = 1170 _/ verified :)
* */
import java.util.*;
//Creating a hash table to match hex entries 000 - fff to their appropriate base 64 entries 00 - //

class challenge1{
	//A hex lookup-array
	char hex_16[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	//A base-64 lookup-array
	char base_64[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
		'0','1','2','3','4','5','6','7','8','9','+','/'};
	//A HashMap formed initialized between two String arrays 
	String possible_hex_values[]=new String[0x1000];
	String possible_base64_values[]=new String[0x1000];
	//A hashmap reference variable
	HashMap<String, String> hex_64;
	private void hex_value_fillup(){ //Filling up the hex string array with strings from 000 to fff
 		try{
			for(int i=0x000 ; i<=0xfff; i++){
				if(i<=0x00f){
					possible_hex_values[i]="00"+""+hex_16[i];
				}
				else if(i>0x00f && i<=0x0ff){
					int k= i/0x10;
					int l = i%0x10;
					possible_hex_values[i]="0"+""+hex_16[k] +hex_16[l];
				}
				else{
					int s = i;		
					int m = s%0x10;
					s/=0x10;
					int l = s%0x10;
					s/=0x10;
					int k = s%0x10;
					possible_hex_values[i]=""+hex_16[k]+hex_16[l] +hex_16[m];
				}
		    }
		}
		catch(NullPointerException e){
			System.out.println(e);
		} 
	}
	private void base64_value_fillup(){//Filling up the base_64 string array with values corresponding to hex 000 to hex fff.
		try{
			for(int i = 0x000 ; i<=0xfff ; i++){
				if(i < 0x040){
					possible_base64_values[i]="0"+""+base_64[i];
				}
				else{
					int k = i/0x40;
					int l = i%0x40;
					possible_base64_values[i]=""+base_64[k] + base_64[l];
				}
			}
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
	}
	//Creates the HashMap with one-to-one mappings between possible_hex_values[] and possible_base64_values[] 			
	private void createHashMap(){
		hex_64 = new HashMap<String, String>();
		hex_value_fillup();
		base64_value_fillup();
		try{
			for(int i = 0x000 ; i <= 0xfff; i++){
				hex_64.put(possible_hex_values[i],possible_base64_values[i]);
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e);
		}
	}
	//Splits the hex string into an array of three-character strings suitable for hashing
	private String[] hex_split_to_map(String hex){
		//if hex key is not a multiple of 12 then we add padding on the left
		int diff= hex.length() % 12;
		if(diff > 0 && hex.length()>12){
			while(diff>0){
				 hex="0"+ hex;
				 diff--;
			 }
		}
		//Base64 is 2 digits long when hex is 3 digits long
		int base_64_len = hex.length()*2/3;
		String hex_split[]=new String[base_64_len/2];
		int j=0;
		for(int i = 0; i<hex.length() ; i+=3){
			hex_split[j++]=hex.substring(i,i+3);
		}
		return hex_split;
	}
	//Applies the hash function on the hex-string
	public String HashFunction(String hex){
		createHashMap();
		String splitvals[] = hex_split_to_map(hex);
		String result_in_base64=new String("");
		for(String val:splitvals){
			result_in_base64 += hex_64.get(val);
		}
		return result_in_base64;
	}
	public static void main(String args[]){
		//Desired result: "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t";
		challenge1 c1=new challenge1();	
		String hex="49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";		
		String base64 = c1.HashFunction(hex);
		System.out.println(base64);
	}
}
	

