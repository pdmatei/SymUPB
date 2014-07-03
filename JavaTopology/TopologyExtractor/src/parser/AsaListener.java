// Generated from C:\Users\Matei\Dropbox\papers\Costin\Symnet\SymUPB\JavaTopology\TopologyExtractor\src\grammar\Asa.g4 by ANTLR 4.x
package parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AsaParser}.
 */
public interface AsaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AsaParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(@NotNull AsaParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(@NotNull AsaParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#host}.
	 * @param ctx the parse tree
	 */
	void enterHost(@NotNull AsaParser.HostContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#host}.
	 * @param ctx the parse tree
	 */
	void exitHost(@NotNull AsaParser.HostContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#portid}.
	 * @param ctx the parse tree
	 */
	void enterPortid(@NotNull AsaParser.PortidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#portid}.
	 * @param ctx the parse tree
	 */
	void exitPortid(@NotNull AsaParser.PortidContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#iprange}.
	 * @param ctx the parse tree
	 */
	void enterIprange(@NotNull AsaParser.IprangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#iprange}.
	 * @param ctx the parse tree
	 */
	void exitIprange(@NotNull AsaParser.IprangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#dst}.
	 * @param ctx the parse tree
	 */
	void enterDst(@NotNull AsaParser.DstContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#dst}.
	 * @param ctx the parse tree
	 */
	void exitDst(@NotNull AsaParser.DstContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#accesslist}.
	 * @param ctx the parse tree
	 */
	void enterAccesslist(@NotNull AsaParser.AccesslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#accesslist}.
	 * @param ctx the parse tree
	 */
	void exitAccesslist(@NotNull AsaParser.AccesslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#acext}.
	 * @param ctx the parse tree
	 */
	void enterAcext(@NotNull AsaParser.AcextContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#acext}.
	 * @param ctx the parse tree
	 */
	void exitAcext(@NotNull AsaParser.AcextContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#proto}.
	 * @param ctx the parse tree
	 */
	void enterProto(@NotNull AsaParser.ProtoContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#proto}.
	 * @param ctx the parse tree
	 */
	void exitProto(@NotNull AsaParser.ProtoContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#ip}.
	 * @param ctx the parse tree
	 */
	void enterIp(@NotNull AsaParser.IpContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#ip}.
	 * @param ctx the parse tree
	 */
	void exitIp(@NotNull AsaParser.IpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#acstd}.
	 * @param ctx the parse tree
	 */
	void enterAcstd(@NotNull AsaParser.AcstdContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#acstd}.
	 * @param ctx the parse tree
	 */
	void exitAcstd(@NotNull AsaParser.AcstdContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#acname}.
	 * @param ctx the parse tree
	 */
	void enterAcname(@NotNull AsaParser.AcnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#acname}.
	 * @param ctx the parse tree
	 */
	void exitAcname(@NotNull AsaParser.AcnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#any}.
	 * @param ctx the parse tree
	 */
	void enterAny(@NotNull AsaParser.AnyContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#any}.
	 * @param ctx the parse tree
	 */
	void exitAny(@NotNull AsaParser.AnyContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(@NotNull AsaParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(@NotNull AsaParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#config}.
	 * @param ctx the parse tree
	 */
	void enterConfig(@NotNull AsaParser.ConfigContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#config}.
	 * @param ctx the parse tree
	 */
	void exitConfig(@NotNull AsaParser.ConfigContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#src}.
	 * @param ctx the parse tree
	 */
	void enterSrc(@NotNull AsaParser.SrcContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#src}.
	 * @param ctx the parse tree
	 */
	void exitSrc(@NotNull AsaParser.SrcContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#packet}.
	 * @param ctx the parse tree
	 */
	void enterPacket(@NotNull AsaParser.PacketContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#packet}.
	 * @param ctx the parse tree
	 */
	void exitPacket(@NotNull AsaParser.PacketContext ctx);
}