/*
 * Copyright (C) 2017-2020 Beezig Team
 *
 * This file is part of Beezig.
 *
 * Beezig is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beezig is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Beezig.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.laby.asm;

import com.mojang.authlib.GameProfile;
import eu.beezig.forge.badge.BadgeRenderer;
import net.labymod.core.asm.global.ClassEditor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;
import java.util.UUID;

public class BeezigTabOverlayVisitor extends ClassEditor {

    public BeezigTabOverlayVisitor() {
        super(ClassEditorType.CLASS_NODE);
    }

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
    public void accept(String name, ClassNode node) {
        boolean obf = false;
        try {
            for (MethodNode m : node.methods) {
                // If one method is called "a", we can safely assume that Minecraft's obfuscated
                if (m.name.equals("a"))
                    obf = true;
                if (Mapping.METHOD_NAME.equalsString(m.name, obf) && Mapping.METHOD_DESC.equalsString(m.desc, obf)) {
                    for (ListIterator<AbstractInsnNode> it = m.instructions.iterator(); it.hasNext(); ) {
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
                                        Mapping.GAME_PROFILE_METHOD_DESC.get(obf), false));
                                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                        Type.getInternalName(GameProfile.class),
                                        "getId", Type.getMethodDescriptor(GameProfile.class.getMethod("getId")), false));
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(BadgeRenderer.class),
                                        "shouldRenderTabBadge",
                                        Type.getMethodDescriptor(BadgeRenderer.class.getMethod("shouldRenderTabBadge",
                                                UUID.class)), false));
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
                                    Mapping.GAME_PROFILE_METHOD_NAME.get(obf), Mapping.GAME_PROFILE_METHOD_DESC.get(obf), false));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                    Type.getInternalName(GameProfile.class),
                                    "getId", Type.getMethodDescriptor(GameProfile.class.getMethod("getId")), false));
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
                                            UUID.class, double.class, double.class, double.class)), false));
                            list.add(new InsnNode(Opcodes.IADD));
                            m.instructions.insertBefore(n, list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
