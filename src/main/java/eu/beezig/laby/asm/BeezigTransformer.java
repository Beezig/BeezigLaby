package eu.beezig.laby.asm;

import net.labymod.core.asm.global.ClassEditor;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

@SuppressWarnings("unused")
public class BeezigTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.labymod.core_implementation.mc18.gui.ModPlayerTabOverlay")) {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(basicClass);
            reader.accept(node, 0);
            new BeezigTabOverlayVisitor().accept(name, node);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
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
