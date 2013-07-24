package ch.bfh.unicrypt.crypto.mixnet.interfaces;

import java.util.List;
import java.util.Random;

import ch.bfh.unicrypt.crypto.encryption.interfaces.RandomizedAsymmetricHomomorphicEncryptionScheme;
import ch.bfh.unicrypt.math.element.Element;
import ch.bfh.unicrypt.math.element.interfaces.PermutationElement;
import ch.bfh.unicrypt.math.group.interfaces.Group;

public interface ReEncryptionMixer extends Mixer {

  public List<Element> shuffle(List<Element> elements, List<Element> randomizations);

  public List<Element> shuffle(List<Element> elements, PermutationElement permutation, Random random);

  public List<Element> shuffle(List<Element> elements, List<Element> randomizations, Random random);

  public List<Element> shuffle(List<Element> elements, PermutationElement permutation, List<Element> randomizations);

  public RandomizedAsymmetricHomomorphicEncryptionScheme getEncryptionScheme();

  public Group getRandomizationSpace();

  public Element getPublicKey();

}