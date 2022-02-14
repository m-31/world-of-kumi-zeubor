package com.meyling.zeubor.core.world;

import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.io.IoUtility.StringData;
import com.meyling.zeubor.core.player.AbstractPlayer;
import com.meyling.zeubor.core.player.AbstractPlayerCreator;
import com.meyling.zeubor.core.player.brain.*;
import com.meyling.zeubor.core.player.dumb.DumbPlayer;

public class PlayerCreator {


    public final AbstractPlayerCreator createCreator(final String name) {
        AbstractPlayerCreator playerCreator;
        try {
            playerCreator = createCreatorFromClass(name);
            playerCreator.create();
        } catch (Exception e) {
            // try json
            playerCreator = createCreatorFromJson(name);
        }
        return playerCreator;
    }
    
    public final AdvancedBrainPlayerCreator createCreatorFromJson(final String playerName) {
        final String name = playerName.replaceAll(".json$", "");
        final StringData json = IoUtility.getStringData(name + ".json");
        final AdvancedBrainPlayerCreator playerCreator = new AdvancedBrainPlayerCreator() {
            public AdvancedAbstractBrainPlayer create() {
                return createPlayer(json.data);
            }
    
            public String getName() {
                return name;
            }
    
            public String getFileName() {
                return json.filePath;
            }

            public String getModificationTime() {
                return json.modificationTimestamp;
            }

            public String getJson() {
                return json.data;
            }

            public String getMd5() {
                return json.md5;
            }
        };
        return playerCreator;
    }
    
    public final AbstractPlayerCreator createCreatorFromClass(final String name) {
        final AbstractPlayerCreator playerCreator = new AbstractPlayerCreator() {
            public AbstractPlayer create() {
                switch (name) {
                case "anton":
                    return new DumbPlayer("Anton", 100, 100);
                case "belinda":
                    return new BrainPlayer7(100, 100);
                case "doris":
                    return new BrainPlayer8(100, 100);
                case "carmen":
                    return new BrainPlayer9(100, 100);
                case "dido":
                    return new BrainPlayer10(100, 100);
                case "dana":
                    return new BrainPlayer11(100, 100);
                }
                throw new IllegalArgumentException("Unknown player: " + name);
            }
    
            public String getName() {
                return name;
            }
    
            public String getFileName() {
                return create().getClass().toString();
            }

            public String getModificationTime() {
                return null;
            }

            public String getMd5() {
                return null;
            }
        };
        return playerCreator;
    }
    
    public BrainPlayerFromJson createPlayer(final String json) {
        return new BrainPlayerFromJson(100, 100, json);
    }

}
