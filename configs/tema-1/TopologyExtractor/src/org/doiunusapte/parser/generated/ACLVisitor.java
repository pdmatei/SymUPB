// Generated from ACL.g by ANTLR 4.1
package org.doiunusapte.parser.generated;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ACLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ACLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ACLParser#port}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPort(@NotNull ACLParser.PortContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(@NotNull ACLParser.IdContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#dstinterface}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDstinterface(@NotNull ACLParser.DstinterfaceContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#protocol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProtocol(@NotNull ACLParser.ProtocolContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#srcinterface}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSrcinterface(@NotNull ACLParser.SrcinterfaceContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#configFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfigFile(@NotNull ACLParser.ConfigFileContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#mask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMask(@NotNull ACLParser.MaskContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(@NotNull ACLParser.LineContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#dst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDst(@NotNull ACLParser.DstContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#src}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSrc(@NotNull ACLParser.SrcContext ctx);

	/**
	 * Visit a parse tree produced by {@link ACLParser#aclextended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAclextended(@NotNull ACLParser.AclextendedContext ctx);
}