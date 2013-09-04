/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.algebra.product.abstracts;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import ch.bfh.unicrypt.math.algebra.general.abstracts.AbstractSet;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.algebra.product.interfaces.CompoundSet;
import ch.bfh.unicrypt.math.helper.IterableCompound;
import ch.bfh.unicrypt.math.utility.MathUtil;

/**
 *
 * @author rolfhaenni
 */
public abstract class AbstractCompoundSet<CS extends AbstractCompoundSet<CS, CE, S, E>, CE extends AbstractCompoundElement<CS, CE, S, E>, S extends Set, E extends Element>
        extends AbstractSet<CE> implements CompoundSet, IterableCompound<CS, S> {

  private final S[] sets;
  private final int arity;

  protected AbstractCompoundSet(S[] sets) {
    this.sets = (S[]) sets.clone();
    this.arity = sets.length;
  }

  protected AbstractCompoundSet(S set, int arity) {
    this.sets = (S[]) new Set[]{set};
    this.arity = arity;
  }

  @Override
  public final boolean contains(final int[] values) {
    return this.contains(MathUtil.intToBigIntegerArray(values));
  }

  @Override
  public final boolean contains(BigInteger... values) {
    int arity = this.getArity();
    if (values == null || values.length != arity) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < arity; i++) {
      if (!this.getAt(i).contains(values[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public final boolean contains(Element... elements) {
    int arity = this.getArity();
    if (elements == null || elements.length != arity) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < arity; i++) {
      if (!this.getAt(i).contains(elements[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public final CE getElement(final int[] values) {
    return this.getElement(MathUtil.intToBigIntegerArray(values));
  }

  @Override
  public final CE getElement(BigInteger[] values) {
    int arity = this.getArity();
    if (values == null || values.length != arity) {
      throw new IllegalArgumentException();
    }
    E[] elements = (E[]) new Element[arity];
    for (int i = 0; i < arity; i++) {
      elements[i] = (E) this.getAt(i).getElement(values[i]);
    }
    return this.abstractGetElement(elements);
  }

  @Override
  public final CE getElement(final Element[] elements) {
    int arity = this.getArity();
    if (elements == null || elements.length != arity) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < arity; i++) {
      if (!this.getAt(i).contains(elements[i])) {
        throw new IllegalArgumentException();
      }
    }
    return this.abstractGetElement(elements);
  }

  protected abstract CE abstractGetElement(final Element[] elements);

  //
  // The following protected methods override the standard implementation from
  // various super-classes
  //
  @Override
  protected BigInteger standardGetMinOrder() {
    if (this.isUniform()) {
      BigInteger minOrder = this.getFirst().getMinOrder();
      return minOrder.pow(this.getArity());
    }
    BigInteger result = BigInteger.ONE;
    for (Set set : this) {
      final BigInteger minOrder = set.getMinOrder();
      result = result.multiply(minOrder);
    }
    return result;
  }

  //
  // The following protected methods implement the abstract methods from
  // various super-classes
  //
  @Override
  protected BigInteger abstractGetOrder() {
    if (this.isNull()) {
      return BigInteger.ONE;
    }
    if (this.isUniform()) {
      BigInteger order = this.getFirst().getOrder();
      if (order.equals(Set.INFINITE_ORDER) || order.equals(Set.UNKNOWN_ORDER)) {
        return order;
      }
      return order.pow(this.getArity());
    }
    BigInteger result = BigInteger.ONE;
    for (Set set : this) {
      final BigInteger order = set.getOrder();
      if (order.equals(BigInteger.ZERO)) {
        return BigInteger.ZERO;
      }
      if (order.equals(Set.INFINITE_ORDER) || result.equals(Set.INFINITE_ORDER)) {
        result = Set.INFINITE_ORDER;
      } else {
        if (order.equals(Set.UNKNOWN_ORDER) || result.equals(Set.UNKNOWN_ORDER)) {
          result = Set.UNKNOWN_ORDER;
        } else {
          result = result.multiply(order);
        }
      }
    }
    return result;
  }

  @Override
  protected CE abstractGetElement(BigInteger value) {
    BigInteger[] values = MathUtil.elegantUnpair(value, this.getArity());
    return this.getElement(values);
  }

  @Override
  protected CE abstractGetRandomElement(Random random) {
    int arity = this.getArity();
    final Element[] randomElements = new Element[arity];
    for (int i = 0; i < arity; i++) {
      randomElements[i] = this.getAt(i).getRandomElement(random);
    }
    return this.abstractGetElement(randomElements);
  }

  @Override
  protected boolean abstractContains(BigInteger value) {
    BigInteger[] values = MathUtil.elegantUnpair(value, this.getArity());
    return this.contains(values);
  }

  @Override
  public int getArity() {
    return this.arity;
  }

  @Override
  public final boolean isNull() {
    return this.getArity() == 0;
  }

  @Override
  public final boolean isUniform() {
    return this.sets.length <= 1;
  }

  @Override
  public S getFirst() {
    return this.getAt(0);

  }

  @Override
  public S getAt(int index) {
    if (index < 0 || index >= this.getArity()) {
      throw new IndexOutOfBoundsException();
    }
    if (this.isUniform()) {
      return this.sets[0];
    }
    return this.sets[index];
  }

  @Override
  public S getAt(int... indices) {
    if (indices == null) {
      throw new IllegalArgumentException();
    }
    S set = (S) this;
    for (final int index : indices) {
      if (set.isCompound()) {
        set = ((IterableCompound<CS, S>) set).getAt(index);
      } else {
        throw new IllegalArgumentException();
      }
    }
    return set;
  }

  @Override
  public S[] getAll() {
    int arity = this.getArity();
    S[] result = (S[]) new Set[arity];
    for (int i = 0; i < this.arity; i++) {
      result[i] = this.getAt(i);
    }
    return result;
  }

  @Override
  public CS removeAt(final int index) {
    int arity = this.getArity();
    if (index < 0 || index >= arity) {
      throw new IndexOutOfBoundsException();
    }
    if (this.isUniform()) {
      return this.abstractRemoveAt(this.getFirst(), arity - 1);
    }
    final S[] remainingSets = (S[]) new Set[arity - 1];
    for (int i = 0; i < arity - 1; i++) {
      if (i < index) {
        remainingSets[i] = this.getAt(i);
      } else {
        remainingSets[i] = this.getAt(i + 1);
      }
    }
    return abstractRemoveAt(remainingSets);
  }

  protected abstract CS abstractRemoveAt(S set, int arity);

  protected abstract CS abstractRemoveAt(S[] sets);

  @Override
  public Iterator<S> iterator() {
    final IterableCompound<CS, S> compoundSet = this;
    return new Iterator<S>() {
      int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < compoundSet.getArity();
      }

      @Override
      public S next() {
        if (this.hasNext()) {
          return compoundSet.getAt(this.currentIndex++);
        }
        throw new NoSuchElementException();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
      }
    };
  }

  @Override
  protected boolean standardIsCompound() {
    return true;
  }

  @Override
  protected boolean standardEquals(Set set) {
    CS other = (CS) set;
    int arity = this.getArity();
    if (arity != other.getArity()) {
      return false;
    }
    for (int i = 0; i < arity; i++) {
      if (!this.getAt(i).equals(other.getAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected boolean standardIsCompatible(Set set) {
    return (set instanceof AbstractCompoundSet);
  }

  @Override
  protected int standardHashCode() {
    final int prime = 31;
    int result = 1;
    for (S set : this) {
      result = prime * result + set.hashCode();
    }
    result = prime * result + this.getArity();
    return result;
  }

  @Override
  protected String standardToString() {
    if (this.isNull()) {
      return "";
    }
    if (this.isUniform()) {
      return this.getFirst().toString() + "^" + this.getArity();
    }
    String result = "";
    String separator = "";
    for (S set : this) {
      result = result + separator + set.toString();
      separator = " x ";
    }
    return result;
  }

}