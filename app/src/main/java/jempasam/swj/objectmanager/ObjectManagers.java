package jempasam.swj.objectmanager;

import jempasam.swj.data.deserializer.impl.StrobjoDataDeserializer;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;
import jempasam.swj.objectmanager.loader.ObjectLoader;
import jempasam.swj.objectmanager.loader.SimpleObjectLoader;
import jempasam.swj.parsing.SimpleValueParser;
import jempasam.swj.parsing.ValueParsers;
import jempasam.swj.textanalyzis.tokenizer.impl.InputStreamSimpleTokenizer;

public class ObjectManagers {
	public static <T> ObjectLoader<T> createLoader(ObjectManager<T> manager, Class<T> type, String prefix, String name){
		ObjectLoader<T> newproto=null;
		SLogger logger=new SimpleSLogger(System.out);
		if(name.equals("strobjo")) {
			newproto=new SimpleObjectLoader<T>(
					manager,
					logger,
					new StrobjoDataDeserializer((i)->new InputStreamSimpleTokenizer(i, " \t\r\n", ":(),", "\"'"),logger),
					ValueParsers.createSimpleValueParser(),
					type,
					prefix);
		}
		return newproto;
	}
}
