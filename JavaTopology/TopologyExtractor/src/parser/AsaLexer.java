// Generated from C:\Users\Matei\Dropbox\papers\Costin\Symnet\SymUPB\JavaTopology\TopologyExtractor\src\grammar\Asa.g4 by ANTLR 4.x
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AsaLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 
		LINE_COMMENT=10, COMMENT=11, TOKEN=12, NUMBER=13, NEWLINE=14, WS=15;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'"
	};
	public static final String[] ruleNames = {
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"LINE_COMMENT", "COMMENT", "TOKEN", "NUMBER", "NEWLINE", "WS"
	};


	public AsaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Asa.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\21\u008f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\13\7\13^\n\13\f\13\16\13a\13\13\3\13\5\13d\n\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\3\f\7\fn\n\f\f\f\16\fq\13\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\7\rz\n\r\f\r\16\r}\13\r\3\16\6\16\u0080\n\16\r\16\16\16\u0081"+
		"\3\17\5\17\u0085\n\17\3\17\3\17\3\20\6\20\u008a\n\20\r\20\16\20\u008b"+
		"\3\20\3\20\4_o\2\21\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21\3\2\6\4\2C\\c|\7\2//\62;C\\aac|\3\2\62;\4\2\13"+
		"\13\"\"\u0095\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\3!\3\2"+
		"\2\2\5*\3\2\2\2\7.\3\2\2\2\t:\3\2\2\2\13<\3\2\2\2\rA\3\2\2\2\17H\3\2\2"+
		"\2\21M\3\2\2\2\23V\3\2\2\2\25Y\3\2\2\2\27i\3\2\2\2\31w\3\2\2\2\33\177"+
		"\3\2\2\2\35\u0084\3\2\2\2\37\u0089\3\2\2\2!\"\7g\2\2\"#\7z\2\2#$\7v\2"+
		"\2$%\7g\2\2%&\7p\2\2&\'\7f\2\2\'(\7g\2\2()\7f\2\2)\4\3\2\2\2*+\7c\2\2"+
		"+,\7p\2\2,-\7{\2\2-\6\3\2\2\2./\7c\2\2/\60\7e\2\2\60\61\7e\2\2\61\62\7"+
		"g\2\2\62\63\7u\2\2\63\64\7u\2\2\64\65\7/\2\2\65\66\7n\2\2\66\67\7k\2\2"+
		"\678\7u\2\289\7v\2\29\b\3\2\2\2:;\7\60\2\2;\n\3\2\2\2<=\7f\2\2=>\7g\2"+
		"\2>?\7p\2\2?@\7{\2\2@\f\3\2\2\2AB\7r\2\2BC\7g\2\2CD\7t\2\2DE\7o\2\2EF"+
		"\7k\2\2FG\7v\2\2G\16\3\2\2\2HI\7j\2\2IJ\7q\2\2JK\7u\2\2KL\7v\2\2L\20\3"+
		"\2\2\2MN\7u\2\2NO\7v\2\2OP\7c\2\2PQ\7p\2\2QR\7f\2\2RS\7c\2\2ST\7t\2\2"+
		"TU\7f\2\2U\22\3\2\2\2VW\7g\2\2WX\7s\2\2X\24\3\2\2\2YZ\7\61\2\2Z[\7\61"+
		"\2\2[_\3\2\2\2\\^\13\2\2\2]\\\3\2\2\2^a\3\2\2\2_`\3\2\2\2_]\3\2\2\2`c"+
		"\3\2\2\2a_\3\2\2\2bd\7\17\2\2cb\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\7\f\2\2"+
		"fg\3\2\2\2gh\b\13\2\2h\26\3\2\2\2ij\7\61\2\2jk\7,\2\2ko\3\2\2\2ln\13\2"+
		"\2\2ml\3\2\2\2nq\3\2\2\2op\3\2\2\2om\3\2\2\2pr\3\2\2\2qo\3\2\2\2rs\7,"+
		"\2\2st\7\61\2\2tu\3\2\2\2uv\b\f\2\2v\30\3\2\2\2w{\t\2\2\2xz\t\3\2\2yx"+
		"\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\32\3\2\2\2}{\3\2\2\2~\u0080\t"+
		"\4\2\2\177~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\34\3\2\2\2\u0083\u0085\7\17\2\2\u0084\u0083\3\2\2\2\u0084"+
		"\u0085\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087\7\f\2\2\u0087\36\3\2\2"+
		"\2\u0088\u008a\t\5\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\20\2\2"+
		"\u008e \3\2\2\2\n\2_co{\u0081\u0084\u008b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}