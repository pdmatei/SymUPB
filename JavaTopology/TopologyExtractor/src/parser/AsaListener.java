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
	 * Enter a parse tree produced by {@link AsaParser#static_src}.
	 * @param ctx the parse tree
	 */
	void enterStatic_src(@NotNull AsaParser.Static_srcContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#static_src}.
	 * @param ctx the parse tree
	 */
	void exitStatic_src(@NotNull AsaParser.Static_srcContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#statick}.
	 * @param ctx the parse tree
	 */
	void enterStatick(@NotNull AsaParser.StatickContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#statick}.
	 * @param ctx the parse tree
	 */
	void exitStatick(@NotNull AsaParser.StatickContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#nat_ip}.
	 * @param ctx the parse tree
	 */
	void enterNat_ip(@NotNull AsaParser.Nat_ipContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#nat_ip}.
	 * @param ctx the parse tree
	 */
	void exitNat_ip(@NotNull AsaParser.Nat_ipContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#interfaceSpec}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceSpec(@NotNull AsaParser.InterfaceSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#interfaceSpec}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceSpec(@NotNull AsaParser.InterfaceSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#configitem}.
	 * @param ctx the parse tree
	 */
	void enterConfigitem(@NotNull AsaParser.ConfigitemContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#configitem}.
	 * @param ctx the parse tree
	 */
	void exitConfigitem(@NotNull AsaParser.ConfigitemContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#global_int}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_int(@NotNull AsaParser.Global_intContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#global_int}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_int(@NotNull AsaParser.Global_intContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#interf_id}.
	 * @param ctx the parse tree
	 */
	void enterInterf_id(@NotNull AsaParser.Interf_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#interf_id}.
	 * @param ctx the parse tree
	 */
	void exitInterf_id(@NotNull AsaParser.Interf_idContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#ip_interface}.
	 * @param ctx the parse tree
	 */
	void enterIp_interface(@NotNull AsaParser.Ip_interfaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#ip_interface}.
	 * @param ctx the parse tree
	 */
	void exitIp_interface(@NotNull AsaParser.Ip_interfaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#has_ip_address}.
	 * @param ctx the parse tree
	 */
	void enterHas_ip_address(@NotNull AsaParser.Has_ip_addressContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#has_ip_address}.
	 * @param ctx the parse tree
	 */
	void exitHas_ip_address(@NotNull AsaParser.Has_ip_addressContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#nat_int}.
	 * @param ctx the parse tree
	 */
	void enterNat_int(@NotNull AsaParser.Nat_intContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#nat_int}.
	 * @param ctx the parse tree
	 */
	void exitNat_int(@NotNull AsaParser.Nat_intContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(@NotNull AsaParser.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(@NotNull AsaParser.DescriptionContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#global_ip}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_ip(@NotNull AsaParser.Global_ipContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#global_ip}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_ip(@NotNull AsaParser.Global_ipContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#nat_destination}.
	 * @param ctx the parse tree
	 */
	void enterNat_destination(@NotNull AsaParser.Nat_destinationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#nat_destination}.
	 * @param ctx the parse tree
	 */
	void exitNat_destination(@NotNull AsaParser.Nat_destinationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#static_mapped_ip}.
	 * @param ctx the parse tree
	 */
	void enterStatic_mapped_ip(@NotNull AsaParser.Static_mapped_ipContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#static_mapped_ip}.
	 * @param ctx the parse tree
	 */
	void exitStatic_mapped_ip(@NotNull AsaParser.Static_mapped_ipContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#interf}.
	 * @param ctx the parse tree
	 */
	void enterInterf(@NotNull AsaParser.InterfContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#interf}.
	 * @param ctx the parse tree
	 */
	void exitInterf(@NotNull AsaParser.InterfContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#nameif}.
	 * @param ctx the parse tree
	 */
	void enterNameif(@NotNull AsaParser.NameifContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#nameif}.
	 * @param ctx the parse tree
	 */
	void exitNameif(@NotNull AsaParser.NameifContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#securitylevel}.
	 * @param ctx the parse tree
	 */
	void enterSecuritylevel(@NotNull AsaParser.SecuritylevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#securitylevel}.
	 * @param ctx the parse tree
	 */
	void exitSecuritylevel(@NotNull AsaParser.SecuritylevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#global}.
	 * @param ctx the parse tree
	 */
	void enterGlobal(@NotNull AsaParser.GlobalContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#global}.
	 * @param ctx the parse tree
	 */
	void exitGlobal(@NotNull AsaParser.GlobalContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#has_securitylevel}.
	 * @param ctx the parse tree
	 */
	void enterHas_securitylevel(@NotNull AsaParser.Has_securitylevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#has_securitylevel}.
	 * @param ctx the parse tree
	 */
	void exitHas_securitylevel(@NotNull AsaParser.Has_securitylevelContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#has_nameif}.
	 * @param ctx the parse tree
	 */
	void enterHas_nameif(@NotNull AsaParser.Has_nameifContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#has_nameif}.
	 * @param ctx the parse tree
	 */
	void exitHas_nameif(@NotNull AsaParser.Has_nameifContext ctx);
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
	 * Enter a parse tree produced by {@link AsaParser#static_netmask}.
	 * @param ctx the parse tree
	 */
	void enterStatic_netmask(@NotNull AsaParser.Static_netmaskContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#static_netmask}.
	 * @param ctx the parse tree
	 */
	void exitStatic_netmask(@NotNull AsaParser.Static_netmaskContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#nat_mask}.
	 * @param ctx the parse tree
	 */
	void enterNat_mask(@NotNull AsaParser.Nat_maskContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#nat_mask}.
	 * @param ctx the parse tree
	 */
	void exitNat_mask(@NotNull AsaParser.Nat_maskContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#vlan}.
	 * @param ctx the parse tree
	 */
	void enterVlan(@NotNull AsaParser.VlanContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#vlan}.
	 * @param ctx the parse tree
	 */
	void exitVlan(@NotNull AsaParser.VlanContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#nat}.
	 * @param ctx the parse tree
	 */
	void enterNat(@NotNull AsaParser.NatContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#nat}.
	 * @param ctx the parse tree
	 */
	void exitNat(@NotNull AsaParser.NatContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link AsaParser#static_dst}.
	 * @param ctx the parse tree
	 */
	void enterStatic_dst(@NotNull AsaParser.Static_dstContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#static_dst}.
	 * @param ctx the parse tree
	 */
	void exitStatic_dst(@NotNull AsaParser.Static_dstContext ctx);
	/**
	 * Enter a parse tree produced by {@link AsaParser#static_real_ip}.
	 * @param ctx the parse tree
	 */
	void enterStatic_real_ip(@NotNull AsaParser.Static_real_ipContext ctx);
	/**
	 * Exit a parse tree produced by {@link AsaParser#static_real_ip}.
	 * @param ctx the parse tree
	 */
	void exitStatic_real_ip(@NotNull AsaParser.Static_real_ipContext ctx);
}