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
	 * Visit a parse tree produced by {@link AsaParser#static_src}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatic_src(@NotNull AsaParser.Static_srcContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#statick}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatick(@NotNull AsaParser.StatickContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#nat_ip}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNat_ip(@NotNull AsaParser.Nat_ipContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#interfaceSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceSpec(@NotNull AsaParser.InterfaceSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#configitem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfigitem(@NotNull AsaParser.ConfigitemContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#portid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPortid(@NotNull AsaParser.PortidContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#global_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_int(@NotNull AsaParser.Global_intContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#interf_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterf_id(@NotNull AsaParser.Interf_idContext ctx);
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
	 * Visit a parse tree produced by {@link AsaParser#acext}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcext(@NotNull AsaParser.AcextContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#ip_interface}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIp_interface(@NotNull AsaParser.Ip_interfaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#has_ip_address}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHas_ip_address(@NotNull AsaParser.Has_ip_addressContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#acstd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcstd(@NotNull AsaParser.AcstdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#nat_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNat_int(@NotNull AsaParser.Nat_intContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#description}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescription(@NotNull AsaParser.DescriptionContext ctx);
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
	 * Visit a parse tree produced by {@link AsaParser#global_ip}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_ip(@NotNull AsaParser.Global_ipContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#nat_destination}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNat_destination(@NotNull AsaParser.Nat_destinationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#static_mapped_ip}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatic_mapped_ip(@NotNull AsaParser.Static_mapped_ipContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#interf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterf(@NotNull AsaParser.InterfContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#host}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHost(@NotNull AsaParser.HostContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#nameif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameif(@NotNull AsaParser.NameifContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#securitylevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecuritylevel(@NotNull AsaParser.SecuritylevelContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal(@NotNull AsaParser.GlobalContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#has_securitylevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHas_securitylevel(@NotNull AsaParser.Has_securitylevelContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#proto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProto(@NotNull AsaParser.ProtoContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#accesslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccesslist(@NotNull AsaParser.AccesslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#ip}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIp(@NotNull AsaParser.IpContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#has_nameif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHas_nameif(@NotNull AsaParser.Has_nameifContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#acname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcname(@NotNull AsaParser.AcnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#static_netmask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatic_netmask(@NotNull AsaParser.Static_netmaskContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#nat_mask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNat_mask(@NotNull AsaParser.Nat_maskContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#vlan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVlan(@NotNull AsaParser.VlanContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#nat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNat(@NotNull AsaParser.NatContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#packet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPacket(@NotNull AsaParser.PacketContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#static_dst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatic_dst(@NotNull AsaParser.Static_dstContext ctx);
	/**
	 * Visit a parse tree produced by {@link AsaParser#static_real_ip}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatic_real_ip(@NotNull AsaParser.Static_real_ipContext ctx);
}