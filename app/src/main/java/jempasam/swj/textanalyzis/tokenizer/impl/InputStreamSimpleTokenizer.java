package jempasam.swj.textanalyzis.tokenizer.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jempasam.swj.textanalyzis.tokenizer.Tokenizer;



public class InputStreamSimpleTokenizer implements Tokenizer{
	private InputStreamReader input;
	private String separator;
	private String solo;
	private String stringsep;
	
	private String comment="";
	
	private int[] buffer;
	private int buffer_last;
	private int buffer_end;
	
	public InputStreamSimpleTokenizer(InputStream input, String separator, String solo_character, String stringsep) {
		this.input=new InputStreamReader(input);
		this.separator=separator;
		this.solo=solo_character;
		this.stringsep=stringsep;
		
		buffer=new int[10];
		buffer_last=0;
		buffer_end=0;
		
	}
	
	public String getComment() { return comment; }
	public void setComment(String comment) { this.comment = comment; }

	private int nextchar() {
		if(buffer_last==buffer_end){
			buffer_last++;
			if(buffer_last>=buffer.length)buffer_last=0;
			buffer_end=buffer_last;
			try {
				buffer[buffer_last]=input.read();
			} catch (IOException e) {
				buffer[buffer_last]=-1;
			}
		}else {
			buffer_last++;
			if(buffer_last>=buffer.length)buffer_last=0;
		}
		return buffer[buffer_last];
	}
	
	private void backward() {
		buffer_last--;
		if(buffer_last<0)buffer_last=buffer.length-1;
	}
	
	@Override
	public String next() {
		StringBuilder sb=new StringBuilder();
		int ch;
		
		//PASS WHITE SPACES
		while(separator.indexOf(ch=nextchar())!=-1 && ch!=-1);
		
		if(ch!=-1) {
			//TEST IF SOLO
			if(solo.indexOf(ch)!=-1) {
				sb.append((char)ch);
			}
			else {
				//READ THE TOKEN
				int instring=-1;
				int incomment=-1;
				do {
					if(instring==-1) {
						if(incomment!=-1) {
							if(ch==incomment)incomment=-1;
						}
						else if(comment.indexOf(ch)!=-1)incomment=ch;
						else if(separator.indexOf(ch)!=-1)break;
						else if(solo.indexOf(ch)!=-1) {
							backward();
							break;
						}
						else if(stringsep.indexOf(ch)!=-1)instring=ch;
						else sb.append((char)ch);
					}
					else {
						if(ch==instring)instring=-1;
						else sb.append((char)ch);
					}
				}while((ch=nextchar())!=-1);
			}
		}
		
		//PASS WHITE SPACES
		int incomment=-1;
		while((ch=nextchar())!=-1) {
			if(incomment!=-1) {
				if(ch==incomment)incomment=-1;
			}
			else {
				if(comment.indexOf(ch)!=-1)incomment=ch;
				else if(separator.indexOf(ch)==-1) {
					backward();
					break;
				}
			}
		}
		
		if(sb.length()==0)return null;
		else return sb.toString();
	}
	
	@Override
	public boolean hasNext() {
		try {
			return buffer_last!=buffer_end || input.ready();
		} catch (IOException e) {
			return false;
		}
	}
	
	public static void main(String[] args) {
		InputStream inputstream=new ByteArrayInputStream("salut les gens\n he h zzz  #oooo zqdqz d# ".getBytes());
		InputStreamSimpleTokenizer token=new InputStreamSimpleTokenizer(inputstream, " \t\n\r", "+", "\"'");
		token.setComment("#");
		while(token.hasNext()) {
			System.out.println(token.next()+";"+token.hasNext());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
