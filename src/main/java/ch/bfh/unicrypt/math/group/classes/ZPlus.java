package ch.bfh.unicrypt.math.group.classes;

import ch.bfh.unicrypt.math.element.classes.AdditiveAtomicElement;
import java.math.BigInteger;
import java.util.Random;

import ch.bfh.unicrypt.math.element.interfaces.Element;
import ch.bfh.unicrypt.math.group.abstracts.AbstractAdditiveAtomicCyclicGroup;
import ch.bfh.unicrypt.math.group.interfaces.Group;
import ch.bfh.unicrypt.math.utility.RandomUtil;

/**
/**
 * This class implements the additive cyclic group of (positive and negative) integers with infinite order.
 * Its identity element is 0, and there are exactly two generators, namely 1 and -1. To invert an element,
 * it is multiplied with -1.
 *
 * @see "Handbook of Applied Cryptography, Example 2.164"
 * @see <a href="http://en.wikipedia.org/wiki/Integer">http://en.wikipedia.org/wiki/Integer</a>
 *
 * @author R. Haenni
 * @author R. E. Koenig
 * @version 2.0
 */
public class ZPlus extends AbstractAdditiveAtomicCyclicGroup {

  private static final long serialVersionUID = 1L;
  private static final int RANDOM_ELEMENT_BIT_LENGTH = 1000;

  /**
   * This is the private constructor of this class. It is called by the static factory methods
   * of the static nested class Factory.
   */
  private ZPlus() {
  }

  //
  // The following protected methods override the standard implementation from
  // various super-classes
  //

  @Override
  protected AdditiveAtomicElement standardSelfApply(Element element, BigInteger amount) {
    return this.abstractGetElement(element.getValue().multiply(amount));
  }

  //
  // The following protected methods implement the abstract methods from
  // various super-classes
  //

  @Override
  protected AdditiveAtomicElement abstractGetRandomElement(final Random random) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected boolean abstractContains(final BigInteger value) {
    return true;
  }

  @Override
  protected BigInteger abstractGetOrder() {
    return Group.INFINITE_ORDER;
  }

  @Override
  protected AdditiveAtomicElement abstractGetDefaultGenerator() {
    return this.abstractGetElement(BigInteger.ONE);
  }

  @Override
  public boolean abstractIsGenerator(final Element element) {
    return element.getValue().abs().equals(BigInteger.ONE);
  }

  @Override
  protected AdditiveAtomicElement abstractGetIdentityElement() {
    return this.abstractGetElement(BigInteger.ZERO);
  }

  @Override
  protected AdditiveAtomicElement abstractApply(final Element element1, final Element element2) {
    return this.abstractGetElement(element1.getValue().add(element2.getValue()));
  }

  @Override
  protected AdditiveAtomicElement abstractInvert(final Element element) {
    return this.abstractGetElement(element.getValue().negate());
  }

  @Override
  public AdditiveAtomicElement abstractGetRandomGenerator(final Random random) {
    if (RandomUtil.createRandomBoolean(random)) {
      return this.getDefaultGenerator();
    }
    return this.invert(this.getDefaultGenerator());
  }

  //
  // STATIC FACTORY METHODS
  //

  private static ZPlus instance;

  /**
   * Returns the singleton object of this class.
   * @return The singleton object of this class
   */
  public static ZPlus getInstance() {
    if (ZPlus.instance == null) {
      ZPlus.instance = new ZPlus();
    }
    return ZPlus.instance;
  }

}