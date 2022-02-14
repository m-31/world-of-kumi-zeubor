package com.meyling.zeubor.core.nerve;

import com.meyling.zeubor.core.nerve.basis.Dendrite;
import com.meyling.zeubor.core.nerve.basis.Neuron;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Encapsulates a neural net and manages interplay with sensors and muscles. 
 */
public final class BrainImpl11 extends AbstractBrain5Output {

    private static final String JSON = "{"
        + "\n  \"description\": \"this file describes a neuronal net\","
        + "\n  \"name\": \"Dana\","
        + "\n  \"created\": \"2015-05-28T18:45\","
        + "\n  \"comment\": \"just the beginning\","
        + "\n  \"groups\": {"
        + "\n    \"input\": {"
        + "\n      \"description\": \"these neuron groups get light input\","
        + "\n      \"eye_width\": 3,"
        + "\n      \"eye_height\": 3,"
        + "\n      \"groups\" : {"
        + "\n        \"center\": {"
        + "\n          \"connections\": ["
        + "\n            {"
        + "\n              \"weight\" : 200,"
        + "\n              \"to\": ["
        + "\n                \"output.forward\""
        + "\n              ]"
        + "\n            }"
        + "\n          ]"
        + "\n        },"
        + "\n        \"cross\": { "
        + "\n          \"groups\" : {"
        + "\n            \"left\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 200,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.right\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"right\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 200,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.right\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"up\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 200,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.up\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"down\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 200,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.up\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            }"
        + "\n          },"
        + "\n          \"connections\": ["
        + "\n            {"
        + "\n              \"weight\" : 40,"
        + "\n              \"to\": ["
        + "\n                \"output.forward\""
        + "\n              ]"
        + "\n            }"
        + "\n          ]"
        + "\n        },"
        + "\n        \"corners\": {"
        + "\n          \"groups\" : {"
        + "\n            \"upperleft\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\","
        + "\n                    \"output.turn.up\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.right\","
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"upperright\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.right\","
        + "\n                    \"output.turn.up\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\","
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"downleft\": {"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\","
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.right\","
        + "\n                    \"output.turn.up\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"downright\": { "
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\" : 100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.right\","
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\" : -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\","
        + "\n                    \"output.turn.up\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            }"
        + "\n          },"
        + "\n          \"connections\": ["
        + "\n            {"
        + "\n              \"weight\" : 40,"
        + "\n              \"to\": ["
        + "\n                \"output.forward\""
        + "\n              ]"
        + "\n            }"
        + "\n          ]"
        + "\n        }"
        + "\n      },"
        + "\n      \"connections\": ["
        + "\n        {"
        + "\n          \"weight\" : 100,"
        + "\n          \"to\": ["
        + "\n            \"random.input\""
        + "\n          ]"
        + "\n        },"
        + "\n      ]"
        + "\n    },"
        + "\n    \"output\": {"
        + "\n      \"description\": \"these neuron groups trigger movement\","
        + "\n      \"groups\" : {"
        + "\n        \"forward\": { "
        + "\n          \"lowerThreshold\": 100,"
        + "\n          \"upperThreshold\": 10000,"
        + "\n          \"connections\": ["
        + "\n            {"
        + "\n              \"weight\": 100,"
        + "\n              \"to\": ["
        + "\n                \"counter.forward\""
        + "\n              ]"
        + "\n            }"
        + "\n          ]"
        + "\n        },"
        + "\n        \"turn\": { "
        + "\n          \"groups\" : {"
        + "\n            \"left\": {"
        + "\n              \"lowerThreshold\": 100,"
        + "\n              \"upperThreshold\": 10000,"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.decider.leftright\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"right\": {"
        + "\n              \"lowerThreshold\": 100,"
        + "\n              \"upperThreshold\": 10000,"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.decider.leftright\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.forward.leftright\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"up\": {"
        + "\n              \"lowerThreshold\": 100,"
        + "\n              \"upperThreshold\": 10000,"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.decider.updown\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.forward.updown\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"down\": { "
        + "\n              \"lowerThreshold\": 100,"
        + "\n              \"upperThreshold\": 10000,"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.decider.updown\""
        + "\n                  ]"
        + "\n                },"
        + "\n                {"
        + "\n                  \"weight\": 50,"
        + "\n                  \"to\": ["
        + "\n                    \"random.forward.updown\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            }"
        + "\n          }"
        + "\n        }"
        + "\n      }"
        + "\n    },"
        + "\n    \"random\": {"
        + "\n      \"groups\" : {"
        + "\n        \"input\": { "
        + "\n          \"kind\": \"SupressRandomFire\","
        + "\n          \"max\": 1000,"
        + "\n          \"connections\": ["
        + "\n            {"
        + "\n              \"weight\":  100,"
        + "\n              \"to\": ["
//        + "\n                \"output.forward\","
        + "\n                \"output.turn\""
        + "\n              ]"
        + "\n            }"
        + "\n          ]"
        + "\n        },"
        + "\n        \"decider\": {"
        + "\n          \"groups\" : {"
        + "\n            \"updown\": { "
        + "\n              \"kind\": \"RandomFire\","   
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.up\","
        + "\n                    \"output.turn.down\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"leftright\": { "
        + "\n              \"kind\": \"RandomFire\","   
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": -100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.turn.left\","
        + "\n                    \"output.turn.right\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            }"
        + "\n          }"
        + "\n        },"
        + "\n        \"forward\": {"
        + "\n          \"comment\": \"flip coin to go forward if decision has to be made\","
        + "\n          \"groups\" : {"
        + "\n            \"updown\": {"
        + "\n              \"comment\": \"flip coin to go forward if decision has to be made between up and down\","
        + "\n              \"kind\": \"RandomFire\","
        + "\n              \"probability\": 0.5,"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": 100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.forward\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            },"
        + "\n            \"leftright\": {"
        + "\n              \"comment\": \"flip coin to go forward if decision has to be made between left and right\","
        + "\n              \"kind\": \"RandomFire\","
        + "\n              \"probability\": 0.5,"
        + "\n              \"connections\": ["
        + "\n                {"
        + "\n                  \"weight\": 100,"
        + "\n                  \"to\": ["
        + "\n                    \"output.forward\""
        + "\n                  ]"
        + "\n                }"
        + "\n              ]"
        + "\n            }"
        + "\n          }"
        + "\n        }"
        + "\n      }"
        + "\n    },"
        + "\n    \"counter\": {"
        + "\n      \"groups\" : {"
        + "\n        \"forward\": { "
        + "\n          \"comment\": \"move forward if we hadn't for 2 cycles\","
        + "\n          \"kind\": \"Counter\","
        + "\n          \"max\": 2,"
        + "\n          \"connections\": ["
        + "\n            {"
        + "\n              \"weight\":  100,"
        + "\n              \"to\": ["
        + "\n                \"output.forward\""
        + "\n              ]"
        + "\n            }"
        + "\n          ]"
        + "\n        },"
        + "\n      }"
        + "\n    }"
        + "\n  }"
        + "\n}";
    
    private HashMap<String, List<Neuron>> neuronHash;


    public final static void main(final String[] args) {
        BrainImpl11 bi = new BrainImpl11();
        bi.grow(JSON);
        bi.getInputNeurons().get(0).setFire(true);
        bi.getInputNeurons().get(1).setFire(true);
//        bi.printOutputNeurons();
        bi.iterate();
        bi.printOutputNeurons();
        System.exit(0);
    }
    
    public BrainImpl11() {
        super();
        neuronHash = new HashMap<String, List<Neuron>>();
    }
    
    public void grow(List<List<Integer>> genom) {
        grow(JSON);
    }    


    protected void addConnection(final String from, Integer from2, final String to, final int weight) {
        System.out.println("mapping neurons from \"" + from + "\" to \"" + to + "\"");
        final List<Neuron> toNeurons = neuronHash.get(to);
        if (toNeurons == null) {
            throw new NullPointerException("no neuron_group \"" + to + "\" found");
        }
        final List<Neuron> fromNeurons = neuronHash.get(from);
        if (fromNeurons == null) {
            throw new NullPointerException("no neuron_group \"" + from + "\" found");
        }
        if (from2 == null) {
            if (toNeurons.size() > 1 && fromNeurons.size() > 1)
                if (toNeurons.size() != fromNeurons.size()) {
                    throw new IllegalArgumentException("mapping " + fromNeurons.size() + " to " + toNeurons.size());
                } else {
                for (int i = 0; i < fromNeurons.size(); i++) {
                    final Neuron toNeuron = toNeurons.get(i);
                    final Neuron fromNeuron = fromNeurons.get(i);
                    toNeuron.addDentrite(fromNeuron, weight);
                }
            } else {
                for (final Neuron toNeuron : toNeurons) {
                    for (final Neuron fromNeuron : fromNeurons) {
                        toNeuron.addDentrite(fromNeuron, weight);
                    }
                }
                
            }
        } else {
            for (final Neuron toNeuron : toNeurons) {
                toNeuron.addDentrite(fromNeurons.get(from2), weight);
            }    
        }
    }
    
    public void grow(final String json) {
        System.out.println(json);

        Scanner scanner = new Scanner(json);
        long i = 0;
        while (scanner.hasNextLine()) {
            System.out.println(++i + scanner.nextLine());
        }

        JSONObject obj = new JSONObject(json);
        final String name = obj.getString("name");
        System.out.println(obj.toString(2));

        System.out.println("grow neuronal net");
        
        getInputNeurons().clear();
        getOutputNeurons().clear();
        getRandomNeurons().clear();
        getGlias().clear();
        neuronHash.clear();
        
        final JSONObject neuron_groups = obj.getJSONObject("groups");

        System.out.println("parse neurons");
        addNeuronGroup("", obj);
        System.out.println();
        
        
        System.out.println("getting input properties");
        final JSONObject input = neuron_groups.getJSONObject("input");
        setEyeWidth(input.getInt("eye_width"));
        System.out.println("eye_width=" + getEyeWidth());
        setEyeHeight(input.getInt("eye_height"));
        System.out.println("eye_width=" + getEyeHeight());
        System.out.println();
        
        System.out.println("parse connections");
        addNeuronGroupConnections("", obj);
        System.out.println();
        
        getInputNeurons().add(getNeuron("input.corners.upperleft"));
        getInputNeurons().add(getNeuron("input.cross.up"));
        getInputNeurons().add(getNeuron("input.corners.upperright"));
        getInputNeurons().add(getNeuron("input.cross.left"));
        getInputNeurons().add(getNeuron("input.center"));
        getInputNeurons().add(getNeuron("input.cross.right"));
        getInputNeurons().add(getNeuron("input.corners.downleft"));
        getInputNeurons().add(getNeuron("input.cross.down"));
        getInputNeurons().add(getNeuron("input.corners.downright"));

        getOutputNeurons().add(getNeuron("output.turn.up"));
        getOutputNeurons().add(getNeuron("output.turn.left"));
        getOutputNeurons().add(getNeuron("output.forward"));
        getOutputNeurons().add(getNeuron("output.turn.right"));
        getOutputNeurons().add(getNeuron("output.turn.down"));

//        printInputNeurons();
//        printOutputNeurons();
//        System.exit(0);
        
    }
    
    public void printInputNeurons() {
        System.out.println("input neurons");
        for (final Neuron neuron : getInputNeurons()) {
            System.out.println("  " + getName(neuron));
        }
    }

    public void printOutputNeurons() {
        System.out.println("output neurons");
        for (final Neuron neuron : getOutputNeurons()) {
            System.out.println("  " + getName(neuron) + " " + neuron.getFire());
            printInputs(neuron);
        }
    }

    public void printInputs(final Neuron neuron) {
        for (final Dendrite dendrite : neuron.getDendrites()) {
//            System.out.println("    " + dendrite.getWeight() + " "+ getName(dendrite.getAxon().getNeuron()));
            System.out.println("    " + dendrite.getWeight() + " " + dendrite.getFire() + " " + getName(dendrite.getAxon().getNeuron()));
        }
    }

    public void printNeurons() {
        System.out.println();
        for (Neuron neuron : getInputNeurons()) {
            System.out.print(" " + (neuron.getFire() ? 'X' : '0'));
            System.out.printf("%4d |", neuron.getPotential());
        }
        System.out.println();
        for (Neuron neuron : getOutputNeurons()) {
            System.out.print(" " + (neuron.getFire() ? 'X' : '0'));
            System.out.printf("%4d |", neuron.getPotential());
        }
        System.out.println();
        printOutputNeurons();
    }
    
    public void iterate2() {
        printInput();
        printNeurons();
        super.iterate();
        printNeurons();
        printOutput();
    }
    
    protected Neuron getNeuron(final String name) {
        List<Neuron> list = neuronHash.get(name);
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("no neuron found for \"" + name + "\"");
        }
        if (list.size() > 1) {
            throw new IllegalArgumentException("more than one neuron found for \"" + name + "\"");
        }
        return neuronHash.get(name).get(0);
    }

    protected String getName(final Neuron neuron) {
        for (final Entry<String, List<Neuron>> entry : neuronHash.entrySet()) {
            if (entry.getValue().size() == 1 && entry.getValue().get(0) == neuron) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("no name for neuron found!");
    }

	private List<Neuron> addNeuronGroup(final String prefix, final JSONObject gs) {
        final JSONObject groups = gs.getJSONObject("groups");
        final JSONArray gNames = groups.names();
        final List<Neuron> neurons= new ArrayList<Neuron>();
        for (int i = 0; i < gNames.length(); i++) {
    	    final String name = (String) gNames.get(i);
            JSONObject group = groups.getJSONObject(name);
            final String newPrefix = prefix + (prefix.length() > 0 ? "." : "") + name;
            System.out.println("parsing group: " + newPrefix);
            if (group.has("groups")) {
                List<Neuron> sub = addNeuronGroup(newPrefix, group);
                neurons.addAll(sub);
            } else {
                Neuron neuron = null;
                if (group.has("kind")) {
                    switch (group.getString("kind")) {
                    case "SupressRandomFire":
                        neuron = createSupressRandomFireNeuron(group.getInt("max"));
                        break;
                    case "RandomFire":
                        neuron = createRandomFireNeuron();
                        break;
                    case "FireRandom":
                        neuron = createFireRandomNeuron(group.getDouble("probability"));
                        break;
                    case "Counter":
                        neuron = createCounterNeuron(group.getInt("max"));
                        break;
                    default:
                        throw new IllegalArgumentException("unknown random kind: " + group.getString("kind"));
                    }
            	} else {
            		neuron = createNeuron();
            	}
                if (group.has("lowerThreshold")) {
                    neuron.setLowerThreshold(group.getInt("lowerThreshold"));
                }
                if (group.has("upperThreshold")) {
                    neuron.setHigherThreshold(group.getInt("upperThreshold"));
                }
                List<Neuron> singleNeuronList = new ArrayList<Neuron>();
                singleNeuronList.add(neuron);
                System.out.println("  put \"" + newPrefix + "\" with " + singleNeuronList.size() + " neurons");
                neuronHash.put(newPrefix, singleNeuronList);
                neurons.add(neuron);
            }    
//            System.out.println("  put \"" + newPrefix + "\" with " + neurons.size() + " neurons");
//            neuronHash.put(newPrefix, neurons);
        }    
        System.out.println("  put \"" + prefix + "\" with " + neurons.size() + " neurons");
        neuronHash.put(prefix, neurons);
        return neurons;
	}

    private void addNeuronGroupConnections(final String prefix, final JSONObject gs) {
        final JSONObject groups = gs.getJSONObject("groups");
        final JSONArray gNames = groups.names();
        for (int i = 0; i < gNames.length(); i++) {
            final String name = (String) gNames.get(i);
            JSONObject group = groups.getJSONObject(name);
            final String newPrefix = prefix + (prefix.length() > 0 ? "." : "") + name;
            System.out.println("parsing group: " + newPrefix);
            addGroupConnections(newPrefix, group);
            if (group.has("groups")) {
                addNeuronGroupConnections(newPrefix, group);
            }
        }
    }    
            
    protected void addGroupConnections(String area, JSONObject group) {
        if (!group.has("connections")) {
            return;
        }
        final JSONArray connections = group.getJSONArray("connections");
        for (int j = 0; j < connections.length(); j++) {
            final JSONObject connection = connections.getJSONObject(j);
            int weight = connection.getInt("weight");
            final JSONArray tos = connection.getJSONArray("to");
            Integer from = null;
            if (connection.has("from")) {
                from = connection.getInt("from");
            }
            for (int k = 0; k < tos.length(); k++) {
                addConnection(area, from, tos.getString(k), weight);
            }
        }    
    }
        

}
