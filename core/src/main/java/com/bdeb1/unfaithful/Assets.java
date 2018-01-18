/*
 * Copyright 2018 Soheib El-Harrache.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bdeb1.unfaithful;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.ArrayList;

/**
 *
 * @author Soheib El-Harrache
 */
public class Assets {

    public static String ATLAS_BACKGROUND_1;

    public AssetManager manager;

    private static Assets instance = null;

    private Assets() {
        manager = new AssetManager();
    }

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    //------------------------ATLAS
    public static final AssetDescriptor<TextureAtlas> ATLAS_NOTHACKING
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/pas_hacking_joe_lvl1.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> ATLAS_HACKING
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/hacking_joe_lvl1.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> ATLAS_RIGHT_TO_LEFT
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/right_left__transition_joe_lvl1.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> ATLAS_BACKGROUND_LV1
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/Background_lvl_1.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> ATLAS_BAR_HACKING
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/UI/progression_bar_hacking.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> ATLAS_BAR_SUSPICION
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/UI/progression_bar_suspicion.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> ATLAS_MENU
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/UI/barre_menu.atlas", TextureAtlas.class);
        
        public static final AssetDescriptor <TextureAtlas> WOMAN_WALKING
            = new AssetDescriptor<TextureAtlas>("atlas/Madame/madame_marche.atlas",TextureAtlas.class);
        
        public static final AssetDescriptor <TextureAtlas> WOMAN_WALKING_SUSPICIOUSLY
            = new AssetDescriptor<TextureAtlas>("atlas/Madame/madame_marche_soupcon.atlas",TextureAtlas.class);
        
        public static final AssetDescriptor <TextureAtlas> WOMAN_ROTATING
            = new AssetDescriptor<TextureAtlas>("atlas/Madame/madame_rotation.atlas",TextureAtlas.class);
        
        public static final AssetDescriptor <TextureAtlas> WOMAN_ROTATING_SUSPICIOUSLY
            = new AssetDescriptor<TextureAtlas>("atlas/Madame/madame_rotation_soupcon.atlas",TextureAtlas.class);
        
        
        public static final AssetDescriptor<TextureAtlas> ATLAS_HACKING_LAPSCREEN
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/hacking_bureau_lvl1.atlas", TextureAtlas.class);
        
        public static final AssetDescriptor<TextureAtlas> ATLAS_NOTHACKING_LAPSCREEN
            = new AssetDescriptor<TextureAtlas>(
                    "atlas/pas_hacking_joe_lvl1.atlas", TextureAtlas.class);


    //------------------------SPRITES
        
    public static final AssetDescriptor<Texture> TEXTURE_NAME
            = new AssetDescriptor<Texture>("sprite/exemple_sprite_nhk.png", Texture.class);

    public static final AssetDescriptor<Pixmap> SPRITE_NAME
            = new AssetDescriptor<Pixmap>("sprite/exemple_sprite.png", Pixmap.class);

    public static final AssetDescriptor<Texture> BTN_PAUSE
            = new AssetDescriptor<Texture>("sprite/bouton_pause.png", Texture.class);

    public static final AssetDescriptor<Texture> BTN_PAUSE_HOVER
            = new AssetDescriptor<Texture>("sprite/bouton_pause_hover.png", Texture.class);
    
//    public static final AssetDescriptor <Texture> SPRITE_WOMAN_WALKING
//            = new AssetDescriptor<Texture>("atlas/Madame/madame_marche.png",Texture.class);
//    
//    public static final AssetDescriptor <Texture> SPRITE_WOMAN_WALKING_sOUPCON
//            = new AssetDescriptor<Texture>("atlas/Madame/madame_marche_soupcon.png",Texture.class);
//    
//    public static final AssetDescriptor <Texture> SPRITE_WOMAN_ROTATING
//            = new AssetDescriptor<Texture> ("atlas/Madame/madame_rotation.png",Texture.class);
//    
//     public static final AssetDescriptor <Texture> SPRITE_WOMAN_ROTATING_SUSPICIOUSLY
//            = new AssetDescriptor<Texture> ("atlas/Madame/madame_rotation_soupcon.png",Texture.class);
     
    //public static final AssetDescriptor <Texture> SPRITE_WOMAN_ROTATING
            //= new AssetDescriptor<Texture> ("atlas/Madame/madame_rotation.png",Texture.class);

    //------------------------IMAGES
    public static final AssetDescriptor<Texture> IMAGE_BACKGROUND_1
            = new AssetDescriptor<Texture>("image/background.png", Texture.class);

    public static final AssetDescriptor<Texture> IMAGE_BACKGROUND_2
            = new AssetDescriptor<Texture>("image/background.png", Texture.class);

    public static final AssetDescriptor<Texture> IMAGE_BACKGROUND_3
            = new AssetDescriptor<Texture>("image/background.png", Texture.class);
    
    public static final AssetDescriptor<Texture> SPLASH_LV1_1
            = new AssetDescriptor<Texture>("image/splash_debut_1.png", Texture.class);
    
    public static final AssetDescriptor<Texture> SPLASH_LV1_2
            = new AssetDescriptor<Texture>("image/splash_debut_2.png", Texture.class);

    //------------------------SOUNDS
    public static final AssetDescriptor<Sound> SOUND_NAME
            = new AssetDescriptor<Sound>("sound/exemple_sound.ogg", Sound.class);

    //------------------------MUSICS
    public static final AssetDescriptor<Music> MUSIC_NAME
            = new AssetDescriptor<Music>("music/exemple_music.ogg", Music.class);

    //------------------------TEXTS
    public static final ArrayList<String> HACKING_PROGRESS_LV1
            = new ArrayList<>();

    /**
     * Loads assets.
     */
    public void load() {
        //Atlas
        manager.load(ATLAS_NOTHACKING);
        manager.load(ATLAS_HACKING);
        manager.load(ATLAS_BACKGROUND_LV1);
        manager.load(ATLAS_BAR_HACKING);
        manager.load(ATLAS_BAR_SUSPICION);
        manager.load(ATLAS_MENU);
        manager.load(ATLAS_RIGHT_TO_LEFT);
        manager.load(WOMAN_WALKING);
        manager.load(WOMAN_ROTATING);
        manager.load(WOMAN_WALKING_SUSPICIOUSLY);
        manager.load(WOMAN_ROTATING_SUSPICIOUSLY);
        
        manager.load(ATLAS_HACKING_LAPSCREEN);
        manager.load(ATLAS_NOTHACKING_LAPSCREEN);
        //Sprites
        manager.load(SPRITE_NAME);
        manager.load(TEXTURE_NAME);
        manager.load(BTN_PAUSE);
        manager.load(BTN_PAUSE_HOVER);

        //Images
        manager.load(IMAGE_BACKGROUND_1);
        manager.load(IMAGE_BACKGROUND_2);
        manager.load(IMAGE_BACKGROUND_3);
        manager.load(SPLASH_LV1_1);
        manager.load(SPLASH_LV1_2);

        //Sounds
        manager.load(SOUND_NAME);

        //Musics
        manager.load(MUSIC_NAME);

        //Texts
        HACKING_PROGRESS_LV1.add("Found IP address of a suspicious account");
        HACKING_PROGRESS_LV1.add("Found unuseful naked pictures!");
        HACKING_PROGRESS_LV1.add("Found old data sets");
        HACKING_PROGRESS_LV1.add("Found old contacts");
        HACKING_PROGRESS_LV1.add("Found interestingly weird convos");
        HACKING_PROGRESS_LV1.add("Found encrypted archives");

        //Font
//        manager.load(font_pixel);
    }

    /**
     * Disposes the manager.
     */
    public void dispose() {
        manager.dispose();
    }
}
