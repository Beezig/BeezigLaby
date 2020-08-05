package eu.beezig.laby.asm;

import com.mojang.authlib.GameProfile;
import eu.beezig.forge.badge.BadgeRenderer;
import net.labymod.core.asm.global.ClassEditor;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

public class BeezigTransformer implements IClassTransformer {

    private enum Mapping {
        CLASS_NAME("net/labymod/core_implementation/mc18/gui/ModPlayerTabOverlay","net/labymod/core_implementation/mc18/gui/ModPlayerTabOverlay"),
        METHOD_NAME("newTabOverlay","newTabOverlay"),
        METHOD_DESC("(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V", "(ILauo;Lauk;)V"),
        NETWORK_PLAYER_INFO_DESC("net/minecraft/client/network/NetworkPlayerInfo", "bdc"),
        GAME_PROFILE_METHOD_NAME("getGameProfile", "a"),
        GAME_PROFILE_METHOD_DESC("()Lcom/mojang/authlib/GameProfile;", "()Lcom/mojang/authlib/GameProfile;");

        String plainName;
        String obfName;

        Mapping (String plainName, String obfName){
            this.plainName = plainName;
            this.obfName = obfName;
        }

        String get(boolean obfuscated) {
            return obfuscated ? obfName : plainName;
        }

        // shorthand for string comparison
        boolean equalsString(String in, boolean obfuscated) {
            return in.equals(get(obfuscated));
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.labymod.core_implementation.mc18.gui.ModPlayerTabOverlay")) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            boolean obf = false;
            try {
                //noinspection unchecked
                for (MethodNode m : (List<MethodNode>) classNode.methods) {
                    // If one method is called "a", we can safely assume that Minecraft's obfuscated
                    if (m.name.equals("a"))
                        obf = true;
                    if (Mapping.METHOD_NAME.equalsString(m.name, obf) && Mapping.METHOD_DESC.equalsString(m.desc, obf)) {
                        for (@SuppressWarnings("unchecked") ListIterator<AbstractInsnNode> it = m.instructions.iterator(); it.hasNext(); ) {
                            AbstractInsnNode n = it.next();
                            // Find our entry point
                            // Add necessary space
                            if (n.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                                    ((MethodInsnNode) n).owner.equals(Mapping.CLASS_NAME.get(obf))) {
                                // Skip two nodes
                                it.next();
                                n = it.next();
                                if (n.getOpcode() == Opcodes.ISTORE && ((VarInsnNode) n).var == 14)
                                {
                                    // Find the next label
                                    AbstractInsnNode label = n;
                                    while (!(label instanceof LabelNode)) {
                                        label = label.getNext();
                                    }
                                    InsnList list = new InsnList();
                                    list.add(new VarInsnNode(Opcodes.ALOAD, 13));
                                    list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                            Mapping.NETWORK_PLAYER_INFO_DESC.get(obf),
                                            Mapping.GAME_PROFILE_METHOD_NAME.get(obf),
                                            Mapping.GAME_PROFILE_METHOD_DESC.get(obf)));
                                    list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                            Type.getInternalName(GameProfile.class),
                                            "getId", Type.getMethodDescriptor(GameProfile.class.getMethod("getId"))));
                                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(BadgeRenderer.class),
                                            "shouldRenderTabBadge",
                                            Type.getMethodDescriptor(BadgeRenderer.class.getMethod("shouldRenderTabBadge",
                                                    UUID.class))));
                                    list.add(new JumpInsnNode(Opcodes.IFEQ, (LabelNode) label));
                                    list.add(new IincInsnNode(14, 10));
                                    m.instructions.insert(n, list);
                                }
                            }
                            // Render the badge
                            if (n.getOpcode() == Opcodes.IADD && (n = it.next()) != null
                                    && n.getOpcode() == Opcodes.I2F &&
                                    n.getNext().getOpcode() == Opcodes.ILOAD && ((VarInsnNode) n.getNext()).var == 27) {
                                InsnList list = new InsnList();
                                list.add(new InsnNode(Opcodes.DUP));
                                list.add(new VarInsnNode(Opcodes.ALOAD, 28));
                                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Mapping.NETWORK_PLAYER_INFO_DESC.get(obf),
                                        Mapping.GAME_PROFILE_METHOD_NAME.get(obf), Mapping.GAME_PROFILE_METHOD_DESC.get(obf)));
                                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                        Type.getInternalName(GameProfile.class),
                                        "getId", Type.getMethodDescriptor(GameProfile.class.getMethod("getId"))));
                                list.add(new InsnNode(Opcodes.SWAP));
                                list.add(new InsnNode(Opcodes.I2F));
                                list.add(new InsnNode(Opcodes.F2D));
                                list.add(new VarInsnNode(Opcodes.ILOAD, 27));
                                list.add(new InsnNode(Opcodes.I2F));
                                list.add(new InsnNode(Opcodes.F2D));
                                list.add(new LdcInsnNode(8.0D));
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(BadgeRenderer.class),
                                        "renderBadge",
                                        Type.getMethodDescriptor(BadgeRenderer.class.getMethod("renderBadge",
                                                UUID.class, double.class, double.class, double.class))));
                                list.add(new InsnNode(Opcodes.IADD));
                                m.instructions.insertBefore(n, list);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }

        if("bcy".equals(name)) {
            System.out.println("[BeezigLaby-ASM] Transforming bcy (NetHandlerPlayClient)");
            ClassEditor editor = new BeezigChatVisitor();
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(basicClass);
            reader.accept(node, 0);
            editor.accept(name, node);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
        }

        return basicClass;
    }
}
