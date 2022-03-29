package jempasam.swj.data.deserializer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import jempasam.swj.data.chunk.DataChunk;
import jempasam.swj.data.chunk.ObjectChunk;
import jempasam.swj.data.chunk.ValueChunk;
import jempasam.swj.data.deserializer.DataDeserializer;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;
import jempasam.swj.textanalyzis.reader.IteratorBufferedReader;
import jempasam.swj.textanalyzis.tokenizer.Tokenizer;
import jempasam.swj.textanalyzis.tokenizer.impl.InputStreamSimpleTokenizer;

public class StrobjoDataDeserializer implements DataDeserializer{
	
	private Function<InputStream, Tokenizer> tokenizerSupplier;
	private SLogger logger;
	
	private String openToken="(";
	private String closeToken=")";
	private String affectationToken=":";
	private String separatorToken=",";
	
	public String getOpenToken() { return openToken; }
	public String getCloseToken() { return closeToken; }
	public String getAffectationToken() { return affectationToken; }
	public String getSeparatorToken() { return separatorToken; }
	
	public void setOpenToken(String s) { openToken=s; }
	public void setCloseToken(String s) { closeToken=s; }
	public void setAffectationToken(String s) { affectationToken=s; }
	public void setSeparatorToken(String s) { separatorToken=s; }

	public StrobjoDataDeserializer(Function<InputStream, Tokenizer> tokenizerSupplier, SLogger logger) {
		super();
		this.tokenizerSupplier=tokenizerSupplier;
		this.logger=logger;
	}
		
	@Override
	public ObjectChunk loadFrom(InputStream input) {
		ObjectChunk ret=loadChunk(new IteratorBufferedReader<String>(tokenizerSupplier.apply(input),new String[5]));
		ret.setName("root");
		return ret;
	}
	
	private ObjectChunk loadChunk(IteratorBufferedReader<String> tokenizer) {
		logger.enter("new Object");
		ObjectChunk newchunk=new ObjectChunk(null);
		
		String token;
		boolean endofobject=false;
		boolean endofparameter;
		
		List<String> names=new ArrayList<>();
		List<DataChunk> values=new ArrayList<>();
		int i=1;
		//Load names of parameter and their values
		while(!endofobject) {
			logger.enter("Parameter "+i);
			
			endofparameter=false;
			
			//LOAD NAMES
			while(true) {
				token=tokenizer.next();
				//CLOSE OBJECT
				if(token==null || token.equals(closeToken)) {
					endofobject=true;
					break;
				}
				//CLOSE NAME LIST
				else if(token.equals(affectationToken)) {
					break;
				}
				//CLOSE PARAMETER
				else if(token.equals(separatorToken)) {
					endofparameter=true;
					break;
				}
				//ADD NAME
				else names.add(token);
			}
			logger.log("names:"+names);
			
			//LOAD VALUES
			if(!endofobject && !endofparameter)
			while(true) {
				token=tokenizer.next();
				//CLOSE OBJECT
				if(token==null || token.equals(closeToken)) {
					endofobject=true;
					break;
				}
				else if(token.equals(separatorToken)) {
					break;
				}
				else{
					tokenizer.backward();
					DataChunk dc=loadDataChunkValue(tokenizer);
					if(dc!=null) {
						values.add(dc);
					}
				}
			}
			
			//CANCELING ERROR
			if(values.size()==0)logger.log("Values are misising. Parameter is ignored.");
			else {
				if(names.size()==0) names.add("");
				for(String n : names) {
					int counter=1;
					for(DataChunk v: values) {
						DataChunk d=v.clone();
						d.setName(n);
						newchunk.add(d);
						counter++;
					}
				}
			}
			
			values.clear();
			names.clear();
			
			i++;
			
			logger.exit();
		}
		logger.log("Result: "+newchunk);
		logger.exit();
		return newchunk;
	}
	
	private DataChunk loadDataChunkValue(IteratorBufferedReader<String> tokenizer) {
		String token;
		if((token=tokenizer.next())!=null) {
			if(token.equals(openToken)) {
				logger.log("As object");
				return loadChunk(tokenizer);
			}
			else{
				logger.log("As value");
				logger.log("="+token);
				return new ValueChunk("", token);
			}
		}
		else return null;
	}
	
	public static void main(String[] args) {
		DataDeserializer serializer=new StrobjoDataDeserializer((i)->new InputStreamSimpleTokenizer(i, " \t\r\n", "():,", "\"'"), new SimpleSLogger(System.out));
		try {
			ObjectChunk obj=serializer.loadFrom(new FileInputStream(new File("test.txt")));
			System.out.println(obj.toString());
			System.out.println(Arrays.toString(obj.toValueArray()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
