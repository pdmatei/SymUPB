// Generated from C:\Users\Matei\Dropbox\papers\Costin\Symnet\SymUPB\JavaTopology\TopologyExtractor\src\grammar\Asa.g4 by ANTLR 4.x
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AsaParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 
		LINE_COMMENT=10, COMMENT=11, TOKEN=12, NUMBER=13, NEWLINE=14, WS=15;
	public static final String[] tokenNames = {
		"<INVALID>", "'extended'", "'any'", "'access-list'", "'.'", "'deny'", 
		"'permit'", "'host'", "'standard'", "'eq'", "LINE_COMMENT", "COMMENT", 
		"TOKEN", "NUMBER", "NEWLINE", "WS"
	};
	public static final int
		RULE_config = 0, RULE_accesslist = 1, RULE_acstd = 2, RULE_acext = 3, 
		RULE_acname = 4, RULE_action = 5, RULE_proto = 6, RULE_src = 7, RULE_dst = 8, 
		RULE_packet = 9, RULE_any = 10, RULE_host = 11, RULE_ip = 12, RULE_iprange = 13, 
		RULE_port = 14, RULE_portid = 15;
	public static final String[] ruleNames = {
		"config", "accesslist", "acstd", "acext", "acname", "action", "proto", 
		"src", "dst", "packet", "any", "host", "ip", "iprange", "port", "portid"
	};

	@Override
	public String getGrammarFileName() { return "Asa.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AsaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ConfigContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(AsaParser.EOF, 0); }
		public AccesslistContext accesslist(int i) {
			return getRuleContext(AccesslistContext.class,i);
		}
		public List<AccesslistContext> accesslist() {
			return getRuleContexts(AccesslistContext.class);
		}
		public ConfigContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_config; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterConfig(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitConfig(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitConfig(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfigContext config() throws RecognitionException {
		ConfigContext _localctx = new ConfigContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_config);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=1 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(32); accesslist();
					}
					} 
				}
				setState(37);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(38); match(EOF);
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

	public static class AccesslistContext extends ParserRuleContext {
		public AcstdContext acstd() {
			return getRuleContext(AcstdContext.class,0);
		}
		public AcnameContext acname() {
			return getRuleContext(AcnameContext.class,0);
		}
		public AcextContext acext() {
			return getRuleContext(AcextContext.class,0);
		}
		public AccesslistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accesslist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterAccesslist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitAccesslist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitAccesslist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AccesslistContext accesslist() throws RecognitionException {
		AccesslistContext _localctx = new AccesslistContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_accesslist);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40); match(T__6);
			setState(41); acname();
			setState(44);
			switch (_input.LA(1)) {
			case T__1:
				{
				setState(42); acstd();
				}
				break;
			case T__8:
				{
				setState(43); acext();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class AcstdContext extends ParserRuleContext {
		public SrcContext src() {
			return getRuleContext(SrcContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public AcstdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acstd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterAcstd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitAcstd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitAcstd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AcstdContext acstd() throws RecognitionException {
		AcstdContext _localctx = new AcstdContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_acstd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46); match(T__1);
			setState(47); action();
			setState(48); src();
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

	public static class AcextContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(AsaParser.NEWLINE, 0); }
		public DstContext dst() {
			return getRuleContext(DstContext.class,0);
		}
		public SrcContext src() {
			return getRuleContext(SrcContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ProtoContext proto() {
			return getRuleContext(ProtoContext.class,0);
		}
		public AcextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acext; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterAcext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitAcext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitAcext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AcextContext acext() throws RecognitionException {
		AcextContext _localctx = new AcextContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_acext);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50); match(T__8);
			setState(51); action();
			setState(52); proto();
			setState(53); src();
			setState(54); dst();
			setState(55); match(NEWLINE);
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

	public static class AcnameContext extends ParserRuleContext {
		public TerminalNode TOKEN() { return getToken(AsaParser.TOKEN, 0); }
		public AcnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterAcname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitAcname(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitAcname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AcnameContext acname() throws RecognitionException {
		AcnameContext _localctx = new AcnameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_acname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57); match(TOKEN);
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

	public static class ActionContext extends ParserRuleContext {
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			_la = _input.LA(1);
			if ( !(_la==T__4 || _la==T__3) ) {
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

	public static class ProtoContext extends ParserRuleContext {
		public TerminalNode TOKEN() { return getToken(AsaParser.TOKEN, 0); }
		public ProtoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_proto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterProto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitProto(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitProto(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProtoContext proto() throws RecognitionException {
		ProtoContext _localctx = new ProtoContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_proto);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61); match(TOKEN);
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
		public PacketContext packet() {
			return getRuleContext(PacketContext.class,0);
		}
		public SrcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_src; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterSrc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitSrc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitSrc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SrcContext src() throws RecognitionException {
		SrcContext _localctx = new SrcContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_src);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63); packet();
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
		public PacketContext packet() {
			return getRuleContext(PacketContext.class,0);
		}
		public DstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterDst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitDst(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitDst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DstContext dst() throws RecognitionException {
		DstContext _localctx = new DstContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_dst);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); packet();
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

	public static class PacketContext extends ParserRuleContext {
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public HostContext host() {
			return getRuleContext(HostContext.class,0);
		}
		public IprangeContext iprange() {
			return getRuleContext(IprangeContext.class,0);
		}
		public AnyContext any() {
			return getRuleContext(AnyContext.class,0);
		}
		public PacketContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterPacket(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitPacket(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitPacket(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PacketContext packet() throws RecognitionException {
		PacketContext _localctx = new PacketContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_packet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(67); any();
				}
				break;
			case T__2:
				{
				setState(68); host();
				}
				break;
			case NUMBER:
				{
				setState(69); iprange();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(73);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(72); port();
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

	public static class AnyContext extends ParserRuleContext {
		public AnyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterAny(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitAny(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitAny(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnyContext any() throws RecognitionException {
		AnyContext _localctx = new AnyContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_any);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); match(T__7);
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

	public static class HostContext extends ParserRuleContext {
		public IpContext ip() {
			return getRuleContext(IpContext.class,0);
		}
		public HostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_host; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterHost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitHost(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitHost(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HostContext host() throws RecognitionException {
		HostContext _localctx = new HostContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_host);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); match(T__2);
			setState(78); ip();
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

	public static class IpContext extends ParserRuleContext {
		public TerminalNode NUMBER(int i) {
			return getToken(AsaParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(AsaParser.NUMBER); }
		public IpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ip; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterIp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitIp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitIp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IpContext ip() throws RecognitionException {
		IpContext _localctx = new IpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ip);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80); match(NUMBER);
			setState(81); match(T__5);
			setState(82); match(NUMBER);
			setState(83); match(T__5);
			setState(84); match(NUMBER);
			setState(85); match(T__5);
			setState(86); match(NUMBER);
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

	public static class IprangeContext extends ParserRuleContext {
		public IpContext ip(int i) {
			return getRuleContext(IpContext.class,i);
		}
		public List<IpContext> ip() {
			return getRuleContexts(IpContext.class);
		}
		public IprangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iprange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterIprange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitIprange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitIprange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IprangeContext iprange() throws RecognitionException {
		IprangeContext _localctx = new IprangeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_iprange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88); ip();
			setState(89); ip();
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
		public PortidContext portid() {
			return getRuleContext(PortidContext.class,0);
		}
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitPort(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91); match(T__0);
			setState(92); portid();
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

	public static class PortidContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(AsaParser.NUMBER, 0); }
		public TerminalNode TOKEN() { return getToken(AsaParser.TOKEN, 0); }
		public PortidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).enterPortid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AsaListener ) ((AsaListener)listener).exitPortid(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AsaVisitor ) return ((AsaVisitor<? extends T>)visitor).visitPortid(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortidContext portid() throws RecognitionException {
		PortidContext _localctx = new PortidContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_portid);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_la = _input.LA(1);
			if ( !(_la==TOKEN || _la==NUMBER) ) {
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\21c\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\7\2$\n\2\f"+
		"\2\16\2\'\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3/\n\3\3\4\3\4\3\4\3\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3"+
		"\13\3\13\5\13I\n\13\3\13\5\13L\n\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3%\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\4\3\2\7\b\3\2\16\17"+
		"W\2%\3\2\2\2\4*\3\2\2\2\6\60\3\2\2\2\b\64\3\2\2\2\n;\3\2\2\2\f=\3\2\2"+
		"\2\16?\3\2\2\2\20A\3\2\2\2\22C\3\2\2\2\24H\3\2\2\2\26M\3\2\2\2\30O\3\2"+
		"\2\2\32R\3\2\2\2\34Z\3\2\2\2\36]\3\2\2\2 `\3\2\2\2\"$\5\4\3\2#\"\3\2\2"+
		"\2$\'\3\2\2\2%&\3\2\2\2%#\3\2\2\2&(\3\2\2\2\'%\3\2\2\2()\7\2\2\3)\3\3"+
		"\2\2\2*+\7\5\2\2+.\5\n\6\2,/\5\6\4\2-/\5\b\5\2.,\3\2\2\2.-\3\2\2\2/\5"+
		"\3\2\2\2\60\61\7\n\2\2\61\62\5\f\7\2\62\63\5\20\t\2\63\7\3\2\2\2\64\65"+
		"\7\3\2\2\65\66\5\f\7\2\66\67\5\16\b\2\678\5\20\t\289\5\22\n\29:\7\20\2"+
		"\2:\t\3\2\2\2;<\7\16\2\2<\13\3\2\2\2=>\t\2\2\2>\r\3\2\2\2?@\7\16\2\2@"+
		"\17\3\2\2\2AB\5\24\13\2B\21\3\2\2\2CD\5\24\13\2D\23\3\2\2\2EI\5\26\f\2"+
		"FI\5\30\r\2GI\5\34\17\2HE\3\2\2\2HF\3\2\2\2HG\3\2\2\2IK\3\2\2\2JL\5\36"+
		"\20\2KJ\3\2\2\2KL\3\2\2\2L\25\3\2\2\2MN\7\4\2\2N\27\3\2\2\2OP\7\t\2\2"+
		"PQ\5\32\16\2Q\31\3\2\2\2RS\7\17\2\2ST\7\6\2\2TU\7\17\2\2UV\7\6\2\2VW\7"+
		"\17\2\2WX\7\6\2\2XY\7\17\2\2Y\33\3\2\2\2Z[\5\32\16\2[\\\5\32\16\2\\\35"+
		"\3\2\2\2]^\7\13\2\2^_\5 \21\2_\37\3\2\2\2`a\t\3\2\2a!\3\2\2\2\6%.HK";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}