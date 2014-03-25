package io.github.fourohfour.gtnlib;

public class InvalidFileException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	/**
	 * InvalidFileException		
	 * 
	 * Codes		
	 * 
	 * 0 - File is not .gtn		
	 * 
	 * 1 - Invalid File Syntax		
	 * 
	 * 2 - Missing compulsary tag		
	 * 
	 * 3 - Invalid tag value		
	 * 
	 * 4 - Missing Namespace		
	 * 
	 * 5 - Tag not applicable		
	 * 
	 * @param code - integer code value		
	 */
	public InvalidFileException(int code){
		if (code == 0){
			message = "File is not .gtn [CODE 0]";
		}
		else if (code == 1){
			message = "Invalid File Syntax [CODE 1]";
		}
		else if (code == 2){
			message = "Missing compulsary tag [CODE 2]";
		}
		else if (code == 3){
			message = "Invalid tag value [CODE 3]";
		}
		else if (code == 4){
			message = "Missing Namespace [CODE 4]";
		}
		else if (code == 5){
			message = "Tag Not Applicable [CODE 5]";
		}
		else{
			message = "Unknown reason [CODE " + String.valueOf(code) + "]";
		}
	}
	
	@Override
	public String getMessage(){
		return message;
	}
	
	public void printWarning(){
		System.out.println("Could not load teams");
		System.out.println("Error: " + this.message);
	}
}
