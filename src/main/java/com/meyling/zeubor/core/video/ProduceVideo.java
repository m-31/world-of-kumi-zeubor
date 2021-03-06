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
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;
import com.meyling.zeubor.core.player.brain.BrainPlayer10;
import com.meyling.zeubor.core.world.World;
import org.monte.media.Format;
import org.monte.media.FormatKeys.*;
import org.monte.media.avi.AVIWriter;
import org.monte.media.math.Rational;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Use Werner Randelshofers Monte Media Library to produce videaos.
 * @see http://www.randelshofer.ch/monte/
 */
public class ProduceVideo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("This is a demo of the Monte Media library.");
        System.out.println("Copyright © Werner Randelshofer. All Rights Reserved.");
        System.out.println("License: Creative Commons Attribution 3.0.");
        System.out.println();
        
        try {
            test(new File("avidemo-jpg.avi"), new Format(EncodingKey, ENCODING_AVI_MJPG, DepthKey, 24, QualityKey, 1f));
            test(new File("avidemo-jpg-q0.5.avi"), new Format(EncodingKey, ENCODING_AVI_MJPG, DepthKey, 24, QualityKey, 0.5f));
            test(new File("avidemo-png.avi"), new Format(EncodingKey, ENCODING_AVI_PNG, DepthKey, 24));
            test(new File("avidemo-raw24.avi"), new Format(EncodingKey, ENCODING_AVI_DIB, DepthKey, 24));
            test(new File("avidemo-raw8.avi"), new Format(EncodingKey, ENCODING_AVI_DIB, DepthKey, 8));
            test(new File("avidemo-rle8.avi"), new Format(EncodingKey, ENCODING_AVI_RLE, DepthKey, 8));
            test(new File("avidemo-tscc8.avi"), new Format(EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 8));
            test(new File("avidemo-tscc24.avi"), new Format(EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24));
            //test(new File("avidemo-rle4.avi"), AVIOutputStreamOLD.AVIVideoFormat.RLE, 4, 1f);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void test(File file, Format format) throws IOException {
        testWriting(file,format);
    }
    private static void testWriting(File file, Format format) throws IOException {
        System.out.println("Writing " + file);

        // Make the format more specific
        format = format.prepend(MediaTypeKey, MediaType.VIDEO, //
                FrameRateKey, new Rational(30, 1),//
//                WidthKey, 320, //
//                HeightKey, 160);
                WidthKey, 320 * 3, //
                HeightKey, 160 * 3);

        // Create a buffered image for this format
        BufferedImage img = createImage(format);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AVIWriter out = null;
        try {
            // Create the writer
            out = new AVIWriter(file);

            // Add a track to the writer
            out.addTrack(format);
            out.setPalette(0, img.getColorModel());

            // initialize the animation
            World world = new World(0, 10000, 10000, 5);
//            World world = new World(0, 100, 100, 5);
            final AbstractBrainPlayer player4 = new BrainPlayer10(100, 100);
            world.add(player4);
            player4.getViewPoint().getPosition()[1] = - 0.1;
            player4.getCamera().getPhotoPlateImpl().setSensitivity(11);
            CalculatorUtility.pointToZero(new double[4], player4.getViewPoint());

            for (int z = 0; z < 100; z++) {
                world.iterate();

/*              
/*
// working                
                Camera camera = player4.getCamera();
                PhotoPlateAttributes attributes = player4.getCamera().getPhotoPlate().getPhotoPlateAttributes();
                attributes.setZoom(500);
                attributes.setSensitivity(13);
                world.drawWorld(camera.getViewPoint(), img.getWidth(), img.getHeight(), true, 
                        g, attributes);
*/                
                world.drawPlayerView(player4, img.getWidth(), img.getHeight(), true, (Graphics2D) g, true, true);
                
                
//                world.takePicture(viewer);

// working                
//                g.drawImage(plate.getPictureImpl().getImage(), 0, 0, img.getWidth(), img.getHeight(), null);

// working                
//                int width = plate.getPicture().getWidth();
//                int height = plate.getPicture().getHeight();
//                AffineTransform tx = AffineTransform.getScaleInstance((double) img.getWidth() / width, (double) img.getHeight() / height);
//                g.drawImage(plate.getPictureImpl().getImage(), new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR), 0, 0);
                
//                final Image tmp = viewer.getCamera().getPhotoPlateImpl().getPictureImpl().getImage().getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_AREA_AVERAGING);

// working                
//                final BufferedImage tmp = Scalr.resize(plate.getPictureImpl().getImage(), img.getWidth(), img.getHeight());
//                g.drawImage(tmp, 0, 0, null);

                // write it to the writer
                out.write(0, img, 1);

            }
            world = null;

        } finally {
            // Close the writer

            if (out != null) {
                out.close();
            }
            
            // Dispose the graphics object
            g.dispose();
        }
    }

    /** Creates a buffered image of the specified depth with a random color palette.*/
    private static BufferedImage createImage(Format format) {
        int depth = format.get(DepthKey);
        int width = format.get(WidthKey);
        int height = format.get(HeightKey);

        Random rnd = new Random(0); // use seed 0 to get reproducable output
        BufferedImage img;
        switch (depth) {
            case 24:
            default: {
                img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
