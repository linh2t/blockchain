package ch.bfh.unicrypt.math.function.abstracts;

import ch.bfh.unicrypt.math.element.Element;
import ch.bfh.unicrypt.math.function.classes.PartiallyAppliedFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.math.group.classes.ProductSet;
import ch.bfh.unicrypt.math.group.interfaces.Group;
import ch.bfh.unicrypt.math.group.interfaces.Set;
import java.util.Random;

/**
 * This abstract class contains standard implementations for most methods of
 * type {@link Function}. For most classes implementing {@link Function}, it is
 * sufficient to inherit from {@link AbstractFunction} and to implement the
 * single abstract method {@link abstractApply(Element element, Random random)}.
 *
 * @author R. Haenni
 * @author R. E. Koenig
 * @version 2.0
 */
public abstract class AbstractFunction implements Function {

  private final Set domain;
  private final Set coDomain;

  protected AbstractFunction(final Set domain, final Set coDomain) {
    this.coDomain = coDomain;
    this.domain = domain;
  }

  @Override
  public final Element apply(final Element element) {
    return this.apply(element, (Random) null);
  }

  @Override
  public final Element apply(final Element element, final Random random) {
    if (this.getDomain().contains(element)) {
      return this.abstractApply(element, random);
    }
    // This is for increased convenience for a function with a ProductSet domain of arity 1.
    if (element.isAtomic()) {
      return this.apply(new Element[]{element}, random);
    }
    throw new IllegalArgumentException();
  }

  @Override
  public final Element apply(final Element... elements) {
    return this.apply(elements, (Random) null);
  }

  @Override
  public final Element apply(final Element[] elements, final Random random) {
    if (this.getDomain().isAtomic()) {
      throw new UnsupportedOperationException();
    }
    return this.apply(((ProductSet) this.getDomain()).getElement(elements), random);
  }

  @Override
  public Set getDomain() {
    return this.domain;
  }

  @Override
  public Set getCoDomain() {
    return this.coDomain;
  }

  @Override
  public final boolean isAtomic() {
    return this.standardIsAtomic();
  }

  @Override
  public final Function partiallyApply(final Element element, final int index) {
    return PartiallyAppliedFunction.getInstance(this, element, index);
  }

  //
  // The following protected methods are standard implementations for atomic
  // functions of arity 1. The standard implementation may change in sub-classes
  // for non-atomic functions.
  //

  protected boolean standardIsAtomic() {
    return true;
  }

  //
  // The following protected abstract method must be implemented in every direct
  // sub-class
  //

  /**
   * This abstract method is the main method to implement in each sub-class of
   * {@link AbstractFunction}. The validity of the two parameters has already
   * been tested.
   *
   * @see apply(Element, Random)
   * @see Group#apply(Element[])
   * @see Element#apply(Element)
   *
   * @param element The given input element
   * @param random Either {@code null} or a given random generator
   * @return The resulting output element
   */
  protected abstract Element abstractApply(Element element, Random random);

}
