package com.meyling.zeubor.core.world;

import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.player.brain.AdvancedAbstractBrainPlayer;
import com.meyling.zeubor.core.player.brain.AdvancedBrainPlayerCreator;
import com.meyling.zeubor.core.player.brain.BrainPlayerFromJson;
import com.meyling.zeubor.core.player.brain.RandomBrainPlayerFromJson;


/**
 * ## Just prototyping main processes.
 */
public class BrainPlayerBreeder {

    final AdvancedBrainPlayerCreator createCreator(final String json, final double changeProbability, final double range, final String newName) {
        final String timestamp = IoUtility.getIsoTimestamp();
        final RandomBrainPlayerFromJson player = new RandomBrainPlayerFromJson(100, 100, json, changeProbability, range, newName);
        final String jsonNew = player.getJson();
        final String md5New = IoUtility.md5(player.getJson());
        final String nameNew = player.getName().toLowerCase();
        final AdvancedBrainPlayerCreator playerCreator = new AdvancedBrainPlayerCreator() {
            public AdvancedAbstractBrainPlayer create() {
                return new BrainPlayerFromJson(100, 100, jsonNew);
            }
    
            public String getName() {
                return nameNew;
            }
    
            public String getFileName() {
                return nameNew + ".json";
            }

            public String getModificationTime() {
                return timestamp;
            }

            public String getJson() {
                return jsonNew;
            }

            public String getMd5() {
                return md5New;
            }
        };
        return playerCreator;
    }

}
