/*
 * Copyright (C) 2019 Beezig (RoccoDev, ItsNiklass)
 *
 * This file is part of BeezigLaby.
 *
 * BeezigLaby is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigLaby is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigLaby.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.laby.asm;

import net.labymod.core.asm.global.ClassEditor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class BeezigChatVisitor extends ClassEditor {

    public BeezigChatVisitor() {
        super(ClassEditorType.CLASS_NODE);
    }

    @Override
    public void accept(String name, ClassNode node) {
        for(MethodNode method : node.methods) {
            if("a".equals(method.name) && "(Lfy;)V".equals(method.desc)) {
                InsnList list = new InsnList();
                LabelNode label = new LabelNode();
                for(AbstractInsnNode instruction : method.instructions.toArray()) {
                    if(instruction instanceof MethodInsnNode) {
                        MethodInsnNode inst = (MethodInsnNode) instruction;
                        if("avt".equals(inst.owner) && "a".equals(inst.name)) { // GuiNewChat.printChatMessage
                            addInsns(list, label);
                        }
                    }
                    list.add(instruction);
                }
                list.add(label);
                list.add(new InsnNode(Opcodes.RETURN));
                method.instructions.clear();
                method.instructions.insert(list);
            }
        }
    }

    private void addInsns(InsnList list, LabelNode label) {
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                "eu/beezig/laby/evt/LabyEventListener", "onChatMessage", "(Lfy;)Z"));
        list.add(new JumpInsnNode(Opcodes.IFNE, label));
    }
}
