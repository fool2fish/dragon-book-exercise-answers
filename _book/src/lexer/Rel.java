package lexer;

public class Rel extends Token{
	public final String lexeme;
	public Rel(String s){
		super(Tag.REL);
		lexeme = new String(s);
	}
}
