package com.meyling.zeubor.core.video;
/**
 * @(#)Main.java  
 *
 * Copyright (c) 2011-2012 Werner Randelshofer, Goldau, Switzerland.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the
 * license agreement you entered into with Werner Randelshofer.
 * For details see accompanying license terms.
 */


import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.player.basis.AbstractPlayer;
import com.meyling.zeubor.core.player.creator.PlayerCreator;
import com.meyling.zeubor.core.world.World;

import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import static com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @see <a href="https://github.com/kalaspuffar/java-video/blob/main/src/main/java/CreateVideo.java">...</a>
 */
public class ProduceVideo3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            String file = "test.mov";
            System.out.println("Writing " + file);
            final IMediaWriter writer = ToolFactory.makeWriter(file);

            writer.addListener(ToolFactory.makeViewer(
                    IMediaViewer.Mode.VIDEO_ONLY, true,
                    javax.swing.WindowConstants.EXIT_ON_CLOSE));

            testWriting(writer);

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void testWriting(IMediaWriter writer) throws IOException {

        // the clock time of the next frame
        long nextFrameTime = 0;

        // video parameters

        final int videoStreamId = 0;
        final int videoWidth = 3840;
        final int videoHeight = 2160;
//        final int videoWidth = 320;
//        final int videoHeight = 200;
        final int depth = 24;
        final int videoStreamIndex = 0;
//        final long frameRate = DEFAULT_TIME_UNIT.convert(500, MILLISECONDS);
//        final long frameRate = 90;
        final long frameRate = DEFAULT_TIME_UNIT.convert(1000 / 60, MILLISECONDS);

        writer.addListener(ToolFactory.makeViewer(
                IMediaViewer.Mode.VIDEO_ONLY, true,
                javax.swing.WindowConstants.EXIT_ON_CLOSE));

        writer.addVideoStream(videoStreamIndex, videoStreamId, videoWidth, videoHeight);

        // Create a buffered image for this format
        BufferedImage img = createImage(videoWidth, videoHeight, depth);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setBackground(Color.white);
        g.clearRect(0, 0, img.getWidth(), img.getHeight());

        try {
            // initialize the animation
            World world = new World(0, 10000, 10000, 5);
//            World world = new World(0, 100, 100, 5);
            final List<AbstractPlayer> players = new ArrayList<AbstractPlayer>();
            final PlayerCreator creator = new PlayerCreator();
            {
                final AbstractPlayer player = creator.createCreator("dido").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = 0.1;
                player.getViewPoint().getPosition()[1] = 0.1;
                player.getViewPoint().getPosition()[2] = 0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }    
            {
                final AbstractPlayer player = creator.createCreator("ellen").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = 0.1;
                player.getViewPoint().getPosition()[1] = -0.1;
                player.getViewPoint().getPosition()[2] = 0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }     
            {
                final AbstractPlayer player = creator.createCreator("frances").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = -0.1;
                player.getViewPoint().getPosition()[1] = 0.1;
                player.getViewPoint().getPosition()[2] = 0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }    
            {
                final AbstractPlayer player = creator.createCreator("ginny").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = 0.1;
                player.getViewPoint().getPosition()[1] = 0.1;
                player.getViewPoint().getPosition()[2] = -0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }    
            {
                final AbstractPlayer player = creator.createCreator("hazel").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = -0.1;
                player.getViewPoint().getPosition()[1] = -0.1;
                player.getViewPoint().getPosition()[2] = 0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[4], player.getViewPoint());
            }
            {
                final AbstractPlayer player = creator.createCreator("inga").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = -0.1;
                player.getViewPoint().getPosition()[1] = 0.1;
                player.getViewPoint().getPosition()[2] = -0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }    
            {
                final AbstractPlayer player = creator.createCreator("jasmine").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = 0.1;
                player.getViewPoint().getPosition()[1] = -0.1;
                player.getViewPoint().getPosition()[2] = -0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }
            {
                final AbstractPlayer player = creator.createCreator("jessie").create();
                players.add(player);
                player.getViewPoint().getPosition()[0] = -0.1;
                player.getViewPoint().getPosition()[1] = -0.1;
                player.getViewPoint().getPosition()[2] = -0.1;
                player.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
            }
            
            
            for (final AbstractPlayer player : players) {
                world.add(player);
            }    

            int width = img.getWidth() / 4 - 1;
            int height = img.getHeight() / 2 - 2;
            
//            for (int z = 0; z < 20000; z++) {
//            for (int z = 0; z < 300; z++) {
            for (int z = 0; z < 1987; z++) {
                world.iterate();
                System.out.println(z);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 2; j++) {
                        AbstractPlayer player = players.get(i + j * 4);
                        world.drawPlayerView(player, 4 + width * i, 4 + height * j, width - 4, height - 4, true, (Graphics2D) g, 
                            true, false);
                    }
                }
                

                // write it to the writer
                writer.encodeVideo(videoStreamIndex, img, nextFrameTime, DEFAULT_TIME_UNIT);
                nextFrameTime += frameRate;


            }
            world = null;

        } finally {

            // Dispose the graphics object
            g.dispose();
        }
    }

    /** Creates a buffered image of the specified depth with a random color palette.*/
    private static BufferedImage createImage(int width, int height, int depth) {

        Random rnd = new Random(0); // use seed 0 to get reproducable output
        BufferedImage img;
        switch (depth) {
            case 24:
            default: {
                img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                break;
            }
            case 8: {
                byte[] red = new byte[256];
                byte[] green = new byte[256];
                byte[] blue = new byte[256];
                for (int i = 0; i < 255; i++) {
                    red[i] = (byte) rnd.nextInt(256);
                    green[i] = (byte) rnd.nextInt(256);
                    blue[i] = (byte) rnd.nextInt(256);
                }
                rnd.setSeed(0); // set back to 0 for reproducable output
                IndexColorModel palette = new IndexColorModel(8, 256, red, green, blue);
                img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, palette);
                break;
            }
            case 4: {
                byte[] red = new byte[16];
                byte[] green = new byte[16];
                byte[] blue = new byte[16];
                for (int i = 0; i < 15; i++) {
                    red[i] = (byte) rnd.nextInt(16);
                    green[i] = (byte) rnd.nextInt(16);
                    blue[i] = (byte) rnd.nextInt(16);
                }
                rnd.setSeed(0); // set back to 0 for reproducable output
                IndexColorModel palette = new IndexColorModel(4, 16, red, green, blue);
                img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, palette);
                break;
            }
        }
        return img;
    }
}
