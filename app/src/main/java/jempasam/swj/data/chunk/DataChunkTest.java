package jempasam.swj.data.chunk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataChunkTest {
	
	ObjectChunk object1;
	ObjectChunk object2;
	ObjectChunk object3;
	ObjectChunk temp;
	ObjectChunk temp2;
	
	@BeforeEach
	void beforeeach() {
		object1=new ObjectChunk("root");
		object1.add(new ValueChunk("light", "40%"));
		object1.add(new ValueChunk("color", "red"));
			temp=new ObjectChunk("direction"); object1.add(temp);
			temp.add(new ValueChunk("x", "90°"));
			temp.add(new ValueChunk("y", "30°"));
			
			temp=new ObjectChunk("default"); object1.add(temp);
			temp.add(new ValueChunk("light", "100%"));
			temp.add(new ValueChunk("color", "white"));
				temp2=new ObjectChunk("direction"); temp.add(temp2);
				temp2.add(new ValueChunk("x", "0°"));
				temp2.add(new ValueChunk("y", "45°"));
		
		
		
		object2=new ObjectChunk("fruit");
		object2.add(new ValueChunk("banane", "3€"));
		object2.add(new ValueChunk("pomme", "6€"));
		object2.add(new ValueChunk("kiwi", "7€"));
		object2.add(new ValueChunk("pomme", "8.5€"));
		
		
		object3=new ObjectChunk("france");
		temp=new ObjectChunk("paca"); object3.add(temp);
		temp2=new ObjectChunk("alpes"); temp.add(temp2);
		temp=new ObjectChunk("alpes"); temp2.add(temp);
		temp2=new ObjectChunk("antibes"); temp.add(temp2);
		temp2.add(new ValueChunk("habitants", "1000"));
		object3.add(new ValueChunk("habitants", "100000"));
		
		System.out.println(object1.toString());
		System.out.println(object2.toString());
		System.out.println(object3.toString());
		
	}
	
	@AfterEach
	void aftereach() {
		
	}

	@Test
	void sizeNameTotalsize() {
		forall(object1, "root", 4, 8);
		forall(object2, "fruit", 4, 4);
		forall(object3, "france", 2, 2);
	}
	
	@Test
	void getAndSet() {
		//Simple
		assertNull(object1.get("ligh"));
		assertNotNull(object1.get("light"));
		
		assertNull(object2.get("kiw"));
		assertNull(object2.get("kiwii"));
		assertNull(object2.get("bananen"));
		assertNotNull(object2.get("banane"));
		assertNotNull(object2.get("pomme"));
		
		assertNull(object3.get("pacaa"));
		assertNull(object3.get("pacca"));
		assertNull(object3.get("bananen"));
		assertNotNull(object3.get("habitants"));
		assertNotNull(object3.get("paca"));
		
		//With path
		assertNull(object1.get("direction.z"));
		assertNull(object1.get("direction.x.x"));
		assertNull(object1.get("direction.x."));
		assertNull(object1.get("default.direction.z"));
		assertNotNull(object1.get("direction.x"));
		assertNotNull(object1.get("default.direction.y"));
		assertNotNull(object1.get("default.direction"));
		
		object2.get("banane").setName("orange");
		assertNull(object2.get("banane"));
		assertNotNull(object2.get("orange"));		
	}
	
	@Test
	void rename(){
		assertNotNull(object1.get("light"));
		assertNotNull(object1.get("default.light"));
		assertNull(object1.get("pouet"));
		assertNull(object1.get("default.pouet"));
		object1.rename("light", "pouet");
		assertNull(object1.get("light"));
		assertNull(object1.get("default.light"));
		assertNotNull(object1.get("pouet"));
		assertNotNull(object1.get("default.pouet"));
	}
	
	@Test
	void forEachRecursively(){
		assertNotNull(object1.get("light"));
		assertNotNull(object1.get("default.light"));
		assertNull(object1.get("pouet"));
		assertNull(object1.get("default.pouet"));
		object1.forEachRecursively((e)->{
			if(e.getName().equals("light"))e.setName("pouet");
			});
		assertNull(object1.get("light"));
		assertNull(object1.get("default.light"));
		assertNotNull(object1.get("pouet"));
		assertNotNull(object1.get("default.pouet"));
	}
	
	void forall(ObjectChunk objectChunk, String name, int size, int totalsize) {
		
		//Name
		assertNotEquals(objectChunk.getName(), "root1");
		assertEquals(objectChunk.getName(), name);
		
		//Size
		if(size!=0)assertNotEquals(objectChunk.size(), 0);
		assertNotEquals(objectChunk.size(), -1);
		assertNotEquals(objectChunk.size(), size+1);
		assertEquals(objectChunk.size(), size);
		
		//Total size
		if(totalsize!=0)assertNotEquals(objectChunk.totalSize(), 0);
		assertNotEquals(objectChunk.totalSize(), -1);
		assertNotEquals(objectChunk.totalSize(), totalsize+1);
		assertEquals(objectChunk.totalSize(), totalsize);
		
		assertEquals(objectChunk.getAsPathList().size(), objectChunk.totalSize());
		
		List<ValueChunk> values=new ArrayList<>();
		objectChunk.fillWithValues(values);
		assertEquals(values.size(), objectChunk.totalSize());
	}

}
