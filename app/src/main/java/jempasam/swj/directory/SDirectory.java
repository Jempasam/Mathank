package jempasam.swj.directory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SDirectory implements Iterable<SDirectory.SFileName>{
	
	private Collection<SFileName> files;
	private Iterator<SFileName> it;
	
	public static class SFileName{
		SFileName(String filename, String option) {
			super();
			this.filename = filename;
			this.option = option;
		}
		
		public String filename;
		public String option;
	}	
	
	public SDirectory(String dir) {
		Map<String,SFileName> files=new HashMap<>();
		Class<?> clazz=SDirectory.class;
		
		BufferedReader reader;
		InputStream inputstream;
		String line;
		
		//Load from inside of the jar
		inputstream=clazz.getResourceAsStream("/"+dir+"/index.txt");
		if(inputstream!=null) {
			reader=new BufferedReader(new InputStreamReader(inputstream));
			try {
				while( (line=reader.readLine()) != null ) {
					String[] fileparam=line.split(";");
					String name=fileparam[0];
					String option=fileparam.length>1 ? fileparam[1] : null;
					files.put(name,new SFileName(name, option));
				}
			}catch (Exception e) { }
		}
		
		//Load from extern files
		File dirf=new File(dir+"/");
		if(dirf.isDirectory()) {
			for(String file : dirf.list()){
				files.put(file,new SFileName(file, null));
			}
		}
		
		this.files=files.values();
	}
	
	@Override
	public Iterator<SFileName> iterator() {
		return files.iterator();
	}
	
}
