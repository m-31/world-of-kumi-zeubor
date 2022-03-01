# zeubor

Ziel des neuronalen Netzes ist die Vorhersage des bebachteten Verhaltens.
Die Gewichte des Netzes müssen so austariert werden, das die Vorhersage
mit dem tatsächlich Beobachteten übereinstimmt.


Unterschied zu anderen Implementierungen: beim Triggern eines Axon-Fire-Events geht
das Aktionspotential nicht auf 0 zurück, sondern nur um den Schwellwertbetrag.
Dadurch kann ein sehr grosser Input auch zu einem zweiten Impuls führen.

# Ideen

## Idee 3
Erst einmal 9 Eingangsneuronen (3 x 3 Raster) und 5 Ausgangsneuronen.
Die Eingangsneuronen bekommen nicht das Bild direkt, sondern schon über eine
Kontrasteinheit vorverarbeitet: soweit verstärkt, dass mindestens ein Neuron
der Eingangsschicht feuern wird.
In der Ausgangsschicht gibt es Neuronen für links - rechts und oben - unten
Bewegungen, sowie das mittlere Vorwärtsbewegungsneuron. Wenn links - rechts oder
oben - unten gleichzeitig feuern heben sie sich gegenseitig auf.

Wir starten erst einmal mit den beiden Schichten und einer symmetrischen
Gewichteverteilung.

## Idee 2
Aufbau eines Wesens durch Zellteilung mit Plan.
Jede Zelle weiß wie oft sie sich geteilt hat. Jede Zelle kennt den Zeitpunkt, an
dem die Trennwand für ihren Nachbarn aufgebaut wurde (=Zellteilung zum Nachbarn).

Das sieht dann beispielsweise so aus:

Anfang:

                   ,-,
                   |0|
                   `-`

Nach erster Teilung

                ,-, 1 ,-,
                |1|---|1|
                `-`   `-´

Nach zweiter Teilung

                ,-, 1 ,-,
                |2|---|2|
                `-`   `-´
                2|     |2
                ,-, 1 ,-,
                |2|---|2|
                `-`   `-´

Nach dritter Teilung

          ,-, 3 ,-, 1 ,-, 3 ,-,
          |3|---|3|---|3|---|3|
          `-´   `-`   `-´   `-´
          3|s   2|     |2   s|3
          ,-, 3 ,-, 1 ,-, 3 ,-,
          |3|---|3|---|3|---|3|
          `-´   `-`   `-´   `-´

Das *s* steht für eine Verbindung, die durch Zusammenschluss und nicht durch
Teilung zustande kam.

Nach vierter Teilung

    ,-, 4 ,-, 3 ,-, 1 ,-, 3 ,-, 4 ,-,
    |4|---|4|---|3|---|3|---|4|---|4|
    `-´   `-´   `-`   `-´   `-´   `-`
    4|s   3|s   2|     |2   s|3   s|4
    ,-, 4 ,-, 3 ,-, 1 ,-, 3 ,-, 4 ,-,
    |4|---|4|---|3|---|3|---|4|---|4|
    `-´   `-´   `-`   `-´   `-´   `-´

Bei 6 Raumrichtungen kann sich eine Zelle maximal 6 mal teilen. (Wenn man ein kubisches Gitter zugrundelegt.)
Die äußeren Zellen sind von einer späteren Generation.
Durch Festlegung der Teilungsfolge kann ein symmetrisches Gebilde entstehen.

## Idee 4
Wenn man bei den Teilungen aus Idee 3 Original und Kopie voneinander unterscheidet, bekommt
man zusätzliche Möglichkeiten:


Anfang:

                   ,-,
                   |0|
                   `-`

Nach erster Teilung

                ,-, 1 ,-,
                |1|---|0|
                `-`   `-´

Nach zweiter Teilung

                ,-, 1 ,-,
                |2|---|1|
                `-`   `-´
                2|     |2
                ,-, 2 ,-,
                |0|---|0|
                `-`   `-´

Nach dritter Teilungtime   : 91000

          ,-, 3 ,-, 1 ,-, 3 ,-,
          |2|---|3|---|2|---|0|
          `-´   `-`   `-´   `-´
          3|s   2|     |2   s|3
          ,-, 3 ,-, 2 ,-, 3 ,-,
          |0|---|1|---|1|---|0|
          `-´   `-`   `-´   `-´

Nach vierter Teilung

    ,-, 4 ,-, 3 ,-, 1 ,-, 3 ,-, 4 ,-,
    |0|---|3|---|4|---|3|---|1|---|0|
    `-´   `-´   `-`   `-´   `-´   `-`
    4|s   3|s   2|     |2   s|3   s|4
    ,-, 4 ,-, 3 ,-, 1 ,-, 3 ,-, 4 ,-,
    |0|---|1|---|2|---|2|---|1|---|0|
    `-´   `-´   `-`   `-´   `-´   `-´

Also kann eine Zelle verschiedene Parameter haben:
Alter, wie viele Teilungen (und wann), Original / Kopie und bei jeder Zellwand
Informationen wann und wie (Zusammenschluss oder Teilung) diese Wand zustande kam.

Dadurch sollte sich ein komplexer Bauplan realisieren lassen.

Vernachlässigt wird das "innere Wachstum" einer Zelle.


## Idee 0b
Zittern der Augen in das neuronale Netz einprogrammieren.


# Idee 1
Bewegungszustandssensoren auch als Input mit aufnehmen. Sie müssen mit den
Bewegungsmuskelneuronen abgeglichen werden.

# Idee 3
Umgebung vierdimensional gestalten. Dann können die Neuronen lichtsensoren
einen 3D Raum (als Schnittprojektion) abtasten


# Design

### Physik Engine
Objekte, Positionen, Geschwindigkeiten, kann Bilder erzeugen.

### Aufbau der Zellen
Kann den Organismus aufbauen aus einem Genom

###


# Development-Blog

### 2015-05-09 Weiter
Was jetzt? 3 x 3 Neuronen als Eingang, 5 als Ausgang (links, rechts, oben, unten und
vorwärts). Dabei sollen die 3 x 3 Eingangszellen nur vorverstärkte Signale bekommen:
das hellste feuert auf jeden Fall. Bei den Ausgängen sollen gleiche Signale sich
aufheben: links plus rechts = nix, entsprechend bei oben und unten.
Problem ist noch der Aufbau und die Vererbung. Ideen dazu: zunächst nur symmetrische
Gewichte erlauben, die variieren und ausprobieren, was am besten geht.

### 2015-05-12 Good learning means prediction is successful

Random Generator Zelle, die genutzt werden kann, falls keine anderen Impulse vorliegen!

Doris findet die letzten Algen nicht.

Ausbildung neuronaler Zentren für bestimmte: Bereiche: Augen, Motorische Kontrolle, Zufallsgenerator etc.

Carmen schlägt alle. Carmen ist Doris mit einem individuellen Zoomfaktor von 100.

In einer World mit mehr Algen sieht es schlecht für Carmen aus:

    World world = new World(0, 10000, 20);

    time   : 778000
    objects: 8386
      Anton 450
      Belinda 444
      Carmen 287
      Doris 457

    time   : 953000
    objects: 8034
      Anton 549
      Belinda 543
      Carmen 335
      Doris 563

Ein einzelner grüner Punkt ist nicht genug dafür, dass Doris ihn erkennt!!!

now using SCALE_AREA_AVERAGING for scaling images

Dido is the first player with random neurons.

      Dido 57

Searching for last algae. It is not surprising that Belinda gets most of the last
algae because Belinda is (together with Anton) the only player with a default
zoom factor. (Carmen and Doris have a zoom factor of 100.) Nevertheless Doris
gets some of the last algae.

### 2015-05-20

Newest model Dido has three random nerves.
+ suppressed random fire: generates movement if we get no input
+ random fire: used to make a decision between
  left and right or up and down if both directions were firing
+ fire random: fire if activated and random occurs


### 2015-05-26
Doku:

http://neuralnetworksanddeeplearning.com/chap1.html


### 2015-06-12 Evolution mit Simulated Annealing

TODOs

- measurement Funktion schneller abbrechen, falls schlechte Ergebnisse da
- "test" parallelisieren: pro name, pro run eigenen Thread aus Threadpool
- measurement Funktion weiter fortführen (genauere Ergebnisse) für höhere Temperatur
- Simulated Annealing als Pattern rausziehen
- Temperaturfunktion verfeinern
- measurement Funktion weiter fortführen, bei besseren Ergebnissen
- Verhältnis Iterationen / Algenanzahl muss aus mesurementfunktion rausgerechnet werden
- Anzahl der weight Änderungen protokollieren
- Verteilungsfunktionen für Änderungen nutzen: kleine Änderungen sind am wahrscheinlichsten
- Limitfunktion verbessern
    + bei 800 Algen sind 10% immer ok
    + bei 300 Algen sind 12.5% immer ok
    + bei 50  Algen sind 23% immer ok
    + bei 3   Algen sind 130% immer ok
- Videoproduktion auch per Kommandozeile
- restartfähig machen



# External data and ideas

https://leanpub.com/realsmartmachines/read \
https://en.wikipedia.org/wiki/Sparse_distributed_memory \
https://en.wikipedia.org/wiki/Self-organizing_map \
https://faculty.washington.edu/chudler/glia.html


Transactions on Computational Science V: Special Issue on Cognitive ...
edited by Yingxu Wang, C. J. Kenneth Tan, Keith Chan

D.S. Greer: Images as Symbols

### 2.6 Celluar Computations and Neuroglia

An abstract processing element can be defined as any cell that detects and aemits a signal.
In biological systems, we cannot restrict the focus to electrical signals, since even neurons detect
chemical signals. Moreover, we cannot expect the behavior of neurons to be predictable if neighboring cells
that use only chemical signaling are excluded from consideration.

In the central nervous system of vertebrates, there are 10 to 50 times more glial cells than neurons.
Astrocytes, the most common type of neuroglia, are recptive to potassium ions and take up neurotransmitters
in synaptic zones. Glial cells have also be shown to release neurotransmitters.

Unlike neurons, glial cells do not generate action potentials. Consequently, if state is encoded
in the firing of neurons, glia are relegated to a support role. However, in a neurotransmitter-field model,
glia can take a central position along side neurons. They may participate in both short-term and long-term
memory as well as computations. However, since the lack action ptentials, glial cells transmit the results
of their computations more slowly.

A notable difference between a perceptron neural-network model and the neurotransmitter-field model is
the speed and simplicity of learning new associations. A two-layer neural network sucha as the one shown
in Fig. 4B typically requires an extended training phase involving repeated iterations of a back-propagation
algorithm. In contrast, the neurotransmitter field model shown in Fig. 4A can learn a new association
by adding a single cell, which could be a glial cell. When the new cell regoginzes its image pattern, \sigma_i
in the input manifold, it emits the associated neurotransmitter \tau_i into the output manifold.
If the new input pattern is relatively unique, previously learned associations will not be affected.

### 3. Association Processors

Based on neurotransmitter-field theory, we can construct a system where arbitrary collections of
photographs operate as symbols. If we wish to represent numbers or letters, we can pick a font
and record images of their alphanumeric glyph. Similarly, fixed-time snapshots of any two-dimensional computiational
map can be used as symbols representing external stimuli or motor control actions.

In Fig. 5 we show how using topological alignment, multiple input images can be combined and associated
with a single output image. The ability to associate images allows us to learn the additional tables,
multiplication tables or arbitray Boolean logic operations. We therefore refer to this feed-forward system as a \Lambda
map (\Lambda from the Greek word *Logikos*). Like a Boolean logic gate, this computational model is stateless;
its output depends only on the current value of its inputs.





## Images as Symbols: An Associative Neurotransmitter-Field Model of the Brodmann Areas.

Douglas S Greer

### 2.1 Evolution of the Nervous System

Although the evolution of the senses and the central nervous system was a complex process that occurred over an extended time interval [27], we can attempt to understand some of the general constraints that may have influenced its development. One of these constraints was the need to evaluate the current state of the body and its immediate environment. This required the creation of internal representations that could be equated with physical quantities defined over the continuous variables of space, time, and frequency.

In the standard neural network model, a synapse is characterized mathematically by a single real-valued weight representing the effect one neuron has on another. The products of the weights times the activation values of the input neurons are summed, and a nonlinear transfer function is applied to the result [28]. This model describes electrical and chemical synapses uniformly, that is, by a single real value. Examining the difference between electrical and chemical synapses, we note that electrical synapses, which may have a weighted response proportional to the number of ion channels connecting the pre- and postsynaptic neuron, are more than ten times faster. They are also more efficient, since they do not require the metabolism of neurotransmitters, or the mechanics of chemical signaling. However, chemical synapses are found almost exclusively throughout the central nervous systems of vertebrates. This raises the question: Given a time interval of several hundred million years, and the wide range of species involved, why has nature consistently retained the cumbersome chemical synapses and not replaced them with electrical synapses?

We note that neurotransmitters, the core component of chemical synapses, are actually located outside the neuron cell walls in the extracellular space. Moreover, the chemical signaling often occurs in multiple-synapse boutons such as the one shown in Fig. 3. Within these complex synapses, which connect the axons and dendritic spines of many adjacent neurons, the density of neurotransmitter is equal to the sum of the contributions from each of the individual axons.

Another constraint during the course of evolution was the limited amount of processing power available. Solutions that required more than a very small number of neurons were not feasible. In addition, the space within the organism that could be devoted to representing physical quantities was limited, so small, compact representations were preferable.

If we leave the confines of the standard neuron model and consider the density of neurotransmitters as the state variables, we discover a number of advantages. The first is higher resolution; billions of small molecules can fit in the space occupied by a single neuron. The second is energy consumption; the concentration of neurotransmitters, like the concentration of ink on a sheet of paper, is passive and can store information indefinitely without expending energy. In contrast, action potentials require the continuous expenditure of energy in order to maintain state. Another

*Transactions on Computational Science V, LNCS 5540, pp. 38–68, 2009. M.L. Gavrilova et al. (Eds.)  Springer-Verlag*

advantage is that a very high-resolution representation can be maintained with only a few processing elements. For example, the terminal arbor of a single neuron that encodes a joint angle can support a high-resolution model describing the location of the surface of the limb in space. This collection of related concepts results in the following conjecture.

#### The Neurotransmitter Cloud Hypothesis:
When multicellular fauna first appeared, organisms began to represent quantities such as mass, force, energy and position by the chemical concentration of identifiable molecules in the extracellular space. The basic principles of operation developed during this period still govern the central nervous system today.

The basic laws of physics are based on quantities defined in space, time, and frequency, which can be internally represented by the chemical concentration of neurotransmitters in three-dimensional space.

Neurotransmitter clouds in early metazoa would have suffered from two problems: chemical diffusion of the molecules and chemical inertia due to the large amount of neurotransmitter required to fill in the extracellular space. As a result, evolutionary adaptation would have favored neural structures where the neurotransmitters remained confined to the small regions in the synaptic clefts between the pre- and postsynaptic neurons.


Axon 2 Axon 3 Axon 1 τ1 τ2 τ3 Σ Dendrite 1 Dendrite 2 τi Fig. 3.

This idealized view of multi-synapse boutons shows how the concentration of neurotransmitter perceived by multiple dendrites is the summation of that produced by three separate axon terminals. The summation occurs in the extracellular space and is separated from the intracellular summation by the nonlinear responses of the cell membranes.


In order to visualize how a computation can be performed on a neurotransmitter cloud, imagine the dendritic arbor of a neuron as a leafless tree with its branches inside of the cloud. The surface of the tree is “painted” with a shade of gray that corresponds to its sensitivity to a particular neurotransmitter. When multiplied by the actual concentration of neurotransmitter present in the extracellular space, and integrated over a region of space that contains the dendritic tree, the result is a first-order approximation of the neuron’s response. We can mathematically represent the “shade of gray” that corresponds to the sensitivity of a neuron’s dendritic arbor in physical space by a function μ(x,y,z).


### 2.2 Neurotransmitter Field Theory

The standard projection neural network calculation is based on the inner product of two vectors, a vector of input values, and a weight vector. Hilbert spaces generalize the inner product operation to continuous domains by replacing the summation of the products of the vector coefficients, with the integral of the product of two functions [29]. One of these two functions, μ(x,y,z), is used to represent the sensitivity of the dendritic arbor and is analogous to the weight vector.

Let H be the three-dimensional space representing a neurotransmitter cloud, and let h(x,y,z) be a field corresponding to the density of transmitter in the extracellular space. We conceptually model the operation of a neuron as an abstract Processing Element (PE). The dendritic arbor computation of the PE, which is analogous to the vector inner product, is defined by the integral of h and with respect to μ

    response = ∫∫∫ h(x,y,z) dμ(x,y,z)  (1)
                H

In [30] it is shown that by using Lebesgue integrals and Dirac delta functions the mathematical formulation of neurotransmitter fields (1) subsumes the functionality of the neural networks. That is, for every neural network, there exists a corresponding neurotransmitter field integral over a dendritic tree that generates an identical result.

Mathematically the neurotransmitter “clouds” are three-dimensional manifolds which we illustrate diagrammatically as rectangular blocks such as the input manifold H and the output manifold G shown in Fig. 4A.  To distinguish between the input and output spaces, we substitute the parameters (ξ,η,ζ ) for (x,y,z) in the input manifold H.

In addition to the dendritic arbor, each neuron (or astrocyte) also has an axonal tree, or terminal arbor, which releases neurotransmitter into the extracellular space. Let τ(x,y,z) denote the function that quantitatively describes the output of a neuron in terms of the spatial distribution of chemical neurotransmitter it generates.

We use the index i to enumerate the set of processing elements {PEi}. Each processing element, such as the ones shown in Fig. 4A, consists of a unique receptor measure, μ_i(ξ,η,ζ), a transmitter function τi(x,y,z) and nonlinear cell-membrane-transfer functions, χ and σ. The spatial variations are modeled using μi and τi and we assume that χ and σ are fixed functions of a single real variable, similar to the sigmoidal transfer functions used in neural networks.

The transformation from a discrete real value back to a continuous field results from scaling the output of the nonlinear transfer function σ by the transmitter
function τ_i(x,y,z) . Taking into account the cell-membrane transfer functions and summing over all of the PEs gives the complete output function g.


    g(x,y,z)  = χ (∑ σ(∫∫∫ h(ξ,η,ζ) dμ(ξ,η,ζ) )) τ_i(x,y,z))     (2)
                   i    H

The receptor measure μ_i models the shape and sensitivity the dendritic arbor in the input manifold H, while transmitter function τ_i models the signal distribution in the terminal arbor and the concomitant release of neurotransmitters into the output manifold G. The receptor measures {μi} and the transmitter functions {τ_i} perform the complementary operations of converting back and forth between fields defined on continuous manifolds and discrete real values resulting in both an integral and a summation sign in (2).


B Input Layer Hidden Layer Output Layer w1,j,i w2,k,j A H G PEi ρi, μi τi NH,G PEi Fig. 4. A computational manifold (A) transforms continuous fields while a neural network (B) transforms discrete vectors. In the neurotransmitter field model (A) the Processing Elements (PEi) represent neurons which are points in the function space NH,G. Since there are effectively two summations, one in the intracellular space and one in the extracellular space, the receptor measure μ, together with the transmitter function τ , allow a single layer of neurons to perform the equivalent computation of a two-layer neural network The nonlinear responses of the cell membranes separate the two summations.
Douglas S. Greer 2.3 Basis Functions The continuous version of a projection neural network can be extended by generalizing the notion of radial basis functions [31] to computational manifolds. For discrete neural networks, a set of pattern vectors {u }α and a radial basis function θ form the discriminate functions ( ) uuα θ − . The real-valued function ()x θ has its maximum at the origin and the properties ()  0x θ > and ()    0x θ → as x→∞ . Typically the theta functions are a Gaussian, exp(-x2/2(stddev)2, or a similar function. To construct the analogous continuous basis functions, we replace the discrete pattern vectors uα with a continuous field ρα. Each of the functions ρα(ξ,η,ζ) represents a “pattern” density defined on the input manifold. We associate a particular “target” function gα(x,y,z) in the output manifold with each input pattern ρα. Assuming that there are several PEs available for every pattern, each PEi is assigned a particular pattern which we label ρi. The equation corresponding to a basis-function neural network can be obtained by substituting θ(h(ξ,η,ζ ) – ρi(ξ,η,ζ )) for h in (2) () (, , ) (, , ) iii i H g xyz h        d           xyzσθ ρμτ⎛⎞=−⋅⎜⎟⎝⎠ ⌠⎮⌡ ∑ (3) where we have omitted the variables of integration (ξ,η,ζ ) for h, ρi, and μi. Each processing element now has an additional property ρi, which represents the pattern to which it is the most sensitive. For each PE, the integral inside (3) is maximum when h = ρi over the region of integration. This in turn maximizes the coefficient for the transmitter function τi. The sum of the transmitter functions {τi} associated with a particular input pattern ρα can then be defined to approximate the desired target function gα, thereby creating the required associations. The measures μi in (2) and (3) can identify the regions where the pattern ρi is the most sensitive. For example, we can imagine photographs of two different animals that appear very similar except for a few key features. The photographs, representing two patterns ρ1 and ρ2, are approximately equal, but the measures can be trained so that their value where the patterns are the same is small, but in the key regions where the patterns differ, they have much larger values. In this way, even though the two image patterns are almost the same, the output functions gα that result from the integrals in Equation (3) could be very different.  2.4 Computational Equivalence While models that use action potentials as state variables can form associations by using matrix operations on a large vector of neuron outputs, equation (3) shows the neurotransmitter-field state model makes it possible for a small number of neurons, even a single neuron, to establish an association between an arbitrary input pattern ρα(ξ,η,ζ ) and an arbitrary output pattern gα(x,y,z).  A continuous computational manifold and a two-layer discrete neural network are shown in Fig. 4A and 4B. The measures {μi} in the computational manifolds can Transactions on Computational Sc ience V, LNCS 5540, pp. 38–68, 2009. M.L. Gavrilova et al. (Eds.)  Springer-Verlag



--------------------------


## Neurons listen to glia cells

Research collaboration uncovers a novel mechanism of altered information processing between neurons / Major relevance for learning and processing of sensory input
12.12.2014

Scientists at Johannes Gutenberg University Mainz (JGU) have discovered a new signal pathway in the brain that plays an important role in learning and the processing of sensory input. It was already known that distinct glial cells receive information from neurons. However, it was unknown that these same glial cells also transmit information to neurons. The glia release a specific protein fragment that influences neuronal cross-talk, most likely by binding to the synaptic contacts that neurons use for communication. Disruption of this information flow from the glia results in changes in the neural network, for example during learning processes. The team composed of Dr. Dominik Sakry, Dr. Angela Neitz, Professor Jacqueline Trotter, and Professor Thomas Mittmann unravelled the underlying mechanism, from the molecular and cellular level to the network and finally the resulting behavioral consequences. Their findings constitute major progress in understanding complex pathways of signal transmission in the brain.
In mammalian brains glial cells outnumber nerve cells, but their functions are still largely unelucidated. A group of glial cells, so-called oligodendrocyte precursor cells (OPC), develop into the oligodendrocytes which ensheathe neuronal axons with a protective myelin layer thus promoting the rapid transmission of signals along the axon. Interestingly, these OPCs are present as a stable proportion – some five to eight percent of all cells in all brain regions, including adult brains. The Mainz-based researchers decided to take a closer look at these OPCs.

In 2000 it was discovered that OPCs receive signals from the neural network via synaptic contacts that they make with neurons. "We have now discovered that the precursor cells do not only receive information via the synapses, but in their turn use these to transmit signals to adjacent nerve cells. They are thus an essential component of the network," explained Professor Jacqueline Trotter from the Institute of Molecular Cell Biology at Mainz University. Classically, neurons have been considered as the major players in the brain. Over the past few years, however, increasing evidence has come to light that glial cells may play an equally important role. "Glial cells are enormously important for our brains and we have now elucidated in detail a novel important role for glia in signal transmission," explained Professor Thomas Mittmann of the Institute of Physiology of the Mainz University Medical Center.

The chain of communication starts with signals traveling from the neurons to the OPCs across the synaptic cleft via the neurotransmitter glutamate. This results in a stimulation of the activity of a specific protease, the alpha-secretase ADAM 10 in OPCs, which acts on the NG2 protein expressed by the precursor cells releasing a NG2 fragment into the extracellular space, where it influences neighboring neuronal synapses. The neurons react to this in the form of altered electrical activity. "We can use patch-clamp techniques to hear, as it were, how the cells talk to one another," said Mittmann.

"The process starts with the reception of signals coming from the neurons by the OPCs. This means that the feedback to the neurons cannot be seen as separated from the signal reception," explained Dr. Dominik Sakry, joint first author of the study, describing the cascade of events. The role of NG2 in this process became apparent when the researchers removed the protein: neuronal synaptic function is altered, modifying learning and disrupting the processing of sensory input that manifests in the form of behavioral changes in test animals.

The evidence that the communication between the two cell types in the brain is not a one-way system but a complex mechanism involving feedback loops was obtained in a collaborative project involving physiologists and molecular biologists. Participating in the project at Mainz University were the Faculties of Biology and Medicine and the Focus Program Translational Neurosciences (FTN) in the form of platform technology provided by the Mouse Behavioral Unit (MBU). The project was additionally supported by two Mainz Collaborative Research Centers (CRC 1080 and CRC-TR 128) and involved participation of the Leibniz Institute for Neurobiology in Magdeburg. Scientists from seven countries participated in the study.

## Neural Networks and Deep Learning

http://neuralnetworksanddeeplearning.com/chap6.html

Local receptive fields

Feature Maps

Pooling layers
