package kr.ms.core.version.nms.tank;

import lombok.Getter;
import kr.ms.core.version.nms.wrapper.NBTTagCompoundWrapper;

import java.lang.reflect.Method;

public class NmsNbtTagCompoundUtil {

    @Getter private Class<?> NBTTagCompound;
    @Getter private Method getStringMethod;
    @Getter private Method setStringMethod;

    NmsNbtTagCompoundUtil(
            String nbtTagCompoundClassName
    ) throws ClassNotFoundException, NoSuchMethodException {
        try {
            NBTTagCompound = Class.forName(nbtTagCompoundClassName); } catch (Exception ignored) {
            NBTTagCompound = Class.forName("net.minecraft.nbt.NBTTagCompound");
        }
        try { getStringMethod = NBTTagCompound.getDeclaredMethod("getString", String.class); }
        catch (Exception e) {getStringMethod = NBTTagCompound.getDeclaredMethod("l", String.class); }
        try { setStringMethod = NBTTagCompound.getDeclaredMethod("setString", String.class, String.class); }
        catch (Exception e) { setStringMethod = NBTTagCompound.getDeclaredMethod("a", String.class, String.class); }
    }

    /**
     * NMS 의 NBTTagCompound 를 생성해줍니다.
     * @return NBTTagCompoundWrapper
     */
    public NBTTagCompoundWrapper newInstance() {
        try {
            return new NBTTagCompoundWrapper(NBTTagCompound.newInstance(), this);
        } catch (Exception e) { return null; }
    }

}
