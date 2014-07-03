// Generated from C:\Users\Matei\Dropbox\papers\Costin\Symnet\SymUPB\JavaTopology\TopologyExtractor\src\grammar\Asa.g4 by ANTLR 4.x
package parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AsaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AsaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AsaParser#port}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPort(@NotNull AsaParser.PortContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#host}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHost(@NotNull AsaParser.HostContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#portid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPortid(@NotNull AsaParser.PortidContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#iprange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIprange(@NotNull AsaParser.IprangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#dst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDst(@NotNull AsaParser.DstContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#accesslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccesslist(@NotNull AsaParser.AccesslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#acext}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcext(@NotNull AsaParser.AcextContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#proto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProto(@NotNull AsaParser.ProtoContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#ip}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIp(@NotNull AsaParser.IpContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#acstd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcstd(@NotNull AsaParser.AcstdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#acname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcname(@NotNull AsaParser.AcnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#any}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny(@NotNull AsaParser.AnyContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAction(@NotNull AsaParser.ActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#config}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfig(@NotNull AsaParser.ConfigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#src}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSrc(@NotNull AsaParser.SrcContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#packet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPacket(@NotNull AsaParser.PacketContext ctx);
}