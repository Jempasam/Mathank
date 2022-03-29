package jempasam.swj.objectmanager.loader;

import java.io.InputStream;

import jempasam.swj.data.chunk.ObjectChunk;
import jempasam.swj.data.deserializer.DataDeserializer;
import jempasam.swj.logger.SLogger;
import jempasam.swj.objectmanager.ObjectManager;

public abstract class ObjectLoader<T> {
	protected ObjectManager<T> manager;
	protected SLogger logger;
	protected DataDeserializer deserializer;
	
	
	protected ObjectLoader(ObjectManager<T> manager, SLogger logger, DataDeserializer serializer) {
		super();
		this.manager = manager;
		this.logger = logger;
		this.deserializer = serializer;
	}
	
	
	public final void loadFrom(InputStream input) {
		ObjectChunk data=deserializer.loadFrom(input);
		interpret(data);
	}
	
	protected abstract void interpret(ObjectChunk data);
}
