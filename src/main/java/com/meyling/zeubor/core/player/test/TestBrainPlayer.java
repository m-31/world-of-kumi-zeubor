package com.meyling.zeubor.core.player.test;

import com.meyling.zeubor.core.common.Context;
import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.player.creator.PlayerCreator;
import com.meyling.zeubor.core.player.basis.AbstractPlayer;
import com.meyling.zeubor.core.player.basis.AbstractPlayerCreator;
import com.meyling.zeubor.core.world.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class TestBrainPlayer {

    /**
     * Call the main loop.
     *
     * @param args    Player names. Take all if none given.
     */
    public static void main(String[] args) {
        TestBrainPlayer tester = new TestBrainPlayer(10, new File("."), new Context() {
            @Override
            public String getBuildId() {
                return "test id";
            }

            @Override
            public String getKernelVersion() {
                return "test version";
            }

            @Override
            public String getDescriptiveKernelVersion() {
                return "no description";
            }
        },
                1000000l);
        if (args == null || args.length == 0) {
            tester.test("anton");
            tester.test("belinda");
            tester.test("carmen");
            tester.test("doris");
            tester.test("dido");
            tester.test("dana");
            tester.test("ellen");
            tester.test("frances");
            tester.test("ginny");
            tester.test("hazel");
            tester.test("inga");
        } else {
            for (int i = 0; i < args.length; i++) {
                tester.test(args[i]);
            }
        }
    }

    private final int runs;
	private String resultFileName;
	private JSONObject completeResult;
    private final PlayerCreator creator;
    private final File resultDirectory;
    private final long iterations;
    private final Context context;

    public TestBrainPlayer(final int runs, final File resultDirectory, final Context context, long iterations) {
        this.runs = runs;
        this.creator = new PlayerCreator();
        this.resultDirectory = resultDirectory;
        this.context = context;
        this.iterations = iterations;
    }

    public void test(final String name) {
        resultDirectory.mkdirs();
        completeResult = new JSONObject();
        test(creator.createCreator(name));
    }
     
    private void test(final AbstractPlayerCreator playerCreator) {
        final long start = System.currentTimeMillis();
        resultFileName = new File(resultDirectory, "r_" + playerCreator.getName() + "_" + start + ".json").getPath();
        completeResult = new JSONObject();
        JSONObject playerInfo = new JSONObject();
        playerInfo.put("name", playerCreator.getName());
        playerInfo.put("file", playerCreator.getFileName());
        playerInfo.put("modificationTimestamp", playerCreator.getModificationTime());
        playerInfo.put("md5",  playerCreator.getMd5());
        playerInfo.put("player_name", playerCreator.create().getName());
        completeResult.put("player", playerInfo);

        JSONObject environment = new JSONObject();

        JSONObject executor = new JSONObject();
        environment.put("executor", executor);
        executor.put("version", context.getDescriptiveKernelVersion());
        
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        JSONObject host = new JSONObject();

        host.put("host_name", hostName);
        host.put("java_version", System.getProperty("java.version"));
        host.put("os_name", System.getProperty("os.name"));
        host.put("os_version", System.getProperty("os.version"));
        host.put("os_arch", System.getProperty("os.arch"));
        environment.put("host", host);
        environment.put("execution_begin", IoUtility.getIsoTimestamp());
        completeResult.put("player", playerInfo);
        completeResult.put("execution_environment", environment);
        JSONObject scenarios = new JSONObject();
        completeResult.put("scenarios", scenarios);
        saveResult();
        final double factor = 10;
		test_scenario(scenarios, "full center", "many algae, center has high concentration", playerCreator, 250, 750, factor, iterations);
        test_scenario(scenarios, "homogeneous", "homogenous algae concentration", playerCreator, 1000, 0, factor, iterations);
        test_scenario(scenarios, "airy", "not much algae", playerCreator, 100, 0, factor, iterations);
        test_scenario(scenarios, "empty", "very few algae", playerCreator, 10, 0, factor, iterations);
        environment.put("execution_time", System.currentTimeMillis() - start);
        environment.put("execution_end", IoUtility.getIsoTimestamp());
        saveResult();
    }
    
    private void saveResult() {
        IoUtility.save(resultFileName, completeResult);
    }
    
    private JSONObject test_scenario(final JSONObject scenarios, final String scenario, final String description, 
    		final AbstractPlayerCreator playerCreator, 
            final int balls, final int squares, final double factor, final long iterations) {
        final JSONObject r = new JSONObject();
    	scenarios.put(scenario, r);

    	r.put("description", description);
        
        final JSONObject w = new JSONObject();
        w.put("ball_algae", balls);
        w.put("square_algae", squares);
        w.put("speed_factor", factor);
        w.put("iterations", iterations);
        r.put("world", w);

        final JSONArray results = new JSONArray();
        r.put("results", results);
        saveResult();
        for (int run = 0; run < runs; run++) {
            final World world = new World(0, balls, squares, factor);
            final AbstractPlayer player = playerCreator.create();
            world.add(player);
    
            final long start = System.currentTimeMillis();
            final JSONObject result = new JSONObject();
            results.put(result);
            final JSONObject eating = new JSONObject();
            result.put("eating", eating);
            eating.put("all_algae", world.getAlgaeNumber());
            eating.put("rest_algae", world.getAlgaeNumber());
            eating.put("player_algae", player.getAlgae());
            final JSONObject history = new JSONObject();
            eating.put("history", history);
            long lastAlgaeIteration = 0;
            for (long i = 1; i <= iterations; i++) {
                int lastAlgae = player.getAlgae();
                world.iterate();
                if (i % (iterations / 10) == 0) {
                    history.put("iteration_" + i + "_algae", player.getAlgae());
                    saveResult();
                }
                if (lastAlgae < player.getAlgae()) {
                    lastAlgaeIteration = i;
                    if (player.getAlgae() % ((balls + squares) / 10) == 0) {
                    	history.put("algae_" + player.getAlgae() + "_iteration", i);
                    }
                    history.put("last_algae_iteration", lastAlgaeIteration);
                    eating.put("player_algae", player.getAlgae());
                    eating.put("rest_algae", world.getAlgaeNumber());
                    saveResult();
                }
                if (world.getAlgaeNumber() == 0) {
                    break;
                }
            }
            result.put("execution_time", System.currentTimeMillis() - start);
            saveResult();
        }    
        
        return r;
    }

}
