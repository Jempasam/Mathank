package jempasam.swj.data.deserializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import jempasam.swj.data.chunk.ObjectChunk;
import jempasam.swj.data.deserializer.impl.BaliseDataDeserializer;
import jempasam.swj.data.deserializer.impl.StrobjoDataDeserializer;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;
import jempasam.swj.textanalyzis.tokenizer.impl.InputStreamSimpleTokenizer;

public class DataDeserializers {
	
	public static DataDeserializer createStrobjoDS(SLogger logger) {
		StrobjoDataDeserializer ret=new StrobjoDataDeserializer(
				(i)->{
					InputStreamSimpleTokenizer r=new InputStreamSimpleTokenizer(i," \n\r\t","():,","\"'");
					r.setComment("#");
					return r;
				},
				logger
				);
		return ret;
	}
	
	public static DataDeserializer createJSONLikeStrobjoDS(SLogger logger) {
		StrobjoDataDeserializer ret=new StrobjoDataDeserializer(
				(i)->{
					InputStreamSimpleTokenizer r=new InputStreamSimpleTokenizer(i," \n\r\t","{}:,","\"'");
					r.setComment("#");
					return r;
				},
				logger
				);
		ret.setCloseToken("}");
		ret.setOpenToken("{");
		ret.setSeparatorToken(",");
		ret.setAffectationToken(":");
		return ret;
	}
	
	public static DataDeserializer createSGMLLikeBaliseDS(SLogger logger) {
		BaliseDataDeserializer ret=new BaliseDataDeserializer(
				(i)->{
					InputStreamSimpleTokenizer r=new InputStreamSimpleTokenizer(i," \n\r\t","<>=;/","\"'");
					r.setComment("#");
					return r;
				},
				logger
				);
		return ret;
	}
	
	public static DataDeserializer createBoxLikeBaliseDS(SLogger logger) {
		BaliseDataDeserializer ret=new BaliseDataDeserializer(
				(i)->{
					InputStreamSimpleTokenizer r=new InputStreamSimpleTokenizer(i," \n\r\t","[:=,]","\"'");
					r.setComment("#");
					return r;
				},
				logger
				);
		ret.setPermissive(true);
		ret.setSeparatorToken(",");
		ret.setCloseBaliseToken(":");
		ret.setOpenBaliseToken("[");
		ret.setEndBaliseToken("]");
		return ret;
	}
	
	public static DataDeserializer createStructLikeStrobjoDS(SLogger logger) {
		StrobjoDataDeserializer ret=new StrobjoDataDeserializer(
				(i)->{
					InputStreamSimpleTokenizer r=new InputStreamSimpleTokenizer(i," \n\r\t","{}=;","\"'");
					r.setComment("#");
					return r;
				},
				logger
				);
		ret.setCloseToken("}");
		ret.setOpenToken("{");
		ret.setSeparatorToken(";");
		ret.setAffectationToken("=");
		return ret;
	}
	
	public static void main(String[] args) {
		try {
			ObjectChunk d=createSGMLLikeBaliseDS(new SimpleSLogger(System.out)).loadFrom(new FileInputStream(new File("test.txt")));
			System.out.println("-OBJECT DISPLAY-");
			System.out.println(d);
			System.out.println("-LIST DISPLAY-");
			System.out.println(d.getAsPathList().stream().map((v)->v.getName()+": "+v.getValue()).collect(Collectors.joining("\n")));
			
			ObjectChunk dd=(ObjectChunk)d.clone();
			dd.numerateSameName();
			System.out.println("-OBJECT DISPLAY-");
			System.out.println(dd);
			System.out.println("-LIST DISPLAY-");
			System.out.println(dd.getAsPathList().stream().map((v)->v.getName()+": "+v.getValue()).collect(Collectors.joining("\n")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
