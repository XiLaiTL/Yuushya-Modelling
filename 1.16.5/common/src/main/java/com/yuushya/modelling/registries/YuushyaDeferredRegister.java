package com.yuushya.modelling.registries;

import com.google.common.base.Suppliers;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.Registries;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.yuushya.modelling.Yuushya.MOD_ID;
import static com.yuushya.modelling.Yuushya.MOD_ID_USED;

public class YuushyaDeferredRegister <T>{
    private final Map<String, RegistrySupplier<T>> OBJECT_MAP = new HashMap<>();

    private static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID_USED));
    private final DeferredRegister<T>  REGISTER;

    public YuushyaDeferredRegister(ResourceKey<Registry<T>> key){
        REGISTER =DeferredRegister.create(MOD_ID,key);
    }
    //set
    public <I extends T> RegistrySupplier<T> register(String name, Supplier<I> sup){
        RegistrySupplier<T> registryObject = REGISTER.register(new ResourceLocation(MOD_ID, name), (Supplier<T>) sup);
        OBJECT_MAP.put(name,registryObject);
        return registryObject;
    }
    public void register(){
        REGISTER.register();
    }


    //get
    @Nullable
    public T getValue(String name){return OBJECT_MAP.get(name).get();}
    @Nullable
    public RegistrySupplier<T> get(String name){return OBJECT_MAP.get(name);}
    @NotNull
    public T getInstanceOrDefault(String name,T defaultObject){
        RegistrySupplier<T> registryObject = get(name);
        if(registryObject==null){ return defaultObject;}
        else return registryObject.get();
    }
}
