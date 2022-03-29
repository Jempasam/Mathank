package jempasam.swj.data.deserializer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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

public class ChardentDataDeserializer implements DataDeserializer {
	
	private Function<InputStream, Tokenizer> tokenizerSupplier;
	private SLogger logger;
		
	private String separatorToken="\n";
	private String affectationToken=":";
	private String indentorToken="-";
	
	public String getIndentorToken() { return indentorToken;}
	public String getAffectationToken() { return affectationToken;}
	public String getSeparatorToken() { return separatorToken; }
	
	public void setIndentorToken(String indentorToken) {this.indentorToken = indentorToken;}
	public void setAffectationToken(String affectationToken) { this.affectationToken = affectationToken;}
	public void setSeparatorToken(String separatorToken) { this.separatorToken = separatorToken;}

	public ChardentDataDeserializer(Function<InputStream, Tokenizer> tokenizerSupplier, SLogger logger) {
		super();
		this.tokenizerSupplier = tokenizerSupplier;
		this.logger = logger;
	}
	
	@Override
	public ObjectChunk loadFrom(InputStream i) {
		IteratorBufferedReader<String> input=new IteratorBufferedReader<>(tokenizerSupplier.apply(i), new String[5]);
		ObjectChunk ret=loadObject(input, countIndent(input));
		ret.setName("root");
		return ret;
	}
	
	private int countIndent(IteratorBufferedReader<String> input) {
		int i=0;
		while(input.hasNext() && input.next().equals(indentorToken)) {
			i++;
		}
		input.backward();
		logger.log("indent "+i);
		return i;
	}
	
	private ObjectChunk loadObject(IteratorBufferedReader<String> input, int actual_indent) {
		logger.enter("new Object");
		
		ObjectChunk ret=new ObjectChunk(null);
		
		String token;
		while( input.hasNext() && !(token=input.next()).equals(separatorToken)) {
			ret.add(new ValueChunk("", token));
		}
			
		int newindent;
		while(input.hasNext() && (newindent=countIndent(input))>=actual_indent) {
			List<String> names=new ArrayList<>();
			List<DataChunk> values=new ArrayList<>();
			try {
				logger.enter("parameter");
				//Load names
				while(input.hasNext() && !(token=input.next()).equals(affectationToken)) {
					if(token.equals(separatorToken))throw new Throwable();
					else names.add(token);
				}
				//Load values
				if(newindent>actual_indent) {
					logger.log("as Object");
					values.add(loadObject(input, newindent));
				}
				else {
					logger.log("as Value");
					while(input.hasNext() && !(token=input.next()).equals(separatorToken)) {
						values.add(new ValueChunk(null, token));
					}
				}
			}catch (Throwable t) { }
			
			if(values.size()==0)logger.log("Values are misising. Parameter is ignored.");
			else {
				if(names.size()==0) names.add("");
				for(String n : names) {
					int counter=1;
					for(DataChunk v: values) {
						DataChunk d=v.clone();
						d.setName(n);
						ret.add(d);
						counter++;
					}
				}
			}
			logger.exit();
		}
		logger.exit();
		return ret;
	}
	
	public static void main(String[] args) {
		DataDeserializer ds=new ChardentDataDeserializer((i)->new InputStreamSimpleTokenizer(i," \r\t",":-\n","\"'"), new SimpleSLogger(System.out));
		try {
			System.out.println(ds.loadFrom(new FileInputStream(new File("test.txt"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
