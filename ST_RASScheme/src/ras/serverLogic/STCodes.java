package ras.serverLogic;

import java.util.ArrayList;
import java.util.List;

import ras.interfaces.Utilities.TypeCode;
import ras.interfaces.Utilities.TypeCodeAction;

public class STCodes {
	public STCodes() {
		super();
	}

	private int checkBitsNumb(String binary) {
		int numberBits = 0;
		for (char c : binary.toCharArray())
			if (c == '1')
				numberBits++;
		return numberBits;
	}

	private String complementBinary(String binary, boolean flagReverse)
			throws Exception {
		String complement = "", extraBits = "", checkData = "";
		int lengthComplement = 0, binaryLength = 0, k = 0;

		if (flagReverse)
			checkData = binary;
		else {
			binaryLength = binary.trim().length();
			lengthComplement = checkBitsNumb(binary);
			k = (int) Math.ceil(Math.log(binaryLength + 1) / Math.log(2));
			checkData = valueToBinary(lengthComplement);
			int i = checkData.startsWith("0") ? 0 : -1,j = checkData.indexOf("1");
			checkData = (i == 0 && j > 0 ? checkData.substring(j, checkData.length()) : !checkData.contains("1") ? "0" : checkData);
			if (k - checkData.length() > 0)
				extraBits = new String(new char[k - checkData.length()]).replace('\0', '0');
			checkData = extraBits + checkData;
		}

		for (char c : checkData.toCharArray()){
			if (c == '1')
				complement += "0";
			else {
				if (c == '0')
					complement += "1";
				else{
					System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: complementBinary-bit is not valid");
					throw new Exception("STCodes err: complementBinary-bit is not valid");
				}
			}
		}
		return complement;
	}

	private Object binaryToValue(String binaryValue) throws Exception {
		Object value = 0;
		for (char c : binaryValue.toCharArray()){
			if (c != '0' && c != '1' && c != ' '){
				System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: binaryToValue-Value is not binary");
				throw new Exception("STCodes err: binaryToValue-Value is not binary");
			}
		}
		if (binaryValue.length() >= Integer.SIZE && binaryValue.length() <= Float.SIZE && !binaryValue.contains(" ")) {
			int intBits = (int) Long.parseLong(binaryValue, 2);
			value = Float.intBitsToFloat(intBits);
		} else {
			if (binaryValue.length() > 0 && binaryValue.length() < Integer.SIZE && !binaryValue.contains(" "))
				value = Integer.parseInt(binaryValue, 2);
			else {
				if((binaryValue.length() > Integer.SIZE && binaryValue.length() > Float.SIZE) || binaryValue.contains(" ")){
					String val = "";
					value = "";
					for(int i=0; i<binaryValue.trim().length(); i+=8){
						val = binaryValue.substring(i, i+8);
						value += new Character((char)Integer.parseInt(val,2)).toString();
					}
				}
			}
		}
		return value;
	}

	private String valueToBinary(Object value) throws Exception {
		String binary = "";
		int intBits = 0;
		if (value.getClass() == Float.class || value.getClass() == Double.class) {
			if(value.getClass() == Float.class)
				intBits = Float.floatToIntBits((Float) value);
			else
				intBits = (int) Double.doubleToLongBits((Double) value);
			binary = Integer.toBinaryString(intBits);
			binary = binary.length() < Integer.SIZE ? new String(new char[Integer.SIZE - binary.length()]).replace('\0', '0') + binary : binary;
		} else {
			if (value.getClass() == Integer.class) {
				binary = Integer.toBinaryString((int) value);
				if(binary.toString().length() % 8 != 0)
					binary = new String(new char[8 - binary.toString().length() % 8]).replace('\0', '0')+binary;
			} else {
				if(value.getClass() == String.class){
					String temp = "";
					byte[] valueByte = value.toString().getBytes("UTF-8");
					for(byte val : valueByte){
						temp = Integer.toBinaryString(val);
						binary += temp.length() < 8 ? new String(new char[8 - temp.length()]).replace('\0', '0')+temp : temp;
					}
					if(binary.length() <= Integer.SIZE)
						binary += " ";
				} else{
					System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: valueToBinary-Wrong value");
					throw new Exception("STCodes err: valueToBinary-Wrong value");
				}
			}
				
		}

		return binary;
	}

	private Object swapCompare(String binary, boolean flagCompare) {
		Object codeWord;
		String upperHalf, lowerHalf;
		int divisor = flagCompare ? 4 : 2;

		int lengthHalves = binary.trim().length() / divisor;
		int initial = flagCompare ? lengthHalves * 2 : 0;

		lengthHalves = flagCompare ? lengthHalves + lengthHalves * 2 : lengthHalves;
		upperHalf = binary.trim().substring(initial, lengthHalves);
		lowerHalf = binary.trim().substring(lengthHalves, binary.trim().length());
		if (flagCompare) {
			char data[] = binary.trim().toCharArray();
			String word = lowerHalf + upperHalf;
			char check[] = word.toCharArray();
			codeWord = true;
			for (int i = 0; i < binary.trim().length() / 2; i++){
				if (data[i] != check[i]) {
					codeWord = false;
					break;
				}
			}
			codeWord = (boolean) codeWord ? word : codeWord;
		} else
			codeWord = binary.trim() + lowerHalf + upperHalf;

		return codeWord;
	}

	private Object checkSum(String binary, boolean flagCompare)
			throws Exception {
		Object codeWord = "";
		int numbCarry = 0, newNumbCarry = 0;
		// LENGTHWORD MUST BE 8
		int lengthWord = 8, numbDataWords = 0;
		List<String> arrayWords = new ArrayList<String>();
		int residue = binary.trim().length() % lengthWord;
		String extraBits = "", residueCodeWord = "";
		if (residue != 0)
			extraBits = new String(new char[lengthWord - residue]).replace('\0', '0');
		String binaryVal = extraBits + binary.trim();
		numbDataWords = binaryVal.length() / lengthWord;
		for (int i = 0, j = lengthWord; j < binaryVal.length() + 1; i = j, j += lengthWord) {
			arrayWords.add((binaryVal).substring(i, j));
		}

		char bit = '0';
		char operand1, operand2;
		boolean flagResidue = false;
		for (int i = lengthWord - 1; i >= 0; i--) {
			operand1 = flagResidue ? residueCodeWord.toString().charAt(i) : arrayWords.get(0).charAt(i);
			while (numbCarry > 0) {
				newNumbCarry += operand1 == '1' ? 1 : 0;
				operand1 = carryBit(operand1, '1');
				numbCarry--;
			}

			numbCarry = newNumbCarry;
			newNumbCarry = 0;
			for (int k = 1; k < numbDataWords; k++) {
				operand2 = flagResidue && numbCarry > 0 ? '1' : flagResidue ? '0' : arrayWords.get(k).charAt(i);
				bit = carryBit(operand1, operand2);
				numbCarry += (operand1 == '1' && operand2 == '1') ? 1 : 0;
				operand1 = bit;
			}

			if (numbDataWords == 0)
				bit = operand1;
			codeWord = String.valueOf(bit) + codeWord;
			if (i == 0 && numbCarry > 0) {
				flagResidue = true;
				i = lengthWord;
				numbDataWords = 0;
				residueCodeWord = (String) codeWord;
				codeWord = "";
			}
		}
		return binary.trim() + codeWord + (binary.contains(" ") ? " " : "");

	}

	private char carryBit(char operand1, char operand2) throws Exception {
		char bit = '0';
		if ((operand1 != '0' && operand1 != '1')
				|| (operand2 != '0' && operand2 != '1')){
			System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: carryBit-value is not a Bit");
			throw new Exception("STCodes err: carryBit-value is not a Bit");
		}
		if (operand1 == '0' && operand2 == '0')
			bit = '0';
		else {
			if (operand1 == '1' && operand2 == '0' || operand1 == '0'
					&& operand2 == '1')
				bit = '1';
			else if (operand1 == '1' && operand2 == '1') {
				bit = '0';
			}
		}
		return bit;
	}

	public Object codingTechnique(Object value, String typeCode, String codeAction)
			throws Exception {
		String typeCodeAction = "";
		Object codeWord = "";
		if (value.getClass() == Float.class || value.getClass() == Integer.class || codeAction.equals(TypeCodeAction.Encoding.toString())) {
			//System.out.println("The value:" + value + " will be enconded");
			typeCodeAction = codeAction;
		} else {
			if (value.getClass() == String.class && codeAction.equals(TypeCodeAction.Decoding.toString())) {
				//System.out.println("The value:" + value + " will be decoded");
				typeCodeAction = codeAction;
			} else{
				System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: codingTechnique-Wrong coding value");
				throw new Exception("STCodes err: codingTechnique-Wrong coding value");
			}
		}

		if (typeCode.equals(TypeCode.Berger.toString()))
			codeWord = checkBergerCode(value, typeCodeAction);
		else {
			if (typeCode.equals(TypeCode.Duplication.toString()))
				codeWord = checkDuplicationCode(value, typeCodeAction);
			else {
				if (typeCode.equals(TypeCode.Residue.toString()))
					codeWord = checkResidueCode(value, typeCodeAction);
				else
					throw new Exception("");
			}
		}
		return codeWord;
	}

	private Object checkBergerCode(Object code, String typeCodeAction)
			throws Exception {
		Object codeWord = "";
		if (typeCodeAction.equals(TypeCodeAction.Encoding.toString())) {
			System.out.println(ras.interfaces.Utilities.separator1+"PROCESS WILL ENCODE THE VALUE: " + code + " WITH "+TypeCode.Berger.toString());
			codeWord = valueToBinary(code);
 			codeWord = codeWord.toString().trim() + complementBinary((String) codeWord, false) + (codeWord.toString().contains(" ") ? " " : "");
 			System.out.println(ras.interfaces.Utilities.separator1+"VALUE ENCODED WITH "+TypeCode.Berger.toString()+": " + codeWord);
		} else {
			if (typeCodeAction.equals(TypeCodeAction.Decoding.toString())) {
				int infoLength, infoBits, kNumb, k;
				String infoData, kData;

				System.out.println(ras.interfaces.Utilities.separator1+"PROCESS WILL DECODE THE VALUE: " + code + " WITH "+TypeCode.Berger.toString());
				for (infoLength = 0; infoLength < code.toString().trim().length(); infoLength++) {
					k = (int) Math.ceil(Math.log(infoLength + 1) / Math.log(2));
					if (infoLength + k == code.toString().trim().length())
						break;
				}
				infoData = code.toString().trim().substring(0, infoLength);
				infoBits = checkBitsNumb(infoData);
				kData = code.toString().trim().substring(infoLength,
						code.toString().trim().length());
				kData = complementBinary(kData, true);
				kNumb = (int) binaryToValue(kData);
				if (infoBits == kNumb){
					if(code.toString().contains(" "))
						infoData += " ";
					codeWord = binaryToValue(infoData);
					System.out.println(ras.interfaces.Utilities.separator1+"VALUE DECODED WITH "+TypeCode.Berger.toString()+": " + codeWord);
				} else{
					System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkBergerCode-Code detected an unidirectional error");
					throw new Exception("STCodes err: checkBergerCode-Code detected an unidirectional error");
				}
			} else {
				System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkBergerCode-Type Code Action");
				throw new Exception(
						"STCodes err: checkBergerCode-Type Code Action");
			}
		}
		return codeWord;
	}

	private Object checkDuplicationCode(Object code, String typeCodeAction)
			throws Exception {
		Object codeWord = "";
		if (typeCodeAction.equals(TypeCodeAction.Encoding.toString())) {
			System.out.println(ras.interfaces.Utilities.separator1+"PROCESS WILL ENCODE THE VALUE: " + code + " WITH "+TypeCode.Duplication.toString());
			codeWord = valueToBinary(code);
			codeWord = swapCompare((String) codeWord, false) + (codeWord.toString().contains(" ") ? " " : "");
			System.out.println(ras.interfaces.Utilities.separator1+"VALUE ENCODED WITH "+TypeCode.Duplication.toString()+": " + codeWord);
		} else {
			if (typeCodeAction.equals(TypeCodeAction.Decoding.toString())) {
				System.out.println(ras.interfaces.Utilities.separator1+"PROCESS WILL DECODE THE VALUE: " + code + " WITH "+TypeCode.Duplication.toString());
				codeWord = swapCompare(code.toString().trim(), true);
				if (codeWord.getClass() == Boolean.class){
					System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkDuplicationCode-Code detected an error");
					throw new Exception(
							"STCodes err: checkDuplicationCode-Code detected an error");
				}
				if(code.toString().contains(" "))
					codeWord += " ";
				codeWord = binaryToValue((String) codeWord);
				System.out.println(ras.interfaces.Utilities.separator1+"VALUE DECODED WITH "+TypeCode.Duplication.toString()+": " + codeWord);
			} else {
				System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkDuplicationCode-Type Code Action");
				throw new Exception(
						"STCodes err: checkDuplicationCode-Type Code Action");
			}
		}
		return codeWord;
	}

	private Object checkResidueCode(Object code, String typeCodeAction)
			throws Exception {
		Object codeWord = "";

		if (typeCodeAction.equals(TypeCodeAction.Encoding.toString())) {
			System.out.println(ras.interfaces.Utilities.separator1+"PROCESS WILL ENCODE THE VALUE: " + code + " WITH "+TypeCode.Residue.toString());
			codeWord = valueToBinary(code);
			codeWord = (String) checkSum((String) codeWord, false) + (codeWord.toString().contains(" ")? " ": "");
			System.out.println(ras.interfaces.Utilities.separator1+"VALUE ENCODED WITH "+TypeCode.Residue.toString()+": " + codeWord);
		} else {
			if (typeCodeAction.equals(TypeCodeAction.Decoding.toString())) {
				int lengthCodeWord = 0;
				System.out.println(ras.interfaces.Utilities.separator1+"PROCESS WILL DECODE THE VALUE: " + code + " WITH "+TypeCode.Residue.toString());
				lengthCodeWord = code.toString().trim().length() % 8;
				if (lengthCodeWord != 0){
					System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkResidueCode-Invalid codeWord (length)");
					throw new Exception(
							"STCodes err: checkResidueCode-Invalid codeWord (length)");
				}
				lengthCodeWord = (code.toString().trim().length()) - 8;
				String checksumCalculated = "", checksumRecived = "";
				codeWord = code.toString().trim().substring(0, lengthCodeWord);
				checksumCalculated = (String) checkSum((String) codeWord, false);
				checksumCalculated = checksumCalculated.substring(lengthCodeWord, checksumCalculated.length());
				checksumRecived = code.toString().trim().substring(lengthCodeWord, code.toString().trim().length());
				for (int i = 0; i < 8; i++)
					if (checksumCalculated.charAt(i) != checksumRecived.charAt(i)){
						System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkResidueCode-Code detected an error");
						throw new Exception(
								"STCodes err: checkResidueCode-Code detected an error");
					}
				if(code.toString().contains(" "))
					codeWord += " ";
				codeWord = binaryToValue((String) codeWord);
				System.out.println(ras.interfaces.Utilities.separator1+"VALUE DECODED WITH "+TypeCode.Residue.toString()+": " + codeWord);
			} else {
				System.out.println(ras.interfaces.Utilities.separator3+"STCodes err: checkResidueCode-Type Code Action");
				throw new Exception(
						"STCodes err: checkResidueCode-Type Code Action");
			}
		}
		return codeWord;
	}
	
	public List<Object> valueCode(List<Object> values, String typeCode, String typeCodeAction){
		List<Object> results = new ArrayList<Object>();
		Object partialResult = null;
		try {
			for(Object val : values){
				partialResult = codingTechnique(val, typeCode, typeCodeAction);
				results.add(partialResult);
			}
		 } catch (Exception e) {
			 // TODO Auto-generated catch block e.printStackTrace();
			 e.printStackTrace();
		 }
		return results;
	}
	
/*	
	 @SuppressWarnings("unchecked")
	public static void main(String[] args) { 
	
		 float f = 100f; 
		 int intBits = Float.floatToIntBits(f);
		 // PREVIOUS VALUES ARE NEEDED 
//		 -- BEGIN OF COMMENTS 
//		 int rawIntBits = Float.floatToRawIntBits(f);
//		 System.out.printf("f = %f  intBits = %d  " + "rawIntBits = %d%n", f,
//		 intBits, rawIntBits); float toFloat = Float.intBitsToFloaflagt(intBits);
//		 System.out.printf("toFloat = %f%n", toFloat);
//		 
//		 int sign = intBits & 0x80000000; int exponent = intBits & 0x7f800000; int
//		 mantissa = intBits & 0x007fffff;
//		 System.out.printf("sign = %d  exponent = %d  mantissa = %d%n", sign,
//		 exponent, mantissa);
//		 
//		 String binarySign = Integer.toBinaryString(sign); String binaryExponent =
//		 Integer.toBinaryString(exponent); String binaryMantissa =
//		 Integer.toBinaryString(mantissa);
//		 System.out.printf("binarySign     = %s%nbinaryExponent = %s%n" +
//		 "binaryMantissa = %s%n", binarySign, binaryExponent, binaryMantissa);
//		 
//		 Bit 31 (the bit that is selected by the mask 0x80000000) represents the
//		 sign of the floating-point number. Bits 30-23 (the bits that are selected
//		 by the mask 0x7f800000) represent the exponent. Bits 22-0 (the bits that
//		 are selected by the mask 0x007fffff) represent the significand (sometimes
//		 called the mantissa) of the floating-point number.
//		 11000010110010000000000000000000 (-)Sign 01000010110010000000000000000000
//		 (+)Sign
//		 
//		 
//		 intBits = Float.floatToIntBits(f); String binary =
//		 Integer.toBinaryString(intBits); float myFloat =
//		 Float.intBitsToFloat(intBits);
//		 System.out.println(intBits+" "+binary+" "+myFloat);
//		 
//		 -- END OF COMMENTS
	 
		 //************************TEST OF CODES STARTS HERE 
		 STCodes code = new	 STCodes(); 
		 String binary = "0111010011"; 
		 int numberBits = code.checkBitsNumb(binary);
	 
		 try { 
			 float myFloat = (int) code.binaryToValue(binary); 
			 binary = code.valueToBinary(f);
			 System.out.println(intBits+" "+binary+" "+myFloat+" "+numberBits);
			 code.codingTechnique(-100f, TypeCode.Berger.toString(),TypeCodeAction.Decoding.toString()); 
		 } catch(Exception e1) { 
			 // TODO Auto-generated catch block e1.printStackTrace();
		 }
		 System.out.println("\n\n");
		 String value = "true";//"Operation, partial operation, or misoperation of a fully redundant Special Protection System (or Remedial Action Scheme) in response to an event or abnormal system condition for which it was not intended to operate";
		 //float value = -12.1234567f;
		 //int value = 1250;
		 //(int) code.binaryToValue("0111010"); 
		 try {
			 System.out.println("initial decimal value \n"+ras.interfaces.Utilities.separator1+value); 
			 //binary = code.valueToBinary(value); 
			 //System.out.println("BINARY VALUE \n"+ras.interfaces.Utilities.separator1+binary);
			 binary = (String) code.codingTechnique(value, TypeCode.Berger.toString(),TypeCodeAction.Encoding.toString()); 
			 //System.out.println("ENCODE BERGER: \n"+ras.interfaces.Utilities.separator1+binary);
			 //System.out.println("initial value "+binary); 
			 value = (String) code.codingTechnique(binary, TypeCode.Berger.toString(),TypeCodeAction.Decoding.toString());
			 //value = (float) code.codingTechnique(binary, TypeCode.Berger.toString(),TypeCodeAction.Decoding.toString());
			 //value = (int) code.codingTechnique(binary, TypeCode.Berger.toString(),TypeCodeAction.Decoding.toString());
	 
			 //System.out.println("DECODE BERGER: \n"+ras.interfaces.Utilities.separator1+value); 
		 } catch (Exception e) {
			 // TODO Auto-generated catch block e.printStackTrace();
			 e.printStackTrace();
		 }
		 System.out.println("\n\n"); 
		 //value = 100F; 
		 try {
			 System.out.println("initial decimal value \n"+ras.interfaces.Utilities.separator1+value); 
			 binary = code.valueToBinary(value); 
			 //System.out.println("BINARY VALUE \n"+ras.interfaces.Utilities.separator1+binary);
			 binary = (String) code.codingTechnique(value,TypeCode.Duplication.toString(),TypeCodeAction.Encoding.toString());
			 //System.out.println("ENCODE DUPLICLATION: \n"+ras.interfaces.Utilities.separator1+binary); 
			 //binary = "0100001011001000000000000000000100000000000000000100001011001000";
			 //System.out.println("initial value "+binary); 
			 value = (String) code.codingTechnique(binary, TypeCode.Duplication.toString(),TypeCodeAction.Decoding.toString());
			 //value = (float) code.codingTechnique(binary, TypeCode.Duplication.toString(),TypeCodeAction.Decoding.toString());
			 //value = (int) code.codingTechnique(binary, TypeCode.Duplication.toString(),TypeCodeAction.Decoding.toString());
			 //System.out.println("DECODE DUPLICATION: \n"+ras.interfaces.Utilities.separator1+value);
		 } catch (Exception e) {
			 // TODO: handle exception e.printStackTrace();
			 e.printStackTrace();
		 } 
		 System.out.println("\n\n");
		 //"Operation, partial operation, or misoperation of a fully redundant Special Protection System (or Remedial Action Scheme) in response to an event or abnormal system condition for which it was not intended to operate";
		 String value1 = value;
		 //float value1 = value;
		 //int value1 = value;
		 try {
			 System.out.println("initial decimal value "+value1); 
			 binary = code.valueToBinary(value1); 
			 //System.out.println("BINARY VALUE: \n"+ras.interfaces.Utilities.separator1+binary);
			 binary = (String) code.codingTechnique(value1, TypeCode.Residue.toString(),TypeCodeAction.Encoding.toString()); 
			 //System.out.println("ENCODE RESIDUE: \n"+ras.interfaces.Utilities.separator1+binary);
			 //binary = "0100010101111111111100000000000010110101";
			 //System.out.println("initial value "+binary); 
			 value = (String) code.codingTechnique(binary, TypeCode.Residue.toString(),TypeCodeAction.Decoding.toString());
			 //value = (float) code.codingTechnique(binary, TypeCode.Residue.toString(),TypeCodeAction.Decoding.toString());
			 //value = (int) code.codingTechnique(binary, TypeCode.Residue.toString(),TypeCodeAction.Decoding.toString());
			 //System.out.println("DECODE RESIDUE: \n"+ras.interfaces.Utilities.separator1+value); 
		 } catch (Exception e) {
			 // TODO: handle exception e.printStackTrace();
			 e.printStackTrace();
		 }
		 		 
		List<Object> valFloat = new ArrayList<Object>();
		//valFloat.add("Operation, partial operation, or misoperation of a fully redundant Special Protection System (or Remedial Action Scheme) in response to an event or abnormal system condition for which it was not intended to operate");
		valFloat.add("foo");
		//valFloat.add("0.1234561F");
		List<Object> valString = new ArrayList<Object>();
		try {
			valString = code.valueCode(valFloat, TypeCode.Duplication.toString(), TypeCodeAction.Encoding.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//for(Object val : valString)
		//	System.out.println(val);
		
		try {
			valFloat = code.valueCode(valString, TypeCode.Duplication.toString(), TypeCodeAction.Decoding.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//for(Object val :valFloat)
		//	System.out.println(val);
	}
*/
}