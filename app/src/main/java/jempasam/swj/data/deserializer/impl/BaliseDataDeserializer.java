package jempasam.swj.data.deserializer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import jempasam.swj.data.chunk.ObjectChunk;
import jempasam.swj.data.chunk.ValueChunk;
import jempasam.swj.data.deserializer.DataDeserializer;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;
import jempasam.swj.textanalyzis.reader.IteratorBufferedReader;
import jempasam.swj.textanalyzis.tokenizer.Tokenizer;
import jempasam.swj.textanalyzis.tokenizer.impl.InputStreamSimpleTokenizer;

public class BaliseDataDeserializer implements DataDeserializer{
	
	private Function<InputStream, Tokenizer> tokenizerSupplier;
	private SLogger logger;
	
	private boolean permissive=false;
	
	private String openBaliseToken="<";
	private String closeBaliseToken=">";
	private String endBaliseToken="/";
	private String assignementToken="=";
	private String separatorToken=";";
	
	public void setPermissive(boolean permissive) { this.permissive = permissive; }
	public boolean isPermissive() { return permissive; }
	
	public String getOpenBaliseToken() {return openBaliseToken;}
	public String getCloseBaliseToken() {return closeBaliseToken;}
	public String getEndBaliseToken() {return endBaliseToken;}
	public String getAssignementToken() {return assignementToken;}
	public String getSeparatorToken() {return separatorToken;}

	public void setOpenBaliseToken(String openBaliseToken) {this.openBaliseToken = openBaliseToken;}
	public void setCloseBaliseToken(String closeBaliseToken) {this.closeBaliseToken = closeBaliseToken;}
	public void setEndBaliseToken(String endBaliseToken) {this.endBaliseToken = endBaliseToken;}
	public void setAssignementToken(String assignementToken) {this.assignementToken = assignementToken;}
	public void setSeparatorToken(String separatorToken) {this.separatorToken=separatorToken;}

	public BaliseDataDeserializer(Function<InputStream, Tokenizer> tokenizerSupplier, SLogger logger) {
		super();
		this.tokenizerSupplier = tokenizerSupplier;
		this.logger = logger;
	}
	
	@Override
	public ObjectChunk loadFrom(InputStream i) {
		IteratorBufferedReader<String> input=new IteratorBufferedReader<>(tokenizerSupplier.apply(i), new String[5]);
		ObjectChunk ret=loadObject(input);
		ret.setName("root");
		return ret;
	}
	
	private ObjectChunk loadObject(IteratorBufferedReader<String> input) {
		logger.enter("new object");
		String token=null;
		ObjectChunk ret=new ObjectChunk(null);
		//Each parameter
		boolean inparam=true;
		boolean hasmember=true;
		while(inparam&&input.hasNext()){
			List<String> names=new ArrayList<>();
			List<String> values=new ArrayList<>();
			
			//Load valueparameters
			logger.enter("New parameter");
			paramload:{
				while(input.hasNext() && !(token=input.next()).equals(assignementToken)) {
					if(token.equals(closeBaliseToken)) {
						inparam=false;
						break paramload;
					}
					else if(token.equals(separatorToken)){
						break paramload;
					}
					else if(token.equals(endBaliseToken)){
						token=input.next();
						if(!token.equals(closeBaliseToken)) {
							if(!permissive)logger.log("Miss a closingToken after endBaliseToken in opening balise");
							input.backward();
						}
						hasmember=false;
						inparam=false;
						break paramload;
					}
					else if(token.equals(assignementToken))break;
					else names.add(token);
				}
				
				while(input.hasNext() && !(token=input.next()).equals(separatorToken)) {
					if(token.equals(closeBaliseToken)){
						inparam=false;
						break paramload;
					}
					else if(token.equals(separatorToken)){
						break paramload;
					}
					else if(token.equals(endBaliseToken)){
						token=input.next();
						if(!token.equals(closeBaliseToken)) {
							if(!permissive)logger.log("Miss a closingBaliseToken after endBaliseToken in opening balise");
							input.backward();
						}
						hasmember=false;
						inparam=false;
						break paramload;
					}
					else values.add(token);
				}
			}
			if(values.size()==0 && names.size()==0) {
				logger.log("Empty parameter: ignore");
			}
			else if(values.size()==0) {
				logger.enter("As solo boolean parameter");
				for(String name : names) {
					logger.log(name+"=true");
					ret.add(new ValueChunk(name, "true"));
				}
				logger.exit();
			}
			else if(names.size()==0) {
				logger.log("As unnamed parameter");
				for(String value : values)ret.add(new ValueChunk("", value));
			}
			else{
				logger.log("As normal parameter");
				for(String value : values) for(String name : names) ret.add(new ValueChunk(name, value));
			}
			logger.exit();
		}
		while(hasmember&&input.hasNext()) {
			token=input.next();
			if(!token.equals(openBaliseToken)) {
				logger.log("Invalid token \""+token+"\" should be an openBaliseToken");
			}
			else if(input.next().equals(endBaliseToken)){
				token=input.next();
				if(!token.equals(closeBaliseToken)) {
					if(!permissive)logger.log("Miss a closingBaliseToken after endBaliseToken in opening balise");
					input.backward();
				}
				break;
			}
			else {
				input.backward();
				String name=input.next();
				ObjectChunk objectchunk=loadObject(input);
				objectchunk.setName(name);
				ret.add(objectchunk);
			}
		}
		logger.exit();
		return ret;
	}
	
	public static void main(String[] args) {
		DataDeserializer ds=new BaliseDataDeserializer((i)->new InputStreamSimpleTokenizer(i," \r\n\t","<>=;/","\"'"), new SimpleSLogger(System.out));
		
		try {
			ObjectChunk oc=ds.loadFrom(new FileInputStream(new File("test.txt")));
			System.out.println(oc);
			System.out.println(oc.getAsPathList().stream().map((e)->e.getName()+": "+e.getValue()).collect(Collectors.joining("\n")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
