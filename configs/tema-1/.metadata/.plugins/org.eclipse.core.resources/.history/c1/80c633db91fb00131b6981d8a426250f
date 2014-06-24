// Generated from ACL.g by ANTLR 4.1
package org.doiunusapte.parser.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ACLParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, STRING=7, NUMBER=8, ANYTHING=9, 
		NEWLINE=10, WS=11;
	public static final String[] tokenNames = {
		"<INVALID>", "'extended'", "'interface'", "'access-list'", "'deny'", "'permit'", 
		"'host'", "STRING", "NUMBER", "ANYTHING", "'\n'", "WS"
	};
	public static final int
		RULE_configFile = 0, RULE_line = 1, RULE_aclextended = 2, RULE_srcinterface = 3, 
		RULE_dstinterface = 4, RULE_id = 5, RULE_protocol = 6, RULE_src = 7, RULE_dst = 8, 
		RULE_mask = 9, RULE_port = 10;
	public static final String[] ruleNames = {
		"configFile", "line", "aclextended", "srcinterface", "dstinterface", "id", 
		"protocol", "src", "dst", "mask", "port"
	};

	@Override
	public String getGrammarFileName() { return "ACL.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public ACLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ConfigFileContext extends ParserRuleContext {
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public ConfigFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configFile; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitConfigFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfigFileContext configFile() throws RecognitionException {
		ConfigFileContext _localctx = new ConfigFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_configFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(22); line();
				}
				}
				setState(25); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==3 || _la==NEWLINE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(ACLParser.NEWLINE, 0); }
		public AclextendedContext aclextended() {
			return getRuleContext(AclextendedContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		try {
			setState(29);
			switch (_input.LA(1)) {
			case 3:
				enterOuterAlt(_localctx, 1);
				{
				setState(27); aclextended();
				}
				break;
			case NEWLINE:
				enterOuterAlt(_localctx, 2);
				{
				setState(28); match(NEWLINE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AclextendedContext extends ParserRuleContext {
		public List<PortContext> port() {
			return getRuleContexts(PortContext.class);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public MaskContext mask() {
			return getRuleContext(MaskContext.class,0);
		}
		public DstContext dst(int i) {
			return getRuleContext(DstContext.class,i);
		}
		public List<DstContext> dst() {
			return getRuleContexts(DstContext.class);
		}
		public DstinterfaceContext dstinterface() {
			return getRuleContext(DstinterfaceContext.class,0);
		}
		public ProtocolContext protocol() {
			return getRuleContext(ProtocolContext.class,0);
		}
		public SrcContext src() {
			return getRuleContext(SrcContext.class,0);
		}
		public SrcinterfaceContext srcinterface() {
			return getRuleContext(SrcinterfaceContext.class,0);
		}
		public PortContext port(int i) {
			return getRuleContext(PortContext.class,i);
		}
		public AclextendedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aclextended; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitAclextended(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AclextendedContext aclextended() throws RecognitionException {
		AclextendedContext _localctx = new AclextendedContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_aclextended);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31); match(3);
			setState(32); id();
			setState(33); match(1);
			setState(34);
			_la = _input.LA(1);
			if ( !(_la==4 || _la==5) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(36);
			_la = _input.LA(1);
			if (_la==STRING || _la==NUMBER) {
				{
				setState(35); protocol();
				}
			}

			setState(40);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(38); match(2);
				setState(39); srcinterface();
				}
				break;
			}
			setState(53);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(42); match(6);
				setState(43); src();
				setState(45);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(44); port();
					}
					break;
				}
				setState(51);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(47); dst();
					setState(49);
					_la = _input.LA(1);
					if (_la==STRING) {
						{
						setState(48); mask();
						}
					}

					}
				}

				}
				break;
			}
			setState(57);
			_la = _input.LA(1);
			if (_la==2) {
				{
				setState(55); match(2);
				setState(56); dstinterface();
				}
			}

			setState(64);
			_la = _input.LA(1);
			if (_la==6) {
				{
				setState(59); match(6);
				setState(60); dst();
				setState(62);
				_la = _input.LA(1);
				if (_la==STRING || _la==NUMBER) {
					{
					setState(61); port();
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SrcinterfaceContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public SrcinterfaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_srcinterface; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitSrcinterface(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SrcinterfaceContext srcinterface() throws RecognitionException {
		SrcinterfaceContext _localctx = new SrcinterfaceContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_srcinterface);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66); id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DstinterfaceContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public DstinterfaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dstinterface; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitDstinterface(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DstinterfaceContext dstinterface() throws RecognitionException {
		DstinterfaceContext _localctx = new DstinterfaceContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_dstinterface);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(ACLParser.STRING, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProtocolContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(ACLParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(ACLParser.STRING, 0); }
		public ProtocolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_protocol; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitProtocol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProtocolContext protocol() throws RecognitionException {
		ProtocolContext _localctx = new ProtocolContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_protocol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_la = _input.LA(1);
			if ( !(_la==STRING || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SrcContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(ACLParser.STRING, 0); }
		public SrcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_src; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitSrc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SrcContext src() throws RecognitionException {
		SrcContext _localctx = new SrcContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_src);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DstContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(ACLParser.STRING, 0); }
		public DstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dst; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitDst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DstContext dst() throws RecognitionException {
		DstContext _localctx = new DstContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_dst);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MaskContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(ACLParser.STRING, 0); }
		public MaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mask; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitMask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MaskContext mask() throws RecognitionException {
		MaskContext _localctx = new MaskContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_mask);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(ACLParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(ACLParser.STRING, 0); }
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ACLVisitor ) return ((ACLVisitor<? extends T>)visitor).visitPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_port);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_la = _input.LA(1);
			if ( !(_la==STRING || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\rU\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\6\2\32\n\2\r\2\16\2\33\3\3\3\3\5\3 \n\3\3\4\3\4\3\4\3\4\3\4"+
		"\5\4\'\n\4\3\4\3\4\5\4+\n\4\3\4\3\4\3\4\5\4\60\n\4\3\4\3\4\5\4\64\n\4"+
		"\5\4\66\n\4\5\48\n\4\3\4\3\4\5\4<\n\4\3\4\3\4\3\4\5\4A\n\4\5\4C\n\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\2"+
		"\r\2\4\6\b\n\f\16\20\22\24\26\2\4\3\2\6\7\3\2\t\nT\2\31\3\2\2\2\4\37\3"+
		"\2\2\2\6!\3\2\2\2\bD\3\2\2\2\nF\3\2\2\2\fH\3\2\2\2\16J\3\2\2\2\20L\3\2"+
		"\2\2\22N\3\2\2\2\24P\3\2\2\2\26R\3\2\2\2\30\32\5\4\3\2\31\30\3\2\2\2\32"+
		"\33\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\3\3\2\2\2\35 \5\6\4\2\36 \7"+
		"\f\2\2\37\35\3\2\2\2\37\36\3\2\2\2 \5\3\2\2\2!\"\7\5\2\2\"#\5\f\7\2#$"+
		"\7\3\2\2$&\t\2\2\2%\'\5\16\b\2&%\3\2\2\2&\'\3\2\2\2\'*\3\2\2\2()\7\4\2"+
		"\2)+\5\b\5\2*(\3\2\2\2*+\3\2\2\2+\67\3\2\2\2,-\7\b\2\2-/\5\20\t\2.\60"+
		"\5\26\f\2/.\3\2\2\2/\60\3\2\2\2\60\65\3\2\2\2\61\63\5\22\n\2\62\64\5\24"+
		"\13\2\63\62\3\2\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\61\3\2\2\2\65\66\3"+
		"\2\2\2\668\3\2\2\2\67,\3\2\2\2\678\3\2\2\28;\3\2\2\29:\7\4\2\2:<\5\n\6"+
		"\2;9\3\2\2\2;<\3\2\2\2<B\3\2\2\2=>\7\b\2\2>@\5\22\n\2?A\5\26\f\2@?\3\2"+
		"\2\2@A\3\2\2\2AC\3\2\2\2B=\3\2\2\2BC\3\2\2\2C\7\3\2\2\2DE\5\f\7\2E\t\3"+
		"\2\2\2FG\5\f\7\2G\13\3\2\2\2HI\7\t\2\2I\r\3\2\2\2JK\t\3\2\2K\17\3\2\2"+
		"\2LM\7\t\2\2M\21\3\2\2\2NO\7\t\2\2O\23\3\2\2\2PQ\7\t\2\2Q\25\3\2\2\2R"+
		"S\t\3\2\2S\27\3\2\2\2\r\33\37&*/\63\65\67;@B";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}