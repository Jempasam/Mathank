package jempasam.swj.parsing;

public interface ValueParser {
	public <T> T parse(Class<T> type, String string);
}
